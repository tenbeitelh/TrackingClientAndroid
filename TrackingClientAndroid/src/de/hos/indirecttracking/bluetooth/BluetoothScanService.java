package de.hos.indirecttracking.bluetooth;

import java.util.UUID;

import de.hos.indirecttracking.model.BluetoothTracking;
import de.hos.indirecttracking.sql.TrackingSQLiteHelper;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.telephony.TelephonyManager;

public class BluetoothScanService extends Service {
	BluetoothManager bManager;
	BluetoothAdapter bAdapter;

	BluetoothReceiver bReceiver;
	
	private String UDID;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onCreate() {
		super.onCreate();
	
		bAdapter = BluetoothAdapter.getDefaultAdapter();
		
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		this.UDID = telephonyManager.getDeviceId();
		
		if(bAdapter != null){
			if(bAdapter.isEnabled()){
				bReceiver = new BluetoothReceiver();
				
				registerReceiver(bReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
			}
			else{
				Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				this.startActivity(intent);
			}
			
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(bReceiver);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}
	
	private class BluetoothReceiver extends BroadcastReceiver{

		private TrackingSQLiteHelper sqlite;

		@Override
		public void onReceive(Context context, Intent intent) {
			
			String action = intent.getAction();
			
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
			
			if(BluetoothDevice.ACTION_FOUND.equals(action)){
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				
				BluetoothTracking track = new BluetoothTracking(UDID, lat, lon, device.getAddress());
				sqlite.addBTTrack(track);
				
				//TODO
			}
			
		}
		
	}
}
