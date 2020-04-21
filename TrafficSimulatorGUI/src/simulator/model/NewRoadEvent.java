package simulator.model;

public class NewRoadEvent extends Event {
	
	
	
	
	protected String id;
	protected String srcJunc;
	protected String destJunc;
	protected int length,co2Limit,maxSpeed;
	protected Weather weather;
	

	public NewRoadEvent(int time, String id, String srcJun, String
			destJunc, int maxSpeed, int co2Limit, int length, Weather weather)
			{
			super(time);
		this.id = id;
		this.co2Limit = co2Limit;
		this.srcJunc = srcJun;
		this.destJunc = destJunc;
		this.length = length;
		this.maxSpeed = maxSpeed;
		this.weather = weather;
	}


	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public String toString() {
		return "New Road '" + id + "'" ;
	}

}
