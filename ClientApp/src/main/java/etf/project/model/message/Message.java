package etf.project.model.message;

import java.io.Serializable;

import etf.project.model.messageuser.MessageUser;

/**
 * @author NemanjaDavidovic
 * @since 28.01.2023.
 * 
 * <p> Message... </p>
 *
 */

public class Message implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String typeOfMessage, data, dateOfCreating;
	private MessageUser sender, receiver;
	
	public Message(String typeOfMessage, String data, String dateOfCreating, MessageUser receiver) {
		super();
		this.typeOfMessage = typeOfMessage;
		this.data = data;
		this.dateOfCreating = dateOfCreating;
		this.receiver = receiver;
	}
	
	public Message() {
		super();
	}
	
	public String getTypeOfMessage() {
		return typeOfMessage;
	}
	
	public void setTypeOfMessage(String typeOfMessage) {
		this.typeOfMessage = typeOfMessage;
	}
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDateOfCreating() {
		return dateOfCreating;
	}
	public void setDateOfCreating(String dateOfCreating) {
		this.dateOfCreating = dateOfCreating;
	}
	public MessageUser getReceiver() {
		return receiver;
	}
	public void setReceiver(MessageUser receiver) {
		this.receiver = receiver;
	}

	public MessageUser getSender() {
		return sender;
	}

	public void setSender(MessageUser sender) {
		this.sender = sender;
	}
	
	

}
