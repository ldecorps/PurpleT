package decorps.play.tictactoe.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import decorps.play.tictactoe.util.Painter;
import decorps.play.tictactoe.util.Player;
import decorps.play.tictactoe.util.TicTacToeInputException;

public class Board
{
	final public List<Cell> cellInputs = new ArrayList<Cell>(
			Game.BOARD_SIZE);
	Player winner = Player.NOBODY;
	Painter painter = new Painter();
	final Random r = new Random();
	private Player currentPlayer;
	
	public void setCurrentPlayer(Player currentPlayer)
	{
		this.currentPlayer = currentPlayer;
	}
	
	public Player getCurrentPlayer()
	{
		return currentPlayer;
	}
	
	public void newGame()
	{
		cellInputs.clear();
		winner = Player.NOBODY;
	}

	public void add(Cell cell) throws TicTacToeInputException
	{
		if (cellInputs.contains(cell))
		{
			throw new TicTacToeInputException("This cell is already ticked");
		}
		cellInputs.add(cell);
	}

	boolean isThereAWinner()
	{
		final boolean result = isaRowAllTickedBySamePlayer()
				|| isaColumnAllTickedBySamePlayer()
				|| isaDiagonalAllTickedBySamePlayer();
		if (!result)
		{
			winner = Player.NOBODY;
		}
		return result;
	}

	private boolean isaDiagonalAllTickedBySamePlayer()
	{
		final boolean samePlayerOnFirstDiagonal = getPlayerAt(0, 0) == getPlayerAt(
				1, 1)
				&& getPlayerAt(0, 0) == getPlayerAt(2, 2)
				&& getPlayerAt(0, 0) != Player.NOBODY;
		if (samePlayerOnFirstDiagonal)
		{
			winner = getPlayerAt(0, 0);
			return true;
		}
		final boolean samePlayerOnOtherDiagonal = getPlayerAt(2, 0) == getPlayerAt(
				1, 1)
				&& getPlayerAt(2, 0) == getPlayerAt(0, 2)
				&& getPlayerAt(2, 0) != Player.NOBODY;
		if (samePlayerOnOtherDiagonal)
		{
			winner = getPlayerAt(2, 0);
			return true;
		}
		return false;
	}

	private boolean isaColumnAllTickedBySamePlayer()
	{
		boolean result = true;
		for (int column = 0; column < 3; column++)
		{
			Player columnPlayer = null;
			result = true;
			for (int row = 0; row < 3; row++)
			{
				final Player cellPlayer = getPlayerAt(row, column);
				if (Player.NOBODY == cellPlayer)
				{
					result = false;
					continue;
				}
				if (null == columnPlayer)
				{
					columnPlayer = cellPlayer;
				}
				if (!columnPlayer.equals(cellPlayer))
				{
					result = false;
				}
				columnPlayer = cellPlayer;
			}
			if (result)
			{
				winner = columnPlayer;
				return true;
			}
		}
		return false;
	}

	private Player getPlayerAt(int row, int column)
	{
		final Player currentPlayer = getCellAt(column, row).getPlayer();
		return currentPlayer;
	}

	private Cell getCellAt(int column, int row)
	{
		for (Cell cell : cellInputs)
		{
			if (cell.column == column && cell.row == row)
			{
				return cell;
			}
		}
		return Cell.NULL_CELL;
	}

	private boolean isaRowAllTickedBySamePlayer()
	{
		boolean result = true;
		for (int row = 0; row < 3; row++)
		{
			Player rowPlayer = null;
			result = true;
			for (int column = 0; column < 3; column++)
			{
				final Player cellPlayer = getPlayerAt(row, column);
				if (Player.NOBODY == cellPlayer)
				{
					result = false;
					continue;
				}
				if (null == rowPlayer)
				{
					rowPlayer = cellPlayer;
				}
				if (!rowPlayer.equals(cellPlayer))
				{
					result = false;
				}
				rowPlayer = cellPlayer;
			}
			if (result)
			{
				winner = rowPlayer;
				return true;
			}
		}
		return false;
	}

	public Cell pickNextCellAvailable()
	{
		for (int row = 0; row < 3; row++)
		{
			for (int column = 0; column < 3; column++)
			{
				final Player cellPlayer = getPlayerAt(row, column);
				if (Player.NOBODY == cellPlayer)
				{
					return new Cell(row, column);
				}
			}
		}
		return Cell.NULL_CELL;
	}

	List<Cell> getAvailableCells()
	{
		final List<Cell> allCells = new ArrayList<Cell>(
				Game.BOARD_SIZE);

		for (int row = 0; row < 3; row++)
		{
			for (int column = 0; column < 3; column++)
			{
				final Player cellPlayer = getPlayerAt(row, column);
				if (Player.NOBODY == cellPlayer)
				{
					allCells.add(new Cell(row, column));
				}
			}
		}

		return allCells;
	}

	public int numberOfTickedCells()
	{
		int cellsTicked = 0;
		for (Cell cell : cellInputs)
		{
			if (Player.NOBODY != cell.getPlayer())
			{
				cellsTicked++;
			}
		}
		return cellsTicked;
	}

	public boolean isWinning(Cell winningCandidate, Player currentPlayer)
	{
		winningCandidate.setPlayer(currentPlayer);
		cellInputs.add(winningCandidate);
		boolean result = isThereAWinner();
		cellInputs.remove(winningCandidate);
		return result;
	}

	public Cell nextBestCell(Game game)
	{
		Cell nextCell = Cell.NULL_CELL;
		final boolean isFirstGo = game.board.getAvailableCells().size() == Game.BOARD_SIZE;
		final boolean isSecondGo = game.board.getAvailableCells().size() == Game.BOARD_SIZE - 1;
		if (isFirstGo || isSecondGo)
		{
			nextCell = pickOneCellAtRandom();
		}
		if (Cell.NULL_CELL != nextCell)
		{
			return nextCell;
		}
		nextCell = pickWiningCell(game.getCurrentPlayer());
		if (Cell.NULL_CELL != nextCell)
		{
			return nextCell;
		}
		nextCell = pickAdversaryWinningCell(game);
		if (Cell.NULL_CELL != nextCell)
		{
			return nextCell;
		}
		return pickNextCellAvailable();
	}

	private Cell pickOneCellAtRandom()
	{
		while (true)
		{
			int row = r.nextInt(2) + 1;
			int column = r.nextInt(2) + 1;
			if (Cell.NULL_CELL == getCellAt(column, row))
			{
				return new Cell(row, column);
			}
		}
	}

	Cell pickWiningCell(Player currentPlayer)
	{
		for (Cell cell : getAvailableCells())
		{
			if (isWinning(cell, currentPlayer))
			{
				return new Cell(cell.row, cell.column);
			}
		}
		return Cell.NULL_CELL;
	}

	Cell pickAdversaryWinningCell(Game game)
	{
		Player adversary = game.adversary();
		Cell adversaryWinningCell = pickWiningCell(adversary);
		return adversaryWinningCell;
	}

}
