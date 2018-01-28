export class OptionAnswer implements OptionAnswerInterface{
    public id: number;
    public isChecked: boolean;
   
		
	constructor(optionAnswerCfg:OptionAnswerInterface)
	{	
        this.id = optionAnswerCfg.id;
        this.isChecked = optionAnswerCfg.isChecked;    
	}
}

interface OptionAnswerInterface{
    id?: number;
    isChecked: boolean;
    
}