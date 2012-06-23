package currency;

public class Euro extends Money {
	public Euro(int wholeNumber, int decimalNumber,Currency currency) {
	super(wholeNumber, decimalNumber, currency);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(Currency.EUR.toString());
		sb.append(" ");
		sb.append(super.toString());
		return sb.toString();
	}
}
