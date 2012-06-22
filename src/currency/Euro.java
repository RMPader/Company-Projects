package currency;

public class Euro extends Money {
	public Euro(int wholeNumber, int decimalNumber) {
		super(wholeNumber, decimalNumber);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(Currency.EUR.toString());
		sb.append(" ");
		sb.append(super.toString());
		return sb.toString();
	}
}
