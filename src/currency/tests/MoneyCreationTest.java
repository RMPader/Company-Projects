package currency.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;

import currency.Euro;
import currency.Money;
import currency.MoneyFactory;
import currency.USDollar;
import currency.exceptions.IncompatibleCurrencyException;
import currency.exceptions.InvalidMoneyTypeException;
import currency.exceptions.InvalidMoneyValueException;

public class MoneyCreationTest {

	@Test
	public void characterInDecimal() {
		try {
			MoneyFactory.createMoney("PHP 1.1a");
			fail("Must throw an invalid money value exception because decimal contains a non-numeric char");
		} catch (InvalidMoneyValueException e) {
		}
	}

	@Test
	public void characterInWholeNumber() {
		try {
			MoneyFactory.createMoney("PHP 1a.00");
			fail("Must throw an invalid money value exception because it contains a non-numeric char");
		} catch (InvalidMoneyValueException e) {
		}
	}

	@Test
	public void characterInDecimalNoWholeNumber() {
		try {
			MoneyFactory.createMoney("PHP .1a");
			fail("Must throw an invalid money value exception because decimal contains a non-numeric char");
		} catch (InvalidMoneyValueException e) {
		}
	}

	@Test
	public void multipleDots() {
		try {
			MoneyFactory.createMoney("PHP 1.10.12");
			fail("Must throw an invalid money value exception because of multiple decimal points");
		} catch (InvalidMoneyValueException e) {
		}
	}

	@Test
	public void characterInWholeNumberNoDecimal() {
		try {
			MoneyFactory.createMoney("PHP 1a");
			fail("Must throw an invalid money value exception because decimal contains a non-numeric char");
		} catch (InvalidMoneyValueException e) {
		}
	}

	@Test
	public void decimalPrecision() {
		try {
			MoneyFactory.createMoney("PHP 1.001");
			fail("Must throw an invalid money value exception because decimal is more than 2 precision");
		} catch (InvalidMoneyValueException e) {
		}
	}

	@Test
	public void leadingZeroes() {
		try {
			Money result = MoneyFactory.createMoney("PHP 000001.01");
			Money expected = MoneyFactory.createMoney("PHP 1.01");
			assertEquals(expected, result);
		} catch (InvalidMoneyValueException e) {
		}
	}

	@Test
	public void trailingZeroes() {
		try {
			MoneyFactory.createMoney("PHP 1.01000");
			fail("must throw exception since traling zeroes will not be ignored by the factory");
		} catch (InvalidMoneyValueException e) {

		}
	}

	@Test
	public void incompatibleCurrency() {
		Money augend = MoneyFactory.createMoney("USD 1.0");
		Money addend = MoneyFactory.createMoney("EUR 2.0");
		try {
			augend.add(addend);
		} catch (IncompatibleCurrencyException e) {
		}
		augend = MoneyFactory.createMoney("USD 1.0");
		addend = MoneyFactory.createMoney("PHP 2.0");
		try {
			augend.add(addend);
		} catch (IncompatibleCurrencyException e) {
		}
		augend = MoneyFactory.createMoney("PHP 1.0");
		addend = MoneyFactory.createMoney("EUR 2.0");
		try {
			augend.add(addend);
		} catch (IncompatibleCurrencyException e) {
		}
	}

	@Test
	public void noDecimalCreation() {
		Money noDecimal = MoneyFactory.createMoney("USD -1");
		assertEquals(new BigDecimal("-1.00"), noDecimal.getValue());
		assertEquals("USD -1.00", noDecimal.toString());

		noDecimal = MoneyFactory.createMoney("USD 1");
		assertEquals(new BigDecimal("1.00"), noDecimal.getValue());
		assertEquals("USD 1.00", noDecimal.toString());
	}

	@Test
	public void noWholeNumberCreation() {
		Money noDecimal = MoneyFactory.createMoney("USD .12");
		assertEquals(new BigDecimal(".12"), noDecimal.getValue());
		assertEquals("USD 0.12", noDecimal.toString());

		noDecimal = MoneyFactory.createMoney("USD .1");
		assertEquals(new BigDecimal(".10"), noDecimal.getValue());
		assertEquals("USD 0.10", noDecimal.toString());

		noDecimal = MoneyFactory.createMoney("USD -.12");
		assertEquals(new BigDecimal("-0.12"), noDecimal.getValue());
		assertEquals("USD -0.12", noDecimal.toString());

		noDecimal = MoneyFactory.createMoney("USD -0.02");
		assertEquals(new BigDecimal("-0.02"), noDecimal.getValue());
		assertEquals("USD -0.02", noDecimal.toString());
	}

	@Test
	public void createNonExistentType() {
		try {
			MoneyFactory.createMoney("SGD 1.00");
			fail("Must throw an invalid money exception because SGD is not included in available currencies");
		} catch (InvalidMoneyTypeException e) {
		}
		try {
			MoneyFactory.createMoney("HKJASFHKJA 1.00");
			fail("Must throw an invalid money exception because HKJASFHKJA is not included in available currencies");
		} catch (InvalidMoneyTypeException e) {
		}
	}

	@Test
	public void missingCurrencyType() {
		try {
			MoneyFactory.createMoney("1.00");
			fail("Must throw a MissingCurrencyTypeException");
		} catch (InvalidMoneyTypeException e) {
		}
	}

	@Test
	public void getValuePositiveCurrencies() {
		Money usd = MoneyFactory.createMoney("USD 1.20");
		assertEquals(new BigDecimal("1.20"), usd.getValue());

		usd = MoneyFactory.createMoney("PHP .3");
		assertEquals(new BigDecimal("0.30"), usd.getValue());

		usd = MoneyFactory.createMoney("EUR .02");
		assertEquals(new BigDecimal("0.02"), usd.getValue());

		usd = MoneyFactory.createMoney("USD .12");
		assertEquals(new BigDecimal("0.12"), usd.getValue());

		usd = MoneyFactory.createMoney("EUR .60");
		assertEquals(new BigDecimal("0.60"), usd.getValue());

		usd = MoneyFactory.createMoney("PHP 1");
		assertEquals(new BigDecimal("1.00"), usd.getValue());
	}

	@Test
	public void getValueNegativeCurrencies() {
		Money usd = MoneyFactory.createMoney("USD -1.20");
		assertEquals(new BigDecimal("-1.20"), usd.getValue());

		usd = MoneyFactory.createMoney("PHP -.3");
		assertEquals(new BigDecimal("-0.30"), usd.getValue());

		usd = MoneyFactory.createMoney("EUR -.02");
		assertEquals(new BigDecimal("-0.02"), usd.getValue());

		usd = MoneyFactory.createMoney("USD -.12");
		assertEquals(new BigDecimal("-0.12"), usd.getValue());

		usd = MoneyFactory.createMoney("EUR -.60");
		assertEquals(new BigDecimal("-0.60"), usd.getValue());

		usd = MoneyFactory.createMoney("PHP -1");
		assertEquals(new BigDecimal("-1.00"), usd.getValue());
	}

	@Test
	public void stringOfPositiveCurrencies() {
		Money usd = MoneyFactory.createMoney("USD 1.20");
		assertEquals("USD 1.20", usd.toString());

		usd = MoneyFactory.createMoney("PHP .3");
		assertEquals("PHP 0.30", usd.toString());

		usd = MoneyFactory.createMoney("EUR .02");
		assertEquals("EUR 0.02", usd.toString());

		usd = MoneyFactory.createMoney("USD .12");
		assertEquals("USD 0.12", usd.toString());

		usd = MoneyFactory.createMoney("EUR .60");
		assertEquals("EUR 0.60", usd.toString());

		usd = MoneyFactory.createMoney("PHP 1");
		assertEquals("PHP 1.00", usd.toString());
	}

	@Test
	public void stringOfNegativeCurrencies() {
		Money usd = MoneyFactory.createMoney("USD -1.20");
		assertEquals("USD -1.20", usd.toString());

		usd = MoneyFactory.createMoney("PHP -.3");
		assertEquals("PHP -0.30", usd.toString());

		usd = MoneyFactory.createMoney("EUR -.02");
		assertEquals("EUR -0.02", usd.toString());

		usd = MoneyFactory.createMoney("USD -.12");
		assertEquals("USD -0.12", usd.toString());

		usd = MoneyFactory.createMoney("EUR -.60");
		assertEquals("EUR -0.60", usd.toString());

		usd = MoneyFactory.createMoney("PHP -1");
		assertEquals("PHP -1.00", usd.toString());
	}

	@Test
	public void valueOfPhp() {
		Money php = MoneyFactory.createMoney("PHP 333.00");
		assertEquals(new BigDecimal("333.00"), php.getValue());
	}

	@Test
	public void valueOfEur() {
		Money euro = MoneyFactory.createMoney("EUR 1.01");
		assertEquals(new BigDecimal("1.01"), euro.getValue());
	}

	@Test
	public void valueOfUsd() {
		Money usd = MoneyFactory.createMoney("USD 1.20");
		assertEquals(new BigDecimal("1.20"), usd.getValue());
	}

	@Test
	public void stringOfPHP() {
		Money php = MoneyFactory.createMoney("PHP 333.00");
		assertEquals("PHP 333.00", php.toString());
	}

	@Test
	public void stringOfEur() {
		Money euro = MoneyFactory.createMoney("EUR 1.01");
		assertEquals("EUR 1.01", euro.toString());
	}

	@Test
	public void stringOfUsd() {
		Money usd = MoneyFactory.createMoney("USD 1.20");
		assertEquals("USD 1.20", usd.toString());
	}

	@Test
	public void moneyEqualsNull() {
		Money money = MoneyFactory.createMoney("USD 1.00");
		assertEquals("test money null equality", false, money.equals(null));
	}

	@Test
	public void moneyEqualsReflexive() {
		Money money = MoneyFactory.createMoney("USD 1.00");
		if (money.equals(money) && money.hashCode() == money.hashCode())
			assertTrue("money reflexive test are true", true);
		else
			fail("money is not equal to itself");
	}

	@Test
	public void moneyEqualsSymmetric() {
		Euro money1 = (Euro) MoneyFactory.createMoney("EUR 1.00");
		Euro money2 = (Euro) MoneyFactory.createMoney("EUR 1.00");
		if (money1.equals(money2) && money2.equals(money1)
				&& money1.hashCode() == money2.hashCode()) {
			assertTrue("money1 and money2 are the same", true);
		} else {
			fail("money failed symmetric test");
		}
	}

	@Test
	public void moneyEqualsTransitive() {
		USDollar money1 = (USDollar) MoneyFactory.createMoney("USD 1.00");
		USDollar money2 = (USDollar) MoneyFactory.createMoney("USD 1.00");
		USDollar money3 = (USDollar) MoneyFactory.createMoney("USD 1.00");
		if (money1.equals(money2) && money2.equals(money3)
				&& money1.equals(money3)
				&& money1.hashCode() == money3.hashCode()) {
			assertTrue("money1 and money3 are the same", true);
		} else {
			fail("money1 and money3 are not the same");
		}
	}
}
