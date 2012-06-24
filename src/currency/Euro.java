package currency;

public class Euro extends Money {
    public Euro(String value) {
	super(value);
	}

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder(Currency.EUR.toString());
	sb.append(" ");
	sb.append(super.toString());
	return sb.toString();
    }

	@Override
	public String getCurrencyType() {
		return "EUR";
	}
}
