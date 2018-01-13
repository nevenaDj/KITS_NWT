import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { User } from '../../models/user';
import { TenantService } from '../../tenants/tenant.service';

@Component({
  selector: 'app-add-tenant',
  templateUrl: './add-tenant.component.html',
  styleUrls: ['./add-tenant.component.css']
})
export class AddTenantComponent implements OnInit {

  tenant: User;
  apartmentID: number;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private tenantService: TenantService) {
    this.tenant = {
      id: null,
      username: '',
      password: '',
      email: '',
      phoneNo: ''
    }
    this.apartmentID = this.route.snapshot.params['id'];
  }

  ngOnInit() {
  }

  save(): void{
    this.tenantService.addTenant(this.apartmentID, this.tenant)
        .then(tenant => {
          console.log(tenant);
          this.router.navigate([`/apartments/${this.apartmentID}`]);
        })

  }

}
