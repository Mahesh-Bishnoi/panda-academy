/**
 * Represents a single time block for a section.
 * Matches the backend ScheduleSlotDto.
 */
export interface ScheduleSlot {
    dayOfWeek: 'MONDAY' | 'TUESDAY' | 'WEDNESDAY' | 'THURSDAY' | 'FRIDAY';
    startTime: string; // e.g., "09:00"
    endTime: string;   // e.g., "10:00"
}

/**
 * Represents a full section in the master schedule.
 * Matches the backend SectionDto.
 */
export interface Section {
    sectionId: number;
    courseCode: string;
    courseName: string;
    teacherName: string;
    roomName: string;
    capacity: number;
    enrolledCount: number;
    slots: ScheduleSlot[];
}