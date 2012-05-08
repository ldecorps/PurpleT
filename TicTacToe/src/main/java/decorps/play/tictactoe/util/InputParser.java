package decorps.play.tictactoe.util;

import decorps.play.tictactoe.core.Cell;
import decorps.play.tictactoe.core.Game;

public class InputParser
{
	final Game game;

	public InputParser(Game game)
	{
		this.game = game;
	}

	public Cell parse(String input) throws TicTacToeInputException
	{
		if (null == input || "".equals(input))
		{
			throw new TicTacToeInputException("input should not be empty.");
		}

		if (!isaLetter(input.charAt(0)))
		{
			throw new TicTacToeInputException(
					"first element should be a letter between A and C.");
		}

		if (!isaDigit(input.charAt(1)))
		{
			throw new TicTacToeInputException(
					"second element should be a digit between 1 and 3");
		}
		return new Cell(game.getCurrentPlayer(), (int) input.charAt(0) - 'A',
				(int) input.charAt(1) - '1');
	}

	private boolean isaLetter(char charAt)
	{
		final boolean result = charAt >= 'A' && charAt <= 'C';
		return result;
	}

	private boolean isaDigit(char charAt)
	{
		final boolean result = charAt >= '1' && charAt <= '3';
		return result;
	}

	public String showManual(String message)
	{
		return message
				+ " Correct input is a letter from A to B together with a number between 1 and 3";
	}

	public static String formatCellPosition(Cell cell)
	{
		return String.valueOf(new Character((char) (cell.row + 'A')))
				+ String.valueOf(cell.column + 1);
	}
}
