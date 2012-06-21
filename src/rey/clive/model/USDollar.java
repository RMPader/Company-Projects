package rey.clive.model;

public class USDollar extends Money
{
	public USDollar(Builder builder)
	{
		super(builder);
	}

	@Override
	public String toString()
	{
		return Currency.USD + " " + super.toString();
	}
}
