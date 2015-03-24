/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package russ.camera;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Viet
 */
public class UDPOperation {
    private DatagramSocket socket;
    private ServerSocket listenerOfTCPSocket;
    private Socket tcpSocket;
    private BufferedReader tcpInput;
    private PrintStream tcpOutput;
    private final VideoStream vs;
    private String run;
    
    public UDPOperation()
    {
        try {
            socket = new DatagramSocket();
            listenerOfTCPSocket = new ServerSocket(5001);
            System.out.println("Establishing TCP communicating...");
            tcpSocket = listenerOfTCPSocket.accept();
            System.out.println("done.");
            tcpInput = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
            tcpOutput = new PrintStream(tcpSocket.getOutputStream(),true);
            listenerOfTCPSocket.close();
            socket.setSendBufferSize(300000);
        } catch (SocketException ex) {
            Logger.getLogger(UDPOperation.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(UDPOperation.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        vs = new VideoStream();
        run = "0";
    }
    
    public void play() throws IOException
    {
        while(run.equals("0"))
        {
            if(tcpInput.ready())
            {
                run = tcpInput.readLine();
            }
        }
        
        while(socket != null && tcpSocket != null && run.equals("1"))
        {
            byte[] temp = vs.getnextframe();//get the image frame
            InetAddress dest = InetAddress.getByName("192.168.1.10");//ip address of the computer, which the image will be sent to
            System.out.println(temp.length);
            tcpOutput.println(temp.length);//inform the size of this image to the client computer
            int bytePointer = 0;//this pointer will browse through the array of bytes of the image frame
            
            //when the image size is greater than 30000 bytes
            while(temp.length - bytePointer >= 30000)
            {
                byte[] temp1 = new byte[30000];
                System.arraycopy(temp, bytePointer, temp1, 0, 30000);
                DatagramPacket sendPacket = new DatagramPacket(temp1, temp1.length, dest, 5000);
                bytePointer += 30000;
                socket.send(sendPacket);
                System.out.println("sent: "+sendPacket.getLength());
            }
            
            //when the image size is equal or smaller than 30000 bytes, or for processing the rest of the image frame
            byte[] temp1 = new byte[temp.length - bytePointer];
            System.arraycopy(temp, bytePointer, temp1, 0, temp1.length);
            DatagramPacket sendPacket = new DatagramPacket(temp1, temp1.length, dest, 5000);
            socket.send(sendPacket);
            System.out.println("sent last: "+sendPacket.getLength());
            
            while(!tcpInput.ready())
            {}
            run = tcpInput.readLine();
        }
        
        socket.close();
        tcpSocket.close();
        System.exit(1);
    }
    
    
}
