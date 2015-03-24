/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package russ.engine;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author vietdinh
 */
public class ServerTCPSocket {
    private ServerSocket listener;
    private Socket socket;
    
    public ServerTCPSocket(int portNum)
    {
        try {
            listener = new ServerSocket(portNum);
            System.out.println("Connecting...");
            socket = listener.accept();
            System.out.println("Connected!");
            listener.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    public Socket getSocket()
    {
        return socket;
    }
}
