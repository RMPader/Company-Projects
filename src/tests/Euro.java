package tests;

public class Euro extends Money {
	public Euro(String value){
		super(value);
	}
	
	@Override
	public String toString(){
		return "EUR " + super.toString();
	}
}
