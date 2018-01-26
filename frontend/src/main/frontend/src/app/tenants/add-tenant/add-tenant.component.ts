import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import { User } from '../../models/user';
import { TenantService } from '../../tenants/tenant.service';
import { UserService } from '../../users/user.service';


@Component({
  selector: 'app-add-tenant',
  templateUrl: './add-tenant.component.html',
  styleUrls: ['./add-tenant.component.css']
})
export class AddTenantComponent implements OnInit {

  tenant: User;
  apartmentID: number;
  username: string;
  user: User;
  complexForm: FormGroup;

  constructor(private route: ActivatedRoute,
              private location: Location,
              private tenantService: TenantService,
              private userService: UserService,
              private formBuilder: FormBuilder) {
    this.tenant = {
      id: null,
      username: '',
      password: '',
      email: '',
      phoneNo: '',
      address: null
    }
    this.apartmentID = this.route.snapshot.params['id'];
    this.user = null;

    this.complexForm = formBuilder.group({
      'username': [null, Validators.required]
    })
  }

  ngOnInit() {
  }

  save(): void{
    this.tenantService.addTenant(this.apartmentID, this.tenant)
        .then(tenant => this.location.back());
  }

  find(): void {
    this.user = null;
    this.userService.findUser(this.username)
        .then(user => this.user = user);
  }

  add(): void {
    this.tenantService.addTenant(this.apartmentID, this.user)
        .then(tenant => this.location.back());
  }

  cancel(){
    this.location.back();
  }

}
