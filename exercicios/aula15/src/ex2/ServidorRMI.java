package ex2;

import java.rmi.RemoteException;

public class ServidorRMI implements ICEP, Runnable {
	
	public Thread thrd;
	private Compartilhado compartilhado;
	
	public ServidorRMI(String nome, Compartilhado compartilhado){
	    thrd = new Thread(this, nome);
	    
	    thrd.start();
	    this.compartilhado = compartilhado;
	}
	
	public void run(){
	    
		System.out.println(thrd.getName() + ": READY");	
		
		//Tenta produzir 10 vezes
		for(int i=0;i<10;i++){
			//dorme por um intervalo aleatorio
			dorme();
			
			System.out.println(thrd.getName() + ": Write["+i+"]");
			compartilhado.escrever(thrd.getName(), this);
			
		}
	}
	public void dorme(){
		try{
	    	Thread.sleep( (int) (Math.random() * 1000));
	    } catch (InterruptedException e){
	    	System.err.println(e.toString());
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
	public String getRua(String CEP) throws RemoteException { // TODO 3
		String rua="Nao encontrado.";
		if(CEP.equals("123456-000"))
			rua = "Rua 123";
		return rua;
	}
}