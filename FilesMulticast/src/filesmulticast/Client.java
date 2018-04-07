/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesmulticast;

/**
 *
 * @author allan
 */
import java.net.MulticastSocket;
//import java.lang.Thread;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Random;
import java.util.Scanner;
//import java.net.UnknownHostException;

// Classe criada para gerar threads multiplas e verificar o envio das mensagens
public class Client extends Thread {    

    private MulticastSocket s;
    private InetAddress grupo;
    private int porta;

    public Client(InetAddress group,int port) throws IOException {
        this.grupo = group;
        this.porta = port;
        s = new MulticastSocket(port);
        //s.setTimeToLive(2); // especifica um tempo de vida para a comunicacao - deve estar entre 0 e 255, sendo 0 considerado infinito
        s.joinGroup(group);
    }  

    public void run(){
        // o metodo getId() pega a identificacao da thread em uso, sendo este um tipo long, unico durante o tempo de vida da thread
        System.out.println("Olá, eu sou ("+this.getId()+") estou ouvindo!");
        try{
            s.setSoTimeout(15000); // tempo(em ms) para encerrar o programa (desbloquear) caso nenhuma msg seja recebida
            while(true){
                DatagramPacket d = new DatagramPacket(new byte[256],256);
                s.receive(d);
                String mensagem = new String(d.getData());
                System.out.println("Thread: " + this.getId()+" recebeu a mensagem: " + mensagem);
                // verifica se foi digitado o comando para encerrar o programa
                if(mensagem.toLowerCase().startsWith("sair")){
                    return;
                }
            }
        }catch(SocketTimeoutException t){
            System.out.println("Tempo limite sem mensagens atingido. Thread: " + this.getId() + " encerrada."
                                 + " Digite o comando para encerrar o programa");
        }
        catch(IOException e){
            System.err.println("Run IOException: " + e);
        }
    } 

    public void send(String msg) throws IOException {
        byte[] data = msg.getBytes();
        System.out.println("Thread: " + this.getId() + " enviando a mensagem: " + data + " = " + msg);
        DatagramPacket d = new DatagramPacket(data,data.length,grupo,porta);
        s.send(d);
    }

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