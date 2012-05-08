package decorps.play.tictactoe.util;

public class ComputerPlayer extends Player
{

	public ComputerPlayer(String computerName)
	{
		super(computerName);
		this.inputter = new ComputerInputter();
	}

	public ComputerPlayer()
	{
		this(COMPUTER_USER_NAME);
	}

}
