interface UserPasswordInterface{
    currentPassword: string;
    newPassword1: string;
    newPassword2: string;
}

export class UserPassword implements UserPasswordInterface{
  public currentPassword: string;
  public newPassword1: string;
  public newPassword2: string;
    
  constructor(userPasswordCfg:UserPasswordInterface)
  { 
    this.currentPassword = userPasswordCfg.currentPassword;
    this.newPassword1 = userPasswordCfg.newPassword1;
    this.newPassword2 = userPasswordCfg.newPassword2;
  }
}