package tests;

public class USDollar extends Money {
	public USDollar(String value){
		super(value);
	}
	
	@Override
	public String toString(){
		return "USD" + super.toString();
	}
}
