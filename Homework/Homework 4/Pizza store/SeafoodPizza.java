package headfirst.factory.pizzaaf;

public class SeafoodPizza extends Pizza {
	PizzaIngredientFactory ingredientFactory;
 
	public SeafoodPizza(PizzaIngredientFactory ingredientFactory) {
		this.ingredientFactory = ingredientFactory;
	}
 
	void prepare() {
		System.out.println("Preparing " + name);
		dough = ingredientFactory.createDough();
		sauce = ingredientFactory.createSauce();
		cheese = ingredientFactory.createCheese();
		seafood = ingredientFactory.createSeafood();
	}
}
