package tests;

public class MoneyFactory {
	private static enum Currency {
		EUR, USD, PHP;
	};
	
	public static Money create(String money) throws InvalidMoneyFormatException{
		String[] moneyExpression = money.split(" ");
		String value = moneyExpression[1];
		
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
	
	private static String makeErrorMessage(String suspectString){
		StringBuilder message = new StringBuilder(suspectString);
		message.append("-(Must only contain:");
		for(Currency type : Currency.values()){
			message.append(type.toString());
		}
		message.append(")");
		return message.toString();
	}
	private static Currency extractMoneyType(String[] expression){
		return Currency.valueOf(expression[0]);
	}
	
	private MoneyFactory(){
	}
}
