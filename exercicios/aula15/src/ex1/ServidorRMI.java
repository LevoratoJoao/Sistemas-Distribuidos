package ex1;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServidorRMI implements ICEP { // Tanto cliente quanto o servidor implementam a mesma interface
	
	public static void main(String[] args) {
		try { // Código RMI fica sempre dentro do RMI 
			ServidorRMI objServidor = new ServidorRMI();            		
			ICEP stub = (ICEP) UnicastRemoteObject.exportObject(objServidor, 0); // Criação do STUB  
			Registry registro = LocateRegistry.createRegistry(1098); // Aloca registro em uma porta, pode ser alterada assim vários servidores diferentes podem rodar
			registro.rebind("servidorCEP", stub); // Chamada do registro pode ser com o bind ou rebind (rebind mantem o nome do registro caso o servidor caia)						
			System.out.println("Servidor iniciado.");
		} catch (RemoteException e) { //Excecao de comunicacao
			e.printStackTrace();
		} catch (Exception e){ // Pode haver várias outras exceções
			e.printStackTrace();
		}
	}

	@Override
	public String getCEP(String rua) throws RemoteException {
		String CEP="Nao encontrado.";
		if(rua.equals("Rua 123"))
			CEP = "123456-000";
		return CEP;
	}

    @Override
	public String getRua(String CEP) throws RemoteException {
		String rua ="Nao encontrado.";
		if(CEP.equals("123456-000"))
			rua = "Rua 123";
		return rua;
	}

	@Override
	public String getNome(Endereco endereco) throws RemoteException {
		String result ="Nao encontrado.";
		if(endereco.getCEP().equals("123456-000"))
			result = "Deivid";
		return result;
	}
}