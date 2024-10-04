import java.net.ServerSocket;
import java.net.Socket;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
					// System.out.println(fortune.toString());

					// System.out.println(lineCount);
				} // fim while

			} catch (IOException e) {
				System.out.println("SHOW: Excecao na leitura do arquivo.");
			}
		}

		public String read(HashMap<Integer, String> hm) throws FileNotFoundException {

			// SEU CODIGO AQUI
			return hm.get(new Random().nextInt(NUM_FORTUNES));
		}

		public String write(HashMap<Integer, String> hm, String message) {

			// SEU CODIGO AQUI
			try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
				bufferedWriter.write("\n\n" + message + "\n%");
				return "Mensagem \"" + message + "\" escrita com sucesso\n";
			} catch (IOException e) {
				System.out.println("Error appending to file: " + e.getMessage());
			}
			return "Não foi possivel escrever a mensagem";
		}
	}


	private static Socket socket;
	private static ServerSocket server;

	private static DataInputStream entrada;
	private static DataOutputStream saida;

	private int porta = 1025;

	public String[] extractJson(String valor) {
		Pattern pattern = Pattern.compile("\"method\":\"([^\"]+)\",\\s*\"args\":\\[(.*?)\\]");
		Matcher matcher = pattern.matcher(valor);

		if (matcher.find()) {
			String method = matcher.group(1);
			String arguments = matcher.group(2);

			return new String[]{method, arguments};
		} else {
			System.out.println("Pattern not found");
		}
		return new String[] {};
	}

	public void iniciar() {
		System.out.println("Servidor iniciado na porta: " + porta);
		try {

			// Criar porta de recepcao
			server = new ServerSocket(porta);
			socket = server.accept();  //Processo fica bloqueado, ah espera de conexoes

			// Criar os fluxos de entrada e saida
			entrada = new DataInputStream(socket.getInputStream());
			saida = new DataOutputStream(socket.getOutputStream());

			while (true) {
				// Recebimento do valor inteiro
				String valor = entrada.readUTF();
				String[] json = extractJson(valor);
				
				HashMap hm = new HashMap<Integer, String>();
				FileReader fr = new FileReader();
				NUM_FORTUNES = fr.countFortunes();
				fr.parser(hm);

				switch (json[0]) {
					case "read":
						try {
							String resultado = fr.read(hm);
							saida.writeUTF(resultado);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						break;
					case "write":
						System.out.println("Write method");
						System.out.println(json[1]);
						String resultado = fr.write(hm, json[1]);
						saida.writeUTF(resultado);
						break;
					case "exit":
						socket.close();	
					break;
				
					default:
						System.out.println("Invalid option");
						break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		new Servidor().iniciar();

	}

}
