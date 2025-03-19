import { Role } from "./Role";

export interface User {
    id: number;
    username: string;
    fullName: string;
    email: string;
    phone: string;
    isActive: boolean;
    thumbnailUrl?: string | null;
    thumbnailPublicId?: string | null;
    roleDtos: Role[];
}