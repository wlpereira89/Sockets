/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesmulticast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author wagner
 */
public class FilesMulticast {

    /**
     * @param args the command line arguments
     */
   public static void main(String[] args){
        try{
            Random aleatorio = new Random();
            Scanner teclado = new Scanner(System.in);
            System.out.println("Digite o numero de Threads que deseja criar para o teste: \n");
            int nThreads = teclado.nextInt();
            Client[] clients = new Client[nThreads];
            InetAddress group = InetAddress.getByName("224.255.255.255");
            for(int i=0; i<nThreads;i++){
                int port = 9001;
                Client c = new Client(group,port);
                c.start();
                clients[i] = c;
            }
            // Buffer criado para armazenar as mensagens digitadas
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                //System.out.println("Digite uma nova mensagem: \n");
                String x = in.readLine();
                // verifica se foi digitado o comando para encerrar o programa
                if(x.toLowerCase().startsWith("sair")){
                    // seleciona aleatoriamente uma das Threads criadas para enviar a mensagem ao grupo, inclusive para ela mesma
                    clients[aleatorio.nextInt(nThreads)].send(x); 
                    System.out.println("Main: Comando sair selecionado \n");
                    in = null;
                    break;
                }
                // seleciona aleatoriamente uma das Threads criadas para enviar a mensagem ao grupo, inclusive para ela mesma
                clients[aleatorio.nextInt(nThreads)].send(x); 
            }
        }catch(IOException e){
            System.out.println("Main IOException: " + e);
        }
    }
    
}
