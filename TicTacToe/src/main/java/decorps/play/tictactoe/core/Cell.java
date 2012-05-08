package decorps.play.tictactoe.core;

import decorps.play.tictactoe.util.Player;


public class Cell
{
	public final int row;
	public final int column;

	final private static int TOP = 0;
	final private static int LEFT = 0;
	final private static int CENTER = 1;
	final private static int MIDDLE = 1;
	final private static int RIGHT = 2;
	final private static int BOTTOM = 2;
	final private static int NULL = Integer.MAX_VALUE;

	final public static Cell TOP_LEFT = new Cell(TOP, LEFT);
	final public static Cell TOP_CENTER = new Cell(TOP, CENTER);
	final public static Cell TOP_RIGHT = new Cell(TOP, RIGHT);
	final public static Cell MIDDLE_LEFT = new Cell(MIDDLE, LEFT);
	final public static Cell MIDDLE_CENTER = new Cell(MIDDLE, CENTER);
	final public static Cell MIDDLE_RIGHT = new Cell(MIDDLE, RIGHT);
	final public static Cell BOTTOM_LEFT = new Cell(BOTTOM, LEFT);
	final public static Cell BOTTOM_CENTER = new Cell(BOTTOM, CENTER);
	final public static Cell BOTTOM_RIGHT = new Cell(BOTTOM, RIGHT);

	final public static Cell NULL_CELL = new Cell(NULL, NULL)
	{
		public Cell setPlayer(Player player)
		{
			return this;
		};
	};
	private Player player = Player.NOBODY;

	Cell(int row, int column)
	{
		this.row = row;
		this.column = column;
	}

	public Cell(Player player, int row, int column)
	{
		this(row, column);
		this.player = player;
	}

	public boolean equals(Object candidateObject)
	{
		if (!(candidateObject instanceof Cell))
		{
			return false;
		}
		final Cell candidateCell = (Cell) candidateObject;
		final boolean result = candidateCell.column == this.column
				&& candidateCell.row == this.row;
		return result;
	}

	public String toString()
	{
		return "row " + row + " and column " + column + " for player " + player;
	}

	public Cell setPlayer(Player currentPlayer)
	{
		this.player = currentPlayer;
		return this;

	}

	public Player getPlayer()
	{
		return player;
	}
}
