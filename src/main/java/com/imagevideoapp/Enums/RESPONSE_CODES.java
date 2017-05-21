package com.imagevideoapp.Enums;

/**
 * 
 * @author mukeshks
 *
 */
public enum RESPONSE_CODES {
	
	FAIL (1000, "FAIL" , "Something went wrong."),
	INCORRECT_JSON_FORMAT (1001, "The json request seems to be malformed.", "Something went wrong.Please try again!"),
	INVALID_AUTH_TOKEN_PASSED (1002, "Missing auth token.", "Something went wrong. Please try again!"),
	INVALID_METHOD_TYPE_PASSED(1003,"Method type not supported, make it POST call", "Oops! Something went wrong. Please try again!"),
	EMPTY_REQUEST_BODY(1004,"Request Body is empty", "Please try again!"),
	INVALID_USER_AUTH_KEY(1005,"",""),
	INVALID_EMAIL_ID(1006,"Invalid email","Please try with valid email"),
	INVALID_PASSWORD(1007,"Invalid password","Please check your password and try again!"),
	USER_NOT_ACTIVE(1008,"User not active. Please contact admin", "User not active. Please contact admin"),
	USER_NOT_FOUND(1009,"Email-id is not registered. Contact admin","Email is not registered with us"),
	INVALID_REQUEST(1010,"This is invalid request", "Please try with valid request"),
	INCORRECT_OLD_PASSWORD(1011,"Please enter correct old password", "Please enter correct old password"),
	INCORRECT_JSON_PARAM (1012, "The json request Parameter seems to be incorrect.", "Something went wrong.Please try again!"),
	SUCCESS (2000, "SUCCESS", "Transaction successful!"),
	USER_ID_NOT_EXIST(1013,"Invalid user id","User Not Found Please Try with a valid Id"),
	ROLE_ID_NOT_EXIST(1014,"Invalid role id","Role Not Found Please Try with a valid Id"),
	PERMISSION_ID_NOT_EXIST(1015,"Invalid permission id","Permission Not Found Please Try with a valid Id"),
	CLIENT_ID_NOT_EXIST(1015,"Invalid client id","Client Not Found Please Try with a valid Id"),
	EMAIL_ALREADY_EXIST(1016,"Email Already Registered","This Email is Already Exist, Please try with a other email"),
	ROLE_ALREADY_EXIST(1017,"Role Already Registered","This Role is Already Exist, Please try with a other Role"),
	DAO_EXCEPTION(1016,"FAIL","JDBC Exception"),
	INCORRECT_BELONG_ID (1018, "Invalid Belong Id" , "Please select valid user belong");
	//EMPTY_REQUEST_BODY
	
	private final int code;
	private final String desc;
	private final String message;

	private RESPONSE_CODES(int code, String description , String message) {
		this.code = code;
		this.desc = description;
		this.message = message;
	}

	public String getDescription() {
		return desc;
	}

	public int getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return code + ": " + desc;
	}
}
