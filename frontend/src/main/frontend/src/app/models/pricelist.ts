import { ItemInPricelist } from './item-in-pricelist';
import { Bill } from './bill';
import { GlitchType } from './glitch-type';
import { User } from './user';


interface PricelistInterface{
  id?:number;
  dateUpdate:Date;
  items: ItemInPricelist[];
  company: User;
  type: GlitchType;
}

export class Pricelist {
  public id:number;
  public dateUpdate:Date;
  public items: ItemInPricelist[];
  public company: User;
  public type: GlitchType;


  constructor(pricelistCfg:PricelistInterface)
  {
    this.id = pricelistCfg.id;
    this.dateUpdate= pricelistCfg.dateUpdate;
    this.items = pricelistCfg.items;
    this.company= pricelistCfg.company;
    this.type = pricelistCfg.type;
  }
}
