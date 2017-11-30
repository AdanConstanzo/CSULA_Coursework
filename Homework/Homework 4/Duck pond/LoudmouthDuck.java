/* 1pts: Create a new Duck subclass called LoudmouthDuck. When it quacks, it does a shouting quack. */
public class LoudmouthDuck extends Duck{

	public LoudmouthDuck(){
		flyBehavior = new FlyWithWings();
		quackBehavior = new QuackBehavior();
	}

	public void performFly() {
		flyBehavior.fly();
	}

	public void performQuack() {
		quackBehavior.quack();
	}

    @Override
	public void display() {
		System.out.println("I'm a really loud mouth duck");
	}
}
