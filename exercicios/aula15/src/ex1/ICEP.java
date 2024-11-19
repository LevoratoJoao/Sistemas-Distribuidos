package ex1;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICEP extends Remote {
	public String getCEP(String rua) throws RemoteException; // Todo metodo aqui pode disparar RemoteException
    public String getRua(String CEP) throws RemoteException;

    public String getNome(Endereco endereco) throws RemoteException;
}
