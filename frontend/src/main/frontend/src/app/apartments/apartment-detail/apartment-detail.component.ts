import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastsManager } from 'ng2-toastr/ng2-toastr';

import { Apartment } from '../../models/apartment';
import { User } from '../../models/user';
import { ApartmentService } from '../../apartments/apartment.service';
import { TenantService } from '../../tenants/tenant.service';

@Component({
  selector: 'app-apartment-detail',
  templateUrl: './apartment-detail.component.html',
  styleUrls: ['./apartment-detail.component.css']
})
export class ApartmentDetailComponent implements OnInit {

  apartment: Apartment;

  tenants: User[];

  constructor(private router: Router,
              private route: ActivatedRoute,
              private apartmentService: ApartmentService,
              private tenantService: TenantService,
              private toastr: ToastsManager, 
              private vcr: ViewContainerRef) { 
    this.toastr.setRootViewContainerRef(vcr);
    this.apartment ={
      id: null,
      number: null,
      description: '',
      owner: null,
      building: null
    }
  }

  ngOnInit() {
    this.apartmentService.getApartment(+this.route.snapshot.params['id'])
        .then(apartment => {
          this.apartment = apartment;
          this.tenantService.getTenantsApartment(this.apartment.id)
              .then(tenants => this.tenants = tenants);
        });
  }

  gotoAddOwner(){
    this.router.navigate([`/apartments/${this.apartment.id}/addOwner`]);
  }

  gotoAddTenant(){
    this.router.navigate([`/apartments/${this.apartment.id}/addTenant`]);
  }

  gotoGetTenant(id: number){
    this.router.navigate([`/apartments/${this.apartment.id}/tenants/${id}`]);
  }

  gotoEditApartment(){
    this.router.navigate(['editApartment', this.apartment.id]);
  }

  deleteApartment(){
    this.apartmentService.deleteApartment(this.apartment.id)
        .then(() => {
        this.router.navigate([`/buildings/${+this.route.snapshot.params['idBuilding']}`])
      }).catch(error => {
        this.toastr.error('The apartment can not be deleted.');
      });
  }
  
  deleteOwner(){
    this.apartmentService.deleteOwner(this.apartment.id)
        .then(apartment => this.apartment = apartment)
        .catch(error => this.toastr.error('The owner can not be deleted.'));
  }
}
