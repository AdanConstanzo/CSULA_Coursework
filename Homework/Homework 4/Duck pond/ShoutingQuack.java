/* 1pts: Add a new kind of quacking behavior called ShoutingQuack (implement it by having the duck say "QUACK QUACK!" */
public class ShoutingQuack implements QuackBehavior{

	@Override
	public void quack() {
		System.out.println("QUACK QUACK!");
	}

}
