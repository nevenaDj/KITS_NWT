import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Apartment } from '../models/apartment';
import { ApartmentService } from '../apartments/apartment.service';

@Component({
  selector: 'app-add-apartment',
  templateUrl: './add-apartment.component.html',
  styleUrls: ['./add-apartment.component.css']
})
export class AddApartmentComponent implements OnInit {

  apartment: Apartment;
  buildingID: number;

  constructor(private route:ActivatedRoute,
              private router: Router,
              private apartmentService: ApartmentService) { 
    this.apartment ={
      id: null,
      description: '',
      number:null,
      owner: {
        id: null,
        username: '',
        password: '',
        email: '',
        phoneNo: ''
      }
    }
    this.buildingID = this.route.snapshot.params['id'];
  }

  ngOnInit() {
    console.log(this.route.snapshot.params);
  }

  save(): void{
    this.apartmentService.addApartment(this.buildingID, this.apartment)
        .then(apartment => {
          console.log(apartment);
          this.router.navigate([`/buildings/${this.buildingID}`]);
        })

  }

}
