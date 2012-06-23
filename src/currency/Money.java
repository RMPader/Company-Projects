package currency;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import currency.exceptions.IncompatibleCurrencyException;

public class Money {

    // TODO subtraction, multiplication, division
    private final Currency currency;
    private final int decimalNumber;
    private final int wholeNumber;
    private final String stringValue;

    protected Money(int wholeNumber, int decimalNumber, Currency currency) {
	this.wholeNumber = wholeNumber;
	this.decimalNumber = decimalNumber;
	this.currency = currency;
	String value;
	if (wholeNumber < 0 || decimalNumber < 0) {
	    value = "-" + Math.abs(wholeNumber) + "." + Math.abs(decimalNumber);
	} else {
	    value = Math.abs(wholeNumber) + "." + Math.abs(decimalNumber);
	}

	BigDecimal bigDecimal = new BigDecimal(value);
	DecimalFormat decimalFormat = new DecimalFormat("0.00");
	this.stringValue = decimalFormat.format(bigDecimal);
	// String format = "%.2f";
	// if (isNegativeDecimal()) {
	// this.stringValue = "-" + String.format(format, bigDecimal);
	// } else {
	// this.stringValue = String.format(format, this.wholeNumber,
	// this.decimalNumber);

	// }
    }

    private boolean isNegativeDecimal() {
	return decimalNumber < 0;
    }

    public BigDecimal getValue() {
	return new BigDecimal(stringValue);
    }

    public Money add(Money addend) throws IncompatibleCurrencyException {
	checkCurrency(addend);
	int wholeNumber = this.wholeNumber + addend.wholeNumber;
	int decimalNumber = this.decimalNumber + addend.decimalNumber;
	String result = generateMoneyResultExpression(wholeNumber,
		decimalNumber);
	return MoneyFactory.createMoney(result);
    }

    private void checkCurrency(Money money)
	    throws IncompatibleCurrencyException {
	if (this.currency != money.currency) {
	    String errorMessage = createIncompatibleCurrencyExceptionMessage(money);
	    throw new IncompatibleCurrencyException(errorMessage);
	}
    }

    private String createIncompatibleCurrencyExceptionMessage(Money operand) {
	StringBuffer errorMessage = new StringBuffer(
		"cannot perform operation on :");
	errorMessage.append(toString());
	errorMessage.append(" and ");
	errorMessage.append(operand.toString());
	return errorMessage.toString();
    }

    private String generateMoneyResultExpression(int wholeNumber,
	    int decimalNumber) {
	StringBuffer rawResult = new StringBuffer();
	rawResult.append(currency);
	rawResult.append(" ");
	rawResult.append(wholeNumber);
	rawResult.append(".");
	rawResult.append(decimalNumber);
	return rawResult.toString();
    }

    public Money subtract(Money subtrahend)
	    throws IncompatibleCurrencyException {
	checkCurrency(subtrahend);
	int wholeNumber = this.wholeNumber - subtrahend.wholeNumber;
	int decimalNumber = this.decimalNumber - subtrahend.decimalNumber;
	if (decimalNumber < 0) {
	    wholeNumber--;
	    decimalNumber += 10;
	}
	String result = generateMoneyResultExpression(wholeNumber,
		decimalNumber);
	return MoneyFactory.createMoney(result);
    }

    @Override
    public String toString() {
	return stringValue;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + decimalNumber;
	result = prime * result
		+ ((stringValue == null) ? 0 : stringValue.hashCode());
	result = prime * result + wholeNumber;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Money other = (Money) obj;
	if (decimalNumber != other.decimalNumber)
	    return false;
	if (stringValue == null) {
	    if (other.stringValue != null)
		return false;
	} else if (!stringValue.equals(other.stringValue))
	    return false;
	if (wholeNumber != other.wholeNumber)
	    return false;
	return true;
    }
}
