package rey.clive.model;

public class PHPeso extends Money
{
	public PHPeso(Builder builder)
	{
		super(builder);
	}

	@Override
	public String toString()
	{
		return Currency.PHP + " " + super.toString();
	}
}
