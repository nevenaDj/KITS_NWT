import { Meeting } from "./meeting";
import { Glitch } from "./glitch";
import { ItemComment } from "./itemComment";
import { CommunalProblem } from "./communal-problem";
import { Notification } from "./notification";


export class AgendaItem implements AgendaItemInterface{
    public id: number;
    public title: string;
    public content: string;
    public conclusion: string;
    public number: number;
    public notification: Notification;
    public glitch: Glitch;
    public communalProblem: CommunalProblem;
    public type: string;
  	public comments: ItemComment[];
		
	constructor(agendaItemCfg:AgendaItemInterface)
	{	
        this.id = agendaItemCfg.id;
        this.title = agendaItemCfg.title;
        this.content = agendaItemCfg.content;
        this.conclusion = agendaItemCfg.conclusion;
        this.number = agendaItemCfg.number;
        this.type = agendaItemCfg.type;
        this.glitch = agendaItemCfg.glitch;
        this.notification= agendaItemCfg.notification;
        this.communalProblem= this.communalProblem;
        this.comments = agendaItemCfg.comments;
	}
}

interface AgendaItemInterface{
    id: number;
    title: string;
    content: string;
    conclusion?: string;
    number: number;
    notification?: Notification;
    glitch?: Glitch;
    communalProblem?: CommunalProblem;
    type: string;
  	comments: ItemComment[];
}