package com.murph.objects;

public class Message {

	User u;
	private int messageId;
	private String sender;
	private String receiver;
	private String message;
	
	public Message() 
	{
		super();
	}
	

	public Message(User u, int messageId, String sender, String receiver,
			String message) {
		this.u = u;
		this.messageId = messageId;
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
	}


	public Message(int messageId, String sender, String receiver, String message) 
	{
		this.messageId = messageId;
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
	}


	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	public User getU() {
		return u;
	}


	public void setU(User u) {
		this.u = u;
	}


	@Override
	public String toString() {
		return messageId +  ". Message to: " + receiver + " --- Message from: " + sender 
				+  "\n" + "Message: " + message + "\n";
	}
	
	
	
}
