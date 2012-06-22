package currency;

import currency.exceptions.InvalidMoneyTypeException;
import currency.exceptions.InvalidMoneyValueException;

public class MoneyFactory {
	private static final int MONEY_CURRENCY_INDEX = 0;
	private static final int MONEY_VALUE_INDEX = 1;
	
	private static final int MONEY_VALUE_WHOLE_NUMBER_INDEX = 0;
	private static final int MONEY_VALUE_DECIMAL_NUMBER_INDEX = 1;

	public static Money createMoney(String inputMoney) throws InvalidMoneyTypeException {
		String[] moneyExpression = inputMoney.split(" ");
		String valueFromInput = moneyExpression[MONEY_VALUE_INDEX];
		String currencyFromInput = moneyExpression[MONEY_CURRENCY_INDEX];
		
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
			throw new InvalidMoneyTypeException(currency.toString()+" is not yet implemented.");
		}
	}
	
	private static int extractWholeNumber(String valuePart) {
		if(startsWithDecimalPoint(valuePart)){
			return 0;
		}
		String splitValue[] = valuePart.split("\\.");
		int wholeNumber = Integer.parseInt(splitValue[MONEY_VALUE_WHOLE_NUMBER_INDEX]);
		return wholeNumber;
	}
	
	private static boolean startsWithDecimalPoint(String valueString){
		return valueString.charAt(0) == '.';
	}
	
	private static int extractDecimalNumber(String valuePart) {
		if(isWholeNumberOnly(valuePart)){
			return 0;
		}
		String splitValue[] = valuePart.split("\\.");
		int decimalNumber = Integer.parseInt(splitValue[MONEY_VALUE_DECIMAL_NUMBER_INDEX]);
		return decimalNumber;
	}
	
	private static boolean isWholeNumberOnly(String valueString){
		return !valueString.contains(".");
	}

	private static Currency currencyTypeFromString(String currencyPart) throws InvalidMoneyTypeException {
		try{
			return Currency.valueOf(currencyPart);
		} catch(IllegalArgumentException e){
			StringBuilder errorMessage = createMoneyTypeExceptionMessage(currencyPart);
			throw new InvalidMoneyTypeException(errorMessage.toString());
		}
		
	}

	private static StringBuilder createMoneyTypeExceptionMessage(String suspectString) {
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