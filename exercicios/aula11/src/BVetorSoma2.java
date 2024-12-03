class BVetorSoma2 {
	private int sum;
	int somarVetor(int nums[]) {
		sum = 0; 

		for(int i=0; i<nums.length; i++) { 
			sum += nums[i]; 
			System.out.println("Total da " + Thread.currentThread().getName() + " eh " + sum);
			/*try { 
				Thread.sleep(10); 
			}
			catch(InterruptedException exc) {
				System.out.println("Thread interrompida.");
			}*/ 
		} 
		return sum;
	}}
