package currency;

import java.math.BigDecimal;

public class Money {
	private final int decimalNumber;
	private final int wholeNumber;
	private final String stringValue;

	protected Money(int wholeNumber, int decimalNumber) {
		this.wholeNumber = wholeNumber;
		this.decimalNumber = decimalNumber;
		this.stringValue = String.format("%d.%02d", this.wholeNumber,this.decimalNumber);
	}

	public BigDecimal getValue() {
		return new BigDecimal(stringValue);
	}

	@Override
	public String toString() {
		return stringValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + decimalNumber;
		result = prime * result
				+ ((stringValue == null) ? 0 : stringValue.hashCode());
		result = prime * result + wholeNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Money other = (Money) obj;
		if (decimalNumber != other.decimalNumber)
			return false;
		if (stringValue == null) {
			if (other.stringValue != null)
				return false;
		} else if (!stringValue.equals(other.stringValue))
			return false;
		if (wholeNumber != other.wholeNumber)
			return false;
		return true;
	}
	// TODO equals, hashcode, money-to-money add/subtract, money-to-constant
	// multuply/divide
}
