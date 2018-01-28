import { User } from "./user";
import { Building } from "./building";

export class Apartment implements ApartmentInterface{
	public id: number;
	public description: string;
    public number: number;
	public owner: User;
	public building: Building;
		
	constructor(apartemntCfg:ApartmentInterface)
	{	
		this.id = apartemntCfg.id;
		this.description = apartemntCfg.description;
        this.number = apartemntCfg.number;
		this.owner = apartemntCfg.owner;
		this.building = apartemntCfg.building;
	}
}

interface ApartmentInterface{
	id?: number;
    description: string;
    number: number;
	owner: User;
	building?: Building;
}