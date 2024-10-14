
package Ex2;
import java.util.Random;

/**
 *
 * @author lucio
 */
public class AThread2 extends Thread {//TODO2: extenda a classe Thread aqui {  

    private int tempoDormir;
    private String nomeTarefa;
    private Random r = new Random();

    public AThread2(String nomeTarefa) {
        super();
        this.nomeTarefa = nomeTarefa;
        tempoDormir = r.nextInt(5000);
        
        
        //TODO2: insira a chamada do metodo start() aqui
    }//fim construtor

    public void run() {
        
        //TODO3
        try {
            System.out.println("NomeTarefa:" + nomeTarefa +
            " tempoDormir:"+tempoDormir);
            Thread.sleep(tempoDormir);
        } catch (Exception e) {
            System.out.println("Excecao na thread: "
                    + e.getMessage());
        }
    }//fim construtor
}
