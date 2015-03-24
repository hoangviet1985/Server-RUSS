/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package russ.engine.USBInterface;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.util.Enumeration;

/**
 *
 * @author vietdinh
 */
public class USBPort {
    public static final int TIME_OUT = 2000;
    public static final int DATA_RATE = 9600;
    
    private CommPortIdentifier portId = null;
    private SerialPort serialPort;
    
    public USBPort(String port)
    {
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
        while(portEnum.hasMoreElements())
        {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            if(currPortId.getName().equals(port))
            {
                portId = currPortId;
                break;
            }
        }
        
        if(portId == null)
        {
            System.out.println("USB port not found!");
        }
        else
        {
            try {
                serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
                serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            } catch (PortInUseException | UnsupportedCommOperationException ex) {
                System.out.println(ex);
            }
        }
    }
    
    public SerialPort getPort()
    {
        return serialPort;
    }
    
}
