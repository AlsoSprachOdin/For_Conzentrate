package pricing;

import java.io.Console;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import country.*;




@Command(name = "PriceCalculator", version = "PriceCalculator 1.0", mixinStandardHelpOptions = true) 

public class MainClass implements Runnable {

    @Parameters(index = "0", description = "The number of the ware being ordered.")
    public int amount;

    //These "parameters" are the obligatory arguments describing the price, amount and type of ware, in that order.
    @Parameters(index = "1", description = "The price of the ware.")
    public double price;

    @Parameters(index = "2", description = "The type of ware.")
    public String type;

    //These "options" are optional arguments.
    @Option(names = {"--vat"}, description = "VAT or Value Added Tax, takes a country code as argument")
    public String countryCode;
    
    @Option(names = {"--input-currency"}, description = "input currency, takes a currency code as argument")
    public String inputCurrencyCode;

    @Option(names = {"--output-currency"}, description = "output currency, takes a currency code as argument")
    public String outputCurrencyCode;


    //The main method reads command-line inputs.
    public static void main(String[] args) {	
		int exitCode = new CommandLine(new MainClass()).execute(args);
		System.exit(exitCode);
	}
    

    //run() executes each time a command in given to the application in the command-line.
	@Override
	public void run() {	

		String res = checkInput();
		//then we print the output
		System.out.println(res);

	}
	
	//first we check if the user has made erroneous inputs
	public String checkInput() {
		
		String res;
		
		if(amount < 1) {
			res = "A greater amount than zero should be ordered";
		} else if (price < 0 ){
			res = "Wares canot cost less than nothing";
		} else if (!type.equalsIgnoreCase("Online") && !type.equalsIgnoreCase("Book")){
			res = "The type of ware must be either 'book' or 'online'";
		} else {
			//for testing purposes we use a separate method to construct the output string that will be produced in CLI 
			res = constructOutput();
			//then we print the output
		}
		
		return res;
	}

	//then we construct the output string for the user.
	public String constructOutput() {	
		
		//first we calculate pricing in DKK
		double priceDkk = calculation();
		
		//We instantiate a string to return
		String res = "";
		
		//If there's one currency-option selected; the default DKK price output will be replaced with the currency output.
		//If there are two currency-options selected; there will be two two price outputs.
		//otherwise the price output will be in the default DKK

		//We use a formatter to ensure uniform decimal output
		NumberFormat formatter = new DecimalFormat("#0.00");
		
		if(outputCurrencyCode == null && inputCurrencyCode == null) {
			res = formatter.format(priceDkk);
			res += " " + "DKK";
		} 
		
		double outputCurrencyPrice;
		double inputCurrencyPrice;
		
		if(outputCurrencyCode != null) {
			outputCurrencyPrice = convertCurrency(priceDkk, false);
			res += formatter.format(outputCurrencyPrice);
			res += " " + outputCurrencyCode.toUpperCase();
		}		
		
		if(inputCurrencyCode != null) {
			inputCurrencyPrice = convertCurrency(priceDkk, true);
			//if we have both input and output currencies, we at least want a space between them.
			if(outputCurrencyCode != null) {
				res += " ";
			}
			res += formatter.format(inputCurrencyPrice);
			res += " " + inputCurrencyCode.toUpperCase();
		}		

		res += " " + type.toLowerCase();
		return res;
	}
	
	
	public double calculation() {
		
		double res = price * amount;
		
		//VAT rate is determined and applied
		if(countryCode != null) {
			res += res * (getVAT() / 100); 
		}
		
		//freight is calculated
		double basicFreight = 50;			//only to be added if more than ten items are ordered.
		double moreFreight = 25;			//for every 10th item ordered after the first ten
		
		if(type.equalsIgnoreCase("Book")) {
			res += basicFreight;
			
			if (amount > 10) {
				double mfMult = Math.floor((amount - 10)/10); //calculating number of times to add "moreFreight" price.
				res += mfMult * moreFreight;
			}
		}


		return res;
	}
	
	

	private double getVAT() {
		double vatRate = 0;

		//We include "country" in the code below to avoid any country codes that could be mistaken for some other variable.
		switch(countryCode.toUpperCase()) {
		  case "DE":
			  vatRate = country.DE.GetVat(type);
			  break;
		  case "DK":
			  vatRate = country.NO.GetVat(type);		
			  break;
		  case "GB":
			  vatRate = country.GB.GetVat(type);
				break;
		  case "NO":
			  vatRate = country.NO.GetVat(type);
			  break;
		  case "SE":
			  vatRate = country.SE.GetVat(type);
			    break;
		  default: System.out.println("No matching country code");   
		}
		
		return vatRate;
	}
	
	
	private double convertCurrency(double price, boolean isItInput) {
		//TODO: add proper currency exchange API

		//we determine if the currency we're converting is the input or output currency.
		String currToConvert = "";
		if(isItInput) {
			currToConvert = inputCurrencyCode;
		} else {
			currToConvert = outputCurrencyCode;
		}
		
		switch(
				currToConvert.toUpperCase()
				) {
		  case "DDK":
			  price /= 1;
			  break;
		  case "NOK":
			  price /= 0.7350;
			  break;
		  case "SEK":
			  price /= 0.7023;
				break;
		  case "GBP":
			  price /= 8.9107;
			  break;
		  case "EUR":
			  price /= 7.4393;
			    break;
		  default: System.out.println("No matching currency code");   
		}
		
		return price;
	}
	
	
}
