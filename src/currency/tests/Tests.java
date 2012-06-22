package currency.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;

import currency.Euro;
import currency.Money;
import currency.MoneyFactory;
import currency.PHPeso;
import currency.USDollar;

public class Tests
{
	/*
	 * @Test public void noDecimalCreation(){ Money noDecimal = MoneyFactory.createMoney("USD 1"); }
	 */
	@Test
	public void valueOfPhp()
	{
		PHPeso php = (PHPeso) MoneyFactory.createMoney("PHP 333.00");
		assertEquals(new BigDecimal("333.00"), php.getValue());
	}

	@Test
	public void valueOfEur()
	{
		Euro euro = (Euro) MoneyFactory.createMoney("EUR 1.01");
		assertEquals(new BigDecimal("1.01"), euro.getValue());
	}

	@Test
	public void valueOfUsd()
	{
		USDollar usd = (USDollar) MoneyFactory.createMoney("USD 1.20");
		assertEquals(new BigDecimal("1.20"), usd.getValue());
	}

	@Test
	public void stringOfPHP()
	{
		PHPeso php = (PHPeso) MoneyFactory.createMoney("PHP 333.00");
		assertEquals("PHP 333.00", php.toString());
	}

	@Test
	public void stringOfEur()
	{
		Euro euro = (Euro) MoneyFactory.createMoney("EUR 1.01");
		assertEquals("EUR 1.01", euro.toString());
	}

	@Test
	public void stringOfUsd()
	{
		USDollar usd = (USDollar) MoneyFactory.createMoney("USD 1.20");
		assertEquals("USD 1.20", usd.toString());
	}

	@Test
	public void moneyEqualsNull()
	{
		Money money = MoneyFactory.createMoney("USD 1.00");
		assertEquals("test money null equality", false, money.equals(null));
	}

	@Test
	public void moneyEqualsReflexive()
	{
		Money money = MoneyFactory.createMoney("USD 1.00");
		if (money.equals(money) && money.hashCode() == money.hashCode())
			assertTrue("money reflexive test are true", true);
		else
			fail("money is not equal to itself");
	}

	@Test
	public void moneyEqualsSymmetric()
	{
		Euro money1 = (Euro) MoneyFactory.createMoney("EUR 1.00");
		Euro money2 = (Euro) MoneyFactory.createMoney("EUR 1.00");
		if (money1.equals(money2) && money2.equals(money1) && money1.hashCode() == money2.hashCode())
		{
			assertTrue("money1 and money2 are the same", true);
		}
		else
		{
			fail("money failed symmetric test");
		}
	}

	@Test
	public void moneyEqualsTransitive()
	{
		USDollar money1 = (USDollar) MoneyFactory.createMoney("USD 1.00");
		USDollar money2 = (USDollar) MoneyFactory.createMoney("USD 1.00");
		USDollar money3 = (USDollar) MoneyFactory.createMoney("USD 1.00");
		if (money1.equals(money2) && money2.equals(money3) && money1.equals(money3)
		      && money1.hashCode() == money3.hashCode())
		{
			assertTrue("money1 and money3 are the same", true);
		}
		else
		{
			fail("money1 and money3 are not the same");
		}
	}
}
