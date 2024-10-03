import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Random;

public class Servidor {

	public final static Path path = Paths.get("src/fortune-br.txt");
	private int NUM_FORTUNES = 0;

	public class FileReader {

		public int countFortunes() throws FileNotFoundException {

			int lineCount = 0;

			InputStream is = new BufferedInputStream(new FileInputStream(path.toString()));
			try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

				String line = "";
				while (!(line == null)) {

					if (line.equals("%"))
						lineCount++;

					line = br.readLine();

				} // fim while

				//System.out.println(lineCount);
			} catch (IOException e) {
				System.out.println("SHOW: Excecao na leitura do arquivo.");
			}
			return lineCount;
		}

		public void parser(HashMap<Integer, String> hm)
				throws FileNotFoundException {

			InputStream is = new BufferedInputStream(new FileInputStream(path.toString()));
			try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

				int lineCount = 0;

				String line = "";
				while (!(line == null)) {

					if (line.equals("%"))
						lineCount++;

					line = br.readLine();
					StringBuffer fortune = new StringBuffer();
					while (!(line == null) && !line.equals("%")) {
						fortune.append(line + "\n");
						line = br.readLine();
						// System.out.print(lineCount + ".");
					}

					hm.put(lineCount, fortune.toString());
					System.out.println(fortune.toString());

					System.out.println(lineCount);
				} // fim while

			} catch (IOException e) {
				System.out.println("SHOW: Excecao na leitura do arquivo.");
			}
		}

		public void read(HashMap<Integer, String> hm) throws FileNotFoundException {

			// SEU CODIGO AQUI
			System.out.println(hm.get(new Random().nextInt(NUM_FORTUNES)));
		}

		public void write(HashMap<Integer, String> hm) {

			// SEU CODIGO AQUI
			System.out.println("Escreva sua nova mensagem: ");
			StringBuffer message = new StringBuffer();
			//int nLine = 0;
			try (BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in))) {
				String line = null;
				while ((line = stdin.readLine()) != null && !line.isEmpty()){
					// if (nLine > 0) {
					// 	System.out.println("Autor: ");
					// 	message.append("\n");
					// 	message.append(stdin.readLine());
					// 	break;
					// }
					message.append(line);
					// nLine++;
				}
				stdin.close();
			} catch (IOException e) {
				System.out.println("Error reading the message: " + e.getMessage());
			}

			try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
				bufferedWriter.write("\n\n" + message + "\n%");
			} catch (IOException e) {
				System.out.println("Error appending to file: " + e.getMessage());
			}
		}
	}


	private static Socket socket;
	private static ServerSocket server;

	private static DataInputStream entrada;
	private static DataOutputStream saida;

	private int porta = 1025;

	public void iniciar() {
		System.out.println("Servidor iniciado na porta: " + porta);
		try {

			// Criar porta de recepcao
			server = new ServerSocket(porta);
			socket = server.accept();  //Processo fica bloqueado, ah espera de conexoes

			// Criar os fluxos de entrada e saida
			entrada = new DataInputStream(socket.getInputStream());
			saida = new DataOutputStream(socket.getOutputStream());

			// Recebimento do valor inteiro
			int valor = entrada.readInt();
			System.out.println(valor);

			// Processamento do valor
			String resultado = "";
			if (valor > 0)
				resultado = "O valor enviado eh maior que 0";
			else
				resultado = "O valor enviado eh menor que 0";

			// Envio dos dados (resultado)
			saida.writeUTF(resultado);

			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		new Servidor().iniciar();

	}

}
