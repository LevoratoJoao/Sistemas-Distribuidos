package Exemplo2;

//Author: Lucio A. Rocha
//Last update: 27/03/2023
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPCliente implements Runnable {

	private static final int PORTA=1234;
	
	//Job
	public void run() {

		String mensagemEnvio;
		String mensagemRetorno;
		try {
			System.out.println("Cliente UDP iniciado na porta: " + PORTA);
			System.out.print(">>>");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			DatagramSocket socket = new DatagramSocket();
			InetAddress IPAdress = InetAddress.getByName("localhost");

			byte[] dadosRecebidos = new byte[128];
			byte[] dadosEnviados = new byte[128];
			
			mensagemEnvio = in.readLine();

			System.out.println("Mensagem enviada: ["+mensagemEnvio+"]");
			System.out.println("Mensagem enviada.length: ["+mensagemEnvio.length()+"]");
			dadosEnviados = mensagemEnvio.getBytes();
			
			DatagramPacket pacoteEnvio = new DatagramPacket(
					dadosEnviados, dadosEnviados.length, IPAdress, PORTA);
			socket.send(pacoteEnvio);
			
			DatagramPacket pacoteResposta = new DatagramPacket(
					dadosRecebidos, dadosRecebidos.length);
			socket.receive(pacoteResposta);
			
			mensagemRetorno = new String(pacoteResposta.getData());
			
			System.out.println("RESPOSTA DO SERVIDOR: " + mensagemRetorno);
			socket.close();
			System.out.println("CLIENTE finalizado.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
