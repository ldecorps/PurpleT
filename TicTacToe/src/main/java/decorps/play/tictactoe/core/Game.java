package decorps.play.tictactoe.core;

import decorps.play.tictactoe.util.ComputerPlayer;
import decorps.play.tictactoe.util.HumanPlayer;
import decorps.play.tictactoe.util.InputParser;
import decorps.play.tictactoe.util.Player;
import decorps.play.tictactoe.util.TicTacToeInputException;

public class Game
{
	private static Player player1;
	static Player player2;
	private Player currentPlayer;
	final Board board = new Board();
	public static final int MAX_NUMBER_OF_TURNS = 9;
	final InputParser inputParser = new InputParser(this);

	Game(Player player1, Player player2)
	{
		Game.setPlayer1(player1);
		Game.player2 = player2;
		setCurrentPlayer(Game.getPlayer1());
	}

	public static Game buildComputerGame()
	{
		Game game = new Game(new ComputerPlayer("Computer 1"),
				new ComputerPlayer("Computer 2"));
		return game;
	}

	public void addOneHuman(String playerName)
	{
		player1 = new HumanPlayer(playerName);
		currentPlayer = player1;
		player2 = new ComputerPlayer("Computer");
	}

	public boolean win()
	{
		return false;
	}

	public Player getWinner()
	{
		return board.winner;
	}

	public void addInput(Cell cell) throws TicTacToeInputException
	{
		cell.setPlayer(getCurrentPlayer());
		board.add(cell);
		if (isThereaWinner())
		{
			return;
		}
		if (getPlayer1() == getCurrentPlayer())
		{
			setCurrentPlayer(player2);
		}
		else
		{
			setCurrentPlayer(getPlayer1());
		}
	}

	boolean isThereaWinner()
	{
		return board.isThereAWinner();
	}

	public boolean isFinished()
	{
		return Cell.NULL_CELL == board.pickNextCellAvailable()
				|| isThereaWinner();
	}

	public Player adversary()
	{
		if (getPlayer1() == getCurrentPlayer())
		{
			return player2;
		}
		return getPlayer1();
	}

	public Player getCurrentPlayer()
	{
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer)
	{
		this.currentPlayer = currentPlayer;
	}

	public static Player getPlayer1()
	{
		return player1;
	}

	public static void setPlayer1(Player player1)
	{
		Game.player1 = player1;
	}

	public Cell nextBestCell()
	{
		return board.nextBestCell(this);
	}

	public void newGame() 
	{
		board.newGame();
		currentPlayer = player1;
	}

}
