export interface OrderForm {
    customerName: string;
    email: string;
    phoneNumber: string;
    address: string;
    note: string;
    paymentMethodId: number;
    createNewOrderDetailDtoList: createNewOrderDetailDtoList[];
}

export interface createNewOrderDetailDtoList {
    productDetailId: number;
    amount: number;
}