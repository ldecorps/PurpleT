package decorps.play.tictactoe.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import decorps.play.tictactoe.util.ComputerInputter;
import decorps.play.tictactoe.util.ComputerPlayer;
import decorps.play.tictactoe.util.InputParser;
import decorps.play.tictactoe.util.Inputter;
import decorps.play.tictactoe.util.Main;
import decorps.play.tictactoe.util.Player;
import decorps.play.tictactoe.util.TicTacToeInputException;
import fitnesse.slim.SystemUnderTest;

public class TicTacToeDriver
{
	Inputter computerInput = new ComputerInputter();
	@SystemUnderTest
	final public TicTacToe cut = new TicTacToe();

	public boolean startAGameAndTie()
	{
		cut.startNewGame();
		return !Player.NOBODY.equals(cut.game.board.winner);
	}

	public String winner()
	{
		return cut.game.board.winner.userName;
	}

	public void setAddInput(String input) throws TicTacToeInputException
	{
		cut.game.addInput(cut.game.inputParser.parse(input));
	}

	public int numberOfCellsTickedIs()
	{
		return cut.game.board.numberOfTickedCells();
	}

	public void humanTicksACell() throws TicTacToeInputException
	{
		setAddInput("A1");
	}

	public void humanTicks(String inputCell) throws TicTacToeInputException
	{
		setAddInput(inputCell);
	}

	public void humanEntersTheGame()
	{
		cut.playerEntersTheGame("Laurent");
	}

	public void computerPlays() throws TicTacToeInputException
	{
		if (!(cut.game.getCurrentPlayer() instanceof ComputerPlayer))
		{
			throw new IllegalStateException("It's not computer turn to play");
		}
		cut.game.addInput(cut.game.board.pickNextCellAvailable());
	}

	public void tick(String input) throws TicTacToeInputException
	{
		setAddInput(input);
	}

	public String availableCellsAre()
	{
		String result = "";
		for (Cell cell : cut.game.board.getAvailableCells())
		{
			result += InputParser.formatCellPosition(cell) + ", ";
		}
		result = result.substring(0, result.length() - 2);
		return result;
	}

	public boolean thisCellWins(String input) throws TicTacToeInputException
	{
		Cell winningCandidate = cut.game.inputParser.parse(input);
		boolean result = cut.game.board.isWinning(winningCandidate,
				cut.game.getCurrentPlayer());

		return result;
	}

	private int player2Wins = 0;

	public void playGames(int numberOfGames) throws IOException
	{
		for (int i = 0; i < numberOfGames; i++)
		{
			Main main = new Main(cut,
					new BufferedReader(new StringReader("0")), System.out);
			main.run();
			if (Game.player2 == main.getWinner())
			{
				player2Wins++;
			}
		}
	}

	public int playerOneHasLost()
	{
		return player2Wins;
	}

	public String nextBestCell()
	{
		return computerInput.getInput(cut.game, null);
	}
}
