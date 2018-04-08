/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesmulticast;

import java.security.*;

/**
 *
 * @author wagner
 */
//classe para armazenar o cliente e chaves criptograficas a serem listadas
public class ClientData {
    private Client cl;
    private PublicKey pk;
    private PrivateKey prvk;
    public ClientData(Client novoCl, PublicKey chavePublica, PrivateKey chavePrivada){
        this.cl = novoCl;
        this.pk = chavePublica;
        this.prvk = chavePrivada;
    }
    public Client getCliente (){
        return this.cl;
    }
    public PublicKey getPublicKey(){
        return this.pk;
    }
    private PrivateKey getPrivateKey(){
        return this.prvk;
    }    
}
