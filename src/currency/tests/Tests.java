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

public class Tests {

    @Test
    public void decimalPrecisionTest() {
	try {
	    MoneyFactory.createMoney("PHP 1.001");
	    fail("Must throw an invalid money value exception because decimal is more than 2 precision");
	} catch (InvalidMoneyValueException e) {
	}
    }

    @Test
    public void sameCurrencyAddition() throws IncompatibleCurrencyException {
	Money augend = MoneyFactory.createMoney("EUR -1.0");
	Money addend = MoneyFactory.createMoney("EUR -2.0");
	Money result = augend.add(addend);
	Money expected = MoneyFactory.createMoney("EUR -3.0");
	assertEquals(expected, result);
	assertEquals(expected.toString(), result.toString());
	assertEquals(expected.getValue(),result.getValue());

	augend = MoneyFactory.createMoney("USD 0.0");
	addend = MoneyFactory.createMoney("USD -1.0");
	result = augend.add(addend);
	expected = MoneyFactory.createMoney("USD -1.0");
	assertEquals(expected, result);
	assertEquals(expected.toString(), result.toString());
	assertEquals(expected.getValue(),result.getValue());
	
	augend = MoneyFactory.createMoney("PHP 0.5");
	addend = MoneyFactory.createMoney("PHP 1.0");
	result = augend.add(addend);
	expected = MoneyFactory.createMoney("PHP 1.5");
	assertEquals(expected.getValue(),result.getValue());
	assertEquals(expected, result);
	assertEquals(expected.toString(), result.toString());
	
	
	augend = MoneyFactory.createMoney("USD -.5");
	addend = MoneyFactory.createMoney("USD -.0");
	result = augend.add(addend);
	expected = MoneyFactory.createMoney("USD -.5");
	assertEquals(expected, result);
	assertEquals(expected.toString(), result.toString());
	assertEquals(expected.getValue(),result.getValue());
	
	augend = MoneyFactory.createMoney("PHP 1");
	addend = MoneyFactory.createMoney("PHP .5");
	result = augend.add(addend);
	expected = MoneyFactory.createMoney("PHP 1.5");
	assertEquals(expected, result);
	assertEquals(expected.toString(), result.toString());
	assertEquals(expected.getValue(),result.getValue());
	
	augend = MoneyFactory.createMoney("EUR 1");
	addend = MoneyFactory.createMoney("EUR .5");
	result = augend.add(addend);
	expected = MoneyFactory.createMoney("EUR 1.5");
	assertEquals(expected, result);
	assertEquals(expected.toString(), result.toString());
	assertEquals(expected.getValue(),result.getValue());
    }

    @Test
    public void sameCurrencySubtraction() throws IncompatibleCurrencyException {
	Money augend = MoneyFactory.createMoney("EUR 1.0");
	Money addend = MoneyFactory.createMoney("EUR 2.0");
	Money result = augend.subtract(addend);
	Money expected = MoneyFactory.createMoney("EUR -1.0");
	assertEquals(expected, result);
	assertEquals(expected.toString(), result.toString());
	assertEquals(expected.getValue(),result.getValue());

	augend = MoneyFactory.createMoney("USD 0.0");
	addend = MoneyFactory.createMoney("USD 1.0");
	result = augend.subtract(addend);
	expected = MoneyFactory.createMoney("USD -1.0");
	assertEquals(expected, result);
	assertEquals(expected.toString(), result.toString());
	assertEquals(expected.getValue(),result.getValue());

	augend = MoneyFactory.createMoney("PHP .5");
	addend = MoneyFactory.createMoney("PHP 1.0");
	result = augend.subtract(addend);
	expected = MoneyFactory.createMoney("PHP -.5");
	assertEquals(expected, result);
	assertEquals(expected.toString(), result.toString());
	assertEquals(expected.getValue(),result.getValue());

	augend = MoneyFactory.createMoney("USD .5");
	addend = MoneyFactory.createMoney("USD .0");
	result = augend.subtract(addend);
	expected = MoneyFactory.createMoney("USD .5");
	assertEquals(expected, result);
	assertEquals(expected.toString(), result.toString());
	assertEquals(expected.getValue(),result.getValue());

	augend = MoneyFactory.createMoney("PHP 1");
	addend = MoneyFactory.createMoney("PHP .5");
	result = augend.subtract(addend);
	expected = MoneyFactory.createMoney("PHP .5");
	assertEquals(expected, result);
	assertEquals(expected.toString(), result.toString());
	assertEquals(expected.getValue(),result.getValue());

	augend = MoneyFactory.createMoney("EUR -1");
	addend = MoneyFactory.createMoney("EUR -.5");
	result = augend.subtract(addend);
	expected = MoneyFactory.createMoney("EUR -0.50");
	assertEquals(expected, result);
	assertEquals(expected.toString(), result.toString());
	assertEquals(expected.getValue(),result.getValue());
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
	
	noDecimal = MoneyFactory.createMoney("USD -0.12");
	assertEquals(new BigDecimal("-0.12"), noDecimal.getValue());
	assertEquals("USD -0.12", noDecimal.toString());
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
    public void missingCurrencyType()
    {
	try {
	    MoneyFactory.createMoney("1.00");
	    fail("Must throw an invalid money exception because HKJASFHKJA is not included in available currencies");
	} catch (InvalidMoneyTypeException e) {
	}
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
