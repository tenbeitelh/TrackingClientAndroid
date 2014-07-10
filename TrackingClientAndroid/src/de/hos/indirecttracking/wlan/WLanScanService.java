package de.hos.indirecttracking.wlan;

import java.util.List;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import de.hos.indirecttracking.model.WLanTracking;
import de.hos.indirecttracking.sql.TrackingSQLiteHelper;

public class WLanScanService extends Service {
	private static final String TAG = "WLanScanService";
	WifiManager wifiManager;
	WifiReceiver receiver;
	
	List<ScanResult> results;
	String UDID;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		this.UDID = telephonyManager.getDeviceId();

		wifiManager = (WifiManager) this.getSystemService(WIFI_SERVICE);
		receiver = new WifiReceiver();
		registerReceiver(receiver, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

		wifiManager.startScan();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try{
		unregisterReceiver(receiver);
		} 
		catch(IllegalArgumentException ex){
			Log.e(TAG, "Could not unregister Wlan receiver", ex);
		}
	}

	private class WifiReceiver extends BroadcastReceiver {
		private String WIFI_RECEIVER_TAG = "WLanReceiver - WlanScanService";
		private TrackingSQLiteHelper sqlite;

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.d(WIFI_RECEIVER_TAG, "onReceive");
			
			sqlite = new TrackingSQLiteHelper(context);

			double lat = 0;
			double lon = 0;

			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
			Location location = locationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location != null) {
				lat = location.getLatitude();
				lon = location.getLongitude();
			} else {
				location = locationManager
						.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				if (location != null) {
					lat = location.getLatitude();
					lon = location.getLongitude();
				}
			}

			results = wifiManager.getScanResults();
			for (ScanResult result : results) {
				WLanTracking track = new WLanTracking(UDID, lat, lon, result.SSID);
				Log.d(WIFI_RECEIVER_TAG, result.toString());
				sqlite.addWlanTrack(track);

			}
			
		}

	}
}