package decorps.play.tictactoe.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

import org.junit.Test;

import decorps.play.tictactoe.core.Board;
import decorps.play.tictactoe.core.Cell;

public class PainterTest
{
	final Painter cut = new Painter();
	final Board board = new Board();

	@Test public void mapCellPositionToBufferPosition()
			throws TicTacToeInputException
	{
		board.add(Cell.TOP_LEFT.setPlayer(board.getCurrentPlayer()));
		board.add(Cell.MIDDLE_CENTER.setPlayer(board.getCurrentPlayer()));
		board.add(Cell.BOTTOM_RIGHT.setPlayer(board.getCurrentPlayer()));
		assertThat(
				cut.drow(board),
				allOf(containsString("|X| | |"), containsString("| |X| |"),
						containsString("| | |X|")));
	}
}
