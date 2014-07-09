package de.hos.indirecttracking.model;

import java.text.SimpleDateFormat;
import java.util.Date;


public class WLanTracking {
	
	private String uuid;
	private String timestamp = null;
	
	private double latitude;
	private double longitude;
	
	private String wlanSSID;
	
	public WLanTracking(){
		super();
	}
	
	/**
	 * 
	 * @param uuid
	 * @param latitude
	 * @param longitude
	 * @param wlanSSID
	 * 
	 * Constructor for the initial creation of a Tracking object.
	 */
	public WLanTracking(String uuid, double latitude,
			double longitude, String wlanSSID) {
		super();
		this.uuid = uuid;
		this.timestamp = new SimpleDateFormat("YYYYMMDD - HH:MM:SS").format(new Date());
		this.latitude = latitude;
		this.longitude = longitude;
		this.wlanSSID = wlanSSID;
	}
	
	/**
	 * 
	 * @param uuid
	 * @param timestamp
	 * @param latitude
	 * @param longitude
	 * @param wlanSSID
	 * 
	 * Constructor for the recreation of an WLanTracking object from the database. The difference of this constructor to the 
	 * one for the initial creation is the aspect that the timestamp is set and not created within the constructor.
	 */
	public WLanTracking(String uuid, String timestamp, double latitude,
			double longitude, String wlanSSID) {
		super();
		this.uuid = uuid;
		this.timestamp = timestamp;
		this.latitude = latitude;
		this.longitude = longitude;
		this.wlanSSID = wlanSSID;
	}

	/**
	 * 
	 * @return Returns the univerially unique device ID
	 */
	public String getUuid() {
		return uuid;
	}
	
	/**
	 * 
	 * @param uuid The univerially unique device ID to be set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	/**
	 * 
	 * @return Returns the timestamp to an tracking object
	 */
	public String getTimestamp() {
		return timestamp;
	}
	
	/**
	 * Sets the timestamp if it was not set before
	 */
	public void setTimestamp(){
		if(this.timestamp == null){
			this.timestamp = new SimpleDateFormat("YYYYMMDD - HH:MM:SS").format(new Date());
		}
	}
	
	/*private void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}*/

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getWlanSSID() {
		return wlanSSID;
	}

	public void setWlanSSID(String wlanSSID) {
		this.wlanSSID = wlanSSID;
	}

	@Override
	public String toString() {
		return String
				.format("WLanTracking [uuid=%s, timestamp=%s, latitude=%s, longitude=%s, wlanSSID=%s]",
						this.uuid, this.timestamp, this.latitude, this.longitude, this.wlanSSID);
	}
	
}
