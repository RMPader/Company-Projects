package currency;

public class PHPeso extends Money {
	public PHPeso(String value) {
		super(value);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(Currency.PHP.toString());
		sb.append(" ");
		sb.append(super.toString());
		return sb.toString();
	}

	@Override
	public String getCurrencyType() {
		return "PHP";
	}
}
