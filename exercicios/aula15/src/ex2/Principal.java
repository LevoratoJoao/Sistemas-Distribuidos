package ex2;

/**
 * DONE: ICEP: Implemente um novo metodo na interface.
 * DONE: ClienteRMI: Invoque o novo metodo atraves do stub.
 * DONE: ServidorRMI: Faca a implementacao do novo metodo da interface.
 * TODO4: ServidorRMI: Retorne a resposta do novo metodo para o ClienteRMI
 * TODO5: Principal: Implemente 10 (dez) objetos servidor e 10(dez) objetos cliente. 
 */


public class Principal {
	
	public static void main(String[] args) {
	 
		Compartilhado recurso = new Compartilhado();

		ServidorRMI[] listServidorRMI = new ServidorRMI[10];
		ClienteRMI[] listClienteRMI = new ClienteRMI[10];

		for (int i = 0; i < listServidorRMI.length; i++) {
			listServidorRMI[i] = new ServidorRMI("SERVIDOR " + i, recurso);
		}

		for (int i = 0; i < listClienteRMI.length; i++) {
			listClienteRMI[i] = new ClienteRMI("CLIENTE " + i, recurso);
		}
	 
	    try {
			for (int i = 0; i < listServidorRMI.length; i++) {
				listServidorRMI[i].thrd.join();
			}
			for (int i = 0; i < listClienteRMI.length; i++) {
				listClienteRMI[i].thrd.join();
			}
	    } catch(Exception e){
	    	e.printStackTrace();
	    }
	    System.out.println("Done.");
	    System.exit(0);
	 }
}