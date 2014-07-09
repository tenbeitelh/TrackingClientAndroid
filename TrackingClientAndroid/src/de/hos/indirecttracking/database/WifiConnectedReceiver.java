package de.hos.indirecttracking.database;

import org.json.JSONException;
import org.json.JSONObject;

import de.hos.indirecttracking.utils.ConnectionInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class WifiConnectedReceiver extends BroadcastReceiver {
	private String serviceURL = ConnectionInfo.getConnectionAdress();

	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nInfo = manager.getActiveNetworkInfo();
		
		if(nInfo.getType() == ConnectivityManager.TYPE_WIFI && nInfo.isConnected()){
			
			
		}
	}
}
