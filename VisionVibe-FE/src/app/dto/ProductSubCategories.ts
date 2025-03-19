import { ProductCategoryDto } from "./Product";

export interface ProductSubCategories {
    id: number;
    name: string;
    thumbnailUrl: string;
    thumbnailPublicId: string;
    isActive: boolean
    productCategoryDto: ProductCategoryDto;
    description: string;
}