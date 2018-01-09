import { User } from "./user";

export class Apartment implements ApartmentInterface{
	public id: number;
	public description: string;
    public number: number;
    public owner: User;
		
	constructor(apartemntCfg:ApartmentInterface)
	{	
		this.id = apartemntCfg.id;
		this.description = apartemntCfg.description;
        this.number = apartemntCfg.number;
        this.owner = apartemntCfg.owner;
	}
}

interface ApartmentInterface{
	id?: number;
    description: string;
    number: number;
    owner: User;
}