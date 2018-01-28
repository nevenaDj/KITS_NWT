import { GlitchType } from "./glitch-type";
import { Apartment } from "./apartment";

export class CommunalProblem implements CommunalProblemInterface{
     public id: number;
     public description: string;
     public type: GlitchType;
	 public dateOfRepair: Date;
	 public companyID:number;
	 public apartments: Apartment[];
	
		
	constructor(communalProblemCfg:CommunalProblemInterface)
	{	
        this.id = communalProblemCfg.id;
        this.description = communalProblemCfg.description;	
        this.type = communalProblemCfg.type;
        this.id = communalProblemCfg.id;
        this.dateOfRepair = communalProblemCfg.dateOfRepair;	
        this.apartments = communalProblemCfg.apartments;	
	}
}

interface CommunalProblemInterface{
    id: number;
    description: string;
	type: GlitchType;
	dateOfRepair: Date;
	companyID:number;
	apartments: Apartment[];
    
}