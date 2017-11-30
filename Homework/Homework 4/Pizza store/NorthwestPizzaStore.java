package headfirst.factory.pizzaaf;

public class NorthwestPizzaStore extends PizzaStore {

	protected Pizza createPizza(String item) {
		Pizza pizza = null;
		PizzaIngredientFactory ingredientFactory =
		new NorthwestIngredientFactory();

		if (item.equals("cheese")) {

			pizza = new CheesePizza(ingredientFactory);
			pizza.setName("North West Style Cheese Pizza");

		} else if (item.equals("seafood")) {

			pizza = new SeafoodPizza(ingredientFactory);
			pizza.setName("North West Style Seafood Pizza");

		} 
		return pizza;
	}
}
