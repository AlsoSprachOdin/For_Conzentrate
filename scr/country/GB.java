package country;

public class GB{
	
	public static double GetVat(String type) {
		
		double vatRate = 0;
		
		if(
			type.equalsIgnoreCase("Online") 
			||
			type.equalsIgnoreCase("Book") 
		) {
			vatRate = 20;
		}
		
		return vatRate;
	}
	
}