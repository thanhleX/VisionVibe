export interface Notification {
    id: number;
    createdAt: Date;
    orderStatus: string;
    notificationStatus: string;
    orderId: number;
}