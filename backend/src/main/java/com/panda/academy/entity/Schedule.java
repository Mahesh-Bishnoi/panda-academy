package com.panda.academy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "schedules",
        uniqueConstraints = {
                // Enforce teacher cannot have two classes in the same time slot (in same semester)
                @UniqueConstraint(columnNames = {"teacher_id", "time_slot_id", "semester_id"}),

                // Enforce classroom cannot host two classes in the same time slot (in same semester)
                @UniqueConstraint(columnNames = {"classroom_id", "time_slot_id", "semester_id"})
        }
)
public class Schedule {

    @EmbeddedId
    private ScheduleId id;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @MapsId("teacherId")
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne
    @MapsId("classroomId")
    @JoinColumn(name = "classroom_id", nullable = false)
    private Classroom classroom;

    @ManyToOne
    @MapsId("timeSlotId")
    @JoinColumn(name = "time_slot_id", nullable = false)
    private TimeSlot timeSlot;

    @ManyToOne
    @MapsId("semesterId")
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;
}