package decorps.play.tictactoe.util;

import java.io.BufferedReader;

import decorps.play.tictactoe.core.Game;

public class ComputerInputter implements Inputter
{

	public String getInput(Game game, BufferedReader in)
	{
		return InputParser.formatCellPosition(game.nextBestCell());
	}

}
