package cn.cerestech.framework.core;


import java.util.List;

import com.google.common.collect.Lists;

public class FileBillItemError {
	
	public int rowIndex;
	
	public List<Message> messages = Lists.newArrayList();
	
	public static FileBillItemError newError(){
		return new FileBillItemError();
	}
	
	public FileBillItemError addMessage(String code,String param){
		Message message = new Message(code,param);
		messages.add(message);
		return this;
	} 
	
	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public List<Message> getMessages() {
		return messages;
	}
	
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public class Message {
		private String code = "";
		private String message = "";

		public Message(String code, String message) {
			super();
			this.code = code;
			this.message = message;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}
		
		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}


}
