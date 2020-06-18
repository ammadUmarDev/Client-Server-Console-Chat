package messages;

import java.io.Serializable;

public abstract class Message implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -8896689769147353604L;
	protected String msgType;
	protected String senderUsername;
	protected String field1;
	protected String field2;

	Message()
	{
		senderUsername=new String();
	}

	public String getMsgType() {
		return msgType;
	}

	public void setSenderUsername(String senderUsername) {
		this.senderUsername = new String(senderUsername);
	}

	public String getSenderUsername() {
		return senderUsername;
	}

	public abstract byte[] toBinary();

	public abstract Message fromBinary(byte[] array);

	public abstract Object getField(String fieldName);

	public abstract void setField(String fieldName, Object fieldValue);
}
