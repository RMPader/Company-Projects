package rey.clive.model;

public class Euro extends Money
{
	public Euro(Builder builder)
	{
		super(builder);
	}

	@Override
	public String toString()
	{
		return Currency.EUR + " " + super.toString();
	}
}
