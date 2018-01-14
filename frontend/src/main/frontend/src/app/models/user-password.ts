interface UserPasswordInterface{
    oldPass: string;
    newPass: string;
    newPassAgain: string;
}

export class UserPassword implements UserPasswordInterface{
  public oldPass: string;
  public newPass: string;
  public newPassAgain: string;
    
  constructor(userPasswordCfg:UserPasswordInterface)
  { 
    this.oldPass = userPasswordCfg.oldPass;
    this.newPass = userPasswordCfg.newPass;
    this.newPassAgain = userPasswordCfg.newPassAgain;
  }
}

