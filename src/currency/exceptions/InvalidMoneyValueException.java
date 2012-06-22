package currency.exceptions;

public class InvalidMoneyValueException extends RuntimeException{
	public InvalidMoneyValueException(String message){
		super("Invalid format for " + message);
	}
}
