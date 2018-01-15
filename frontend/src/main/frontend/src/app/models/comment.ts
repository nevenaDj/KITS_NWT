export class Comment implements CommentInterface{
    public id: number;
    public text: string;
	
		
	constructor(commentCfg:CommentInterface)
	{	
        this.id = commentCfg.id;
        this.text = commentCfg.text;		
	}
}

interface CommentInterface{
    id?: number;
    text: string;
    
}