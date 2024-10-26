
/**
 * Laboratorio 1 de Sistemas Distribuidos
 *
 * Autor: Joâo Vitor Levorato de Souza e Eduardo Yuji Yoshida Yamada
 * Ultima atualizacao: 25/10/2024
 */

import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Cliente {
    public final static Path path = Paths.get("src/fortune-br.txt");

    private static Socket socket;
    private static DataInputStream entrada;
    private static DataOutputStream saida;

    private int porta=1025;

    private StringBuilder buildJson(String method, String[] args) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\n\"method\":\"" + method + "\",\n\"args\":[\"");
        for (int i = 0; i < args.length; i++) {
            jsonBuilder.append(args[i]);
        }
        jsonBuilder.append("\"]\n}");
        return jsonBuilder;
    }

    public void menu(DataInputStream entrada, DataOutputStream saida, BufferedReader br) {
        String resultado = "";

        while (true) {
            try {
                //Recebe do usuario algum valor
                System.out.println("Escolha uma das opcoes:\n1 - Leitura\n2 - Escrita\n3 - Sair");
                int valor = Integer.parseInt(br.readLine());

                switch (valor) {
                    case 1:
                        //Recebe-se o resultado do servidor
                        System.out.println("Leitura");
                        saida.writeUTF(String.valueOf(buildJson("read", new String[]{""})));

                        resultado = entrada.readUTF();

                        //Mostra o resultado na tela
                        System.out.println(resultado);
                        break;
                    case 2:
                        //O valor eh enviado ao servidor
                        System.out.println("Escreva sua nova mensagem: ");
                        String message = "";
                        try {
                            message = br.readLine();
                        } catch (IOException e) {
                            System.out.println("Erro ao ler a mensagem: " + e.getMessage());
                        }
                        saida.writeUTF(String.valueOf(buildJson("write", new String[]{message})));

                        resultado = entrada.readUTF();

                        //Mostra o resultado na tela
                        System.out.println(resultado);
                        break;
                    case 3:
                        saida.writeUTF("");
                        return;
                    default:
                        System.out.println("Opcao invalida");
                        break;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void iniciar(){
    	System.out.println("Cliente iniciado na porta: "+porta);

    	try {

            socket = new Socket("127.0.0.1", porta);

            entrada = new DataInputStream(socket.getInputStream());
            saida = new DataOutputStream(socket.getOutputStream());

            //Recebe do usuario algum valor
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            menu(entrada, saida, br);

            socket.close();

        } catch(Exception e) {
        	e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Cliente().iniciar();
    }
}
