package ex1;

import java.io.Serializable;

public class Endereco implements Serializable {
    private String CEP;
    private String rua;

    
    public String getCEP() {
        return CEP;
    }
    public void setCEP(String cEP) {
        CEP = cEP;
    }
    public String getRua() {
        return rua;
    }
    public void setRua(String rua) {
        this.rua = rua;
    }

    
}
