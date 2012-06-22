package currency.exceptions;

public class InvalidMoneyTypeException extends RuntimeException {
    public InvalidMoneyTypeException(String message) {
	super(message);
    }
}
