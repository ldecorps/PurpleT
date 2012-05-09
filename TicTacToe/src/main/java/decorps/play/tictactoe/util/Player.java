package decorps.play.tictactoe.util;

public class Player
{
	public static final Player NOBODY = new Player("nobody");
	public static final String COMPUTER_USER_NAME = "Computer";
	public final String userName;
	protected Inputter inputter;
	protected boolean isFirstPlayer = false;
	
	Player(String userName)
	{
		this.userName = userName;
	}

	public String toString()
	{
		return userName;
	}

	public Inputter getInputter()
	{
		return inputter;
	}
	
	public Player setFirstPlayer()
	{
		this.isFirstPlayer = true;
		return this;
	}
	
	public boolean isFirstPlayer()
	{
		return isFirstPlayer;
	}
	
}
