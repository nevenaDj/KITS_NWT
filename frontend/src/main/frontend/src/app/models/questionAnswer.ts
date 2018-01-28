import { OptionAnswer } from "./optionAnswer";

export class QuestionAnswer implements QuestionAnswerInterface{
    public id: number;
    public type: string;
    public optionAnswers: OptionAnswer[];
   
		
	constructor(questionAnswerCfg:QuestionAnswerInterface)
	{	
        this.id = questionAnswerCfg.id;
        this.type = questionAnswerCfg.type;
        this.optionAnswers = questionAnswerCfg.optionAnswers;      
	}
}

interface QuestionAnswerInterface{
    id?: number;
    type: string;
    optionAnswers: OptionAnswer[];
    
    
}