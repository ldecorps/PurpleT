package decorps.play.tictactoe.util;

public class HumanPlayer extends Player
{

	public HumanPlayer(String userName)
	{
		super(userName);
		this.inputter = new HumanInputter();
	}

}
