import axios from 'axios';
import type {Section} from '../types';

const api = axios.create({
    baseURL: '/api',
});

// === Challenge 1: Admin API ===
export const generateSchedule = (semesterId: number) => {
    return api.post(`/admin/schedule/generate?semesterId=${semesterId}`);
};

export const getMasterSchedule = (semesterId: number) => {
    return api.get<Section[]>(`/admin/schedule?semesterId=${semesterId}`);
};