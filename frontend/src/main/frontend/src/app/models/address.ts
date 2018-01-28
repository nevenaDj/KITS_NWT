export class Address implements AddressInterface {
	public id: number;
	public street: string;
	public number: string;
    public zipCode: number;
    public city: string;
		
	constructor(addressCfg:AddressInterface)
	{	
		this.id = addressCfg.id;
		this.street = addressCfg.street;
		this.number = addressCfg.number;
        this.zipCode = addressCfg.zipCode;
        this.city = addressCfg.city;
	}
}

interface AddressInterface{
	id?: number;
    street: string;
    number: string;
    zipCode: number;
    city: string;
}