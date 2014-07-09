package de.hos.indirecttracking.bluetooth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartBluetoothServiceReceiver extends BroadcastReceiver {
	public StartBluetoothServiceReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent myIntent = new Intent(context, BluetoothScanService.class);
		context.startService(myIntent);
	}
	
}
