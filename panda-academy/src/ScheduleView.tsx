import {useEffect, useState, Fragment} from 'react';
import { apiService } from './api/apiService';
import type {ScheduleGenerationResult, TimeSlot} from './types';

const days = ['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY'];
const timeSlots: Omit<TimeSlot, 'id' | 'dayOfWeek'>[] = [
    { startTime: '09:00:00', endTime: '10:00:00' },
    { startTime: '10:00:00', endTime: '11:00:00' },
    { startTime: '11:00:00', endTime: '12:00:00' },
    { startTime: '13:00:00', endTime: '14:00:00' },
    { startTime: '14:00:00', endTime: '15:00:00' },
    { startTime: '15:00:00', endTime: '16:00:00' },
    { startTime: '16:00:00', endTime: '17:00:00' },
];

const ScheduleView = () => {
    const [scheduleResult, setScheduleResult] = useState<ScheduleGenerationResult>({
        schedules: [],
        failedCourses: {},
        remainingTeacherAvailability: {},
        remainingClassroomAvailability: {},
        teacherDailyHours: {}
    });
    const [loading, setLoading] = useState(false);
    const [semesterId, setSemesterId] = useState(1); // Default to semester 1
    const [semesters, setSemesters] = useState<{ id: number; name: string ; year: number}[]>([]);

    // Fetch available semesters on mount
    useEffect(() => {
        const fetchSemesters = async () => {
            try {
                const semestersData = await apiService.getSemesters();
                setSemesters(semestersData);
                if (semestersData.length > 0) {
                    setSemesterId(semestersData[0].id);
                }
            } catch (error) {
                console.error('Error fetching semesters:', error);
            }
        };
        void fetchSemesters();
    }, []);

    const generateSchedule = async () => {
        setLoading(true);
        try {
            const newSchedule = await apiService.generateSchedule(semesterId);
            setScheduleResult(newSchedule);
        } catch (error) {
            console.error('Error generating schedule:', error);
        }
        setLoading(false);
    };

    const getScheduleForCell = (day: string, timeSlot: Omit<TimeSlot, 'id' | 'dayOfWeek'>) => {
        return scheduleResult.schedules.filter(item =>
            item.timeSlot.dayOfWeek === day &&
            item.timeSlot.startTime === timeSlot.startTime
        );
    };

    return (
        <div>
            <h2>Master Schedule</h2>
            <div>
                <label>
                    Semester:{' '}
                    <select
                        value={semesterId ?? ''}
                        onChange={(e) => setSemesterId(Number(e.target.value))}
                        disabled={loading || semesters.length === 0}
                    >
                        <option value="" disabled>
                            {semesters.length === 0 ? 'Loading...' : 'Select a semester'}
                        </option>
                        {semesters.map((s) => (
                            <option key={s.id} value={s.id}>
                                {s.name + ' ' + s.year || `Semester ${s.id}`}
                            </option>
                        ))}
                    </select>
                </label>
                <button onClick={generateSchedule} disabled={loading || !semesterId}>
                    {loading ? 'Generating...' : 'Generate Schedule'}
                </button>
            </div>
            <div className="schedule-grid">
                <div className="time-slot-header"></div>
                {days.map(day => <div key={day} className="day-header">{day}</div>)}

                {timeSlots.map(timeSlot => (
                    <Fragment key={`${timeSlot.startTime}-${timeSlot.endTime}`}>
                        <div id={`${timeSlot.startTime}-${timeSlot.endTime}`} key={`${timeSlot.startTime}-${timeSlot.endTime}`} className="time-slot-header">{timeSlot.startTime} - {timeSlot.endTime}</div>
                        {days.map(day => (
                            <div id={`${day}-${timeSlot.startTime}`} key={`${day}-${timeSlot.startTime}`} className="schedule-cell">
                                {getScheduleForCell(day, timeSlot).map(item => (
                                    <div key={`${item.course.id}-${item.teacher.id}-${item.classroom.id}`} className="individual-schedule">
                                        <strong>{item.course.code}-{item.course.name}</strong><br />
                                        {item.teacher.firstName} {item.teacher.lastName}<br />
                                        {item.classroom.name}
                                    </div>
                                ))}
                            </div>
                        ))}
                    </Fragment>
                ))}
            </div>
            {/* Failed Courses Section */}
            {!loading && Object.keys(scheduleResult.failedCourses).length > 0 && (
                <div className="failed-courses">
                    <h3>⚠️ Failed Courses</h3>
                    <ul>
                        {Object.entries(scheduleResult.failedCourses).map(([course, reason]) => (
                            <li key={course}>
                                <strong>{course}</strong>: {reason}
                            </li>
                        ))}
                    </ul>
                </div>
            )}

            {/* Remaining Availability Section */}
            {!loading && (Object.keys(scheduleResult.remainingClassroomAvailability).length > 0 || Object.keys(scheduleResult.remainingTeacherAvailability).length > 0) && (
                <div className="availability-section">
                    <h3>📊 Remaining Availability</h3>

                    <div className="availability-grid">
                        <div>
                            <h4>Teachers</h4>
                            <ul>
                                {Object.entries(scheduleResult.remainingTeacherAvailability).map(([teacher, slots]) => (
                                    <li key={teacher}>
                                        {teacher}: {slots} slot{slots !== 1 && 's'} left
                                    </li>
                                ))}
                            </ul>
                        </div>

                        <div>
                            <h4>Classrooms</h4>
                            <ul>
                                {Object.entries(scheduleResult.remainingClassroomAvailability).map(([room, slots]) => (
                                    <li key={room}>
                                        {room}: {slots} slot{slots !== 1 && 's'} left
                                    </li>
                                ))}
                            </ul>
                        </div>

                        <div>
                            <h4>Teacher Daily Hours</h4>
                            <ul>
                                {Object.entries(scheduleResult.teacherDailyHours).map(([teacher, hours]) => (
                                    <li key={teacher}>
                                        {teacher}: {hours} hour{hours !== 1 && 's'}
                                    </li>
                                ))}
                            </ul>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default ScheduleView;
