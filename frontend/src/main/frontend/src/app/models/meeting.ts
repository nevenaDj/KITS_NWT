import { AgendaItem } from "./agendaItem";
import { Agenda } from "./agenda";


export class Meeting implements MeetingInterface{
    public id: number;
    public active: boolean;
    public dateAndTime: Date;
    public agenda: Agenda;
		
	constructor(meetingCfg:MeetingInterface)
	{	
        this.id = meetingCfg.id;
        this.active = meetingCfg.active;
        this.dateAndTime = meetingCfg.dateAndTime;
        this.agenda = meetingCfg.agenda;
       
	}
}

interface MeetingInterface{
    id?: number;
    active: boolean;
    dateAndTime: Date;
    agenda: Agenda;
}