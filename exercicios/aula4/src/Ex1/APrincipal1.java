package Ex1;
/**
 * TODO1: Crie 2 (duas) novas threads 
 * 
 * TODO2: Modifique o tempo de sleep das threads para ate 1 segundo.
 * 
 * TODO3: AThread1: Crie no construtor da classe um identificador unico para cada thread.
 *                  O construtor devera atribuir a variavel de instancia ID.
 * 
 * TODO4: Crie 20 threads que entram em estado de sleep por ate 1 segundo, mas apenas entrarao em
 *        sleep as que tiverem ID impar.
 */

//Threads implementam interface Runnable
public class APrincipal1 {

    public static void main(String[] args) {
        Thread[] list = new Thread[20];
        for (int i = 0; i < list.length; i++) {
            list[i] = new Thread(new AThread1("t"+i, i));
        }
        for (Thread thread : list) {
            thread.start();
        }

        /* Thread obj;
        int id = 0;
        obj = new Thread(new AThread1("t1", id));
        obj.start();
        
        Thread obj2 = new Thread(new AThread1("t2", id++));
        obj2.start(); */
        
        //new Thread(new AThread1("t1")).start(); //.start sai do estado 'Novo' para o estado 'Executar'
        
    }
}
