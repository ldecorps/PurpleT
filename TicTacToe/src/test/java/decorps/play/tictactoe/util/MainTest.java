package decorps.play.tictactoe.util;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import decorps.play.tictactoe.core.TicTacToe;
import decorps.play.tictactoe.util.Command;
import decorps.play.tictactoe.util.Main;

@RunWith(JMock.class)
public class MainTest
{
	private final JUnitRuleMockery context = new JUnitRuleMockery()
	{
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	private final TicTacToe ticTacToe = context.mock(TicTacToe.class);
	private final BufferedReader in = context.mock(BufferedReader.class);
	private final Main cut = new Main(ticTacToe, in, new PrintStream(
			new ByteArrayOutputStream()));

	@Before public void gameStarts()
	{
		assertEquals(Command.START, cut.commandState);
	}

	@After public void gameIsFinished()
	{
		assertEquals(Command.STOP, cut.commandState);
	}

	@Test public void canStartaGameWithNoHumans() throws IOException,
			TicTacToeInputException
	{
		skipPlay();
		startNewGameWithNoHumans();

		cut.run();
	}

	@Test public void canPlayGameWithNoHumans() throws IOException,
			TicTacToeInputException
	{
		startNewGameWithNoHumans();
		expectPlay();
		cut.run();
	}

	@Test public void canStartaGameWithOneHuman() throws IOException,
			TicTacToeInputException
	{
		skipPlay();
		startGameWithOneHuman();

		cut.run();
	}

	@Test public void canPlayGameWithOneHuman() throws IOException,
			TicTacToeInputException
	{
		startGameWithOneHuman();
		expectPlay();

		cut.run();
	}

	private void startNewGameWithNoHumans() throws IOException
	{
		ignoreReportWhoWins();
		context.checking(new Expectations()
		{
			{
				oneOf(in).readLine();
				will(returnValue("0"));
				oneOf(ticTacToe).isGameFinished();
				will(returnValue(true));

				oneOf(ticTacToe).startNewGame();
			}
		});
	}

	private void ignoreReportWhoWins()
	{
		context.checking(new Expectations()
		{
			{
				ignoring(ticTacToe).getWinner();
			}
		});
	}

	private void skipPlay() throws TicTacToeInputException
	{
		context.checking(new Expectations()
		{
			{
				ignoring(ticTacToe).playOneMove();
				oneOf(ticTacToe).getCurrentPlayer();
			}
		});
	}

	private void startGameWithOneHuman() throws IOException
	{
		ignoreReportWhoWins();
		context.checking(new Expectations()
		{
			{
				oneOf(in).readLine();
				will(returnValue("1"));
				oneOf(in).readLine();
				will(returnValue("Laurent"));
				oneOf(ticTacToe).isGameFinished();
				will(returnValue(true));

				oneOf(ticTacToe).startNewGame();
				oneOf(ticTacToe).playerEntersTheGame("Laurent");
			}
		});
	}

	private void expectPlay() throws TicTacToeInputException
	{
		context.checking(new Expectations()
		{
			{
				oneOf(ticTacToe).playOneMove();
				oneOf(ticTacToe).getCurrentPlayer();
			}
		});
	}
}
