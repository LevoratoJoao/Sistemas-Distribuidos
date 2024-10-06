
/**
 * Laboratorio 1 de Sistemas Distribuidos
 *
 * Autor: Lucio A. Rocha
 * Ultima atualizacao: 17/12/2022
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

    public void menu(DataInputStream entrada, DataOutputStream saida) {
        int i = 1;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String resultado = "";

        while (i!=3) {
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
                        StringBuilder message = new StringBuilder();
                        //int nLine = 0;
                        try {
                            String line = null;
                            while ((line = br.readLine()) != null && !line.isEmpty()){
                                // if (nLine > 0) {
                                // 	System.out.println("Autor: ");
                                // 	message.append("\n");
                                // 	message.append(stdin.readLine());
                                // 	break;
                                // }
                                message.append(line);
                                // nLine++;
                            }
                        } catch (IOException e) {
                            System.out.println("Error reading the message: " + e.getMessage());
                        }
                        saida.writeUTF(String.valueOf(buildJson("write", new String[]{String.valueOf(message)})));

                        resultado = entrada.readUTF();

                        //Mostra o resultado na tela
                        System.out.println(resultado);
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Opcao invalida");
                        break;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            i++;
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
//            System.out.println("Digite um numero: ");
//            int valor = Integer.parseInt(br.readLine());

            menu(entrada, saida);

//            //O valor eh enviado ao servidor
//            saida.writeInt(valor);
//
//            //Recebe-se o resultado do servidor
//            String resultado = entrada.readUTF();
//
//            //Mostra o resultado na tela
//            System.out.println(resultado);

            socket.close();

        } catch(Exception e) {
        	e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Cliente().iniciar();
    }

}
