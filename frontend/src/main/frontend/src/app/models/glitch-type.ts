export class GlitchType implements GlitchTypeInterface{
	public id: number;
	public type: string;
		
	constructor(glitchTypeCfg:GlitchTypeInterface)
	{	
		this.id = glitchTypeCfg.id;
		this.type = glitchTypeCfg.type;
	}
}

interface GlitchTypeInterface{
	id?: number;
    type: string;
}