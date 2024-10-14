package Ex3;

//Soma de vetor nao sincronizado

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
	
        BThread2[] lista = new BThread2[20];
        for (int i = 0; i < lista.length; i++) {
            lista[i] = new BThread2("Filho #"+i, a);
        }
        
		try { 
            for (BThread2 bThread2 : lista) {
                bThread2.thrd.join();
            }
			//TODO2
			
		} catch(InterruptedException exc) { 
			System.out.println("Thread principal interrompida.");
		}
	}
}