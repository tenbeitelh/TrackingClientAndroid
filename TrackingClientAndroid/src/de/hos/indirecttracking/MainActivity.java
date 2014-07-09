package de.hos.indirecttracking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import de.hos.indirecttracking.utils.ServiceUtils;
import de.hos.indirecttracking.wlan.WLanScanService;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        if(!ServiceUtils.isServicRunning(this, WLanScanService.class)){
        	this.startService(new Intent(this, WLanScanService.class));
        }
        
        
		// ConnectivityManager conManager = (ConnectivityManager)
		// this.getSystemService(CONNECTIVITY_SERVICE);
		// NetworkInfo[] info = conManager.getAllNetworkInfo();
		//
		// for(NetworkInfo inf : info){
		// Log.d("NetworkInfo", inf.toString());
		// }
		//
		// WifiManager wifiManager = (WifiManager)
		// this.getSystemService(WIFI_SERVICE);
		// WifiInfo wiInfo = wifiManager.getConnectionInfo();
		// Log.d("WifiInfo", wiInfo.toString());
		//
		// //sth. like the MAC-Adress
		// wiInfo.getBSSID();
		//
		// List<ScanResult> results = wifiManager.getScanResults();
		// for(ScanResult result : results){
		// Log.d("ScanResult", result.toString());
		// }
		//
        //BluetoothManager bluetoothManager = (BluetoothManager) this.getSystemService(BLUETOOTH_SERVICE);
        //bluetoothManager.getConnectedDevices(BluetoothGatt.GATT);
        
    }
    
}
