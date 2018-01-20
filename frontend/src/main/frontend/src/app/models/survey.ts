export class Survey implements SurveyInterface{
    public id: number;
    public title: string;
    public end: Date;
   
		
	constructor(surveyCfg:SurveyInterface)
	{	
        this.id = surveyCfg.id;
        this.title = surveyCfg.title;
        this.end = surveyCfg.end;
        
	}
}

interface SurveyInterface{
    id?: number;
    title: string;
    end: Date;
    
}