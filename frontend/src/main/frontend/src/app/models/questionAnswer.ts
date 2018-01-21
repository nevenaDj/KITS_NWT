import { OptionAnswer } from "./optionAnswer";

export class QuestionAnswer implements QuestionAnswerInterface{
    public id: number;
    public optionAnswers: OptionAnswer[];
   
		
	constructor(questionAnswerCfg:QuestionAnswerInterface)
	{	
        this.id = questionAnswerCfg.id;
        this.optionAnswers = questionAnswerCfg.optionAnswers;      
	}
}

interface QuestionAnswerInterface{
    id?: number;
    optionAnswers: OptionAnswer[];
    
    
}