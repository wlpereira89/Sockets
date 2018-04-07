/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesmulticast;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.lang.Thread;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author a1657500
 */
public class Request extends Thread {
    
    MulticastSocket s;    
    

    public Request(MulticastSocket sea) throws UnknownHostException, IOException {
      
        this.s = sea;
      
    }

    @Override
    public void run() {        
        try {            
            
            while(true) {
                byte[] buffer = new byte[1000];
                DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                this.s.receive(messageIn);
                System.out.println("Received:" + new String(messageIn.getData()));
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
