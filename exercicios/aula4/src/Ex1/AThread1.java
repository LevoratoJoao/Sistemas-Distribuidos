package Ex1;

import java.util.Random;

/**
 *
 * @author lucio
 */
public class AThread1 implements Runnable {

    private int tempoDormir;
    private String nomeTarefa;
    private Random r = new Random();
    private int id;

    public AThread1(String nomeTarefa, int id) {
        super();
        this.nomeTarefa = nomeTarefa;
        this.id = id;
        tempoDormir = r.nextInt(1000);
    }//fim construtor

    public void run() {
        try {
            System.out.println("NomeTarefa:" + nomeTarefa +
            " tempoDormir:"+tempoDormir  + " Id da sua tarefa: " + this.id);
            Thread.sleep(tempoDormir);
        } catch (Exception e) {
            System.out.println("Excecao na thread: "
                    + e.getMessage());
        }
    }//fim construtor
}