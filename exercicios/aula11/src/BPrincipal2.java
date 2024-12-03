//Soma de vetor nao sincronizado

import java.util.concurrent.ExecutionException;

/**
 * TODO1: BPrincipal2: instancie 2 (duas) threads.
 * 
 * TODO2: BPrincipal2: insira o trecho de codigo:
 *           't1.thrd.join();' 
 *        no bloco try-catch 
 * 
 * TODO3: BThread2: define que a classe implemente a interface Runnable.
 * 
 * TODO4: BThread2: insira o trecho de codigo no construtor:
 *          'thrd.start();'
 * 
 */

 class BPrincipal2 { 
	public static void main(String args[]) {
		
		int a[] = {1, 2, 3, 4, 5};
		int TAM = 10;
		
		BThread2[] lista = new BThread2[TAM];
		for (int i=0; i < TAM; i++) {
		    lista[i] = new BThread2("Filho #"+i, a);
		}
		try {
		    for (int i=0; i < TAM; i++) {
		        lista[i].thrd.join();
		    }
		} catch (Exception e) {

        }
		BThread2 t1 = new BThread2("Filho #1", a); 
		//TODO1
		BThread2 t2 = new BThread2("Filho #2", a);
		
		try { 
			//TODO2
			t1.thrd.join();
			t2.thrd.join();
			
		} catch(InterruptedException exc) { 
			System.out.println("Thread principal interrompida.");
		}
	}
}