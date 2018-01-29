import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { User } from '../../models/user';
import { Glitch } from '../../models/glitch';
import { Subscription } from 'rxjs';
import { Router, ActivatedRoute } from '@angular/router';
import { CompanyDataService } from '../../home-company/company-data.service';
import { GlitchService } from '../glitch.service';
import { Route } from '@angular/compiler/src/core';
import { ToastsManager } from 'ng2-toastr';
import { error } from 'selenium-webdriver';

@Component({
  selector: 'app-change-responsible-person',
  templateUrl: './change-responsible-person.component.html',
  styleUrls: ['./change-responsible-person.component.css']
})
export class ChangeResponsiblePersonComponent implements OnInit {

  president: User;
  subscription: Subscription;
  glitch: Glitch;
  users: User[];
  selectedUser:User;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private companyService: CompanyDataService,
              private glitchService: GlitchService,
              private toastr: ToastsManager, 
              private vcr: ViewContainerRef) { 
    this.toastr.setRootViewContainerRef(vcr);

    this.president = {
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
    this.glitch=null;
    this.subscription = companyService.RegenerateData$
      .subscribe(() => this.getPresident());
        }

  ngOnInit() {
    this.getPresident();
  }

  getPresident() {
    this.companyService.getCompany().then(
      company => {this.president = company;
        this.glitchService.getGlitch(+this.route.snapshot.params['id']).then(
          glitch=>{ this.glitch=glitch;
          this.glitchService.getUsers(glitch.id).then(
              users=> {this.users=users;
              }
          );
        }
        );
      }
    );
  }

  onSelectionChange(user){
    this.selectedUser=user;
  }

  update(){
    if ( this.selectedUser==null){
      this.toastr.error('You have to select an user!')
    }else{
      this.glitch.responsiblePerson=this.selectedUser;
      this.glitchService.updateResponsiblePerson(this.glitch.id,this.selectedUser).then(glitch => {
        this.router.navigate(['president/responsiblities']).catch(error=> this.toastr.error('Error!'));}
      );
  }
  }

  goBack() {
    this.router.navigate(['president/responsiblities']);
  }

}
