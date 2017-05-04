
public class DestValueDS {
	
	
	public String dest;
	public Double value;
	public Double influence;

	DestValueDS(String dest, String val,Double influence){
	
		this.dest = dest;
		this.value = Double.parseDouble(val);
		this.influence = influence;
	}
	
	public String getDest(){
		return this.dest;
	}
	
	public Double getValue(){
		return this.value;
	}
	
	
	public Double getInfluence()
	{
		return influence;
	}
	

}
