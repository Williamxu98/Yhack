package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Games.Engine;
import Games.Pong;

public class Server implements Runnable {

	public final static int PORT = 4200;
	ServerSocket server;
	public static ArrayList<Lobby> lobbies;
	public static ArrayList<Lobby> toRemove;

	public Server() {
		try {
			server = new ServerSocket(PORT);
			lobbies = new ArrayList<Lobby>();
			toRemove = new ArrayList<Lobby>();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	/**
	 * Accept clients
	 */
	public void run() {

		while (true) {
			try {
				Socket socket = server.accept();

				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter writer = new PrintWriter(socket.getOutputStream());
				
				// Wait for client input for which game
				char gameType = reader.readLine().charAt(0);
				
				Lobby thisLobby = null;
				
				for (Lobby lobby: lobbies)
				{
					if (!lobby.isFull() && gameType == lobby.gameType)
					{
						thisLobby = lobby;
						break;
					}
				}
				
				if (thisLobby == null)
				{
					switch(gameType)
					{
					case Engine.PONG:
						thisLobby = new Lobby(new Pong(gameType));
						break;
					case Engine.TRON:
						break;
					case Engine.GRAVITY:
						break;
					}
				}
				
				Client player = new Client(socket, reader, writer, thisLobby);
				
				thisLobby.clients.add(player);
				
				for (Lobby lobby: toRemove)
				{
					lobbies.remove(lobby);
				}
				
				toRemove.clear();

				System.out.println("Player has connected");

			} catch (IOException e) {
				System.out.println("IO error");
				e.printStackTrace();
			}

		}
	}
	
	public static void removeLobby(Lobby lobby)
	{
		toRemove.add(lobby);
	}

}
