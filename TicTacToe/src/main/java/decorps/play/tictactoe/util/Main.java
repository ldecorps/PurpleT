package decorps.play.tictactoe.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import decorps.play.tictactoe.core.TicTacToe;

public class Main
{
	TicTacToe ticTacToe;
	final BufferedReader in;
	public final PrintStream out;

	Command commandState = Command.START;

	public Main(TicTacToe ticTacToe, BufferedReader in, PrintStream stringBuffer)
	{
		this.ticTacToe = ticTacToe;
		this.in = in;
		this.out = stringBuffer;
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		Main main = new Main(new TicTacToe(), in, System.out);
		main.run();
	}

	public void run() throws IOException
	{
		out.println("TicTacToe");
		out.println("Enter the number of players");
		Command.setMain(this);
		do
		{
			if (commandState.isValid())
			{
				try
				{
					commandState.executeCommand();
				}
				catch (TicTacToeInputException e)
				{
					e.printStackTrace();
					commandState.printAvailableCommands();
				}
			}
			else
			{
				commandState.printAvailableCommands();
			}
		}
		while (Command.STOP != commandState);
		out.println("Winner: " + getWinner().userName);
	}

	public Player getWinner()
	{
		return ticTacToe.getWinner();
	}
}
