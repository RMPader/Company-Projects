package tests;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public abstract class Money {
	protected final int decimalNumber;
	protected final int wholeNumber;

	private static enum Currency {
		EUR, USD, PHP;
	};

	protected Money(String arg) {
		String wholeNumber = arg.split("\\.")[0];
		String decimalNumber = arg.split("\\.")[1];
		this.decimalNumber = Integer.parseInt(decimalNumber);
		this.wholeNumber = Integer.parseInt(wholeNumber);
	}
	
	public BigDecimal getValue(){
		String value = String.format("%d.%02d", wholeNumber, decimalNumber);
		return new BigDecimal(value);
	}

	public static Money create(String money) {
		String[] moneyExpression = money.split(" ");
		Currency moneyType = extractMoneyType(moneyExpression);
		String value = moneyExpression[1];
		
		switch (moneyType) {
		case EUR:
			return new Euro(value);
		case USD:
			return new USDollar(value);
		case PHP:
			return new PHPeso(value);
			
		default://dummy
			throw new RuntimeException();
			
		}
	}
	
	private static Currency extractMoneyType(String[] expression){
		return Currency.valueOf(expression[0]);
	}

}
