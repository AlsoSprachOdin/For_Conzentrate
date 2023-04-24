package country;

public class DE{
	
	public static double GetVat(String type) {
		
		double vatRate = 0;
		
		if(
			type.equalsIgnoreCase("Online") 
		) {
			vatRate = 19;
		} else if (
			type.equalsIgnoreCase("Book")
		){
			vatRate = 12;
		}
		
		return vatRate;
	}
	
}