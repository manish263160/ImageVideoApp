package com.imagevideoapp.Enums;

public enum STATUS {

	INACTIVE(0), ACTIVE(1) ,DELETE(2),SWEAR(3),BLOCK(4),
	ISLIKE(1),   // for like
	ISSHARE(2), // for share
	ISNEEDVIDEO(1),  // for need type video
	ISOFFERINGVIDEO(0), // for offer Type video
	ISFEED(0), //for promoting as feed
	ISAD(1), //for promoting as an ad
	IMAGE(1),
	VIDEO(2);
	public int ID;

	STATUS(int id) {
		this.ID = id;
	}
}
