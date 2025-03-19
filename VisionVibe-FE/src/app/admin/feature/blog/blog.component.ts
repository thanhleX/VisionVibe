import { Component, OnInit } from '@angular/core';
import { BlogService } from '../../service/blog.service';
import { Blogs } from '../../../dto/Blog';
import { ToastrService } from 'ngx-toastr';
import { JwtPayloadDto } from '../../../dto/JwtPayloadDto';
import { AuthService } from '../../../auth/service/auth.service';

@Component({
  selector: 'app-blog',
  standalone: false,
  templateUrl: './blog.component.html',
  styleUrl: './blog.component.scss'
})
export class BlogComponent implements OnInit {
  userInfo: JwtPayloadDto | undefined;
  currentPage: number = 1;
  pageSize: number = 9;
  allBlogs: Blogs[] | undefined;
  pageAmount: number[] = [];
  totalPages: number | undefined;
  targetPage: number | undefined;

  constructor(
    private blogService: BlogService,
    private toastrService: ToastrService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.getAllBlogs();
    this.getUserInfo();
  }

  getAllBlogs() {
    this.blogService.getAllBlogs(this.currentPage - 1, this.pageSize).subscribe({
      next: (res) => {
        this.allBlogs = res.result.content;
        this.totalPages = res.result.totalPages;
        this.pageAmount = this.getPageAmount();
      },
      error: () => {
        this.toastrService.error('Failed to load blogs', 'Blog Notification');
      }
    });
  }

  getUserInfo() {
    this.userInfo = this.authService.getUserInfo();
  }

  checkScope(roles: string[]): boolean {
    return roles.some(role => this.userInfo?.scope.includes(role));
  }

  deleteBlogById(id: number) {
    this.blogService.deleteBlogById(id).subscribe({
      next: () => {
        this.toastrService.success('Blog deleted successfully', 'Blog Notification');
        this.getAllBlogs();
      },
      error: () => {
        this.toastrService.error("Can't delete blog. Please try again.", 'Blog Notification');
      }
    });
  }

  getPageAmount(): number[] {
    const pages = [];
    if (this.totalPages) {
      for (let i = 1; i <= this.totalPages; i++) {
        pages.push(i);
      }
    }
    return pages;
  }

  changePage(page: number) {
    this.currentPage = page;
    this.getAllBlogs();
  }

  goToPage() {
    if (this.totalPages) {
      if (this.targetPage && this.targetPage >= 1 && this.targetPage <= this.totalPages) {
        this.changePage(this.targetPage);
        this.targetPage = undefined;
      } else {
        this.toastrService.error("Invalid page number. Please enter a valid page.", 'Navigate Notification');
      }
    }
  }

  setCarousel(id: number) {
    this.blogService.setCarousel(id).subscribe({
      next: () => {
        this.toastrService.success('Carousel set successfully', 'Carousel Notification');
      },
      error: () => {
        this.toastrService.error("Can't set carousel. Please try again.", 'Carousel Notification');
      }
    });
  }
}
