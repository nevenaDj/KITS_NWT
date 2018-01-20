interface ItemInBillInterface{
    id:number;
    nameOfItem:string;
    price:number;
    piece:number;
  }
  
  export class ItemInBill {
    public id:number;
    public nameOfItem:string;
    public price:number;
    public piece:number;
    
        
    constructor(itemCfg:ItemInBillInterface)
    { 
      this.id = itemCfg.id;
      this.nameOfItem= itemCfg.nameOfItem;
      this.price = itemCfg.price;
      this.piece=itemCfg.piece;
    }
  }