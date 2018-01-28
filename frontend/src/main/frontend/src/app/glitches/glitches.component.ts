import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Glitch } from '../models/glitch';
import { GlitchService } from './glitch.service';
import { PagerService } from '../services/pager.service';
import { ApartmentService } from '../apartments/apartment.service';
import { Apartment } from '../models/apartment';


@Component({
  selector: 'app-glitches',
  templateUrl: './glitches.component.html',
  styleUrls: ['./glitches.component.css']
})
export class GlitchesComponent implements OnInit {
  glitches: Glitch[];

  glitchesCount: number;

  pager: any = {};

  apartment: Apartment;

  constructor(private glitchService: GlitchService,
              private apartmentService: ApartmentService,
              private pagerService: PagerService,
              private router: Router) { }

  ngOnInit() {
    this.apartment = this.apartmentService.getMyApartment();
    this.glitchService.getGlitchesCount(this.apartment.id)
        .then(count => {
          this.glitchesCount = count;
          this.setPage(1);
        });
  }

  getGlitches(page: number, size: number){
    this.glitchService.getGlitches(this.apartment.id, page, size)
        .then(glitches => this.glitches = glitches);
  }

  setPage(page: number){
    if (page < 1 || page > this.pager.totalPages){
      return;
    }

    this.pager = this.pagerService.getPager(this.glitchesCount, page, 15);
    this.getGlitches(this.pager.currentPage - 1, this.pager.pageSize);
  }

  gotoAddGlitch(){
    this.router.navigate(['/tenant/addGlitch']);
  }

  gotoGetGlitch(id: number){
    this.router.navigate(['/tenant/glitches', id]);
  }

}
