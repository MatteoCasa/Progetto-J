/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import gui.MainGUI;
import json.JSONDecoder;
import json.JSONEncoder;
import gui.TextUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import model.Game;

/**
 *
 * @author Nickelsilver
 */
public class Client implements Runnable {

    public Socket server;
    public BufferedReader in = null;
    public PrintStream out = null;
    final int port = 7777;
    String ip = "localhost";
    public boolean disconnected = false;

    @Override
    public void run() 
    {
        Scanner input = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        String name = input.nextLine();
        try 
        {
            server = new Socket(ip, port);
            System.out.println(server);
            in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            out = new PrintStream(server.getOutputStream(), true);
        } catch (IOException ex) 
        {
            System.out.println("Errore Sconosciuto!");
        }
        Game game = new Game();
        TextUI ui = new TextUI(game);
        MainGUI mainGui = new MainGUI(game);
        mainGui.pack();
        mainGui.setResizable(false);
        mainGui.setLocationRelativeTo(null);
        mainGui.setVisible(true);    
        //game.addObserver(ui);
        game.addObserver(mainGui);
        Sender.init(server);
        try 
        {
            String toDecode = in.readLine();
            if(JSONDecoder.getInstance(game).decode(toDecode) != null)
            {
                Sender.getInstance().sendRaw(JSONEncoder.getInstance().encodeGameJoined(name));
            }
        } 
        catch (IOException | NullPointerException ex) {
            disconnected = true;
        }
        while (true && !disconnected)
        {
            try 
            {
                String toDecode = in.readLine();
                //System.out.println(toDecode);
                JSONDecoder.getInstance(game).decode(toDecode);              
            } 
            catch (IOException | NullPointerException ex)
            {
                System.err.println("CONNESSIONE CON IL SERVER PERSA");
                disconnected = true;
            }
        }
        System.err.println("PROGRAMMA TERMINATO");
    }
}