package currency;

public class Main {
    public static void main(String[] args) {
	Money augend = MoneyFactory.createMoney("USD -.5");
	Money addend = MoneyFactory.createMoney("USD -.0");
	System.out.println("add");
	Money result = augend.add(addend);
    }
}
