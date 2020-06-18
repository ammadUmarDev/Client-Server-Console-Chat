package serverfiles;

import java.io.Serializable;

public class Account implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 6168837219198601107L;
	public String username="-1";
	public String password="-1";
	public boolean onlinestatus=false;
	public String fullname="-1";
	public String DOB="-1";
	public String phoneno="-1";

	public Account()
	{
		username="-1";
		password="-1";
		onlinestatus=false;
		fullname="-1";
		DOB="-1";
		phoneno="-1";
	}
	public Account(String username, String password, String fullname,	String DOB, String phoneno){
		this.username=username;
		this.password=password;
		this.onlinestatus=false;
		this.fullname= fullname;
		this.DOB= DOB;
		this.phoneno= phoneno;
	}
	public void display()
	{
		System.out.println("The username is:"+this.username);
		System.out.println("The password is:"+this.password);
	}
	public void accountassignment(Account ac)
	{
		this.username=ac.username;
		this.password=ac.password;
		this.onlinestatus=ac.onlinestatus;
		this.phoneno=ac.phoneno;
		this.DOB=ac.DOB;
		this.fullname=ac.fullname;
	}


}
