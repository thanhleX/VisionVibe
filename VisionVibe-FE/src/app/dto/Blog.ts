export interface Blogs {
    id: number;
    title: string;
    subTitle: string;
    createdAt: Date;
    updatedAt: Date | null;
    description: string;
    thumbnailUrl: string;
    thumbnailPublicId: string;
    isActive: boolean;
    isCarousel: boolean,
    userDto: UserDto;
    blogCategoryDto: BlogCategoryDto;
    username: string;
}

export interface UserDto {
    id: number;
    username: string;
}

export interface BlogCategoryDto {
    id: number;
    name: string;
}