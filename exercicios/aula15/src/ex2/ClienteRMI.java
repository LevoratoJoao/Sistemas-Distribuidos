package ex2;

public class ClienteRMI implements Runnable{

    public Thread thrd;
    private Compartilhado compartilhado;
    public ClienteRMI(String nome, Compartilhado compartilhado){
        
        thrd = new Thread(this, nome);
        thrd.start();
        
        this.compartilhado = compartilhado;
        
    }

	public void run() {		
		//Tenta consumir 10 vezes
		for(int i=0;i<10;i++){			
			//dorme por um intervalo aleatorio
			dorme();
		    
			System.out.println(thrd.getName() + ": Read["+i+"]");
			compartilhado.ler(thrd.getName());
		}            
	}
	public void dorme(){
		try{
	    	Thread.sleep( (int) (Math.random() * 1000));
	    } catch (InterruptedException e){
	    	System.err.println(e.toString());
	    }
	}
}
