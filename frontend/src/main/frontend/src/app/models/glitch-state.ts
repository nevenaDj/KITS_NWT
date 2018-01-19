export class GlitchState implements GlitchStateInterface{
	public id: number;
	public state: string;
		
	constructor(glitchStateCfg:GlitchStateInterface)
	{	
		this.id = glitchStateCfg.id;
		this.state = glitchStateCfg.state;
	}
}

interface GlitchStateInterface{
	id?: number;
    state: string;
}