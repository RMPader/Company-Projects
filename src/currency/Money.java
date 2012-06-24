package currency;

import java.math.BigDecimal;

import currency.exceptions.IncompatibleCurrencyException;
import currency.exceptions.InvalidMoneyValueException;

public abstract class Money {
	
	private final int decimalNumber;
	private final int wholeNumber;
	private final String stringValue;
	private final BigDecimal value;
	private static final int MONEY_VALUE_WHOLE_NUMBER_INDEX = 0;
	private static final int MONEY_VALUE_DECIMAL_NUMBER_INDEX = 1;
	private static final int DECIMAL_PRECISION = 2;

	protected Money(String value) {
		wholeNumber = extractWholeNumber(value);
		decimalNumber = extractDecimalNumber(value);
		String wholeNumberPart = generateWholeNumberString();
		String decimalPart = generateDecimalPart();
		StringBuilder sb = new StringBuilder();
		if(value.charAt(0) == '-' && wholeNumber == 0){
			sb.append('-');
		}
		sb.append(wholeNumberPart);
		sb.append(decimalPart);
		stringValue = sb.toString();
		this.value = new BigDecimal(stringValue);
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
	private static boolean startsWithDecimalPoint(String valueString) {
		return valueString.charAt(0) == '.' || (valueString.charAt(0) == '-' && valueString.charAt(1) == '.');
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

	private static int extractWholeNumber(String valuePart) {
		if (startsWithDecimalPoint(valuePart)) {
			return 0;
		}
		String splitValue[] = valuePart.split("\\.");
		if (splitValue.length > 2) {
			throw new InvalidMoneyValueException(valuePart
					+ ": input has many decimal points");
		}
		int wholeNumber = Integer.parseInt(splitValue[MONEY_VALUE_WHOLE_NUMBER_INDEX]);
		return wholeNumber;
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
	
	public Money subtract(Money subtrahend)
			throws IncompatibleCurrencyException {
		checkCurrency(subtrahend);
		BigDecimal result = getValue().subtract(subtrahend.getValue());
		String moneyExpression = generateMoneyResultExpression(result);
		return MoneyFactory.createMoney(moneyExpression);
	}

	public abstract String getCurrencyType();
	
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
		result = prime * getCurrencyType().hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Money))
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
	
	private String generateWholeNumberString() {
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
		if (decimalNumber < 10) {
			return ".0" + Math.abs(decimalNumber);
		} else {
			return "." + String.valueOf(Math.abs(decimalNumber));
		}
	}

	private void checkCurrency(Money money)
			throws IncompatibleCurrencyException {
		if (this.getCurrencyType() != money.getCurrencyType()) {
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
		rawResult.append(getCurrencyType());
		rawResult.append(" ");
		String format = total.toString();
		rawResult.append(format);
		return rawResult.toString();
	}
}
