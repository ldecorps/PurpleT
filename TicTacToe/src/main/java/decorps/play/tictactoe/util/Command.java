package decorps.play.tictactoe.util;

import java.io.IOException;

public enum Command
{
	START()
	{
		int numberOfPlayers;

		@Override public boolean isValid()
		{
			readInput();
			numberOfPlayers = Integer.parseInt(inputAsString);
			return true;
		}

		@Override public void executeCommand()
		{
			if (0 == numberOfPlayers)
			{
				main.commandState = Command.START_NEW_GAME;
			}
			if (1 == numberOfPlayers)
			{
				main.commandState = Command.ASK_USERNAME;
			}
		}

	},
	ASK_USERNAME()
	{
		String userName;

		@Override public boolean isValid()
		{
			main.out.println("Please enter username");
			readInput();
			userName = inputAsString;
			return null != userName && !"".equals(userName);
		}

		@Override public void executeCommand()
		{
			main.ticTacToe.playerEntersTheGame(userName);
			main.commandState = Command.START_NEW_GAME;
		}

	},
	START_NEW_GAME()
	{
		@Override public void executeCommand()
		{
			main.ticTacToe.startNewGame();
			main.commandState = Command.PLAYING;
		}
	},
	PLAYING()
	{
		@Override public void executeCommand() throws TicTacToeInputException
		{
			main.out.println(main.ticTacToe.getCurrentPlayer().userName 
					+ " to play");
			main.ticTacToe.playOneMove();
			if (main.ticTacToe.isGameFinished())
			{
				main.commandState = Command.STOP;
			}
		}
	},
	STOP()
	{
	};

	static Main main;
	String inputAsString;

	public boolean isValid()
	{
		return true;
	}

	final public void readInput()
	{
		try
		{
			inputAsString = main.in.readLine();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void printAvailableCommands()
	{

	}

	final public static void setMain(Main main)
	{
		Command.main = main;
	}

	public void executeCommand() throws TicTacToeInputException
	{}
}