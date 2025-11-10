package com.panda.academy.service.impl;

import com.panda.academy.dto.ScheduleDto;
import com.panda.academy.dto.ScheduleGenerationResultDto;
import com.panda.academy.entity.*;
import com.panda.academy.mapper.ScheduleMapper;
import com.panda.academy.repository.*;
import com.panda.academy.service.ScheduleService;
import com.panda.academy.utility.SchedulingReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private static final int MAX_HOURS_PER_DAY = 4;
    private static final int STUDENTS_PER_SECTION = 10;
    private static final double STUDENT_MULTIPLIER = 1; // Assuming 100% pass rate

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final ClassroomRepository classroomRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final ScheduleRepository scheduleRepository;
    private final SemesterRepository semesterRepository;
    private final StudentRepository studentRepository;
    private final ScheduleMapper scheduleMapper;
    private final SchedulingReport report;

    @Override
    public ScheduleGenerationResultDto generateSchedule(Long semesterId) {
        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new RuntimeException("Semester not found"));

        clearExistingSchedules(semester);

        List<Schedule> generatedSchedules = scheduleCourseSections(semester);

        scheduleRepository.saveAll(generatedSchedules);
        List<ScheduleDto> scheduleDtoList = generatedSchedules.stream()
                .map(scheduleMapper::toDto)
                .toList();

        return ScheduleGenerationResultDto.builder()
                .schedules(scheduleDtoList)
                .failedCourses(report.getFailedCourses())
                .remainingTeacherAvailability(report.getRemainingTeacherSlots())
                .remainingClassroomAvailability(report.getRemainingClassroomSlots())
                .teacherDailyHours(report.getTeacherDailyHours())
                .build();
    }

    private void clearExistingSchedules(Semester semester) {
        List<Schedule> existingSchedules = scheduleRepository.findBySemesterId(semester.getId());
        if (!existingSchedules.isEmpty()) {
            scheduleRepository.deleteAll(existingSchedules);
            log.info("Cleared {} existing schedules for semester {}", existingSchedules.size(), semester.getId());
        }
    }

    private List<Schedule> scheduleCourseSections(Semester semester) {

        List<Course> courses = courseRepository.findAllBySemesterOrder(semester.getOrderInYear());
        List<Teacher> teachers = teacherRepository.findAll();
        List<Classroom> classrooms = classroomRepository.findAll();
        List<TimeSlot> timeSlots = timeSlotRepository.findAll();

        Map<Teacher, Set<TimeSlot>> teacherAvailability = initializeTeacherAvailability(teachers, timeSlots);
        Map<Classroom, Set<TimeSlot>> classroomAvailability = initializeClassroomAvailability(classrooms, timeSlots);
        Map<Teacher, Map<DayOfWeek, Integer>> teacherDailyHours = initializeTeacherDailyHours(teachers);

        int sectionsPerCourse = calculateSectionsPerCourse();

        List<Schedule> schedules = new ArrayList<>();

        for (Course course : courses) {
            List<Teacher> suitableTeachers = teachers.stream()
                    .filter(t -> t.getSpecialization().equals(course.getSpecialization()))
                    .toList();

            List<Classroom> suitableClassrooms = classrooms.stream()
                    .filter(c -> c.getRoomType().equals(course.getSpecialization().getRoomType()))
                    .toList();

            for (int sectionIndex = 0; sectionIndex < sectionsPerCourse; sectionIndex++) {
                boolean scheduled = tryScheduleSection(
                        semester, course, suitableTeachers, suitableClassrooms,
                        teacherAvailability, classroomAvailability,
                        teacherDailyHours, schedules
                );

                if (!scheduled) {
                    report.recordFailure(course, "Unable to find matching time slots (section " + sectionIndex + ")");
                    log.warn("Unable to schedule section {} for course {}", sectionIndex, course.getName());
                }
            }
        }
        // Record remaining availability
        report.recordRemainingAvailability(teacherAvailability, classroomAvailability, teacherDailyHours);
        report.logSummary();

        return schedules;
    }

    private int calculateSectionsPerCourse() {
        long totalStudents = studentRepository.count();
        long studentsPerYear = Math.round((totalStudents / 4.0) * STUDENT_MULTIPLIER);
        return (int) Math.ceil((double) studentsPerYear / STUDENTS_PER_SECTION);
    }

    private Map<Teacher, Set<TimeSlot>> initializeTeacherAvailability(List<Teacher> teachers, List<TimeSlot> timeSlots) {
        Map<Teacher, Set<TimeSlot>> availability = new HashMap<>();
        for (Teacher teacher : teachers) {
            availability.put(teacher, new HashSet<>(timeSlots));
        }
        return availability;
    }

    private Map<Classroom, Set<TimeSlot>> initializeClassroomAvailability(List<Classroom> classrooms, List<TimeSlot> timeSlots) {
        Map<Classroom, Set<TimeSlot>> availability = new HashMap<>();
        for (Classroom classroom : classrooms) {
            availability.put(classroom, new HashSet<>(timeSlots));
        }
        return availability;
    }

    private Map<Teacher, Map<DayOfWeek, Integer>> initializeTeacherDailyHours(List<Teacher> teachers) {
        Map<Teacher, Map<DayOfWeek, Integer>> dailyHours = new HashMap<>();
        for (Teacher teacher : teachers) {
            Map<DayOfWeek, Integer> dayHours = new EnumMap<>(DayOfWeek.class);
            for (DayOfWeek day : DayOfWeek.values()) {
                dayHours.put(day, 0);
            }
            dailyHours.put(teacher, dayHours);
        }
        return dailyHours;
    }

    private boolean tryScheduleSection(
            Semester semester,
            Course course,
            List<Teacher> teachers,
            List<Classroom> classrooms,
            Map<Teacher, Set<TimeSlot>> teacherAvailability,
            Map<Classroom, Set<TimeSlot>> classroomAvailability,
            Map<Teacher, Map<DayOfWeek, Integer>> teacherDailyHours,
            List<Schedule> schedules
    ) {
        for (Teacher teacher : teachers) {
            for (Classroom classroom : classrooms) {
                Set<TimeSlot> teacherSlots = teacherAvailability.get(teacher);
                Set<TimeSlot> classroomSlots = classroomAvailability.get(classroom);

                if (teacherSlots.isEmpty()) {
                    log.debug("Teacher {} has no available slots left", teacher.getEmail());
                    continue;
                }
                if (classroomSlots.isEmpty()) {
                    log.debug("Classroom {} has no available slots left", classroom.getName());
                    continue;
                }

                List<TimeSlot> commonSlots = new ArrayList<>(teacherSlots);
                commonSlots.retainAll(classroomSlots);

                if (commonSlots.size() < course.getHoursPerWeek()) continue;

                List<TimeSlot> selectedSlots = findContiguousTimeSlots(commonSlots, course.getHoursPerWeek());
                if (selectedSlots.isEmpty()) continue;

                Map<DayOfWeek,Integer> slotSum = new EnumMap<>(DayOfWeek.class);
                selectedSlots.forEach(
                        s -> slotSum.put(s.getDayOfWeek(),slotSum.getOrDefault(s.getDayOfWeek(),0) + 1)
                );
                boolean canSchedule = selectedSlots.stream().allMatch(slot ->
                        teacherDailyHours.get(teacher).get(slot.getDayOfWeek()) + slotSum.get(slot.getDayOfWeek()) <= MAX_HOURS_PER_DAY
                );

                if (!canSchedule) {
                    log.debug("Teacher {} exceeds daily hour limit for course {}", teacher.getEmail(), course.getName());
                    continue;
                }

                createScheduleEntries(semester, course, teacher, classroom, selectedSlots,
                        teacherAvailability, classroomAvailability, teacherDailyHours, schedules);

                return true;
            }
        }
        return false;
    }

    private void createScheduleEntries(
            Semester semester,
            Course course,
            Teacher teacher,
            Classroom classroom,
            List<TimeSlot> timeSlots,
            Map<Teacher, Set<TimeSlot>> teacherAvailability,
            Map<Classroom, Set<TimeSlot>> classroomAvailability,
            Map<Teacher, Map<DayOfWeek, Integer>> teacherDailyHours,
            List<Schedule> schedules
    ) {
        for (TimeSlot slot : timeSlots) {
            Schedule schedule = Schedule.builder()
                    .id(ScheduleId.builder()
                            .classroomId(classroom.getId())
                            .courseId(course.getId())
                            .timeSlotId(slot.getId())
                            .teacherId(teacher.getId())
                            .semesterId(semester.getId())
                            .build())
                    .course(course)
                    .teacher(teacher)
                    .classroom(classroom)
                    .semester(semester)
                    .timeSlot(slot)
                    .build();

            schedules.add(schedule);

            teacherAvailability.get(teacher).remove(slot);
            classroomAvailability.get(classroom).remove(slot);
            teacherDailyHours.get(teacher).merge(slot.getDayOfWeek(), 1, Integer::sum);
        }
    }

    private List<TimeSlot> findContiguousTimeSlots(List<TimeSlot> availableTimeSlots, int requiredHours) {
        if (availableTimeSlots.size() < requiredHours) {
            return Collections.emptyList();
        }

        availableTimeSlots.sort(Comparator
                .comparing(TimeSlot::getDayOfWeek)
                .thenComparing(TimeSlot::getStartTime)
        );

        int step = Math.max(1, availableTimeSlots.size() / requiredHours);
        List<TimeSlot> selected = new ArrayList<>();

        for (int i = 0; i < availableTimeSlots.size() && selected.size() < requiredHours; i += step) {
            selected.add(availableTimeSlots.get(i));
        }

        return selected;
    }
}
