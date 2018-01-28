import { Question } from "./question";

export class Survey implements SurveyInterface{
    public id: number;
    public title: string;
    public end: Date;
    public questions: Question[];
   
		
	constructor(surveyCfg:SurveyInterface)
	{	
        this.id = surveyCfg.id;
        this.title = surveyCfg.title;
        this.end = surveyCfg.end;
        this.questions = surveyCfg.questions;
        
	}
}

interface SurveyInterface{
    id?: number;
    title: string;
    end: Date;
    questions: Question[];
    
}