package de.hos.indirecttracking.wlan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author ZeusNet
 * 
 * Starts the WlanScanService when the phone is started
 *
 */
public class StartWlanServiceReceiver extends BroadcastReceiver {
	public StartWlanServiceReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent myIntent = new Intent(context, WLanScanService.class);
		context.startService(myIntent);
	}
}
