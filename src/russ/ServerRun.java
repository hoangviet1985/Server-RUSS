/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package russ;

import russ.camera.UDPOperation;
import russ.engine.TCPOperation;

/**
 *
 * @author vietdinh
 */

public class ServerRun {
    
    public static void main (String[] agrs) throws Exception
    {
        //TCPOperation myTCPOperation = new TCPOperation();//initialize motors' operation, and sensors' operation
        UDPOperation myCPort = new UDPOperation();// initialize camera's operation
        
        //myTCPOperation.run();//start controling Russ's motors and sensors
        myCPort.play();//start the camera on raspberry pi
        
    }
}
