import { Component, OnInit } from '@angular/core';
import { BlogService } from '../../../service/blog.service';
import { BlogCategories } from '../../../../dto/BlogCategories';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../../auth/service/auth.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-create-blog',
  standalone: false,
  templateUrl: './create-blog.component.html',
  styleUrl: './create-blog.component.scss'
})
export class CreateBlogComponent implements OnInit {
  blogForm: FormGroup | undefined;
  blogCategories: BlogCategories[] = [];
  selectedThumbnail: File | null = null;
  thumbnailError: string | null = null;
  isSubmitting = false;

  constructor(
    private blogService: BlogService,
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private toastrService: ToastrService) { }

  ngOnInit(): void {
    this.getAllBlogCategories();
    this.initForm();
  }

  initForm() {
    this.blogForm = this.fb.group({
      title: ['', [Validators.required]],
      subTitle: ['', [Validators.required]],
      category: ['', [Validators.required]],
      description: ['', [Validators.required]]
    });
  }

  getAllBlogCategories() {
    this.blogService.getAllBlogCategories().subscribe({
      next: (res) => {
        this.blogCategories = res.result;
      },
      error: (err) => console.error('Error fetching blog categories', err)
    });
  }

  onFileChange(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.thumbnailError = this.validateFileSize(file) ? null : 'File size must be less than 10 MB';
      if (!this.thumbnailError) {
        this.selectedThumbnail = file;
      }
    }
  }

  validateFileSize(file: File): boolean {
    return file.size <= 10 * 1024 * 1024;
  }

  isFormValid(): boolean {
    return this.blogForm?.valid === true && !!this.selectedThumbnail && !this.thumbnailError;
  }

  prepareFormData() {
    const formData = new FormData();
    formData.append('title', this.blogForm?.get('title')?.value);
    formData.append('subTitle', this.blogForm?.get('subTitle')?.value);
    formData.append('blogCategoryId', this.blogForm?.get('category')?.value);
    formData.append('description', this.blogForm?.get('description')?.value);

    if (this.selectedThumbnail)
      formData.append('thumbnail', this.selectedThumbnail);

    const userId = this.authService.getUserInfo()?.sub.toString();
    if (userId)
      formData.append('userId', userId);

    return formData;
  }

  onSubmit() {
    if (this.isFormValid()) {
      this.isSubmitting = true;
      const formData = this.prepareFormData();

      this.blogService.addNewBlog(formData).subscribe({
        next: () => {
          this.toastrService.success("Created new blog successfully", "Blog Notification")
          this.router.navigate(['/admin/blog']);
          this.isSubmitting = false;
        },
        error: () => {
          this.toastrService.error("Can't create new blog", "Blog Notification")
          this.isSubmitting = false;
        }
      });
    }
  }
}
