package de.hos.indirecttracking.sql;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import de.hos.indirecttracking.model.BluetoothTracking;
import de.hos.indirecttracking.model.WLanTracking;

public class TrackingSQLiteHelper extends SQLiteOpenHelper {

	// Database version
	private static final int DATABASE_VERSION = 1;
	// Database name
	private static final String DATABASE_NAME = "TRACKING_DB";

	// TABLE NAME
	private static final String WLAN_TABLE = "WLAN_TRACK";
	private static final String BT_TABLE = "BT_TRACK";

	// COLUMN NAME
	private static final String UUID = "UUID";
	private static final String TIMESTAMP = "TIMESTAMP";
	private static final String LATITUDE = "LATITUDE";
	private static final String LONGITUDE = "LONGITUDE";
	private static final String WLAN_SSID = "WLAN_SSID";
	private static final String BT_ADRESS = "BT_ADRESS";

	private static final String[] WLAN_COLUMNS = { UUID, TIMESTAMP, LATITUDE,
			LONGITUDE, WLAN_SSID };
	private static final String[] BT_COLUMNS = { UUID, TIMESTAMP, LATITUDE,
			LONGITUDE, BT_ADRESS };

	public TrackingSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String createWLanTrackingTable = "CREATE TABLE WLAN_TRACK ("
				+ "UUID Text," + "TIMESTAMP Text," + "LATITUDE Real,"
				+ "LONGITUDE Real," + "WLAN_SSID Text);";
		db.execSQL(createWLanTrackingTable);

		String createBTTrackingTable = "CRATE TABLE BT_TRACK (" + "UUID Text, "
				+ "TIMESTAMP Text," + "LATITUDE Real," + "LONGITUDE Real, "
				+ "BT_ADRESS Text);";
		db.execSQL(createBTTrackingTable);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS WLAN_TRACK");
		db.execSQL("DROP TABLE IF EXISTS BT_TRACK");
		this.onCreate(db);
	}

	public void addWlanTrack(WLanTracking track) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(UUID, track.getUuid());
		values.put(TIMESTAMP, track.getTimestamp());
		values.put(LATITUDE, track.getLatitude());
		values.put(LONGITUDE, track.getLongitude());
		values.put(WLAN_SSID, track.getWlanSSID());

		db.insert(WLAN_TABLE, null, values);

		db.close();
	}

	public void addBTTrack(BluetoothTracking track) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(UUID, track.getUuid());
		values.put(TIMESTAMP, track.getTimestamp());
		values.put(LATITUDE, track.getLatitude());
		values.put(LONGITUDE, track.getLongitude());
		values.put(BT_ADRESS, track.getBTAdress());

		db.insert(BT_TABLE, null, values);

		db.close();
	}

	public List<WLanTracking> getWLanTracksForUUID(String uuid) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(WLAN_TABLE, WLAN_COLUMNS, "UUID = ?",
				new String[] { uuid }, null,// groupBy
				null, // having
				null // orderBy
				);

		List<WLanTracking> tracks = new ArrayList<WLanTracking>();

		if (cursor.moveToFirst()) {
			do {
				WLanTracking track = new WLanTracking(cursor.getString(0),
						cursor.getString(1), cursor.getDouble(2),
						cursor.getDouble(3), cursor.getString(4));
				tracks.add(track);
			} while (cursor.moveToNext());
		}

		db.close();

		return tracks;
	}

	public List<BluetoothTracking> getBTTracksForUUID(String uuid) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(BT_TABLE, BT_COLUMNS, UUID + "=?",
				new String[] { uuid }, null, null, null);

		List<BluetoothTracking> tracks = new ArrayList<BluetoothTracking>();

		if (cursor.moveToFirst()) {
			do {
				BluetoothTracking track = new BluetoothTracking(
						cursor.getString(0), cursor.getString(1),
						cursor.getDouble(2), cursor.getDouble(3),
						cursor.getString(4));
				tracks.add(track);
			} while (cursor.moveToNext());
		}

		db.close();

		return tracks;
	}

	public List<WLanTracking> getAllWLanTracks() {
		SQLiteDatabase db = this.getReadableDatabase();

		String query = "SELECT * FROM " + WLAN_TABLE;

		Cursor cursor = db.rawQuery(query, null);

		List<WLanTracking> tracks = new ArrayList<WLanTracking>();

		if (cursor.moveToFirst()) {
			do {
				WLanTracking track = new WLanTracking(cursor.getString(0),
						cursor.getString(1), cursor.getDouble(2),
						cursor.getDouble(3), cursor.getString(4));
				tracks.add(track);
			} while (cursor.moveToNext());
		}

		db.close();

		return tracks;
	}

	public List<BluetoothTracking> geAllBTTracks() {
		SQLiteDatabase db = this.getReadableDatabase();

		String query = "SELECT * FROM " + BT_TABLE;

		Cursor cursor = db.rawQuery(query, null);

		List<BluetoothTracking> tracks = new ArrayList<BluetoothTracking>();

		if (cursor.moveToFirst()) {
			do {
				BluetoothTracking track = new BluetoothTracking(
						cursor.getString(0), cursor.getString(1),
						cursor.getDouble(2), cursor.getDouble(3),
						cursor.getString(4));
				tracks.add(track);
			} while (cursor.moveToNext());
		}

		return tracks;
	}

	public boolean removeWLanTrack(WLanTracking tracking) {
		SQLiteDatabase db = this.getWritableDatabase();

		int rowsAffected = db.delete(
				WLAN_TABLE,
				UUID + "=? AND " + TIMESTAMP + "=? AND " + LATITUDE + "=? AND "
						+ LONGITUDE + "=? " + "AND " + WLAN_SSID + "=?",
				new String[] { tracking.getUuid(), tracking.getTimestamp(),
						Double.toString(tracking.getLatitude()),
						Double.toString(tracking.getLongitude()),
						tracking.getWlanSSID() });
		if (rowsAffected > 0) {
			return true;
		}
		return false;
	}

	public boolean removeBTTrack(BluetoothTracking tracking) {
		SQLiteDatabase db = this.getWritableDatabase();
		int rowsAffected = db.delete(
				BT_TABLE,
				UUID + "=? AND " + TIMESTAMP + "=? AND " + LATITUDE + "=? AND "
						+ LONGITUDE + "=? AND " + BT_ADRESS + "=?",
				new String[] { tracking.getUuid(), tracking.getTimestamp(),
						Double.toString(tracking.getLatitude()),
						Double.toString(tracking.getLongitude()),
						tracking.getBTAdress() });
		
		if(rowsAffected > 0){
			return true;
		}
		return false;
	}
}