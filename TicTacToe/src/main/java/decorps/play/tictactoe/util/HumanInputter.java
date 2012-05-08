package decorps.play.tictactoe.util;

import java.io.BufferedReader;
import java.io.IOException;

import decorps.play.tictactoe.core.Game;

public class HumanInputter implements Inputter
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see decorps.play.tictactoe.util.Inputter#getInput()
	 */
	public String getInput(Game game, BufferedReader in)
	{
		String result = null;
		try
		{
			result = in.readLine();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return result;
	}

}
