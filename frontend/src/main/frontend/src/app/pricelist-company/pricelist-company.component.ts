import { CompanyDataService } from '../home-company/company-data.service';
import { Pricelist } from '../models/pricelist';
import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { Router } from '@angular/router';

import { User } from '../models/user'
import { Subscription } from 'rxjs/Subscription';
import { ItemInPricelist } from '../models/item-in-pricelist';
import { ToastsManager } from 'ng2-toastr';

@Component({
  selector: 'app-pricelist-company',
  templateUrl: './pricelist-company.component.html',
  styleUrls: ['./pricelist-company.component.css']
})
export class PricelistCompanyComponent implements OnInit {

  token = '';
    company: User;
    subscription: Subscription;
    pricelist: Pricelist;
    addItem:boolean=false;
    newItem:ItemInPricelist;
    updatedItem:ItemInPricelist;
    updatedItemIndex:number=null;

    constructor(private router: Router,
                private companyService: CompanyDataService,
                private toastr: ToastsManager, 
                private vcr: ViewContainerRef) { 
      this.toastr.setRootViewContainerRef(vcr);
      this.newItem={
        id:null,
        nameOfType:'',
        price:0
      }
      this.updatedItem={
        id:null,
        nameOfType:'',
        price:0
      }
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
          }
          );
        }
      );
    }


    gotoGetBill(id: number) {
      this.router.navigate(['/company/bills', id]);
    }

    goToChangePass() {
      this.router.navigate(['/company/changePassword']);
    }

    addItemShow(){
      if (this.addItem==false)
        this.addItem=true;
      else
        this.addItem=false;
    }

    addNewItem(){
      this.newItem.price=Number(this.newItem.price);
      if ( this.newItem.price === null) {
        this.toastr.error('You dont have a price!');
      }else if ( this.newItem.price <=0) {
        this.toastr.error('The price has to be bigger than 0!');
      }else if ( this.newItem.nameOfType ==='') {
        this.toastr.error('Name of type is missing!');
      }else{
      this.companyService.addItem(this.company.id, this.newItem).then(company => {
              this.companyService.announceChange();
              this.newItem={
                id:null,
                price:0,
                nameOfType:''
              };
              this.addItem=false;
            });
          }
      }

    deleteItem(id:number){
        this.companyService.deleteItem(this.company.id,id).then(company => {
                this.companyService.announceChange();            
              }).catch(error=>this.toastr.error('Error during deleting!'));
        }

    saveUpdatedItem(){
        this.updatedItem.price=Number(this.updatedItem.price);
        if ( this.updatedItem.price === null) {
          this.toastr.error('You dont have a price!');
        }else if ( this.updatedItem.price <=0) {
          this.toastr.error('The price has to be bigger than 0!');
        }else if ( this.updatedItem.nameOfType ==='') {
          this.toastr.error('Name of type is missing!');
        }else{
          this.companyService.updateItem(this.company.id, this.updatedItem).then(company => {
                  this.companyService.announceChange();
                  this.updatedItemIndex=null;
                  this.updatedItem={
                      id:null,
                        nameOfType:'',
                        price:0
                      }
                 
                });
              }
          }
      
    updateItem(item:ItemInPricelist, i:number){
          this.updatedItemIndex=i;
          this.updatedItem.id=item.id;
          this.updatedItem.nameOfType=item.nameOfType;
          this.updatedItem.price=Number(item.price);
          
          }
  
    cancel(){
        this.updatedItemIndex=null;
        this.updatedItem={
            id:null,
              nameOfType:'',
              price:0
            }
            }
    
    goToChangeType() {
      this.router.navigate(['/company/pricelist/changeType']);
    }
        
    
}
