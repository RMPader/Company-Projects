package rey.clive.unittest;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import rey.clive.model.Money;
import rey.clive.model.MoneyFactory;
import rey.clive.model.MoneyFactoryImpl;

public class Tests {
	Money php,eur,usd;
	@Before
	public void setUp(){
		MoneyFactory moneyFactory = new MoneyFactoryImpl();
		php = moneyFactory.createMoney("PHP 333.00");
		eur = moneyFactory.createMoney("EUR 1.01");
		usd = moneyFactory.createMoney("USD 1.20");
	}
	
	@Test	
	public void valueOfPhp() {
		assertEquals(new BigDecimal("333.00"), php.getValue());
	}
	
	@Test
	public void valueOfEur() {
		assertEquals(new BigDecimal("1.01"), eur.getValue());
	}
	
	@Test
	public void valueOfUsd() {
		assertEquals(new BigDecimal("1.20"), usd.getValue());
	}
	
	@Test	
	public void stringOfPHP() {
		assertEquals("PHP 333.00", php.toString());
	}
	
	@Test
	public void stringOfEur() {
		assertEquals("EUR 1.01", eur.toString());
	}
	
	@Test
	public void stringOfUsd() {
		assertEquals("USD 1.20", usd.toString());
	}
	
	
}
