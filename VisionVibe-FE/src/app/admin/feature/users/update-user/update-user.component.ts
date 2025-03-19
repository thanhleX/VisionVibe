import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UsersService } from '../../../service/users.service';
import { Role } from '../../../../dto/Role';
import { User } from '../../../../dto/User';

@Component({
  selector: 'app-update-user',
  standalone: false,
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.scss']
})
export class UpdateUserComponent implements OnInit {
  signUpForm!: FormGroup;
  roles: Role[] = [];
  userId!: number;
  user!: User;

  constructor(
    private fb: FormBuilder,
    private usersService: UsersService,
    private router: Router,
    private route: ActivatedRoute,
    private toastrService: ToastrService
  ) {
    this.signUpForm = this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', Validators.required],
      image: [null],
      roles: this.fb.array([]),
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe(param => {
      this.userId = param['id'];
      this.loadUserData();
    });
  }

  private loadUserData(): void {
    this.usersService.getUserById(this.userId).subscribe({
      next: (res) => {
        this.user = res.result;
        this.getAllRoles();
      }
    });
  }

  private initForm(): void {
    this.signUpForm.patchValue({
      fullName: this.user.fullName,
      email: this.user.email,
      phone: this.user.phone,
    });
    this.setRolesFormArray();
    this.roles.forEach((role, index) => {
      const hasRole = this.user.roleDtos.some(userRole => userRole.id === role.id);
      this.rolesFormArray.at(index).setValue(hasRole);
    });
  }

  onSubmit(): void {
    if (this.signUpForm.valid) {
      const formData = this.createFormData();
      this.updateUser(formData);
    }
  }

  private createFormData(): FormData {
    const formData = new FormData();
    formData.append('fullName', this.signUpForm.value.fullName);
    formData.append('email', this.signUpForm.value.email);
    formData.append('phone', this.signUpForm.value.phone);
    if (this.signUpForm.value.image) {
      formData.append('image', this.signUpForm.value.image);
    }
    const selectedRoleIds = this.getSelectedRoleIds();
    selectedRoleIds.forEach(roleId => {
      formData.append('roleId', roleId.toString());
    });
    return formData;
  }

  private updateUser(formData: FormData): void {
    this.usersService.updateUserById(formData, this.userId).subscribe({
      next: () => {
        this.toastrService.success('User updated successfully', 'User Notification');
        this.router.navigate(['/admin']);
      },
      error: () => {
        this.toastrService.error("Can't update user, please check again", 'User Notification');
      }
    });
  }

  onFileSelected(event: Event): void {
    const file = (event.target as HTMLInputElement).files?.[0];
    this.signUpForm.patchValue({ image: file });
  }

  private getAllRoles(): void {
    this.usersService.getAllRoles().subscribe({
      next: (res) => {
        this.roles = res.result;
        this.setRolesFormArray();
        this.initForm();
      }
    });
  }

  private setRolesFormArray(): void {
    this.roles.forEach(() => this.rolesFormArray.push(this.fb.control(false)));
  }

  get rolesFormArray(): FormArray {
    return this.signUpForm.get('roles') as FormArray;
  }

  private getSelectedRoleIds(): number[] {
    return this.roles
      .map((role, index) => (this.rolesFormArray.at(index).value ? role.id : null))
      .filter(id => id !== null) as number[];
  }
}
