package ex2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICEP extends Remote{
	public String getCEP(String rua) throws RemoteException; 
	public String getRua(String CEP) throws RemoteException; // TODO 1
}
