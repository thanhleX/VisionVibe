export interface Product {
    id: number;
    name: string;
    price: number;
    description: string;
    isActive: boolean;
    createdAt: Date;
    updatedAt: Date | null;
    thumbnailUrl: string;
    thumbnailPublicId: string;
    productSubCategoryDto: ProductSubCategoryDto;
    productCategoryDto: ProductCategoryDto;
    productDetailDtoList: ProductDetailDto[];
}

export interface ProductSubCategoryDto {
    id: number;
    name: string;
}

export interface ProductCategoryDto {
    id: number;
    name: string;
}

export interface ProductDetailDto {
    id: number;
    stock: number;
    productColorDto: ProductColorDto;
    productMaterialDto: ProductMaterialDto | null;
    productImageDtoList: ProductImageDto[];
}

export interface ProductColorDto {
    id: number;
    color: string;
}

export interface ProductMaterialDto {
    id: number;
    material: string;
}

export interface ProductImageDto {
    id: number;
    url: string;
    publicId: string;
}