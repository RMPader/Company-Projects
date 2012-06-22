package currency.exceptions;

public class IncompatibleCurrency extends RuntimeException{

    public IncompatibleCurrency(String msg) {
	super(msg);
    }
}
