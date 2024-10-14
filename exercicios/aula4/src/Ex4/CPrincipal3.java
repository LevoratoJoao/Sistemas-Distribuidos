package Ex4;
/**
 * TODO1: CPrincipal3.java: instancie 2 (duas) threads.
 * 
 * TODO2: CPrincipal3.java: Adicione o seguinte trecho de codigo:
 *          't1.thrd.join();'
 * 
 * TODO3: CThread3.java: adicione a sincronizacao da regiao critica.
 *        'synchronized(sa) //Acesso ah regiao critica'
 * 
 */

//Soma de vetor sincronizado
class CPrincipal3 { 
	public static void main(String args[]) {
		
		int a[] = {1, 2, 3, 4, 5};
// 		CThread3 t1 = new CThread3("1", a);
// 		CThread3 t2 = new CThread3("2", a);
		
		CThread3[] lista = new CThread3[50];
        for (int i = 0; i < lista.length; i++) {
            lista[i] = new CThread3("Filho #"+i, a);
        }
		
		try { 
			//TODO2
			for (CThread3 bThread3 : lista) {
                bThread3.thrd.join();
            }
			
		} catch(InterruptedException exc) { 
			System.out.println("Thread principal interrompida.");
		}
	}
}

