package Exemplo2;

/**
 * TODO1: UDPServidor: Permita que o servidor receba varias conexoes
 *        de clientes, sem encerrar a conexao.
 * 
 * TODO2: Principal: Crie 10 (dez) clientes 
 * 
 * TODO3: UDPCliente: Faca que os clientes facam requisicoes automaticas
 *        para o servidor.
 * 
 * No datagrama envia-se o ip e a porta pra então ela poder ser extraida quando chegar no servidor/cliente 
 * 
 */
public class Principal {
    
    public static void main(String [] args){
        
        UDPServidor servidor = new UDPServidor();
        Thread ts = new Thread(servidor);
        ts.start();


        UDPCliente[] clientList = new UDPCliente[10];
        Thread[] clientTs = new Thread[10];
        for (int i = 0; i < clientTs.length; i++) {
            clientList[i] = new UDPCliente();
            clientTs[i] = new Thread(clientList[i]);
            clientTs[i].start();
        }
    }
    
}