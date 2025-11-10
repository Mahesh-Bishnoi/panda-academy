export interface Course {
    id: number;
    code: string;
    name: string;
    credits: number;
    hoursPerWeek: number;
}

export interface Teacher {
    id: number;
    firstName: string;
    lastName: string;
}

export interface Classroom {
    id: number;
    name: string;
}

export interface TimeSlot {
    id: number;
    dayOfWeek: 'MONDAY' | 'TUESDAY' | 'WEDNESDAY' | 'THURSDAY' | 'FRIDAY';
    startTime: string; // e.g., "09:00:00"
    endTime: string;   // e.g., "10:00:00"
}

export interface Semester {
    id: number;
    name: string;
    year: number;
}

export interface Schedule {
    id: number;
    course: Course;
    teacher: Teacher;
    classroom: Classroom;
    timeSlot: TimeSlot;
    semester: Semester;
}