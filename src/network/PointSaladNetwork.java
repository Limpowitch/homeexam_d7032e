package network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import player.IPlayer;
import player.online.OnlineHuman;

public class PointSaladNetwork implements INetwork{

	public void sendToAllPlayers(String message, ArrayList<IPlayer> players) {
		ArrayList<IPlayer> humanList = new ArrayList<>();
		
		for(IPlayer player : players) {
			if(!player.isBot()) {
				humanList.add(player);
			}
		}
		
		for(IPlayer player : humanList) {
			player.sendMessage(message);
		}
	}

	public void client(String ipAddress) throws Exception {
        //Connect to server
        Socket aSocket = new Socket(ipAddress, 2048);
        ObjectOutputStream outToServer = new ObjectOutputStream(aSocket.getOutputStream());
        ObjectInputStream inFromServer = new ObjectInputStream(aSocket.getInputStream());
        String nextMessage = "";
        while(!nextMessage.contains("winner")){
            nextMessage = (String) inFromServer.readObject();
            System.out.println(nextMessage);
            if(nextMessage.contains("Take") || nextMessage.contains("into")) {
                Scanner in = new Scanner(System.in);
                outToServer.writeObject(in.nextLine());
            } 
        }
    }

	public ServerSocket server(int numberPlayers, int numberOfBots, ArrayList<IPlayer> players) throws Exception {
        ServerSocket aSocket = null;
        if(numberPlayers > 1) {
            try {
                aSocket = new ServerSocket(2048);
                System.out.println("Created socket: " + aSocket);
            } catch (BindException e){
                System.out.println("BindException: " + e.getMessage());
                if (aSocket != null && !aSocket.isClosed()) {
                    aSocket.close();
                }
                throw e; // Re-throw to handle elsewhere
            }
        }

        // Accept connections
        for(int i = numberOfBots + 1; i < numberPlayers + numberOfBots; i++) {
            Socket connectionSocket = aSocket.accept();
            ObjectInputStream inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
            ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
            players.add(new OnlineHuman(i, connectionSocket, inFromClient, outToClient, false, true)); // Add an online client
            System.out.println("Connected to player " + i);
            outToClient.writeObject("You connected to the server as player " + i + "\n");
        }    

        return aSocket; // Return the created ServerSocket
    }    
    
	
}
