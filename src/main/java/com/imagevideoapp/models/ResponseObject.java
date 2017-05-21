package com.imagevideoapp.models;

public class ResponseObject {

	/** contains the same HTTP Status code returned by the server */
	String status;
	
	/** application specific error code */
	int statusCode;
	
	/** message describing the error*/
	String message;
	
	/** extra information that might useful for developers */
	protected String description;	

	protected Object data;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
