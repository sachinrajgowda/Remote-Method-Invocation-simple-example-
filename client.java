import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class client
{
	public static void main(String[] args) throws Exception
	{
		try
		{
			Registry reg = LocateRegistry.getRegistry(3334);
			atm_interface atmService = (atm_interface) reg.lookup("localhost");
			reg.rebind("localhost",atmService);
			System.out.println("Client connected successfully \n\n\n");
			System.out.println("Welcome to CSUEB ATM");
			System.out.println("\nenter your account number and password");
			String cmd = "";
			String result ="";
			boolean loggedIn = false;
			BufferedReader userData = new BufferedReader(new InputStreamReader(System.in));
				try
				{
					cmd = userData.readLine();
					String[] tempLogin = cmd.split(" ");
					int id = Integer.parseInt(tempLogin[0]);
					String password = tempLogin[1];
					result = atmService.login(id, password);
					if (result.equals("login successful\n"))
					{
						loggedIn = true;
						System.out.println(result);
					}
					else
					{
						System.out.println(result);
					}
				}
				catch (Exception ex)
				{
					System.out.println("Please enter the command again correctly!");
				}

			while (loggedIn)
			{
				try
				{
					System.out.println("1.Deposit");
            				System.out.println("2.Withdraw");
            				System.out.println("3.Balance");
            				System.out.println("4.Exit");
                        		cmd = userData.readLine();
                    			String[] temp = cmd.split(" ");
                    			if (temp[0].equals("1"))
                    			{
						System.out.println("Enter the account number and amount to be deposited");
						cmd = userData.readLine();
						String[] a = cmd.split(" ");
                        			int id = Integer.parseInt(a[0]);
                        			double amount = Double.valueOf(a[1]);
                        			result = atmService.deposit(id, amount);
						System.out.println(result);
                    			}
                    			else if(temp[0].equals("2"))
                    			{
						System.out.println("Enter the account number and amount to be withdrawn");
						cmd = userData.readLine();
						String[] b = cmd.split(" ");
                        			int id = Integer.parseInt(b[0]);
                        			double amount = Double.valueOf(b[1]);
                        			result = atmService.withdraw(id, amount);
						System.out.println(result);                    			
					}
                    			else if (temp[0].equals("3"))
                    			{
						System.out.println("Enter the account number to show balance");
						cmd = userData.readLine();
						String[] c = cmd.split(" ");
                        			int id = Integer.parseInt(c[0]);
                        			result = atmService.balance(id);
						System.out.println(result);
                    			}
                    			else if(temp[0].equals("4"))
                    			{
						System.out.println("\nexiting\n\n");	
						System.exit(0);
						
                    			}
				}
                		catch (Exception ex)
                		{
                    			System.out.println("enter the correct option again");
                		}
            		}
		}
        	catch (Exception ex)
        	{
            		System.out.println(ex);
        	}
    	}
}
