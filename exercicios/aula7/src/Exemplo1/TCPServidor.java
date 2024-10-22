package Exemplo1;
//Author: Lucio A. Rocha
//Last update: 27/03/2023
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class TCPServidor implements Runnable {

	private static final int PORTA=1234;	
	
	//Job
	public void run() {
		
		String mensagemCliente;
		
		try {			
			ServerSocket socket = new ServerSocket(PORTA);
			
			System.out.println("Servidor iniciado na porta: " + PORTA);
			//while(true){
				System.out.println("Aguardando conexoes...");
				
				Socket conexao = socket.accept();
				BufferedReader entradaDoCliente = new BufferedReader(
						new InputStreamReader(conexao.getInputStream()));
				
				DataOutputStream resposta = new DataOutputStream(
						conexao.getOutputStream());
				
				mensagemCliente = entradaDoCliente.readLine();
				
				resposta.writeBytes("[["+mensagemCliente+"]]\n");
				System.out.println("Resposta enviada para o cliente.");
				
			    //conexao.close();	
			    System.out.println("SERVIDOR finalizado.");
			//}
					
		} catch (Exception e){
			e.printStackTrace();
		}

	}

}
