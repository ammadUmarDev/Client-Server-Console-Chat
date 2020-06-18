package messages;

import java.io.Serializable;

import Serialization.SerializedObject;

public class LoginRequest extends Message implements Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = 3812376970258160182L;

	LoginRequest() {
		msgType = new String("LoginRequest");
		field1=new String();
		field2=new String();
	}

	@Override
	public byte[] toBinary() {
		return (new SerializedObject<LoginRequest>()).toByteStream(this);
		//byte[] array=new byte[25*1024*1024];
		//int occupiedLengthOfArray=0;
		//byte[] arr=(new SerializedObject<String>()).toByteStream(this);
	}

	@Override
	public LoginRequest fromBinary(byte[] array) {
		// TODO Auto-generated method stub
		return (new SerializedObject<LoginRequest>()).fromByteStream(array);
	}

	@Override
	public Object getField(String fieldName) {
		if (fieldName.equals("username"))
			return field1;
		else if (fieldName.equals("password"))
			return field2;
		return null;
	}

	@Override
	public void setField(String fieldName, Object fieldValue) {
		if (fieldName.equals("username"))
			field1 = new String((String) fieldValue);
		else if (fieldName.equals("password"))
			field2 = new String((String) fieldValue);
	}
}
