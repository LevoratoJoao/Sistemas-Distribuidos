package ex2;

import java.rmi.AccessException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Programa produtor/consumidor
 * 
 * @author Lucio A. Rocha
 * @since 2023-04-09
 *
 */
public class Compartilhado {
	
	private boolean leitura=false; ////Nao permite a leitura antes do servico ser registrado
	private boolean escrita=true;

	public synchronized void ler(String nome){	
		
		while(!leitura){

		    try{
		    	System.out.println(nome + ": WAIT" );
		    	wait();
		    } catch (InterruptedException e ){
		    	System.err.println(e.toString());
		    }
		}//fim while
		
		leitura=false;
		escrita=true;
		
		///
		try {            
            Registry registro = 
            	LocateRegistry.getRegistry("localhost", 1099);            
            ICEP stub = (ICEP) registro.lookup("servidorCEP");            
            //System.out.println(nome + ": O CEP eh: " + stub.getCEP("Rua 123"));          
			System.out.println("A rua eh: " + stub.getRua("123456-000")); // TODO 2
		} catch (AccessException e) {  			
			//Excecao de permissao de acesso ao metodo remoto			
				e.printStackTrace();
		} catch (RemoteException e) {
			//Excecao de comunicacao
				e.printStackTrace();		
		} catch (NotBoundException e) { 
			//Excecao de localizar nome sem binding			
				e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		///
		
		
		notifyAll();  //'notify()' method wakes only one thread that is waiting for a notify
		System.out.println(nome +" Escrita:"+escrita+" Leitura:"+leitura);
	}
	public synchronized void escrever(String nome, ServidorRMI objServidor){			
		
		while (!escrita){
			try{
		    	System.out.println(nome + ": WAIT" );
		    	wait();
		    } catch (InterruptedException e ){
		    	System.err.println(e.toString());
		    }

		}//fim while
		escrita=false;
		leitura=true;
		
		///		
		try {			
			//Novo RMI Registry
			ICEP stub =
					(ICEP) UnicastRemoteObject.exportObject(objServidor, 0);			
			Registry registro = 
					LocateRegistry.createRegistry(1099);			
			registro.bind("servidorCEP", stub);								
			System.out.println("Servidor iniciado.");			
			
		} catch (RemoteException e) { //Excecao de comunicacao
			//e.printStackTrace();
			System.out.println("RMIRegistry ja iniciado.");			
			//Remove a exportacao anterior
			try {
				UnicastRemoteObject.unexportObject(objServidor,true);
				//Tenta novamente
				ICEP stub =
						(ICEP) UnicastRemoteObject.exportObject(objServidor, 0);				
				Registry registro = LocateRegistry.getRegistry("localhost",1099);
				registro.rebind("servidorCEP", stub);
				
			} catch (NoSuchObjectException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
								
			System.out.println("Servidor iniciado.");
		} catch (Exception e){
			e.printStackTrace();
		}		
		///
		
		notifyAll(); //'notify()' method wakes only one thread that is waiting for a notify
		System.out.println(nome +" Escrita:"+escrita+" Leitura:"+leitura);
	}	
	
}//fim classe

