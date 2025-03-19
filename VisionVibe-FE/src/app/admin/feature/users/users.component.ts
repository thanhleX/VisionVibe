import { Component, OnInit } from '@angular/core';
import { UsersService } from '../../service/users.service';
import { Router } from '@angular/router';
import { User } from '../../../dto/User';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-users',
  standalone: false,
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {
  users: User[] = [];

  constructor(
    private usersService: UsersService,
    private router: Router,
    private toastrService: ToastrService
  ) { }

  ngOnInit(): void {
    this.getAllUsers();
  }

  private getAllUsers(): void {
    this.usersService.getAllUser().subscribe({
      next: (res) => {
        this.users = res.result;
      }
    });
  }

  deleteUser(id: number): void {
    this.usersService.deleteUserById(id).subscribe({
      next: () => {
        this.toastrService.success('User deleted successfully', 'User Notification');
        this.getAllUsers();
      },
      error: () => {
        this.toastrService.error("Can't delete user, please try again", 'User Notification');
      }
    });
  }

  checkScope(roles: string[]): boolean {
    // Implement role-check logic here, for example:
    const currentUserRoles = ['ADMIN', 'SALE']; // Replace with actual roles from the current session
    return roles.some(role => currentUserRoles.includes(role));
  }
}
