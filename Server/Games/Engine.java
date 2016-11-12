package Games;

import java.util.ArrayList;

import Objects.Player;

public abstract class Engine implements Runnable{

	public final static char PONG = 'p';
	public final static char TRON = 't';
	public final static char GRAVITY = 'g';
	public char type;
	
	public ArrayList<Object> objects;
	
	public Player playerOne;
	public Player playerTwo;
	
	public Engine(char type, Player one, Player two)
	{
		this.type = type;
		this.objects = new ArrayList<Object>();
	}

	public void update()
	{
		
	}
	
}
