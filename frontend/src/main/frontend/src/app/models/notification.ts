import { User } from "./user";

export class Notification implements NotificationInterface{
    public id: number;
    public text: string;
    public date: Date;   
    public user: User;
		
	constructor(notificationCfg:NotificationInterface)
	{	
        this.id = notificationCfg.id;
        this.text = notificationCfg.text;
        this.date = notificationCfg.date;   
        this.user = notificationCfg.user;     
	}
}

interface NotificationInterface{
    id?: number;
    text: string;
    date: Date;
    user: User;
    
}