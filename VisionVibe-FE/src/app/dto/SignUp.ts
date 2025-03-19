export interface SignUp {
    username: string;
    password: string;
    rePassword: string;
    fullName: string;
    email: string;
    phone: string;
    image: File | null;
    roleId: number[];
}