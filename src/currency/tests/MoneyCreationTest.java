package currency.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;

import currency.Euro;
import currency.Money;
import currency.MoneyFactory;
import currency.USDollar;
import currency.exceptions.InvalidMoneyTypeException;
import currency.exceptions.InvalidMoneyValueException;

public class MoneyCreationTest {

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
    public void symbolsInValue() {
	try {
	    MoneyFactory.createMoney("PHP 1&.1");
	    MoneyFactory.createMoney("PHP 1.1*");
	    MoneyFactory.createMoney("PHP .1!");
	    MoneyFactory.createMoney("PHP 1^");
	    fail("Must throw an invalid money value exception because whole number contains a non-numeric char");
	} catch (InvalidMoneyValueException e) {
	}
    }

    @Test
    public void characterInDecimal() {
	try {
	    MoneyFactory.createMoney("PHP 1.1a");
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
	    fail("must not throw exception since leading zeroes should be ignored");
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
	    fail("Must throw an invalid money exception since currency type is missing");
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
    public void sameCurrencyDiffValue() {
	Money money1 = MoneyFactory.createMoney("PHP 1");
	Money money2 = MoneyFactory.createMoney("PHP 1.01");
	assertFalse(money1.equals(money2));
    }

    @Test
    public void sameValueDiffCurrency() {
	Money money1 = MoneyFactory.createMoney("PHP 10");
	Money money2 = MoneyFactory.createMoney("USD 10");
	assertFalse(money1.equals(money2));

	money1 = MoneyFactory.createMoney("PHP .1");
	money2 = MoneyFactory.createMoney("USD .1");
	assertFalse(money1.equals(money2));
    }

    @Test
    public void moneyEqualsNull() {
	Money money = MoneyFactory.createMoney("USD 1.00");
	assertFalse(money.equals(null));
    }

    @Test
    public void moneyEqualsReflexive() {
	Money money = MoneyFactory.createMoney("USD 1.00");
	assertTrue(money.equals(money) && money.hashCode() == money.hashCode());

	money = MoneyFactory.createMoney("PHP 1.10");
	assertTrue(money.equals(money) && money.hashCode() == money.hashCode());

	money = MoneyFactory.createMoney("USD 10.01");
	assertTrue(money.equals(money) && money.hashCode() == money.hashCode());
    }

    @Test
    public void moneyEqualsSymmetric() {
	Euro money1 = (Euro) MoneyFactory.createMoney("EUR 12.34");
	Euro money2 = (Euro) MoneyFactory.createMoney("EUR 12.34");
	assertTrue((money1.equals(money2) && money2.equals(money1))
		&& (money1.hashCode() == money2.hashCode()));
    }

    @Test
    public void moneyEqualsTransitive() {
	USDollar money1 = (USDollar) MoneyFactory.createMoney("USD 987.65");
	USDollar money2 = (USDollar) MoneyFactory.createMoney("USD 987.65");
	USDollar money3 = (USDollar) MoneyFactory.createMoney("USD 987.65");
	assertTrue(money1.equals(money2) && money2.equals(money3)
		&& money1.equals(money3)
		&& money1.hashCode() == money3.hashCode());
    }
}
