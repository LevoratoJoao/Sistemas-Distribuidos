package Ex5;

/**
 * TODO 1: Crie 20 novas instancias de threads.
 * TODO 2: Execute as instancias no pool de threads.
 * 
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Principal {

    public class AThread implements Runnable {

        public void run() {
            System.out.println("Ola Mundo");
        }
    }

    public Principal() {
        ExecutorService pool = Executors.newCachedThreadPool();

        AThread[] lista = new AThread[50];
        for (int i = 0; i < lista.length; i++) {
            lista[i] = new AThread();
        }

        for (AThread aThread : lista) {
            pool.execute(aThread);
        }
        pool.shutdown();
    }

    public static void main(String[] args) {
        new Principal();
    }
}