package currency;

public class USDollar extends Money {
	public USDollar(String value) {
		super(value);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(Currency.USD.toString());
		sb.append(" ");
		sb.append(super.toString());
		return sb.toString();
	}

	@Override
	public String getCurrencyType() {
		return "USD";
	}
}
