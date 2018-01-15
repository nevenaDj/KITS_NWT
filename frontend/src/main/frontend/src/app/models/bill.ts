import { ItemInPricelist } from './item-in-pricelist';

interface BillInterface {
  id: number;
  price: number;
  date: Date;
  approved: boolean;
  items: ItemInPricelist[];
}

export class Bill {
  public id:number;
  public price: number;
  public date: Date;
  public approved: boolean;
  public items: ItemInPricelist[];
  
      
  constructor(itemCfg:BillInterface)
  { 
    this.id = itemCfg.id;
    this.date= itemCfg.date;
    this.price = itemCfg.price;
    this.approved= itemCfg.approved;
    this.items = itemCfg.items;
  }
}