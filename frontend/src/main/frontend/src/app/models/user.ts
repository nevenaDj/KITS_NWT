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

interface UserInterface{
	id?: number;
    username: string;
    password: string;
    email?: string;
    phoneNo?: string;
}