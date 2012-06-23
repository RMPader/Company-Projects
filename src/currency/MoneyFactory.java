package currency;

import currency.exceptions.*;

public class MoneyFactory {
	private static final int MONEY_CURRENCY_INDEX = 0;
	private static final int MONEY_VALUE_INDEX = 1;

	private static final int MONEY_VALUE_WHOLE_NUMBER_INDEX = 0;
	private static final int MONEY_VALUE_DECIMAL_NUMBER_INDEX = 1;

	private static final int DECIMAL_PRECISION = 2;

	public static Money createMoney(String inputMoney)
			throws InvalidMoneyTypeException, InvalidMoneyValueException {
		String[] moneyExpression = inputMoney.split(" ");
		String valueFromInput = moneyExpression[MONEY_VALUE_INDEX];
		String currencyFromInput = moneyExpression[MONEY_CURRENCY_INDEX];
		try {
			int wholeNumber = extractWholeNumber(valueFromInput);
			int decimalNumber = extractDecimalNumber(valueFromInput);
			Currency currency = currencyTypeFromString(currencyFromInput);
			switch (currency) {
			case EUR:
				return new Euro(wholeNumber, decimalNumber);
			case USD:
				return new USDollar(wholeNumber, decimalNumber);
			case PHP:
				return new PHPeso(wholeNumber, decimalNumber);
			default:
				throw new InvalidMoneyTypeException(currency.toString()
						+ " is not yet implemented.");
			}
		} catch (NumberFormatException e) {
			throw new InvalidMoneyValueException(valueFromInput
					+ " contains a non-numeric character in it's value.");
		}
	}

	private static int extractWholeNumber(String valuePart) {
		if (startsWithDecimalPoint(valuePart)) {
			return 0;
		}
		String splitValue[] = valuePart.split("\\.");
		if(splitValue.length > 2){
			throw new InvalidMoneyValueException(valuePart + ": input has many decimal points");
		}
		int wholeNumber = Integer
				.parseInt(splitValue[MONEY_VALUE_WHOLE_NUMBER_INDEX]);
		return wholeNumber;
	}

	private static boolean startsWithDecimalPoint(String valueString) {
		return valueString.charAt(0) == '.';
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
		return decimalFromInput;
	}

	private static boolean decimalPrecisionIsMoreThanTwo(String decimalNumber) {
		return decimalNumber.length() > DECIMAL_PRECISION;
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
}
