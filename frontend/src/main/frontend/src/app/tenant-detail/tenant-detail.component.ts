import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { User } from '../models/user';
import { TenantService } from '../tenants/tenant.service';

@Component({
  selector: 'app-tenant-detail',
  templateUrl: './tenant-detail.component.html',
  styleUrls: ['./tenant-detail.component.css']
})
export class TenantDetailComponent implements OnInit {
  tenant: User;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private location: Location,
              private tenantService: TenantService) {
    this.tenant = {
      id: null,
      username: '',
      password: '',
      email: '',
      phoneNo: '',
      address:{
        city:'',
        id:null,
        number: '',
        street: '',
        zipCode: 0
      }
    }
  }

  ngOnInit() {
    this.tenantService.getTenant(+this.route.snapshot.params['id'])
        .then(tenant => this.tenant = tenant);
    
  }

  deleteTenant(){
    this.tenantService.deleteTenant(this.tenant.id, +this.route.snapshot.params['idApartment'])
        .then(() => this.location.back());

  }

}
