import { Component, OnInit } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { DashboardService } from '../../service/dashboard.service';
import { Order } from '../../../dto/Order';

Chart.register(...registerables);
@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {
  orders: Order[] = [];
  chartData: any;
  chartOptions: any;
  labels: Date[] = [];

  constructor(private dashboardService: DashboardService) { }

  ngOnInit(): void {
    this.getAllOrderInMonth();
  }

  getAllOrderInMonth() {
    this.dashboardService.findAllOrdersInMonth().subscribe({
      next: (res) => {
        this.orders = res.result;
        const totalPriceByDay = this.getTotalPriceByDay(this.orders, this.getLabels());
        this.setChart(totalPriceByDay)
      }, error: (err) => {
        console.log(err)
      }
    })
  }

  setChart(totalPriceByDay: { date: string, totalAmount: number }[]) {
    this.chartData = {
      labels: totalPriceByDay.map(totalPriceByDay => totalPriceByDay.date),
      datasets: [
        {
          label: 'Sales',
          data: totalPriceByDay.map(totalPriceByDay => totalPriceByDay.totalAmount),
          backgroundColor: 'rgba(75, 192, 192, 0.2)',
          borderColor: 'rgba(75, 192, 192, 1)',
          borderWidth: 1,
        },
      ],
    };

    this.chartOptions = {
      responsive: true,
      plugins: {
        legend: {
          display: true,
          position: 'top' as const,
        },
      },
    };
  }

  getTotalPriceByDay(orders: Order[], labels: string[]): { date: string, totalAmount: number }[] {
    const revenueByDate: Map<string, number> = new Map();
    orders.forEach(order => {
      const orderCreatedAt: Date = new Date(order.createdAt);
      const dateKey = orderCreatedAt.toLocaleDateString('en-CA', { month: '2-digit', day: '2-digit' });

      const totalAmount = order.orderDetailResponseList.reduce((sum, detail) => {
        return sum + (detail.productDetailResponse.productDto.price * detail.amount);
      }, 0);
      revenueByDate.set(dateKey, (revenueByDate.get(dateKey) || 0) + totalAmount);
    });

    const result: { date: string, totalAmount: number }[] = labels.map(date => ({
      date,
      totalAmount: revenueByDate.get(date) || 0,
    }));
    return result;
  }
  getLabels(): string[] {
    const dateArr: string[] = [];
    const today = new Date();
    const year = today.getFullYear();
    const month = today.getMonth();

    const startDate = new Date(year, month, 1);
    let currentDate = startDate;

    while (currentDate <= today) {
      const dateString = currentDate.toLocaleDateString('en-CA', { month: '2-digit', day: '2-digit' });

      dateArr.push(dateString);
      currentDate.setDate(currentDate.getDate() + 1);
    }
    return dateArr;
  }
}