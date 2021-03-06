import { Glitch } from "./glitch";
import { Notification } from "./notification";
import { CommunalProblem } from "./communal-problem";

export class ContentWithoutAgenda implements ContentWithoutAgendaInterface{
	public glitches: Glitch[];
	public notifications: Notification[];
	public communalProblems: CommunalProblem[];
		
	constructor(cwaCfg:ContentWithoutAgendaInterface)
	{	
		this.glitches = cwaCfg.glitches;
		this.notifications = cwaCfg.notifications;
		this.communalProblems = cwaCfg.communalProblems;
	}
}

interface ContentWithoutAgendaInterface{
	glitches: Glitch[];
	notifications: Notification[];
	communalProblems: CommunalProblem[];
}