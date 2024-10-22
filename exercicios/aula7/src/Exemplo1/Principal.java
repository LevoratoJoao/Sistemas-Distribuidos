package Exemplo1;
/**
 * TODO1: TCPServidor: Permita que o servidor receba varias conexoes
 *        de clientes, sem encerrar a conexao.
 * 
 * TODO2: Principal: Crie 10 (dez) clientes 
 * 
 * TODO3: TCPCliente: Faca que os clientes facam requisicoes automaticas
 *        para o servidor. 
 * 
 */
public class Principal {
    
    public static void main(String [] args){
        
        TCPServidor servidor = new TCPServidor();
        Thread ts = new Thread(servidor);
        ts.start();  
        
        TCPCliente[] clientList = new TCPCliente[10];
        Thread[] clientTs = new Thread[10];
        for (int i = 0; i < clientTs.length; i++) {
            clientList[i] = new TCPCliente();
            clientTs[i] = new Thread(clientList[i]);
            clientTs[i].start();
        }

    }
    
}