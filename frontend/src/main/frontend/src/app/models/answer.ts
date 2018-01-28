

export class Answer implements AnswerInterface{
    public questionID: number;
    public options: number[];
   
		
	constructor(answerCfg:AnswerInterface)
	{	
        this.questionID = answerCfg.questionID;
        this.options = answerCfg.options;
	}
}

interface AnswerInterface{
    questionID?: number;
    options: number[];    
}