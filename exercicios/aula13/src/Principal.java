
/**
 * TODO1: Leve esse codigo para uma IDE de sua escolha.
 * TODO2: Inicie 2 (dois) processos. Faca o teste de troca de mensagens entre eles.
 * TODO3: Mostre uma mensagem de "boas-vindas" para o usuario que
 *        ingressou no grupo pela primeira vez.
 * TODO4: Mostre uma mensagem de "bom retorno" para o usuario que
 *        sair do grupo.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/*
 * A Thread dentro do processo eh usada para receber mensagens 
 * de outros pares
 */
public class Principal extends Thread {

    private static String usuario = null;
    private static InetAddress endereco;
    private static int porta;

    @Override
    public void run() {

        try {
            byte[] msg = new byte[128];
            MulticastSocket socket = new MulticastSocket(porta);
            socket.joinGroup(endereco);

            // Listener de mensagens de outros pares
            while (true) {
                DatagramPacket dgPacket = new DatagramPacket(msg, msg.length);
                socket.receive(dgPacket);
                String mensagem = new String(dgPacket.getData());

                // Mensagens que nao sao minhas sao exibidas aqui
                if (!mensagem.contains(usuario)) {
                    System.out.println("\n" + mensagem + "\n");
                    System.out.print("Digite a mensagem: ");
                }
                msg = new byte[128];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        try {
            porta = 1234;
            endereco = InetAddress.getByName("224.0.0.10");

            Principal chat = new Principal();
            chat.start(); // Aguarda mensagens de outros pares na thread

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(System.in));

            System.out.print("Digite o seu nome: ");
            usuario = br.readLine();

            MulticastSocket socket = new MulticastSocket();
            socket.joinGroup(endereco);

            byte[] msg = new byte[128];

            while (true) { // Minhas mensagens enviadas para outros pares

                System.out.print("Digite a mensagem: ");
                String mensagem = br.readLine();

                if (mensagem.equals("sair")) {
                    System.exit(0);
                }

                mensagem = usuario + " disse: " + mensagem;
                msg = mensagem.getBytes();
                DatagramPacket dgPacket = new DatagramPacket(msg, msg.length, endereco, porta);
                socket.send(dgPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
