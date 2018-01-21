export class Option implements OptionInterface{
    public id: number;
    public text: string;
   
		
	constructor(optionCfg:OptionInterface)
	{	
        this.id = optionCfg.id;
        this.text = optionCfg.text;        
	}
}

interface OptionInterface{
    id?: number;
    text: string;
    
}