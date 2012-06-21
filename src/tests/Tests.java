package tests;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class Tests {

	@Test	
	public void creationOfPhpInKeyword() {
		Money php = Money.create("PHP 1.00");
		assertEquals(new BigDecimal("1.00"), php.getValue());
	}	
	@Test
	public void creationOfUsdInKeyword() {
		Money php = Money.create("USD 1.00");
		assertEquals(new BigDecimal("1.00"), php.getValue());
	}
	@Test
	public void creationOfEurInKeyword() {
		Money php = Money.create("EUR 1.00");
		assertEquals(new BigDecimal("1.00"), php.getValue());
	}
	
}
