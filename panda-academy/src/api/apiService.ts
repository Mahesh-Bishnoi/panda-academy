// src/services/apiService.ts
import axios, {type AxiosInstance } from 'axios';
import type { ScheduleGenerationResult, Semester } from '../types';

/**
 * Generic API client
 */
class ApiService {
    private readonly client: AxiosInstance;

    constructor(baseURL = '/api') {
        this.client = axios.create({ baseURL });
    }

    // ---------- Generic Methods ----------
    async get<T>(url: string): Promise<T> {
        try {
            const response = await this.client.get<T>(url);
            return response.data;
        } catch (error) {
            this.handleError(error);
            throw error;
        }
    }

    async post<T>(url: string, body?: any): Promise<T> {
        try {
            const response = await this.client.post<T>(url, body);
            return response.data;
        } catch (error) {
            this.handleError(error);
            throw error;
        }
    }

    // ---------- Domain-Specific Methods ----------
    getSemesters = (): Promise<Semester[]> => this.get<Semester[]>('/semesters');

    generateSchedule = (semesterId: number): Promise<ScheduleGenerationResult> =>
        this.post<ScheduleGenerationResult>(`/schedules/generate/${semesterId}`);

    // ---------- Helpers ----------
    private handleError(error: any): void {
        console.error('API error:', error?.response?.data || error.message);
    }
}

// Singleton instance export
export const apiService = new ApiService();
