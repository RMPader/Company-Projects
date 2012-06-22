package rey.clive.model;

import rey.clive.exceptions.InvalidMoneyFormatException;

public class MoneyFactoryImpl implements MoneyFactory
{
	private static final int	MONEY_CURRENCY_INDEX	            = 0;
	private static final int	MONEY_VALUE_INDEX	               = 1;
	private static final int	MONEY_VALUE_WHOLE_NUMBER_INDEX	= 0;
	private static final int	MONEY_VALUE_DECIMAL_NUMBER_INDEX	= 1;

	public Money createMoney(String moneyExpression) throws InvalidMoneyFormatException
	{
		String[] splittedMoneyExpression = moneyExpression.split(" ");
		String valuePart = splittedMoneyExpression[MONEY_VALUE_INDEX];
		int wholeNumber = extractWholeNumber(valuePart);
		int decimalNumber = extractDecimalNumber(valuePart);
		
		
		String currencyPart = splittedMoneyExpression[MONEY_CURRENCY_INDEX];
		Currency currency = determineCurrencyType(currencyPart);
		switch (currency)
		{
			case EUR:
				return new Euro(new Money.Builder(wholeNumber, decimalNumber));
			case USD:
				return new USDollar(new Money.Builder(wholeNumber, decimalNumber));
			case PHP:
				return new PHPeso(new Money.Builder(wholeNumber, decimalNumber));
			default:
				StringBuilder errorMessage = createInvalidMoneyFormatMessage();
				throw new InvalidMoneyFormatException(errorMessage.toString());
		}
	}

	//TODO refactor and add throw exception for invalid format
	private int extractWholeNumber(String valuePart)
	{
		String splittedvalue[] = valuePart.split("\\.");
		int wholeNumber = Integer.parseInt(splittedvalue[MONEY_VALUE_WHOLE_NUMBER_INDEX]);
		return wholeNumber;
	}

	private int extractDecimalNumber(String valuePart)
	{
		String splittedvalue[] = valuePart.split("\\.");
		int decimalNumber = Integer.parseInt(splittedvalue[MONEY_VALUE_DECIMAL_NUMBER_INDEX]);
		return decimalNumber;
	}

	private Currency determineCurrencyType(String currencyPart)
	{
		return Currency.valueOf(currencyPart);
	}

	private StringBuilder createInvalidMoneyFormatMessage()
	{
		StringBuilder errorMessage = new StringBuilder();
		errorMessage.append("must be of format [");
		appendCurrencyTypes(errorMessage);
		errorMessage.append(" ");
		errorMessage.append("[#.#|.#]");
		return errorMessage;
	}

	private StringBuilder appendCurrencyTypes(StringBuilder errorMessage)
	{
		for (Currency type : Currency.values())
		{
			errorMessage.append(type.toString());
			errorMessage.append("|");
		}
		return errorMessage.append("]");
	}
}
