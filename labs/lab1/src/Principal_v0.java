
/**
 * Lab0: Leitura de Base de Dados N�o-Distribuida
 *
 * Autor: Lucio A. Rocha
 * Ultima atualizacao: 20/02/2023
 *
 * Referencias:
 * https://docs.oracle.com/javase/tutorial/essential/io
 *
 */

 import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
 import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Random;

public class Principal_v0 {

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

	public void iniciar() {

		FileReader fr = new FileReader();
		try {
			NUM_FORTUNES = fr.countFortunes();
			HashMap hm = new HashMap<Integer, String>();
			fr.parser(hm);
			fr.read(hm);
			fr.write(hm);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new Principal_v0().iniciar();
	}

}
