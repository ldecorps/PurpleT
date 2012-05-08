package decorps.play.tictactoe.util;

import java.io.BufferedReader;

import decorps.play.tictactoe.core.Game;

public interface Inputter
{

	public abstract String getInput(Game game, BufferedReader in);

}