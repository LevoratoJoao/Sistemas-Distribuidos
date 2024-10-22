package Exemplo2;

//Author: Lucio A. Rocha
//Last update: 27/03/2023
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServidor implements Runnable {

	private static final int PORTA=1234;
	public void run() {

		String mensagemRecebida;
		String mensagemEnviada;
		
		try {			
			DatagramSocket socket = new DatagramSocket(PORTA);
			System.out.println("Servidor UDP iniciado na porta: " + PORTA);
			
			byte[] dadosEnviados = new byte[128];
			byte[] dadosRecebidos = new byte[128];
			
			while(true){
				System.out.println("Aguardando conexoes...");
				
				DatagramPacket pacoteRecebido = new DatagramPacket(
						dadosRecebidos, dadosRecebidos.length);
				socket.receive(pacoteRecebido);
				
				mensagemRecebida = new String(pacoteRecebido.getData());
				System.out.println("Mensagem recebida: ["+mensagemRecebida+"]");
				mensagemRecebida = mensagemRecebida.trim();
				System.out.println("Mensagem recebida.trim: ["+mensagemRecebida+"]");
				System.out.println("Mensagem recebida.length: ["+mensagemRecebida.length()+"]");
				
				InetAddress IPAdress = pacoteRecebido.getAddress();				
				int porta = pacoteRecebido.getPort();				
				
				String novaMensagem = new String("[["+mensagemRecebida+"]]");
				dadosEnviados = novaMensagem.getBytes();
				
				DatagramPacket pacoteEnviado = new DatagramPacket(
						dadosEnviados, dadosEnviados.length, IPAdress, porta);
				socket.send(pacoteEnviado);
				
				System.out.println("Servidor enviou resposta para o cliente.");
				socket.close();
				System.out.println("SERVIDOR finalizado.");
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
