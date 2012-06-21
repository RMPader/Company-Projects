package tests;

public class MoneyFactory {
	private static final int MONEY_TYPE_INDEX = 0;
	private static final int MONEY_VALUE_INDEX = 1;
	
	private static enum Currency {
		EUR, USD, PHP;
	};
	
	public static Money create(String money) throws InvalidMoneyFormatException{
		String[] moneyExpression = money.split(" ");
		String value = moneyExpression[MONEY_VALUE_INDEX];
		
		Currency moneyType = extractMoneyType(moneyExpression);
		switch (moneyType) {
		case EUR:
			return new Euro(value);
		case USD:
			return new USDollar(value);
		case PHP:
			return new PHPeso(value);		
		default:
			String errorMessage = makeErrorMessage(value);
			throw new InvalidMoneyFormatException(errorMessage);
		}
	}
	
	private static Currency extractMoneyType(String[] expression){
		return Currency.valueOf(expression[MONEY_TYPE_INDEX]);
	}
	
	private static String makeErrorMessage(String suspectString){
		StringBuilder message = initMessage(suspectString);
		message = appendCurrencyTypes(message);
		return message.toString();
	}
	
	private static StringBuilder initMessage(String suspectString){
		StringBuilder message = new StringBuilder(suspectString);
		message.append("-(Must only contain:");
		return message;
	}
	
	private static StringBuilder appendCurrencyTypes(StringBuilder message){
		for(Currency type : Currency.values()){
			message.append(type.toString());
		}
		return message.append(")");
	}
	
	private MoneyFactory(){
	}
}
