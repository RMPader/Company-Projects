package rey.clive.model;

import rey.clive.exceptions.InvalidMoneyFormatException;

public interface MoneyFactory
{
	public Money createMoney(String money) throws InvalidMoneyFormatException;
}
