package ex1;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClienteRMI {

	public static void main(String[] args) {		
		try {            
            Registry registro = LocateRegistry.getRegistry("localhost", 1098); // Cria registro           
            ICEP stub = (ICEP) registro.lookup("servidorCEP"); // Inicializa STUB do cliente para o servidorCEP
            System.out.println("O CEP eh: " + stub.getCEP("Rua 123")); // Pega o o CEP da rua armazenado no servidor
            System.out.println("A rua eh: " + stub.getRua("123456-000"));
			Endereco endereco = new Endereco();
			endereco.setCEP("123456-000");
			System.out.println("O endereco eh do(a): " + stub.getNome(endereco));
		} catch (AccessException e) { //Excecao de permissao de acesso ao metodo remoto			
				e.printStackTrace();
		} catch (RemoteException e) { //Excecao de comunicacao
				e.printStackTrace();		
		} catch (NotBoundException e) { //Excecao de localizar nome sem binding			
				e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}            
	}
}
