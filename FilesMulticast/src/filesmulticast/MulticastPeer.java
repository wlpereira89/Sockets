package filesmulticast;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.lang.Thread;


public class MulticastPeer{
    public static void main(String args[]){ 
		// args give message contents and destination multicast group (e.g. "228.5.6.7")
		MulticastSocket s =null;
		try {
			InetAddress group = InetAddress.getByName("228.5.6.7");
			s = new MulticastSocket(6789);
                        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
			s.joinGroup(group);
 				
			
            		//for(int i=0; i< 3;i++) { // get messages from others in group
                        
                        String texto = teclado.readLine();
 			while(!texto.equals("exit")){                                  
                                byte [] m = texto.getBytes();
                               
                                DatagramPacket me = new DatagramPacket(m, m.length, group, 6789);   
                                s.send(me);
                                Request nova = new Request(s);
                                nova.start();      
                                
                                texto = teclado.readLine();
  			}
			s.leaveGroup(group);		
		}catch (SocketException e){System.out.println("Socket: " + e.getMessage());
		}catch (IOException e){System.out.println("IO: " + e.getMessage());
		}finally {if(s != null) s.close();}
	}		      		
}
