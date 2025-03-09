// Sample data for demonstration
const weeklyData = {
    labels: ['Thứ 2', 'Thứ 3', 'Thứ 4', 'Thứ 5', 'Thứ 6', 'Thứ 7', 'Chủ nhật'],
    datasets: [{
        label: 'Doanh thu (VNĐ)',
        data: [1200000, 1900000, 1500000, 2100000, 2800000, 3200000, 2500000],
        backgroundColor: 'rgba(54, 162, 235, 0.2)',
        borderColor: 'rgba(54, 162, 235, 1)',
        borderWidth: 2,
        tension: 0.4
    }]
};

const monthlyData = {
    labels: ['T1', 'T2', 'T3', 'T4', 'T5', 'T6', 'T7', 'T8', 'T9', 'T10', 'T11', 'T12'],
    datasets: [{
        label: 'Doanh thu (VNĐ)',
        data: [15000000, 18000000, 22000000, 25000000, 28000000, 32000000, 35000000, 38000000, 42000000, 45000000, 48000000, 52000000],
        backgroundColor: 'rgba(54, 162, 235, 0.2)',
        borderColor: 'rgba(54, 162, 235, 1)',
        borderWidth: 2,
        tension: 0.4
    }]
};

// Chart configuration
const config = {
    type: 'line',
    options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
            title: {
                display: true,
                text: 'Biểu đồ doanh thu',
                font: {
                    size: 16
                }
            },
            legend: {
                position: 'bottom'
            }
        },
        scales: {
            y: {
                beginAtZero: true,
                ticks: {
                    callback: function(value) {
                        return new Intl.NumberFormat('vi-VN', {
                            style: 'currency',
                            currency: 'VND'
                        }).format(value);
                    }
                }
            }
        }
    }
};

// Initialize chart
let revenueChart;

function initChart(data) {
    const ctx = document.getElementById('revenueChart').getContext('2d');
    
    if (revenueChart) {
        revenueChart.destroy();
    }

    config.data = data;
    revenueChart = new Chart(ctx, config);
}

// Handle time range changes
document.getElementById('timeRange').addEventListener('change', function(e) {
    const selectedRange = e.target.value;
    const data = selectedRange === 'week' ? weeklyData : monthlyData;
    initChart(data);
});

// Initialize with weekly data by default
document.addEventListener('DOMContentLoaded', function() {
    initChart(weeklyData);
}); 