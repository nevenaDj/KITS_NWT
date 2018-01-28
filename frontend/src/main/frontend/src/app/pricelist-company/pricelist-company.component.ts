import { CompanyDataService } from '../home-company/company-data.service';
import { Pricelist } from '../models/pricelist';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { User } from '../models/user'
import { Subscription } from 'rxjs/Subscription';
import { ItemInPricelist } from '../models/item-in-pricelist';

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

    constructor(private router: Router, private companyService: CompanyDataService) {
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
      this.token = localStorage.getItem('token');
      this.getCompany();
    }

    logout() {
      localStorage.removeItem('token');
      this.router.navigate(['login']);

    }

    getCompany() {
      this.companyService.getCompany().then(
        company => {this.company = company;
          this.companyService.getPricelist(this.company.id).then(
            pricelist=>{ this.pricelist=pricelist;
            console.log(JSON.stringify(pricelist));
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
        console.log(JSON.stringify(this.newItem));
        console.log("error")
        return;
      }
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

    deleteItem(id:number){
        this.companyService.deleteItem(this.company.id,id).then(company => {
                this.companyService.announceChange();
               
              });
        }

    saveUpdatedItem(){
        this.updatedItem.price=Number(this.updatedItem.price);
          console.log("updated:" +JSON.stringify(this.updatedItem));
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
      
    updateItem(item:ItemInPricelist, i:number){
        console.log("update:" +JSON.stringify(item));
          this.updatedItemIndex=i;
          this.updatedItem.id=item.id;
          this.updatedItem.nameOfType=item.nameOfType;
          this.updatedItem.price=Number(item.price);
          console.log("updatedItem:" +JSON.stringify(this.updatedItem));
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
