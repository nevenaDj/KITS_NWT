import { Address } from "./address";
import { User } from "./user";

export class Building implements BuildingInterface{
	public id: number;
	public address: Address;
	public president: User;
		
	constructor(buildingCfg:BuildingInterface)
	{	
		this.id = buildingCfg.id;
		this.address = buildingCfg.address
		this.president = buildingCfg.president;
	}
}

interface BuildingInterface{
	id?: number;
    address: Address;
    president?: User;
}