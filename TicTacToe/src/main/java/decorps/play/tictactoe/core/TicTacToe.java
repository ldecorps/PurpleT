package decorps.play.tictactoe.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import decorps.play.tictactoe.util.InputParser;
import decorps.play.tictactoe.util.Player;
import decorps.play.tictactoe.util.TicTacToeInputException;

public class TicTacToe
{
	final Game game;
	final InputParser inputParser; 
	final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	public TicTacToe()
	{
		game = Game.buildComputerGame();
		inputParser = new InputParser(game);
	}

	public void playerEntersTheGame(String userName)
	{
		game.addOneHuman(userName);
	}

	public void playOneMove() throws TicTacToeInputException
	{
		final Cell cell = getNextCellToPlay();
		game.addInput(cell);
		System.out.println(game.board.painter.drow(game.board));
	}

	private Cell getNextCellToPlay() throws TicTacToeInputException
	{
		final String inputAsString = getCurrentPlayer().getInputter().getInput(game, in);
		final Cell cell = inputParser.parse(inputAsString);
		return cell;
	}

	public boolean isGameFinished()
	{
		return game.isFinished();
	}

	public Player getWinner()
	{
		return game.getWinner();
	}
	
	public Player getCurrentPlayer()
	{
		return game.getCurrentPlayer();
	}

	public void startNewGame() 
	{
		game.newGame();
	}
}
