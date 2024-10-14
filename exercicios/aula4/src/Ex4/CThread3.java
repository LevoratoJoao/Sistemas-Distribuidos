package Ex4;

class CThread3 implements Runnable { 
	Thread thrd; 
	static CVetorSoma3 sa = new CVetorSoma3(); 
	int a[];
	int resposta;

	CThread3(String name, int nums[]) {
		thrd = new Thread(this, name); 
		a = nums;  //Thread inicia com acesso ao recurso compartilhado
		thrd.start(); 
	}

	public void run() {
		int sum;

		System.out.println(thrd.getName() + " iniciou.");

		//TODO2
		synchronized(sa) {
			resposta = sa.somarVetor(a);
		}
		 
		System.out.println("Soma para " + thrd.getName() + " eh " + resposta); 
		System.out.println(thrd.getName() + " terminando.");
	}
}