package currency;

import java.math.BigDecimal;

import currency.exceptions.IncompatibleCurrencyException;
import currency.exceptions.InvalidMoneyTypeException;
import currency.exceptions.InvalidMoneyValueException;

public class Money {
    
    private static final int MONEY_CURRENCY_INDEX = 0;
    private static final int MONEY_VALUE_INDEX = 1;
    private static final int MONEY_VALUE_WHOLE_NUMBER_INDEX = 0;
    private static final int MONEY_VALUE_DECIMAL_NUMBER_INDEX = 1;
    private static final int DECIMAL_PRECISION = 2;
    
    private final int decimalNumber;
    private final int wholeNumber;
    private final BigDecimal value;
    private final Currency currency;

    @SuppressWarnings("unused")
    private Money() {
	decimalNumber = 0;
	wholeNumber = 0;
	value = null;
	currency = null;
    }

    public Money(String inputMoney) 
	    throws InvalidMoneyTypeException, InvalidMoneyValueException {
	try {
	    String[] moneyExpression = inputMoney.split(" ");
	    String valueFromInput = moneyExpression[MONEY_VALUE_INDEX];
	    String currencyFromInput = moneyExpression[MONEY_CURRENCY_INDEX];
	    String valueString = normalizeValueStringFormat(valueFromInput);

	    currency = currencyTypeFromString(currencyFromInput);
	    wholeNumber = extractWholeNumber(valueFromInput);
	    decimalNumber = extractDecimalNumber(valueFromInput);
	    this.value = new BigDecimal(valueString);
	} catch (NumberFormatException e) {
	    throw new InvalidMoneyValueException(inputMoney
		    + " contains a non-numeric character in it's value.");
	} catch (ArrayIndexOutOfBoundsException e) {
	    throw new InvalidMoneyTypeException(inputMoney
		    + " has no currency.");
	}
    }

    private static Currency currencyTypeFromString(String currencyPart)
	    throws InvalidMoneyTypeException {
	try {
	    return Currency.valueOf(currencyPart);
	} catch (IllegalArgumentException e) {
	    StringBuilder errorMessage = createMoneyTypeExceptionMessage(currencyPart);
	    throw new InvalidMoneyTypeException(errorMessage.toString());
	}

    }

    private static StringBuilder createMoneyTypeExceptionMessage(
	    String suspectString) {
	StringBuilder errorMessage = new StringBuilder(suspectString);
	errorMessage.append(", type can only be ");
	return appendCurrencyTypes(errorMessage);
    }

    private static StringBuilder appendCurrencyTypes(StringBuilder errorMessage) {
	for (Currency type : Currency.values()) {
	    errorMessage.append(type.toString());
	    errorMessage.append(",");
	}
	return errorMessage.append(".");
    }

    private static int extractWholeNumber(String valuePart) {
	if (startsWithDecimalPoint(valuePart)) {
	    return 0;
	} else {
	    return wholeNumberPartToInteger(valuePart);
	}
    }

    private static boolean startsWithDecimalPoint(String valueString) {
	return valueString.charAt(0) == '.'
		|| (valueString.charAt(0) == '-' && valueString.charAt(1) == '.');
    }

    private static int wholeNumberPartToInteger(String valuePart) {
	String splitValue[] = valuePart.split("\\.");
	if (splitValue.length > 2) {
	    throw new InvalidMoneyValueException(valuePart
		    + ": input has many decimal points");
	}
	return Integer.parseInt(splitValue[MONEY_VALUE_WHOLE_NUMBER_INDEX]);
    }

    private static int extractDecimalNumber(String valuePart) {
	if (isWholeNumberOnly(valuePart)) {
	    return 0;
	}
	String decimalFromInput = extractDecimalFromInput(valuePart);

	if (decimalPrecisionIsMoreThanTwo(decimalFromInput)) {
	    throw new InvalidMoneyValueException(valuePart
		    + " has higher precision. Expected is 2 (e.g 1.00, 30.01)");
	}
	int decimalNumber = Integer.parseInt(decimalFromInput);
	return decimalNumber;
    }

    private static boolean isWholeNumberOnly(String valueString) {
	return !valueString.contains(".");
    }

    private static String extractDecimalFromInput(String value) {
	String[] splitValue = value.split("\\.");
	String decimalFromInput = splitValue[MONEY_VALUE_DECIMAL_NUMBER_INDEX];
	if (decimalFromInput.length() == 1) {
	    decimalFromInput = decimalFromInput + "0";
	}
	return decimalFromInput;
    }

    private static boolean decimalPrecisionIsMoreThanTwo(String decimalNumber) {
	return decimalNumber.length() > DECIMAL_PRECISION;
    }

    private String normalizeValueStringFormat(String value) {
	String decimalPart = generateDecimalString();
	StringBuilder sb = new StringBuilder();
	if (isNegativeFractional(value)) {
	    sb.append('-');
	}
	sb.append(wholeNumber);
	sb.append(decimalPart);
	return sb.toString();
    }

    private String generateDecimalString() {
	if (decimalNumber < 10) {
	    return ".0" + decimalNumber;
	} else {
	    return "." + decimalNumber;
	}
    }

    private boolean isNegativeFractional(String inputValue) {
	// in the case of a negative fractional (e.g. -0.01)
	// the parsed integer whole number (in this case, -0) is represented as
	// unsigned zero -> int wholeNumber = 0;
	// so we must check if the input is actually a negative.
	return inputValue.charAt(0) == '-' && wholeNumber == 0;
    }

    public Money multiply(String multiplicand) {
	BigDecimal result = value.multiply(new BigDecimal(multiplicand));
	return createScaledMoneyFromResult(result);
    }

    public Money divide(String multiplicand) {
	BigDecimal result = value.divide(new BigDecimal(multiplicand));
	return createScaledMoneyFromResult(result);
    }

    private Money createScaledMoneyFromResult(BigDecimal result) {
	result = result.setScale(DECIMAL_PRECISION, BigDecimal.ROUND_HALF_UP);
	return createMoneyFromResult(result);
    }

    public Money add(Money augend) throws IncompatibleCurrencyException {
	checkCurrency(augend);
	BigDecimal result = value.add(augend.getValue());
	return createMoneyFromResult(result);
    }

    public Money subtract(Money subtrahend)
	    throws IncompatibleCurrencyException {
	checkCurrency(subtrahend);
	BigDecimal result = value.subtract(subtrahend.getValue());
	return createMoneyFromResult(result);
    }

    private void checkCurrency(Money money)
	    throws IncompatibleCurrencyException {
	if (this.getCurrencyType() != money.getCurrencyType()) {
	    String errorMessage = createIncompatibleCurrencyExceptionMessage(money);
	    throw new IncompatibleCurrencyException(errorMessage);
	}
    }

    private String createIncompatibleCurrencyExceptionMessage(Money operand) {
	String toReturn = concatAll("cannot perform operation on :",
		toString(), " and ", operand.toString());
	return toReturn.toString();
    }

    private Money createMoneyFromResult(BigDecimal result) {
	String moneyExpression = generateMoneyExpressionFromResult(result);
	return new Money(moneyExpression);
    }

    private String generateMoneyExpressionFromResult(BigDecimal total) {
	String resultValueString = total.toString();
	String toReturn = concatAll(getCurrencyType(), " ", resultValueString);
	return toReturn.toString();
    }

    private String concatAll(String... strings) {
	StringBuilder newString = new StringBuilder();
	for (String s : strings) {
	    newString.append(s);
	}
	return newString.toString();
    }

    public String getCurrencyType() {
	return currency.toString();
    }

    public BigDecimal getValue() {
	return value;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder(getCurrencyType());
	sb.append(" ");
	sb.append(value.toString());
	return sb.toString();
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
		+ ((currency == null) ? 0 : currency.hashCode());
	result = prime * result + decimalNumber;
	result = prime * result + ((value == null) ? 0 : value.hashCode());
	result = prime * result + wholeNumber;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	Money other = (Money) obj;
	if (currency != other.currency) {
	    return false;
	}
	if (decimalNumber != other.decimalNumber) {
	    return false;
	}
	if (value == null) {
	    if (other.value != null) {
		return false;
	    }
	} else if (!value.equals(other.value)) {
	    return false;
	}
	if (wholeNumber != other.wholeNumber) {
	    return false;
	}
	return true;
    }
}
