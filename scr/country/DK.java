package country;

public class DK{
	
	public static double GetVat(String type) {
		
		double vatRate = 0;
		
		if(
			type.equalsIgnoreCase("Online") 
			||
			type.equalsIgnoreCase("Book") 
		) {
			vatRate = 25;
		}
		
		return vatRate;
	}
	
}