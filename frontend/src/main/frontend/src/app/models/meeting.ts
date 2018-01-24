import { AgendaItem } from "./agendaItem";
import { Agenda } from "./agenda";
import { Builder } from "selenium-webdriver";
import { Building } from "./building";


export class Meeting implements MeetingInterface{
    public id: number;
    public active: boolean;
    public dateAndTime: Date;
    public agenda: Agenda;
    public building: Building;
		
	constructor(meetingCfg:MeetingInterface)
	{	
        this.id = meetingCfg.id;
        this.active = meetingCfg.active;
        this.dateAndTime = meetingCfg.dateAndTime;
        this.agenda = meetingCfg.agenda;
        this.building= meetingCfg.building;
	}
}

interface MeetingInterface{
    id?: number;
    active: boolean;
    dateAndTime: Date;
    agenda: Agenda;
    building: Building;
}