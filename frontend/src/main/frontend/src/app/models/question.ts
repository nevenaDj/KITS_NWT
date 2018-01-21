import { Option } from "./option";

export class Question implements QuestionInterface{
    public id: number;
    public text: string;
    public options: Option[];
   
		
	constructor(questionCfg:QuestionInterface)
	{	
        this.id = questionCfg.id;
        this.text = questionCfg.text;  
        this.options = questionCfg.options;      
	}
}

interface QuestionInterface{
    id?: number;
    text: string;
    options: Option[];
    
}