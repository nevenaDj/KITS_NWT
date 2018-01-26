import { User } from "./user";


export class ItemComment implements ItemCommentInterface{
    public id: number;
    public writer: User;
    public text: string;
    public date: Date;

		
	constructor(itemCommentCfg:ItemCommentInterface)
	{	
        this.id = itemCommentCfg.id;
        this.writer = itemCommentCfg.writer;
        this.text = itemCommentCfg.text;
        this.date = itemCommentCfg.date;
	}
}

interface ItemCommentInterface{
    id?: number;
    writer: User;
    text: string;
    date: Date;
}