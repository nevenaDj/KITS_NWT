import { Apartment } from './apartment';
import { GlitchType } from './glitch-type';

interface GlitchInterface {
  id?: number;
  description: string;
  dateOfReport:Date;
  apartment: Apartment;
  type: GlitchType;
  state: String;
  dateOfRepair?: Date;
  // Bill bill
  repairApproved?: boolean;
  
}

export class Glitch implements GlitchInterface {
  
  public id: number;
  public description: string;
  public dateOfReport:Date;
  public apartment: Apartment;
  public type: GlitchType;
  public state: String;
  public dateOfRepair: Date;
  // Bill bill
  public repairApproved: boolean;
  
  constructor(glitchCfg:GlitchInterface){
    this.id = glitchCfg.id;
    this.description = glitchCfg.description;
    this.dateOfReport = glitchCfg.dateOfReport;
    this.apartment = glitchCfg.apartment;
    this.type = glitchCfg.type;
    this.state = glitchCfg.state;
    this.dateOfRepair = glitchCfg.dateOfRepair;
    // Bill bill
    this.repairApproved = glitchCfg.repairApproved;
  }

}