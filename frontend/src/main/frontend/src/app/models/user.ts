export class User implements UserInterface{
	public id: number;
	public username: string;
	public password: string;
    public email: string;
    public phoneNo: string;
		
	constructor(userCfg:UserInterface)
	{	
		this.id = userCfg.id;
		this.username = userCfg.username;
		this.password = userCfg.password;
        this.email = userCfg.email;
        this.phoneNo = userCfg.phoneNo;
	}
}

export class UserRegister implements UserInterface{
	public username: string;
	public password: string;
	public password2: string;
    public email: string;
	public phoneNo: string;
	public apartmentId: number;
	
	constructor(userCfg:UserInterface)
	{	
		this.username = userCfg.username;
		this.password = userCfg.password;
		this.password2 = userCfg.password2;
        this.email = userCfg.email;
		this.phoneNo = userCfg.phoneNo;
		this.apartmentId = userCfg.apartmentId;
	}

}

interface UserInterface{
	id?: number;
    username: string;
	password: string;
	password2?: string;
    email?: string;
	phoneNo?: string;
	apartmentId?: number;
}