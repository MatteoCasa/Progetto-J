/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import client.Client;

/**
 *
 * @author Nickelsilver
 */
public class ClientTest {

    public static void main(String[] args) {
        Client client = new Client();
        Thread mainThread = new Thread(client);
        mainThread.start();
    }
    
}
