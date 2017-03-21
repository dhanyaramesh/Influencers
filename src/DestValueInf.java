
public class DestValueInf {
	
	
	public String dest;
	public Double influence;

	DestValueInf(String dest, Double influence){
	
		this.dest = dest;

		this.influence = influence;
	}
	
	public String getDest(){
		return this.dest;
	}

	
	public Double getInfluence()
	{
		return influence;
	}
	
	public void setInfluence(Double r)
	{
		influence=r;
	}
	
	
	

}
