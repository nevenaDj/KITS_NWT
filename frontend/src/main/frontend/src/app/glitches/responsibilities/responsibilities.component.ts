import { Component, OnInit } from '@angular/core';
import { GlitchService } from '../glitch.service';
import { PagerService } from '../../services/pager.service';
import { Router } from '@angular/router';
import { Glitch } from '../../models/glitch';
import { AuthService } from '../../login/auth.service';

@Component({
  selector: 'app-responsibilities',
  templateUrl: './responsibilities.component.html',
  styleUrls: ['./responsibilities.component.css']
})
export class ResponsibilitiesComponent implements OnInit {

  glitches: Glitch[];

  glitchesCount: number;

  pager: any = {};

  constructor(private glitchService: GlitchService,
              private pagerService: PagerService,
              private router: Router,
              private authService: AuthService) { }

  ngOnInit() {
    this.glitchService.getMyResponsabilitiesCount()
        .then(count => {
          this.glitchesCount = count;
          this.setPage(1);
        });
  }

  getMyResponsibilities(page: number, size: number){
    this.glitchService.getMyResponsabilities(page, size)
        .then(glitches => this.glitches = glitches);
    console.log("glitches: "+JSON.stringify(this.glitches))
  }

  setPage(page: number){
    if (page < 1 || page > this.pager.totalPages){
      return;
    }

    this.pager = this.pagerService.getPager(this.glitchesCount, page, 15);
    this.getMyResponsibilities(this.pager.currentPage - 1, this.pager.pageSize);
  }

  gotoGetGlitch(id: number){
    console.log('authService: tenant - '+this.authService.isTenant()+', pres - '+this.authService.isPresident());
    if (this.authService.isTenant())
      this.router.navigate(['/tenant/myResponsiblities', id]);
    if (this.authService.isPresident())
      this.router.navigate(['/president/responsiblities', id]);
  }

}
