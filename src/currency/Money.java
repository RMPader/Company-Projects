package currency;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import currency.exceptions.IncompatibleCurrencyException;

public class Money {

    private final Currency currency;
    private final int decimalNumber;
    private final int wholeNumber;
    private final String stringValue;
    private final LeadingDecimalZeroes leadingDecimalZeroes;
    private final DecimalFormat decimalFormat;

    protected Money(int wholeNumber, int decimalNumber, Currency currency,
	    LeadingDecimalZeroes leadingDecimalZeroes) {
	this.wholeNumber = wholeNumber;
	this.decimalNumber = decimalNumber;
	this.currency = currency;
	this.leadingDecimalZeroes = leadingDecimalZeroes;
	String wholeNumberPart = generateWholeNumberPart();
	String decimalPart = generateDecimalPart();
	String value = wholeNumberPart + decimalPart;
	BigDecimal bigDecimal = new BigDecimal(value);
	decimalFormat = new DecimalFormat("0.00");
	this.stringValue = decimalFormat.format(bigDecimal);
    }

    private String generateWholeNumberPart() {
	if (isWholeNumberOrDecimalNegative()) {
	    return "-" + Math.abs(wholeNumber);
	} else
	    return String.valueOf(wholeNumber);
    }

    private boolean isWholeNumberOrDecimalNegative() {
	return wholeNumber < 0 || decimalNumber < 0;
    }

    private String generateDecimalPart() {
	int decimalNumber = Math.abs(this.decimalNumber);
	if (leadingDecimalZeroes == LeadingDecimalZeroes.ONE) {
	    return ".0" + Math.abs(decimalNumber);
	} else {
	    return "." + String.valueOf(Math.abs(decimalNumber));
	}
    }

    public BigDecimal getValue() {
	return new BigDecimal(stringValue);
    }

    public Money multiply(String multiplicand) {
	BigDecimal result = getValue().multiply(new BigDecimal(multiplicand));
	String moneyExpression = generateMoneyResultExpression(result);
	return MoneyFactory.createMoney(moneyExpression);
    }
    
    public Money divide(String multiplicand) {
   	BigDecimal result = getValue().divide(new BigDecimal(multiplicand));
   	String moneyExpression = generateMoneyResultExpression(result);
   	return MoneyFactory.createMoney(moneyExpression);
       }

    public Money add(Money augend) throws IncompatibleCurrencyException {
	checkCurrency(augend);
	BigDecimal result = getValue().add(augend.getValue());
	String moneyExpression = generateMoneyResultExpression(result);
	return MoneyFactory.createMoney(moneyExpression);
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

    private String generateMoneyResultExpression(BigDecimal total) {
	StringBuffer rawResult = new StringBuffer();
	rawResult.append(currency);
	rawResult.append(" ");
	String format = decimalFormat.format(total);
	rawResult.append(format);
	return rawResult.toString();
    }

    public Money subtract(Money subtrahend)
	    throws IncompatibleCurrencyException {
	checkCurrency(subtrahend);
	BigDecimal result = getValue().subtract(subtrahend.getValue());
	String moneyExpression = generateMoneyResultExpression(result);
	return MoneyFactory.createMoney(moneyExpression);
    }

    @Override
    public String toString() {
	return stringValue;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
		+ ((currency == null) ? 0 : currency.hashCode());
	result = prime * result + decimalNumber;
	result = prime
		* result
		+ ((leadingDecimalZeroes == null) ? 0 : leadingDecimalZeroes
			.hashCode());
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
	if (currency != other.currency)
	    return false;
	if (decimalNumber != other.decimalNumber)
	    return false;
	if (leadingDecimalZeroes != other.leadingDecimalZeroes)
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
