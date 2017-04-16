
public class DestValueInf implements Cloneable{
	
	
	public String dest;
	public Double influence;

	DestValueInf(String dest, Double influence){
	
		this.dest = dest;

		this.influence = influence;
	}
	
	DestValueInf(DestValueInf g)
	{
		this.dest=g.dest;
		this.influence = g.influence;
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
