export class Option implements OptionInterface{
    public id: number;
    public text: string;
    public count: number;
   
		
	constructor(optionCfg:OptionInterface)
	{	
        this.id = optionCfg.id;
        this.text = optionCfg.text;
        this.count = optionCfg.count;        
	}
}

interface OptionInterface{
    id?: number;
    text: string;
    count?: number;
    
}