package rey.clive.model;

public enum Currency
{
	EUR("EUR"), USD("USD"), PHP("PHP");
	private Currency(String toStringValue)
	{
		this.stringValue = toStringValue;
	}

	@Override
	public String toString()
	{
		return stringValue;
	}

	private final String	stringValue;
}
