package decorps.play.tictactoe.core;

import static decorps.play.tictactoe.core.Cell.MIDDLE_CENTER;
import static decorps.play.tictactoe.core.Cell.MIDDLE_LEFT;
import static decorps.play.tictactoe.core.Cell.TOP_CENTER;
import static decorps.play.tictactoe.core.Cell.TOP_LEFT;
import static decorps.play.tictactoe.core.Cell.TOP_RIGHT;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import decorps.play.tictactoe.core.Cell;
import decorps.play.tictactoe.core.Game;
import decorps.play.tictactoe.util.Player;
import decorps.play.tictactoe.util.TicTacToeInputException;

public class GameTest
{
	final Game cut = Game.buildComputerGame();

	@Test public void gameStartsWithPlayerOne()
	{
		assertThat(cut.getCurrentPlayer(), is(cut.getPlayer1()));
	}

	@Test public void playersPlayInTurn() throws TicTacToeInputException
	{
		Player currentPlayer = cut.getCurrentPlayer();
		cut.addInput(TOP_LEFT);
		assertThat(cut.getCurrentPlayer(), is(not(currentPlayer)));
	}

	@Test public void threeAlignedHorizontallyAtTheTopWins()
			throws TicTacToeInputException
	{
		addInputs(TOP_LEFT, MIDDLE_LEFT, TOP_CENTER, MIDDLE_CENTER);
		assertFalse("the top row is not finished", cut.isThereaWinner());
		Cell winnerCell = TOP_RIGHT;
		winnerCell.setPlayer(cut.getPlayer1());
		cut.addInput(winnerCell);
		assertTrue("the top row is from the same user", cut.isThereaWinner());
	}

	@Test public void cannotPlaySameCellTwice() throws TicTacToeInputException
	{
		Cell oneCell = TOP_RIGHT;
		cut.addInput(oneCell);

		try
		{
			cut.addInput(oneCell);
			fail("playing same cell twice should not be allowed");
		}
		catch (TicTacToeInputException tttie)
		{

		}

	}

	private void addInputs(Cell... cells) throws TicTacToeInputException
	{
		for (Cell cell : cells)
		{
			cut.addInput(cell);
		}
	}

}
