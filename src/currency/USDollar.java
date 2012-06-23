package currency;

public class USDollar extends Money {
    public USDollar(int wholeNumber, int decimalNumber, Currency currency) {
	super(wholeNumber, decimalNumber, currency);
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder(Currency.USD.toString());
	sb.append(" ");
	sb.append(super.toString());
	return sb.toString();
    }
}
