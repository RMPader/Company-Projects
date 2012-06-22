package rey.clive.unittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.BeforeClass;
import org.junit.Test;

import rey.clive.model.Euro;
import rey.clive.model.Money;
import rey.clive.model.MoneyFactory;
import rey.clive.model.MoneyFactoryImpl;
import rey.clive.model.PHPeso;
import rey.clive.model.USDollar;

public class Tests
{
	private static MoneyFactory	moneyFactory;

	@BeforeClass
	public static void setUp()
	{
		moneyFactory = new MoneyFactoryImpl();
	}

	@Test
	public void valueOfPhp()
	{
		PHPeso php = (PHPeso) moneyFactory.createMoney("PHP 333.00");
		assertEquals(new BigDecimal("333.00"), php.getValue());
	}

	@Test
	public void valueOfEur()
	{
		Euro euro = (Euro) moneyFactory.createMoney("EUR 1.01");
		assertEquals(new BigDecimal("1.01"), euro.getValue());
	}

	@Test
	public void valueOfUsd()
	{
		USDollar usd = (USDollar) moneyFactory.createMoney("USD 1.20");
		assertEquals(new BigDecimal("1.20"), usd.getValue());
	}

	@Test
	public void stringOfPHP()
	{
		PHPeso php = (PHPeso) moneyFactory.createMoney("PHP 333.00");
		assertEquals("PHP 333.00", php.toString());
	}

	@Test
	public void stringOfEur()
	{
		Euro euro = (Euro) moneyFactory.createMoney("EUR 1.01");
		assertEquals("EUR 1.01", euro.toString());
	}

	@Test
	public void stringOfUsd()
	{
		USDollar usd = (USDollar) moneyFactory.createMoney("USD 1.20");
		assertEquals("USD 1.20", usd.toString());
	}

	@Test
	public void moneyEqualsNull()
	{
		Money money = moneyFactory.createMoney("USD 1.00");
		assertEquals("test money null equality", false, money.equals(null));
	}

	@Test
	public void moneyEqualsReflexive()
	{
		Money money = moneyFactory.createMoney("USD 1.00");
		if (money.equals(money) && money.hashCode() == money.hashCode())
			assertTrue("money reflexive test are true", true);
		else
			fail("money is not equal to itself");
	}

	@Test
	public void moneyEqualsSymmetric()
	{
		Euro money1 = (Euro) moneyFactory.createMoney("EUR 1.00");
		Euro money2 = (Euro) moneyFactory.createMoney("EUR 1.00");
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
		USDollar money1 = (USDollar) moneyFactory.createMoney("USD 1.00");
		USDollar money2 = (USDollar) moneyFactory.createMoney("USD 1.00");
		USDollar money3 = (USDollar) moneyFactory.createMoney("USD 1.00");
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
