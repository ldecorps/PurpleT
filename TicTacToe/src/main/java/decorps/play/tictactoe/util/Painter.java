package decorps.play.tictactoe.util;

import decorps.play.tictactoe.core.Board;
import decorps.play.tictactoe.core.Cell;

public class Painter
{
	final static String returnCarriage = "\n";
	final String oddLine = " - - -" + returnCarriage;
	final String evenLine = "| | | |" + returnCarriage;

	public String drow(Board board)
	{

		String[] boardAsArrayOfString = new String[] { oddLine, evenLine,
				oddLine, evenLine, oddLine, evenLine, oddLine };
		StringBuilder stringBuilder = new StringBuilder();
		for (String string : boardAsArrayOfString)
		{
			stringBuilder.append(string);
		}
		stringBuilder = addCells(board, stringBuilder);
		return stringBuilder.toString();
	}

	StringBuilder addCells(Board board, StringBuilder builder)
	{
		final char player1Marker = 'O';
		final char player2Marker = 'X';

		for (Cell cell : board.cellInputs)
		{
			int bufferPosition = mapCellPositionToBufferPosition(cell);
			builder = builder
					.replace(bufferPosition, bufferPosition + 1,
							String.valueOf(null != cell
									.getPlayer() && cell
									.getPlayer().isFirstPlayer() ? player1Marker
									: player2Marker));
		}
		return builder;
	}

	int mapCellPositionToBufferPosition(Cell cell)
	{
		int offset = 0;
		if (cell.row == 2)
		{
			offset += 3 * longAsOddLine() + 2 * longAsEvenLine();
		}
		else if (cell.row == 1)
		{
			offset += 2 * longAsOddLine() + 1 * longAsEvenLine();
		}
		else
		{
			offset += longAsOddLine();
		}
		if (cell.column == 2)
		{
			offset += 5;
		}
		else if (cell.column == 1)
		{
			offset += 3;
		}
		else
		{
			offset += 1;
		}

		return offset;
	}

	public int longAsOddLine()
	{
		return oddLine.length();
	}

	public int longAsEvenLine()
	{
		return evenLine.length();
	}

}
