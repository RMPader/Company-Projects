package currency.tests;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.BeforeClass;
import org.junit.Test;

import currency.Money;
import currency.MoneyFactory;


public class Tests
{
	private static Money	php, eur, usd;

	@BeforeClass
	public static void setUp()
	{
		php = MoneyFactory.createMoney("PHP 333.00");
		eur = MoneyFactory.createMoney("EUR 1.01");
		usd = MoneyFactory.createMoney("USD 1.20");
	}
	/*@Test
	public void noDecimalCreation(){
		Money noDecimal = MoneyFactory.createMoney("USD 1");
	}*/
	@Test
	public void valueOfPhp()
	{
		assertEquals(new BigDecimal("333.00"), php.getValue());
	}

	@Test
	public void valueOfEur()
	{
		assertEquals(new BigDecimal("1.01"), eur.getValue());
	}

	@Test
	public void valueOfUsd()
	{
		assertEquals(new BigDecimal("1.20"), usd.getValue());
	}

	@Test
	public void stringOfPHP()
	{
		assertEquals("PHP 333.00", php.toString());
	}

	@Test
	public void stringOfEur()
	{
		assertEquals("EUR 1.01", eur.toString());
	}

	@Test
	public void stringOfUsd()
	{
		assertEquals("USD 1.20", usd.toString());
	}
}
