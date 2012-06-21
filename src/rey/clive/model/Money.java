package rey.clive.model;

import java.math.BigDecimal;

public abstract class Money {
	protected final int decimalNumber;
	protected final int wholeNumber;
	protected final String value;
	
	protected Money(String arg) {
		String wholeNumber = arg.split("\\.")[0];
		String decimalNumber = arg.split("\\.")[1];
		
		this.decimalNumber = Integer.parseInt(decimalNumber);
		this.wholeNumber = Integer.parseInt(wholeNumber);
		this.value = String.format("%d.%02d", this.wholeNumber, this.decimalNumber);
	}
	
	public BigDecimal getValue(){
		return new BigDecimal(value);
	}
	
	@Override
	public String toString(){
		return value;
	}
	
	//TODO equals, hashcode, money-to-money add/subtract, money-to-constant multuply/divide
}
