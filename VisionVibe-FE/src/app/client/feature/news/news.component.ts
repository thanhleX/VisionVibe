import { Component } from '@angular/core';
import { NewsService } from '../../service/news.service';
import { Blogs } from '../../../dto/Blog';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-news',
  standalone: false,
  templateUrl: './news.component.html',
  styleUrl: './news.component.scss'
})
export class NewsComponent {
  constructor(private newService: NewsService, private toastrService:ToastrService) { }
  targetPage: number | undefined | null;
  news: Blogs[] = [];
  totalPages!: number;
  currentPage: number = 1;
  pageSize: number = 2;
  pageAmount: any[] = [];

  ngOnInit(): void {
    this.getAllNews()
  }

  getAllNews() {
    this.newService.getAllBlogCategories().subscribe((res)=>{
      const newsId = res.result.find(blog => blog.name = "News")?.id;
      this.newService.getAllNews(Number(newsId),this.currentPage - 1, this.pageSize).subscribe(
        (res) => {
          this.news = res.result.content
          this.totalPages = res.result.totalPages;
          this.pageAmount = [];
          this.pageAmount = this.getPageAmount();
  
        })
    })
   
  }

  getPageAmount(): any[] {
    for (let i = 1; i <= this.totalPages; i++) {
      this.pageAmount.push(i);
    }
    return this.pageAmount;
  }

  changePage(page: number) {
    this.currentPage = page;
    this.getAllNews();
  

  }

  goToPage() {
    if (this.targetPage && this.targetPage >= 1 && this.targetPage <= this.totalPages) {
      this.currentPage = this.targetPage;
      this.changePage(this.targetPage);
    } else {
      this.toastrService.error(`can't navigate to page ${this.targetPage}`, `Navigate Notification`)
    }
  }
}
