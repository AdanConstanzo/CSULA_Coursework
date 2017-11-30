
public class MiniDuckSimulator {

	public static void main(String[] args) {

		Duck mallard = new MallardDuck();
		mallard.performFly();
		mallard.performQuack();
		mallard.display();

		Duck rubberDuck = new RubberDuck();
		rubberDuck.performFly();
		rubberDuck.performQuack();
		rubberDuck.display();

		System.out.println("Rubber ducky eats a magic pellet.");
		rubberDuck.setFlyBehavior(new RocketFly());
		rubberDuck.performFly();

		System.out.println("Mallard duck eats a magic pellet.");
		mallard.setFlyBehavior(new RocketFly());
		System.out.println("Mallard says:");
		mallard.performFly();

		/* 1pt: At run time (in the test program), make the MallardDuck shout quack. */
		System.out.println("MallardDuck shout quack");
		mallard.setQuackBehavior(new ShoutingQuack());
		mallard.performQuack();

	}

}
