import { Component } from '@angular/core';
import { FooterService } from '../../service/footer.service';
import { BlogCategories } from '../../../dto/BlogCategories';
import { Blogs } from '../../../dto/Blog';

@Component({
  selector: 'app-footer',
  standalone: false,
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.scss'
})
export class FooterComponent {
  blogCategories: BlogCategories[] = [];
  aboutUs: Blogs[] = [];
  support: Blogs[] = [];
  legal: Blogs[] = [];
  currentPage: number = 0;
  pageSize: number = 10;

  constructor(private footerService: FooterService) { }

  ngOnInit(): void {
    this.getData()
  }

  getData() {
    this.footerService.getAllBlogCategories().subscribe((res) => {
      this.blogCategories = res.result;
      const aboutUsId = this.getCategoryId("About Us");
      const supportId = this.getCategoryId("Support");
      const legalId = this.getCategoryId("Legal");
      if (aboutUsId) {
        this.footerService.getAllBlog(aboutUsId, this.currentPage, this.pageSize).subscribe((res) => {
          this.aboutUs = res.result.content;
        })
      }
      if (supportId) {
        this.footerService.getAllBlog(supportId, this.currentPage, this.pageSize).subscribe((res) => {
          this.support = res.result.content;
        })
      }
      if (legalId) {
        this.footerService.getAllBlog(legalId, this.currentPage, this.pageSize).subscribe((res) => {
          this.legal = res.result.content;
        })
      }
    })
  }

  private getCategoryId(categoryName: string): number | undefined {
    return this.blogCategories.find(item => item.name === categoryName)?.id;
  }
}


