package currency;

public class PHPeso extends Money {
    public PHPeso(int wholeNumber, int decimalNumber, Currency currency) {
	super(wholeNumber, decimalNumber, currency);
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder(Currency.PHP.toString());
	sb.append(" ");
	sb.append(super.toString());
	return sb.toString();
    }
}
