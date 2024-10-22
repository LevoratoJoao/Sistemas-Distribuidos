package Exemplo1;
//Author: Lucio A. Rocha
//Last update: 27/03/2023
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;


public class TCPCliente implements Runnable {

	private static final int PORTA=1234;	
	
	public void run() {
		
		String mensagem;
		String mensagemMod;
		try {
			System.out.println("Cliente iniciado na porta: " + PORTA);
			System.out.print(">>>");
			//BufferedReader  in = new BufferedReader(
			//		new InputStreamReader(System.in));
			Scanner in = new Scanner(System.in);
			
			Socket clienteSocket = new Socket("localhost", PORTA);
			DataOutputStream saida = new DataOutputStream(
					clienteSocket.getOutputStream());
			
			BufferedReader resposta = new BufferedReader(
					new InputStreamReader(clienteSocket.getInputStream()));
			
			//mensagem = in.readLine(); //Com BufferedReader
			mensagem = in.nextLine();   //Com Scanner
			saida.writeBytes(mensagem+"\n");
			mensagemMod = resposta.readLine();
			System.out.println("RESPOSTA DO SERVIDOR: " + mensagemMod);
			clienteSocket.close();
			System.out.println("CLIENTE finalizado.");
					
		} catch (Exception e){
			e.printStackTrace();
		}

	}

}
