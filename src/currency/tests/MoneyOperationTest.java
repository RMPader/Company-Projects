package currency.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;

import currency.Money;
import currency.MoneyFactory;
import currency.exceptions.IncompatibleCurrencyException;

public class MoneyOperationTest {
    
    @Test
    public void incompatibleCurrencyUsingAddition() {
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
    public void incompatibleCurrencyUsingSubtraction() {
	Money minuend = MoneyFactory.createMoney("USD 1.0");
	Money subtrahend = MoneyFactory.createMoney("EUR 2.0");
	try {
	    minuend.subtract(subtrahend);
	} catch (IncompatibleCurrencyException e) {
	}
	minuend = MoneyFactory.createMoney("USD 1.0");
	subtrahend = MoneyFactory.createMoney("PHP 2.0");
	try {
	    minuend.subtract(subtrahend);
	} catch (IncompatibleCurrencyException e) {
	}
	minuend = MoneyFactory.createMoney("PHP 1.0");
	subtrahend = MoneyFactory.createMoney("EUR 2.0");
	try {
	    minuend.subtract(subtrahend);
	} catch (IncompatibleCurrencyException e) {
	}
    }

    @Test
    public void divisionByZero() {
	try {
	    Money multiplier = MoneyFactory.createMoney("PHP 0.1");
	    multiplier.divide("0");
	    fail("must throw ArithmeticException. Cannot divide by zero");
	} catch (ArithmeticException e) {
	}
    }

    @Test
    public void divisionWithRoundingOff() {
	Money dividend = MoneyFactory.createMoney("USD 0.06");
	Money result = MoneyFactory.createMoney("USD 0.01");
	Money expected = dividend.divide("5");
	assertEquals(expected, result);

	dividend = MoneyFactory.createMoney("USD 0.02");
	result = MoneyFactory.createMoney("USD 0");
	expected = dividend.divide("5");
	assertEquals(expected, result);

	dividend = MoneyFactory.createMoney("USD 0.02");
	result = MoneyFactory.createMoney("USD 0");
	expected = dividend.divide("5");
	assertEquals(expected, result);

	dividend = MoneyFactory.createMoney("USD 0.05");
	result = MoneyFactory.createMoney("USD .01");
	expected = dividend.divide("5");
	assertEquals(expected, result);
    }

    @Test
    public void multiplyWithRoundingOff() {
	Money multiplier = MoneyFactory.createMoney("USD 0.01");
	Money result = MoneyFactory.createMoney("USD .01");
	Money expected = multiplier.multiply(".5");
	assertEquals(expected, result);

	multiplier = MoneyFactory.createMoney("USD 0.01");
	result = MoneyFactory.createMoney("USD 0");
	expected = multiplier.multiply(".1");
	assertEquals(expected, result);

	multiplier = MoneyFactory.createMoney("USD 1");
	result = MoneyFactory.createMoney("USD 0");
	expected = multiplier.multiply(".001");
	assertEquals(expected, result);

	multiplier = MoneyFactory.createMoney("USD 5");
	result = MoneyFactory.createMoney("USD .01");
	expected = multiplier.multiply(".001");
	assertEquals(expected, result);
    }

    @Test
    public void positiveCurrencyDivision() {
	Money dividend = MoneyFactory.createMoney("PHP 0.1");
	Money result = dividend.divide("2");
	Money expected = MoneyFactory.createMoney("PHP 0.05");
	assertEquals(expected, result);
	assertEquals("PHP 0.05", result.toString());
	assertEquals(new BigDecimal("0.05"), result.getValue());

	dividend = MoneyFactory.createMoney("USD 0");
	result = dividend.divide("0.1");
	expected = MoneyFactory.createMoney("USD 0.0");
	assertEquals(expected, result);
	assertEquals("USD 0.00", result.toString());
	assertEquals(new BigDecimal("0.00"), result.getValue());

	dividend = MoneyFactory.createMoney("PHP 0.1");
	result = dividend.divide("0.2");
	expected = MoneyFactory.createMoney("PHP 0.5");
	assertEquals(expected, result);
	assertEquals("PHP 0.50", result.toString());
	assertEquals(new BigDecimal("0.50"), result.getValue());

	dividend = MoneyFactory.createMoney("EUR 1.1");
	result = dividend.divide("0.2");
	expected = MoneyFactory.createMoney("EUR 5.5");
	assertEquals(expected, result);
	assertEquals("EUR 5.50", result.toString());
	assertEquals(new BigDecimal("5.50"), result.getValue());

	dividend = MoneyFactory.createMoney("PHP 5");
	result = dividend.divide("2");
	expected = MoneyFactory.createMoney("PHP 2.5");
	assertEquals(expected, result);
	assertEquals("PHP 2.50", result.toString());
	assertEquals(new BigDecimal("2.50"), result.getValue());

	dividend = MoneyFactory.createMoney("PHP 6");
	result = dividend.divide("2");
	expected = MoneyFactory.createMoney("PHP 3");
	assertEquals(expected, result);
	assertEquals("PHP 3.00", result.toString());
	assertEquals(new BigDecimal("3.00"), result.getValue());
    }

    @Test
    public void negativeCurrencyDivision() {
	Money dividend = MoneyFactory.createMoney("PHP -0.1");
	Money result = dividend.divide("2");
	Money expected = MoneyFactory.createMoney("PHP -0.05");
	assertEquals(expected, result);
	assertEquals("PHP -0.05", result.toString());
	assertEquals(new BigDecimal("-0.05"), result.getValue());

	dividend = MoneyFactory.createMoney("USD 0");
	result = dividend.divide("-0.1");
	expected = MoneyFactory.createMoney("USD 0.0");
	assertEquals(expected, result);
	assertEquals("USD 0.00", result.toString());
	assertEquals(new BigDecimal("0.00"), result.getValue());

	dividend = MoneyFactory.createMoney("PHP -0.1");
	result = dividend.divide("-0.2");
	expected = MoneyFactory.createMoney("PHP 0.5");
	assertEquals(expected, result);
	assertEquals("PHP 0.50", result.toString());
	assertEquals(new BigDecimal("0.50"), result.getValue());

	dividend = MoneyFactory.createMoney("EUR 1.1");
	result = dividend.divide("-0.2");
	expected = MoneyFactory.createMoney("EUR -5.5");
	assertEquals(expected, result);
	assertEquals("EUR -5.50", result.toString());
	assertEquals(new BigDecimal("-5.50"), result.getValue());
    }

    @Test
    public void positiveCurrencyMultiplication() {
	Money multiplier = MoneyFactory.createMoney("PHP 0.1");
	Money result = multiplier.multiply("2");
	Money expected = MoneyFactory.createMoney("PHP 0.2");
	assertEquals(expected, result);
	assertEquals("PHP 0.20", result.toString());
	assertEquals(new BigDecimal("0.20"), result.getValue());

	multiplier = MoneyFactory.createMoney("USD 0.1");
	result = multiplier.multiply("0");
	expected = MoneyFactory.createMoney("USD 0.0");
	assertEquals(expected, result);
	assertEquals("USD 0.00", result.toString());
	assertEquals(new BigDecimal("0.00"), result.getValue());

	multiplier = MoneyFactory.createMoney("PHP 0.1");
	result = multiplier.multiply("0.2");
	expected = MoneyFactory.createMoney("PHP 0.02");
	assertEquals(expected, result);
	assertEquals("PHP 0.02", result.toString());
	assertEquals(new BigDecimal("0.02"), result.getValue());

	multiplier = MoneyFactory.createMoney("EUR 1.1");
	result = multiplier.multiply("0.2");
	expected = MoneyFactory.createMoney("EUR 0.22");
	assertEquals(expected, result);
	assertEquals("EUR 0.22", result.toString());
	assertEquals(new BigDecimal("0.22"), result.getValue());

	multiplier = MoneyFactory.createMoney("PHP 5");
	result = multiplier.multiply("2");
	expected = MoneyFactory.createMoney("PHP 10");
	assertEquals(expected, result);
	assertEquals("PHP 10.00", result.toString());
	assertEquals(new BigDecimal("10.00"), result.getValue());
    }

    @Test
    public void negativeCurrencyMultiplication() {
	Money multiplier = MoneyFactory.createMoney("PHP -0.1");
	Money result = multiplier.multiply("-2");
	Money expected = MoneyFactory.createMoney("PHP 0.2");
	assertEquals(expected, result);
	assertEquals("PHP 0.20", result.toString());
	assertEquals(new BigDecimal("0.20"), result.getValue());

	multiplier = MoneyFactory.createMoney("USD -0.1");
	result = multiplier.multiply("0");
	expected = MoneyFactory.createMoney("USD 0.0");
	assertEquals(expected, result);
	assertEquals("USD 0.00", result.toString());
	assertEquals(new BigDecimal("0.00"), result.getValue());

	multiplier = MoneyFactory.createMoney("PHP -0.1");
	result = multiplier.multiply("0.2");
	expected = MoneyFactory.createMoney("PHP -0.02");
	assertEquals(expected, result);
	assertEquals("PHP -0.02", result.toString());
	assertEquals(new BigDecimal("-0.02"), result.getValue());

	multiplier = MoneyFactory.createMoney("EUR 1.1");
	result = multiplier.multiply("-0.2");
	expected = MoneyFactory.createMoney("EUR -0.22");
	assertEquals(expected, result);
	assertEquals("EUR -0.22", result.toString());
	assertEquals(new BigDecimal("-0.22"), result.getValue());
    }

    @Test
    public void positiveCurrencyAddition() {
	Money augend = MoneyFactory.createMoney("EUR -.1");
	Money addend = MoneyFactory.createMoney("EUR -.2");
	Money result = augend.add(addend);
	Money expected = MoneyFactory.createMoney("EUR -.3");
	assertEquals(expected, result);
	assertEquals("EUR -0.30", result.toString());
	assertEquals(new BigDecimal("-0.30"), result.getValue());

	augend = MoneyFactory.createMoney("USD 0.51");
	addend = MoneyFactory.createMoney("USD 0.51");
	result = augend.add(addend);
	expected = MoneyFactory.createMoney("USD 1.02");
	assertEquals(expected, result);
	assertEquals("USD 1.02", result.toString());
	assertEquals(new BigDecimal("1.02"), result.getValue());

	augend = MoneyFactory.createMoney("PHP 0.5");
	addend = MoneyFactory.createMoney("PHP -.1");
	result = augend.add(addend);
	expected = MoneyFactory.createMoney("PHP .4");
	assertEquals(expected, result);
	assertEquals("PHP 0.40", result.toString());
	assertEquals(new BigDecimal("0.40"), result.getValue());

	augend = MoneyFactory.createMoney("PHP -.05");
	addend = MoneyFactory.createMoney("PHP 0.10");
	result = augend.add(addend);
	expected = MoneyFactory.createMoney("PHP .05");
	assertEquals(expected, result);
	assertEquals("PHP 0.05", result.toString());
	assertEquals(new BigDecimal("0.05"), result.getValue());

	augend = MoneyFactory.createMoney("PHP .05");
	addend = MoneyFactory.createMoney("PHP 0.10");
	result = augend.add(addend);
	expected = MoneyFactory.createMoney("PHP .15");
	assertEquals(expected, result);
	assertEquals("PHP 0.15", result.toString());
	assertEquals(new BigDecimal("0.15"), result.getValue());

	augend = MoneyFactory.createMoney("PHP 1.05");
	addend = MoneyFactory.createMoney("PHP 2.10");
	result = augend.add(addend);
	expected = MoneyFactory.createMoney("PHP 3.15");
	assertEquals(expected, result);
	assertEquals("PHP 3.15", result.toString());
	assertEquals(new BigDecimal("3.15"), result.getValue());

	augend = MoneyFactory.createMoney("PHP 3");
	addend = MoneyFactory.createMoney("PHP 2");
	result = augend.add(addend);
	expected = MoneyFactory.createMoney("PHP 5");
	assertEquals(expected, result);
	assertEquals("PHP 5.00", result.toString());
	assertEquals(new BigDecimal("5.00"), result.getValue());
    }

    @Test
    public void positiveCurrencySubtraction() {
	Money augend = MoneyFactory.createMoney("EUR .1");
	Money addend = MoneyFactory.createMoney("EUR -.2");
	Money result = augend.subtract(addend);
	Money expected = MoneyFactory.createMoney("EUR .3");
	assertEquals(expected, result);
	assertEquals("EUR 0.30", result.toString());
	assertEquals(new BigDecimal("0.30"), result.getValue());

	augend = MoneyFactory.createMoney("USD 0.51");
	addend = MoneyFactory.createMoney("USD 0.51");
	result = augend.subtract(addend);
	expected = MoneyFactory.createMoney("USD 0");
	assertEquals(expected, result);
	assertEquals("USD 0.00", result.toString());
	assertEquals(new BigDecimal("0.00"), result.getValue());

	augend = MoneyFactory.createMoney("PHP 0.5");
	addend = MoneyFactory.createMoney("PHP .1");
	result = augend.subtract(addend);
	expected = MoneyFactory.createMoney("PHP .4");
	assertEquals(expected, result);
	assertEquals("PHP 0.40", result.toString());
	assertEquals(new BigDecimal("0.40"), result.getValue());

	augend = MoneyFactory.createMoney("EUR -.05");
	addend = MoneyFactory.createMoney("EUR -0.10");
	result = augend.subtract(addend);
	expected = MoneyFactory.createMoney("EUR .05");
	assertEquals(expected, result);
	assertEquals("EUR 0.05", result.toString());
	assertEquals(new BigDecimal("0.05"), result.getValue());

	augend = MoneyFactory.createMoney("PHP -.05");
	addend = MoneyFactory.createMoney("PHP 0.10");
	result = augend.subtract(addend);
	expected = MoneyFactory.createMoney("PHP -.15");
	assertEquals(expected, result);
	assertEquals("PHP -0.15", result.toString());
	assertEquals(new BigDecimal("-0.15"), result.getValue());

	augend = MoneyFactory.createMoney("PHP 1.05");
	addend = MoneyFactory.createMoney("PHP 2.10");
	result = augend.subtract(addend);
	expected = MoneyFactory.createMoney("PHP -1.05");
	assertEquals(expected, result);
	assertEquals("PHP -1.05", result.toString());
	assertEquals(new BigDecimal("-1.05"), result.getValue());

	augend = MoneyFactory.createMoney("PHP 3");
	addend = MoneyFactory.createMoney("PHP 2");
	result = augend.subtract(addend);
	expected = MoneyFactory.createMoney("PHP 1");
	assertEquals(expected, result);
	assertEquals("PHP 1.00", result.toString());
	assertEquals(new BigDecimal("1.00"), result.getValue());
    }
}
