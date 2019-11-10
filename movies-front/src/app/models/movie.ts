import { Director } from './director';

export interface Movie {
    id: number;
    title: string;
    directors: Director[];
    releaseDate: Date;
    type: string;
}