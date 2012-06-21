package rey.clive.model;

public class PHPeso extends Money {
	public PHPeso(String value){
		super(value);
	}
	
	@Override
	public String toString(){
		return "PHP " + super.toString();
	}
}
