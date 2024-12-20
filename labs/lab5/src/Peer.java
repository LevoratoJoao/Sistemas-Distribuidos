/**
 * Lab05: Sistema P2P
 *
 * Autor: João Vitor Levorato de Souza, Eduardo Yuji Yoshida Yamada
 * Ultima atualizacao: 10/12/2024
 *
 * Item 2 DONE: interface mostrando os peers ativos foi feita na funcao interfaceGrafica(), linha 58
 * Item 3 DONE: peer desconecta e outro entra no lugar na lista, linha 174
 * Item 4 DONE: tambem feito dentro da funcao interfaceGrafica(), usuario escolhe o peer (caso ele digite um valor invalido eh solicitado novamente)
 *
 * Referencias:
 * https://docs.oracle.com/javase/tutorial/essential/io
 * http://fortunes.cat-v.org/
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Peer implements IMensagem{

	ArrayList<PeerLista> alocados;

	public Peer() {
		  alocados = new ArrayList<>();
	}

	//Cliente: invoca o metodo remoto 'enviar'
	//Servidor: invoca o metodo local 'enviar'
	@Override
	public Mensagem enviar(Mensagem mensagem) throws RemoteException {
		Mensagem resposta;
		try {
			System.out.println("Mensagem recebida: " + mensagem.getMensagem());
			resposta = new Mensagem(parserJSON(mensagem.getMensagem()));
		} catch (Exception e) {
			e.printStackTrace();
			resposta = new Mensagem("{\n" + "\"result\": false\n" + "}");
		}
		return resposta;
	}

	public static boolean isNumeric(String str) {
		try {
		  Integer.parseInt(str);
		  return true;
		} catch(NumberFormatException e){
		  return false;
		}
	}

	public PeerLista interfaceGrafica(List<PeerLista> listaPeers) throws IOException {
		System.out.println("Lista de PEERs ativos:");
		for (int i = 0; i < listaPeers.size(); i++) {
			System.out.println((i + 1) +" - " + listaPeers.get(i).getNome());
		}
		System.out.println("Digite o número do PEER que deseja utilizar");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = -1;
		while (n < 0 || n > 4) {
			String input = br.readLine();
			if (isNumeric(input)) {
				n = Integer.parseInt(input);
			}
			if (n < 0 || n > 4) {
				System.out.println("Número invalido");
			}
		}
		PeerLista peer = listaPeers.get(n - 1);
		return peer;
	}

    public String parserJSON(String json) {
		String result = "false";

		String fortune = "-1";

		String[] v = json.split(":");
		System.out.println(">>>" + v[1]);
		String[] v1 = v[1].split("\"");
		System.out.println(">>>" + v1[1]);
		if (v1[1].equals("write")) {
			String [] p = json.split("\\[");
			 System.out.println(p[1]);
			 String [] p1 = p[1].split("]");
			 System.out.println(p1[0]);
			 String [] p2 = p1[0].split("\"");
			 System.out.println(p2[1]);
			 fortune = p2[1];

			// Write in file
			Principal pv2 = new Principal();
			pv2.write(fortune);
		} else if (v1[1].equals("read")) {
			// Read file
			Principal pv2 = new Principal();
			fortune = pv2.read();
		}

		result = "{\n" + "\"result\": \"" + fortune + "\"" + "}";
		System.out.println(result);

		return result;
	}

    public void iniciar(){

    try {
    		//Adquire aleatoriamente um ID do PeerList
    		List<PeerLista> listaPeers = new ArrayList<>();
    		for( PeerLista peer : PeerLista.values())
    			listaPeers.add(peer);

    		Registry servidorRegistro;
    		try {
    			servidorRegistro = LocateRegistry.createRegistry(1099);
    		} catch (java.rmi.server.ExportException e){ //Registro jah iniciado
    			System.out.print("Registro jah iniciado. Usar o ativo.\n");
    		}
    		servidorRegistro = LocateRegistry.getRegistry(); //Registro eh unico para todos os peers
    		String [] listaAlocados = servidorRegistro.list();
    		for(int i=0; i<listaAlocados.length;i++)
    			System.out.println(listaAlocados[i]+" ativo.");

    		SecureRandom sr = new SecureRandom();

			PeerLista peer = interfaceGrafica(listaPeers);

    		int tentativas=0;
    		boolean repetido = true;
    		boolean cheio = false;
    		while(repetido && !cheio){
    			repetido=false;
    			//peer = listaPeers.get(sr.nextInt(listaPeers.size()));
    			for(int i=0; i<listaAlocados.length && !repetido; i++){

    				if(listaAlocados[i].equals(peer.getNome())){
    					System.out.println(peer.getNome() + " ativo. Tentando proximo...");
    					repetido=true;
    					tentativas=i+1;
    				}

    			}
    			//System.out.println(tentativas+" "+listaAlocados.length);

    			//Verifica se o registro estah cheio (todos alocados)
    			if(listaAlocados.length>0 && //Para o caso inicial em que nao ha servidor alocado,
    					                     //caso contrario, o teste abaixo sempre serah true
    				tentativas==listaPeers.size()){
    				cheio=true;
    			}
    		}

    		if(cheio){
    			System.out.println("Sistema cheio. Tente mais tarde.");
    			System.exit(1);
    		}

            IMensagem skeleton  = (IMensagem) UnicastRemoteObject.exportObject(this, 0); //0: sistema operacional indica a porta (porta anonima)

            servidorRegistro.rebind(peer.getNome(), skeleton);
            System.out.print(peer.getNome() +" Servidor RMI: Aguardando conexoes...");

            //---Cliente RMI
			new ClienteRMI().iniciarCliente(peer);
			listaPeers.remove(peer);
			servidorRegistro.unbind(peer.getNome());


        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Peer servidor = new Peer();
		servidor.iniciar();
    }
}
