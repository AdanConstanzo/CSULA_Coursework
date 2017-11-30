package headfirst.factory.pizzaaf;

public class NorthwestIngredientFactory implements PizzaIngredientFactory {

	public Dough createDough() {
		return new ThinCrustDough();
	}

	public Sauce createSauce() {
		return new HawaiianStyleAlfredo();
	}

	public Cheese createCheese() {
		return new Gouda();
	}

	public Veggies[] createVeggies() {
		return null;
	}

	public Pepperoni createPepperoni() {
		return null;
	}

	public Clams createClam() {
		return null;
	}

	public Seafood createSeafood() {
		return new MahiMahi();
	}
}