/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package russ.engine;

import russ.engine.USBInterface.USBPort;
import russ.engine.USBInterface.USBOperation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 *
 * @author vietdinh
 */
public class TCPOperation {

    private final USBPort myUSBPortInput;
    private final USBPort myUSBPortoutput;
    private final USBOperation usbInput;
    private final USBOperation usbOutput;
    private final ServerTCPSocket tcpSocket;
   
    public TCPOperation()
    {
        myUSBPortoutput = new USBPort("/dev/ttyACM1");//for raspberry pi
        usbOutput = new USBOperation(myUSBPortoutput);
        myUSBPortInput = new USBPort("/dev/ttyACM0");//for raspberry pi
        usbInput = new USBOperation(myUSBPortInput);
        tcpSocket = new ServerTCPSocket(9090);
    }
    
    public void run()
    {
        try
        {
            BufferedReader tcpInput = new BufferedReader(new InputStreamReader(tcpSocket.getSocket().getInputStream()));
            PrintStream tcpOutput = new PrintStream(tcpSocket.getSocket().getOutputStream(),true);
            while(!tcpSocket.getSocket().isClosed())
            {
                if(tcpInput.ready())
                {
                    int receive = tcpInput.read();
                //while(!tcpInput.ready())
                //{
                    //System.out.println(usbInput.getData());
                    if(receive == 106)
                    {
                        usbInput.sendData(receive);
                        if(usbInput.receivingDataReady())
                        {
                            tcpOutput.println(usbInput.getData());
                        }
                    }
                    else
                    {
                    usbOutput.sendData(receive);//send controling signal to arduino to control the motor
                    System.out.println(receive);
                    }

                    if(receive == 103 || receive == -1)
                    {
                        usbOutput.sendData(0);
                        myUSBPortoutput.getPort().close();
                        myUSBPortInput.getPort().close();
                        tcpSocket.getSocket().close();
                        System.out.println("lost connection!");
                    }
                }
                /*
                while(socket.isClosed())
                {
                    System.out.println("Connecting...");
                    socket = listener.accept();
                    System.out.println("Connected!");
                }*/
            }
            
        }
        catch(IOException ioe1)
        {
            System.out.println(ioe1);
        }
    }

}
