package tests;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class Tests {
	Money php,eur,usd;
	@Before
	public void setUp(){
		php = MoneyFactory.create("PHP 333.00");
		eur = MoneyFactory.create("USD 1.01");
		usd = MoneyFactory.create("EUR 1.20");
	}
	
	@Test	
	public void valueOfPhp() {
		assertEquals(new BigDecimal("333.00"), php.getValue());
	}	
	@Test
	public void valueOfEur() {
		assertEquals(new BigDecimal("1.01"), eur.getValue());
	}
	@Test
	public void valueOfUsd() {
		assertEquals(new BigDecimal("1.20"), usd.getValue());
	}
	
}
