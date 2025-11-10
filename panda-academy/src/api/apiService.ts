import type {Schedule, Semester} from '../types';
import axios from 'axios';

const apiClient = axios.create({
    baseURL: '/api',
});

const getSemesters = async (): Promise<Semester[]> => {
    const response = await apiClient.get<Semester[]>('/semesters');
    return response.data;
}

const generateSchedule = async (semesterId: number): Promise<Schedule[]> => {
    const response = await apiClient.post(`/schedules/generate/${semesterId}`);
    return response.data;
};

export const apiService = {
    getSemesters,
    generateSchedule,
};