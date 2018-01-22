
import { Apartment } from "./apartment";
import { User } from "./user";
import { GlitchType } from "./glitch-type";
import { GlitchState } from "./glitch-state";
import { Bill } from "./bill";

export class Glitch implements GlitchInterface{
    public id: number;
    public description: string;
    public dateOfReport: Date;
    public dateOfRepair: Date;
    public apartment: Apartment;
    public responsiblePerson: User;
    public type: GlitchType;
    public state: GlitchState;
    public companyID: number;
    public bill: Bill;
  	public repairApproved: boolean;
		
	constructor(glitchCfg:GlitchInterface)
	{	
        this.id = glitchCfg.id;
        this.description = glitchCfg.description;
        this.dateOfReport = glitchCfg.dateOfReport;
        this.dateOfRepair = glitchCfg.dateOfRepair;
        this.apartment = glitchCfg.apartment;
        this.responsiblePerson = glitchCfg.responsiblePerson;
        this.type = glitchCfg.type;
        this.state = glitchCfg.state;
        this.companyID= glitchCfg.companyID;
        this.bill= this.bill;
        this.repairApproved = glitchCfg.repairApproved;
	}
}

interface GlitchInterface{
    id?: number;
    description: string;
    dateOfReport?: Date;
    dateOfRepair?: Date;
    apartment?: Apartment;
    responsiblePerson?: User;
    type: GlitchType;
    state?: GlitchState;
    repairApproved?: boolean;
    companyID: number;
    bill: Bill;
}