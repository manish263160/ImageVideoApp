package com.imagevideoapp.Enums;

public enum NOTIFICATION_TYPE {

	SENDNOW(1), SENDLATER(2);

	public int ID;

	NOTIFICATION_TYPE(int id) {
		this.ID = id;
	}
}
