package com.polytech.RestService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class AnylogicServer {

	private Socket socket;
	
	public AnylogicServer() {
		
	}
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(1500);
            System.out.println("le serveur est a l'ecoute sur le port 1500");
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
            System.out.println("connexion accepté");
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                clientSocket.getInputStream()));
        
        String inputLine=null, outputLine;
        System.out.println("je ss la !!");
        while(inputLine == null ){
        	inputLine = in.readLine();
        	System.out.println("iteration");
        }
       
        System.out.println("message reçu : " + inputLine);
       // KnockKnockProtocol kkp = new KnockKnockProtocol();

     //   outputLine = kkp.processInput(null);
      //  out.println(outputLine);

//        while ((inputLine = in.readLine()) != null) {
//             outputLine = kkp.processInput(inputLine);
//             out.println(outputLine);
//             if (outputLine.equals("Bye."))
//                break;
//        }
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
        
        } catch (Exception e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(1);
        }

	}

}
