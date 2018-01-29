import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { Router, ActivatedRoute } from '@angular/router';
import { forEach } from '@angular/router/src/utils/collection';
import { User } from '../../models/user';
import { Glitch } from '../../models/glitch';
import { Pricelist } from '../../models/pricelist';
import { ItemInBill } from '../../models/item-in-bill';
import { CompanyDataService } from '../../home-company/company-data.service';
import { GlitchDataService } from '../../glitches/glitch-details-company/glitch-data.service';
import { Bill } from '../../models/bill';
import { ItemInPricelist } from '../../models/item-in-pricelist';
import { ToastsManager } from 'ng2-toastr';

@Component({
  selector: 'app-send-bill',
  templateUrl: './send-bill.component.html',
  styleUrls: ['./send-bill.component.css']
})
export class SendBillComponent implements OnInit {

  public selectedMoment = new Date();
  public min = new Date();
  
  price:number=0;
  token = '';
  company: User;
  subscription: Subscription;
  glitch: Glitch;
  pricelist: Pricelist;
  items: ItemInBill[];

  constructor(private router: Router,
              private route:ActivatedRoute,
              private companyService: CompanyDataService,
              private glitchService: GlitchDataService,
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
    this.items=[];
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
    this.companyService.getGlitch(+this.route.snapshot.params['id'])
        .then(glitch => {
          console.log("id:"+JSON.stringify(this.route.snapshot.params['id']));
          console.log("glitch"+JSON.stringify(glitch));
          this.glitch = glitch;
          this.companyService.getPricelist(this.company.id).then(
            pricelist=>{ 
              this.pricelist=pricelist;
              console.log("pricelist: "+JSON.stringify(pricelist));
              
              for (let item of pricelist.items) {
                console.log("1: "+JSON.stringify(item));
                var itemInBill: ItemInBill={
                  nameOfItem:item.nameOfType,
                  piece:0,
                  price:item.price,
                  id:null
                }
                console.log("1-item: "+JSON.stringify(itemInBill));
                this.items.push(itemInBill);
              }
              console.log("items: "+JSON.stringify(this.items));
          }
          );
        }
      );
  }

  getCompany() {
;    this.companyService.getCompany().then(
      company => {this.company = company;
      }
    );
  }

  changePrice(i:ItemInBill){
    if (i.piece<0){
      this.toastr.error('Item cant be negative.');
      i.piece=0;
    } else {
      this.price=0;
      for (let item of this.items) {
        if (item.piece!=0){
          this.price+=item.price*item.piece;
        }
      }
    }
  }



  goBack() {
    this.router.navigate(['/company/pricelist']);
  }


  sendBill(){
    if (this.price<=0){
      this.toastr.error('You dont have item.');
    }else{
    let bill:Bill={
      items:[],
      approved:false,
      price:this.price,
      id:null,
      date:new Date()
    }
    for (let item of this.items){
      if (item.piece!=0){
        bill.items.push(item);
      }
    }
    this.glitchService.addBill(bill, this.glitch).then(
      bill=>{
        this.router.navigate(['/company/bills']);
      }
    );
  }}
}
