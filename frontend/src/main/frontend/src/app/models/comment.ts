import { User } from "./user";

export class Comment implements CommentInterface{
    public id: number;
    public text: string;
    public user: User;
	
		
	constructor(commentCfg:CommentInterface)
	{	
        this.id = commentCfg.id;
        this.text = commentCfg.text;	
        this.user = commentCfg.user;	
	}
}

interface CommentInterface{
    id?: number;
    text: string;
    user: User;
    
}