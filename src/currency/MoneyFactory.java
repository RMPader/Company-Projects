package currency;

import currency.exceptions.*;

public class MoneyFactory {
	private static final int MONEY_CURRENCY_INDEX = 0;
	private static final int MONEY_VALUE_INDEX = 1;

	public static Money createMoney(String inputMoney)
			throws InvalidMoneyTypeException, InvalidMoneyValueException {
		try {
			String[] moneyExpression = inputMoney.split(" ");
			String valueFromInput = moneyExpression[MONEY_VALUE_INDEX];
			String currencyFromInput = moneyExpression[MONEY_CURRENCY_INDEX];
			
			Currency currency = currencyTypeFromString(currencyFromInput);

			switch (currency) {
			case EUR:
				return new Euro(valueFromInput);
			case USD:
				return new USDollar(valueFromInput);
			case PHP:
				return new PHPeso(valueFromInput);
			default:
				throw new InvalidMoneyTypeException(currency.toString()
						+ " is not yet implemented.");
			}
		} catch (NumberFormatException e) {
			throw new InvalidMoneyValueException(inputMoney
					+ " contains a non-numeric character in it's value.");
		} catch (ArrayIndexOutOfBoundsException e){
			throw new InvalidMoneyTypeException(inputMoney + " has no currency.");
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
}
