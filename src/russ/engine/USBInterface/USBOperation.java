/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package russ.engine.USBInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vietdinh
 */
public class USBOperation {
    private BufferedReader input;
    private String inputLine;
    private OutputStream output;
    
    public USBOperation(USBPort myUSBPort)
    {   
        try {
            output = myUSBPort.getPort().getOutputStream();
            input = new BufferedReader(new InputStreamReader(myUSBPort.getPort().getInputStream()));
        } catch (IOException ex) {
            System.out.println(ex);
        } 
    }
    public boolean receivingDataReady()
    {
        try {
            return input.ready();
        } catch (IOException ex) {
            Logger.getLogger(USBOperation.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public String getData()
    {
        try {
            inputLine = input.readLine();
            if(inputLine.length() == 0)
                return null;
            else
                return inputLine;
        } catch (IOException ex) {
            System.out.println(ex);
            return null;
        }
    }
    
    public void sendData(int value)
    {
        try 
        {
            output.write(value);
        } 
        catch (IOException ex) 
        {
            System.out.println(ex);
        }
    }
}
