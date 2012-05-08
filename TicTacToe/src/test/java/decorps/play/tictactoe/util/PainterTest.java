package decorps.play.tictactoe.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

import org.junit.Test;

import decorps.play.tictactoe.core.Board;
import decorps.play.tictactoe.core.Cell;
import decorps.play.tictactoe.core.Game;

public class PainterTest
{
	final Painter cut = new Painter();
	final Board board = new Board();

	@Test public void mapCellPositionToBufferPosition()
			throws TicTacToeInputException
	{
		board.add(Cell.TOP_LEFT.setPlayer(Game.getPlayer1()));
		board.add(Cell.MIDDLE_CENTER.setPlayer(Game.getPlayer1()));
		board.add(Cell.BOTTOM_RIGHT.setPlayer(Game.getPlayer1()));
		assertThat(
				cut.drow(board),
				allOf(containsString("|O| | |"), containsString("| |O| |"),
						containsString("| | |O|")));
	}
}
