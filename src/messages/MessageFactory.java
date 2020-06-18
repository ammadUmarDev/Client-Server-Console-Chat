package messages;

import java.lang.reflect.*;

public class MessageFactory {

	public MessageFactory(){}
	public Message getMessage(String messageType)
	{
		if (messageType.equals("LoginRequest"))
		{
			return (new LoginRequest());
		}
		System.out.println("Message Type does not exist");
		return (Message)null;
	}

	static public void main(String[]args)
	{
		MessageFactory mf=new MessageFactory();
		Message lr=mf.getMessage("LoginRequest");
		lr.setSenderUsername("zubs");
		System.out.println("Before");
		System.out.println(lr.getSenderUsername());

		lr.setField("username", "abc");
		lr.setField("password", "pass");

		System.out.println(lr.getField("username"));
		System.out.println(lr.getField("password"));

		byte[] arr=lr.toBinary();
		Message lr2=mf.getMessage("LoginRequest");
		lr2=lr2.fromBinary(arr);

		System.out.println("After");
		System.out.println(lr2.getSenderUsername());

		System.out.println(lr2.getField("username"));
		System.out.println(lr2.getField("password"));
	}
}
