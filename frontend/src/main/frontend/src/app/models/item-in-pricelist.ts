interface ItemInPricelistInterface{
  id:number;
  nameOfType:string;
  price:number;
}

export class ItemInPricelist {
  public id:number;
  public nameOfType:string;
  public price:number;
  
      
  constructor(itemCfg:ItemInPricelistInterface)
  { 
    this.id = itemCfg.id;
    this.nameOfType= itemCfg.nameOfType;
    this.price = itemCfg.price;
  }
}