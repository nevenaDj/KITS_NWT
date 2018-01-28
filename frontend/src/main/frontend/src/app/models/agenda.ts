import { Survey } from "./survey";
import { AgendaItem } from "./agenda-item";


export class Agenda implements AgendaInterface{
    public id: number;
    public surveys: Survey[];
    public agendaPoints: AgendaItem[];
		
	constructor(agendaCfg:AgendaInterface)
	{	
        this.id = agendaCfg.id;
        this.surveys = agendaCfg.surveys;
        this.agendaPoints = agendaCfg.agendaPoints;

	}
}

interface AgendaInterface{
    id?: number;
    surveys: Survey[];
    agendaPoints: AgendaItem[];
}