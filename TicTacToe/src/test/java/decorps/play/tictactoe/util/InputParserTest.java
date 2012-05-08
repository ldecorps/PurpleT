package decorps.play.tictactoe.util;

import static decorps.play.tictactoe.core.Cell.BOTTOM_RIGHT;
import static decorps.play.tictactoe.core.Cell.MIDDLE_CENTER;
import static decorps.play.tictactoe.core.Cell.TOP_LEFT;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.Test;

import decorps.play.tictactoe.core.Cell;
import decorps.play.tictactoe.core.Game;
import decorps.play.tictactoe.util.InputParser;
import decorps.play.tictactoe.util.TicTacToeInputException;

public class InputParserTest
{
	final InputParser cut = new InputParser(Game.buildComputerGame());
	final Random r = new Random();

	@Test public void failsWhenEmpty()
	{
		fails("", "empty");
	}

	@Test public void failsWhenFirstCharIsNotaLetter()
	{
		fails("11", "letter");
		for (int i = 0; i < 100; i++)
		{
			fails(randomNumber(), "letter");
		}
	}

	@Test public void failsWhenSecondCharIsNotaDigit()
	{
		fails("AA", "digit");
		for (int i = 0; i < 100; i++)
		{
			fails(randomLetter() + randomLetter(), "digit");
		}
	}

	@Test public void theseOnesAreOk()
	{
		equals("A1", TOP_LEFT);
		equals("B2", MIDDLE_CENTER);
		equals("C3", BOTTOM_RIGHT);
	}

	private String randomLetter()
	{
		return Character.toString((char) (r.nextInt(2) + 'A'));
	}

	private String randomNumber()
	{
		return Character.toString((char) (r.nextInt(2) + '0'));
	}

	private void fails(String input, String containedErrorMessage)
	{
		try
		{
			cut.parse(input);
			fail("input " + input
					+ " should have failed with message containing \""
					+ containedErrorMessage + "\"");
		}
		catch (TicTacToeInputException iae)
		{
			assertThat(iae.getMessage(), containsString(containedErrorMessage));
		}
	}

	private void equals(String input, Cell expectedCell)
	{
		try
		{
			Cell cell = cut.parse(input);
			assertThat(cell, equalTo(expectedCell));
		}
		catch (TicTacToeInputException iae)
		{
			iae.printStackTrace();
			fail(cut.showManual(iae.getMessage()));
		}
	}
}
