/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package russ.camera;

/**
 *
 * @author vietdinh
 */
//VideoStream

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.videoio.*;
import org.opencv.imgcodecs.Imgcodecs;
import static org.opencv.videoio.Videoio.*;

public class VideoStream 
{
    private final VideoCapture camera;
    private final Mat imageMatrix;

  public VideoStream()
  {

    System.out.println("Hello, OpenCV");
    // Load the native library.
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    camera = new VideoCapture(0);
    camera.set(CAP_PROP_FPS, 30);
      try {
          Thread.sleep(10);
      } catch (InterruptedException ex) {
          
      }
    if(!camera.isOpened()){
        System.out.println("Camera Error");
    }
    else{
        System.out.println("Camera OK?");
    }

    imageMatrix = new Mat();
    
    //camera.set(CAP_PROP_FRAME_WIDTH, 640);
    //camera.set(CAP_PROP_FRAME_HEIGHT, 480);
  }
 
  public byte[] getnextframe()
  {
    byte[] temp;
    camera.read(imageMatrix);
    MatOfByte byteMate = new MatOfByte();
    Imgcodecs.imencode(".jpg", imageMatrix, byteMate);
    temp = byteMate.toArray();
    
    return temp;
  }
}





