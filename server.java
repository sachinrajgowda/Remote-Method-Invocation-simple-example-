import java.rmi.Naming;
import java.rmi.*;
import java.rmi.server.*;
import sun.applet.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.rmi.registry.*;

public class server extends UnicastRemoteObject implements atm_interface
{
    private static Map<Integer, acc> accounts = new HashMap<Integer, acc>();

    protected server() throws RemoteException
    {
        super();
    }

    public static void main(String[] args) throws Exception
    {
        try
        {
		initAccount();
		atm_interface atmService = new server();
		Registry reg=LocateRegistry.createRegistry(3334);
		reg.bind("localhost",atmService);
	        System.out.println("server started");
        }
        catch (Exception ex)
        {
            System.out.println("System error");
	    System.out.println(ex);
        }

    }

    @Override
    public String login(int id, String password) throws RemoteException
    {
        acc account = accounts.get(id);
        if (account == null)
        {
            return "Account is INVALID";
        }

        if (password.equals(account.password))
        {
            return "login successful";
        }
        return "login failed";
    }

    @Override
    public String deposit(int n, double d) throws RemoteException
    {
        acc account = accounts.get(n);
        if (account == null)
        {
            return "Account is INVALID";
        }

        if (d <= 0d)
        {
            return "Cannot deposit negative amount";
        }

        account.balance += d;
        return "Amount deposited successfully, new balance: " + account.balance;
    }

    @Override
    public String withdraw(int n, double d) throws RemoteException
    {
        acc account = accounts.get(n);
        if (account == null)
        {
            return "Account is INVALID";
        }

        if (d <= 0d)
        {
            return  "Cannot withdraw negative amount";
        }

        if (d >= account.balance)
        {
            return "Withdraw amount exceeds the balance.Cannot withdraw";
        }

        account.balance -= d;
        return "Amount withdrawn successfully, new balance: " + account.balance;
    }

    @Override
    public String balance(int n) throws RemoteException
    {
        acc account = accounts.get(n);
        if (account == null)
        {
            return "Account is INVALID";
        }

        return "Account balance: " + account.balance;
    }

    private static void initAccount()
    {
        acc a = new acc();
        a.id = 1;
        a.balance = 100.00;
        a.password = "1";
        accounts.put(a.id, a);
    }
}
