package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pricing.MainClass;



class cmdTester {

	private static MainClass main;
	
	@BeforeAll
	public static void setUp() {
		main = new MainClass();
	}
	
	@AfterEach
	public void reset() {
		main.price = 0;
		main.amount = 0;
		main.type = null;
		main.countryCode = null;
		main.inputCurrencyCode = null;
		main.outputCurrencyCode = null;
		
	}
	
	@Test
	public void oneForOne() {
		main.price = 1;
		main.amount = 1;
		main.type = "book";
		
		String res = main.checkInput();
		String expectedRes = "51,00 DKK book";

	    assertEquals(expectedRes, res);
	}
	
	@Test
	public void oneForNothing() {
		main.price = 0;
		main.amount = 1;
		main.type = "book";
		
		String res = main.checkInput();
		String expectedRes = "50,00 DKK book";

	    assertEquals(expectedRes, res);
	}

	@Test
	public void NothingOrdered() {
		main.price = 1;
		main.amount = 0;
		main.type = "book";
		
		String res = main.checkInput();
		String expectedRes = "A greater amount than zero should be ordered";

	    assertEquals(expectedRes, res);
	}
	
	@Test
	public void negativeOneOrdered() {
		main.price = 100;
		main.amount = -1;
		main.type = "book";

		String res = main.checkInput();
		String expectedRes = "A greater amount than zero should be ordered";

	    assertEquals(expectedRes, res);
	}
	
	
	@Test
	public void tenOrdered() {
		main.price = 15;
		main.amount = 10;
		main.type = "book";

		String res = main.checkInput();
		String expectedRes = "200,00 DKK book";

	    assertEquals(expectedRes, res);
	}
	
	@Test
	public void nineteenOrdered() {
		main.price = 15;
		main.amount = 19;
		main.type = "book";

		String res = main.checkInput();
		String expectedRes = "335,00 DKK book";

	    assertEquals(expectedRes, res);
	}
	
	@Test
	public void twentyOrdered() {
		main.price = 15;
		main.amount = 20;
		main.type = "book";

		String res = main.checkInput();
		String expectedRes = "375,00 DKK book";

	    assertEquals(expectedRes, res);
	}
	
	@Test
	public void twentyOneOrdered() {
		main.price = 15;
		main.amount = 21;
		main.type = "book";

		String res = main.checkInput();
		String expectedRes = "390,00 DKK book";

	    assertEquals(expectedRes, res);
	}
	
	@Test
	public void hundredsBooksOrdered() {
		main.price = 15;
		main.amount = 500;
		main.type = "book";

		String res = main.checkInput();
		String expectedRes = "8775,00 DKK book";

	    assertEquals(expectedRes, res);
	}
	
	@Test
	public void fiftyOnlineOrdered() {
		main.price = 100;
		main.amount = 50;
		main.type = "online";

		String res = main.checkInput();
		String expectedRes = "5000,00 DKK online";

	    assertEquals(expectedRes, res);
	}
	
	
	@Test
	public void fiftyOnline_VatDk() {
		main.price = 100;
		main.amount = 50;
		main.type = "online";
		main.countryCode = "dk";

		String res = main.checkInput();
		String expectedRes = "6250,00 DKK online";

	    assertEquals(expectedRes, res);
	}

	@Test
	public void fiftyOnline_VatNo() {
		main.price = 100;
		main.amount = 50;
		main.type = "online";
		main.countryCode = "NO";

		String res = main.checkInput();
		String expectedRes = "6250,00 DKK online";

	    assertEquals(expectedRes, res);
	}

	@Test
	public void fiftyOnline_VatSe() {
		main.price = 100;
		main.amount = 50;
		main.type = "online";
		main.countryCode = "Se";

		String res = main.checkInput();
		String expectedRes = "6250,00 DKK online";

	    assertEquals(expectedRes, res);
	}

	@Test
	public void fiftyOnline_VatGb() {
		main.price = 100;
		main.amount = 50;
		main.type = "online";
		main.countryCode = "gB";

		String res = main.checkInput();
		String expectedRes = "6000,00 DKK online";

	    assertEquals(expectedRes, res);
	}

	@Test
	public void fiftyOnline_VatDeOnline() {
		main.price = 100;
		main.amount = 50;
		main.type = "online";
		main.countryCode = "dE";

		String res = main.checkInput();
		String expectedRes = "5950,00 DKK online";

	    assertEquals(expectedRes, res);
	}

	@Test
	public void fiftyOnline_VatDeBook() {
		main.price = 100;
		main.amount = 50;
		main.type = "BoOk";
		main.countryCode = "De";

		String res = main.checkInput();
		String expectedRes = "5750,00 DKK book";

	    assertEquals(expectedRes, res);
	}
	
	
	@Test
	public void tenOnline_OutCurNo() {
		main.price = 100;
		main.amount = 10;
		main.type = "Online";
		main.outputCurrencyCode = "NOK";

		String res = main.checkInput();
		String expectedRes = "1360,54 NOK online";

	    assertEquals(expectedRes, res);
	}

	@Test
	public void tenOnline_InCurSe() {
		main.price = 100;
		main.amount = 10;
		main.type = "Online";
		main.inputCurrencyCode = "sek";

		String res = main.checkInput();
		String expectedRes = "1423,89 SEK online";

	    assertEquals(expectedRes, res);
	}

	@Test
	public void tenOnline_OutCurGb_InCurDe() {
		main.price = 100;
		main.amount = 10;
		main.type = "Online";
		main.outputCurrencyCode = "GbP";
		main.inputCurrencyCode = "EUr";

		String res = main.checkInput();
		String expectedRes = "112,22 GBP 134,42 EUR online";

	    assertEquals(expectedRes, res);
	}
	

	@Test
	public void twentyfiveBook_VatDe_OutCurDe_InCurDk() {
		main.price = 100;
		main.amount = 10;
		main.type = "book";
		main.countryCode = "De";
		main.outputCurrencyCode = "eUr";
		main.inputCurrencyCode = "dKk";

		String res = main.checkInput();
		String expectedRes = "157,27 EUR 1170,00 DKK book"; 

	    assertEquals(expectedRes, res);
	}


	
}



//border values