export class UserPassword implements UserPasswordInterface{
	public currentPassword: string;
	public newPassword1: string;
	public newPassword2: string;
		
	constructor(userCfg:UserPasswordInterface)
	{	
		this.currentPassword = userCfg.currentPassword;
		this.newPassword1 = userCfg.newPassword1;
        this.newPassword2 = userCfg.newPassword2;
	}
}

interface UserPasswordInterface{
	currentPassword: string;
	newPassword1: string;
	newPassword2: string;
}