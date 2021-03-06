import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { Router } from '@angular/router';
import { FormControl, FormGroup } from '@angular/forms';

import { User } from '../../models/user';
import { Pricelist } from '../../models/pricelist';
import { GlitchType } from '../../models/glitch-type';
import { CompanyDataService } from '../../home-company/company-data.service';
import { GlitchTypeService } from '../glitch-type.service';
import { ToastsManager } from 'ng2-toastr';
import { error } from 'selenium-webdriver';

@Component({
  selector: 'app-change-type-company',
  templateUrl: './change-type-company.component.html',
  styleUrls: ['./change-type-company.component.css']
})
export class ChangeTypeCompanyComponent implements OnInit {

  company: User;
  subscription: Subscription;
  pricelist: Pricelist;
  glitchTypes: GlitchType[];
  selectedType:GlitchType;

  constructor(private router: Router, 
              private companyService: CompanyDataService, 
              private glitchTypeService: GlitchTypeService,
              private toastr: ToastsManager, 
              private vcr: ViewContainerRef) { 
    this.toastr.setRootViewContainerRef(vcr);
    this.company = {
      id: null,
      password: '',
      username: '',
      email: '',
      phoneNo: '',
      address: {
        city: '',
        id: null,
        number: '',
        street: '',
        zipCode: 0
      }
    };
    this.pricelist={
      id:null,
      dateUpdate:null,
      items: [],
      company: this.company,
      type: {
        id:null,
        type:''
      }
    }
    this.subscription = companyService.RegenerateData$
      .subscribe(() => this.getCompany());
        }

  ngOnInit() {
    this.getCompany();
  }

  getCompany() {
    this.companyService.getCompany().then(
      company => {this.company = company;
        this.companyService.getPricelist(this.company.id).then(
          pricelist=>{ this.pricelist=pricelist;
          this.glitchTypeService.getGlitchTypes().then(
              types=> {this.glitchTypes=types;
              this.selectedType=pricelist.type;
              }
          );
        }
        );
      }
    );
  }

  onSelectionChange(type){
    this.selectedType=type;
  }

  update(){
    if (this.selectedType===null)
      this.toastr.error('You dont have selected type!')
    else{
      this.pricelist.type=this.selectedType;
      this.companyService.updateGlitchType(this.company.id,this.selectedType).then(company => {
        this.companyService.announceChange();
        this.router.navigate(['/company/pricelist']);}
      ).catch(error=>  this.toastr.error('Error!'));
    }
  }


  goBack() {
    this.router.navigate(['/company/pricelist']);
  }

}
