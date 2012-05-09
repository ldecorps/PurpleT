package decorps.play.tictactoe.core;

import decorps.play.tictactoe.util.ComputerPlayer;
import decorps.play.tictactoe.util.HumanPlayer;
import decorps.play.tictactoe.util.InputParser;
import decorps.play.tictactoe.util.Player;
import decorps.play.tictactoe.util.TicTacToeInputException;

public class Game
{
	private Player player1;
	 Player player2;
	final Board board = new Board();
	public static final int BOARD_SIZE = 9;
	final InputParser inputParser = new InputParser(this);

	Game(Player player1, Player player2)
	{
		this.player1 = player1;
		this.player2 = player2;
		board.setCurrentPlayer(this.player1);
	}

	public static Game buildComputerGame()
	{
		Game game = new Game(new ComputerPlayer("Computer 1").setFirstPlayer(),
				new ComputerPlayer("Computer 2"));
		return game;
	}

	public void addOneHuman(String playerName)
	{
		player1 = new HumanPlayer(playerName).setFirstPlayer();
		board.setCurrentPlayer(player1);
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
		return board.getCurrentPlayer();
	}

	public void setCurrentPlayer(Player currentPlayer)
	{
		board.setCurrentPlayer(currentPlayer);
	}

	public Player getPlayer1()
	{
		return player1;
	}

	public void setPlayer1(Player player1)
	{
		this.player1 = player1;
	}

	public Cell nextBestCell()
	{
		return board.nextBestCell(this);
	}

	public void newGame() 
	{
		board.newGame();
		board.setCurrentPlayer(player1);
	}

}
