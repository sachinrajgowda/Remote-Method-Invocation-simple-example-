import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.*;
import java.rmi.server.*;

public interface atm_interface extends Remote
{
    public String login (int id, String password) throws RemoteException;
    public String deposit (int n, double d) throws RemoteException;
    public String withdraw (int n, double d) throws RemoteException;
    public String balance (int n) throws RemoteException;
}
