import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BlogCategories } from '../../../../dto/BlogCategories';
import { BlogService } from '../../../service/blog.service';
import { Blogs } from '../../../../dto/Blog';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-update-blog',
  standalone: false,
  templateUrl: './update-blog.component.html',
  styleUrl: './update-blog.component.scss'
})
export class UpdateBlogComponent {
  blogId!: number;
  blog!: Blogs;
  blogForm!: FormGroup;
  blogCategories: BlogCategories[] = [];
  isSubmitting = false;

  constructor(
    private blogService: BlogService,
    private fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private toastrService: ToastrService) {
    this.blogForm = this.fb.group({
      title: ['', [Validators.required]],
      subTitle: ['', [Validators.required]],
      category: ['', [Validators.required]],
      description: ['', [Validators.required]],
      thumbnail: [null]
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe((param) => {
      this.blogId = param['id'];
      this.blogService.getBlogById(this.blogId).subscribe({
        next: (res) => {
          this.blog = res.result;
          this.initForm()
        }
      })
    })
  }

  initForm(): void {
    this.blogForm.patchValue({
      title: this.blog.title,
      subTitle: this.blog.subTitle,
      category: this.blog.blogCategoryDto.name,
      description: this.blog.description
    });
  }

  onFileSelected(event: Event): void {
    const file = (event.target as HTMLInputElement).files?.[0];
    this.blogForm.patchValue({ thumbnail: file });
  }

  onSubmit() {
    if (this.blogForm.valid) {
      this.isSubmitting = true
      const formData = new FormData();
      formData.append('title', this.blogForm.get('title')?.value);
      formData.append('subTitle', this.blogForm.get('subTitle')?.value);
      formData.append('description', this.blogForm.get('description')?.value);
      if (this.blogForm.value.thumbnail)
        formData.append('thumbnail', this.blogForm.get('thumbnail')?.value);

      this.blogService.updateBlogById(this.blogId, formData).subscribe({
        next: () => {
          this.toastrService.success('Your blog was updated successfully', 'Blog Notification')
          this.isSubmitting = false
          this.router.navigate(['/admin/blog']);
        },
        error: () => {
          this.isSubmitting = false
          this.toastrService.error(`Error updateing blog`, 'Blog Notification');
        }
      });
    }
  }
}
