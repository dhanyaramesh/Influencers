
public class DestValueDS {
	
	
	public String dest;
	public Double value;
	

	DestValueDS(String dest, String val){
	
		this.dest = dest;
		this.value = Double.parseDouble(val);
	}
	
	public String getDest(){
		return this.dest;
	}
	
	public Double getValue(){
		return this.value;
	}

}
