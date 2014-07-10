package de.hos.indirecttracking;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;
import de.hos.indirecttracking.bluetooth.BluetoothScanService;
import de.hos.indirecttracking.utils.ServiceUtils;
import de.hos.indirecttracking.wlan.WLanScanService;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivtiy";

	private ToggleButton wlanToggle;
	private ToggleButton btToggle;
	
	private TextView wlanTV;
	private TextView btTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.wlanToggle = (ToggleButton) findViewById(R.id.wlanToggle);
		
		if (!ServiceUtils.isServicRunning(this, WLanScanService.class)) {
			ComponentName returnValue = this.startService(new Intent(this,
					WLanScanService.class));
			if (returnValue != null) {
				this.wlanToggle.setChecked(true);
				this.wlanTV.setText(R.string.wlan_enabled);
			} else {
				this.wlanToggle.setChecked(false);
				this.wlanTV.setText(R.string.wlan_disabled);
			}

		}

		this.wlanToggle
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							Log.d(TAG, "WLAN: isChecked");
							startService(new Intent(MainActivity.this,
									WLanScanService.class));
						} else {
							Log.d(TAG, "WLAN: !isChecked");
							stopService(new Intent(MainActivity.this,
									WLanScanService.class));
						}
					}
				});
		
		this.btToggle = (ToggleButton) findViewById(R.id.bluetoothToggle);

		if (BluetoothAdapter.getDefaultAdapter() != null) {
			if (!ServiceUtils.isServicRunning(this, BluetoothScanService.class)) {
				ComponentName returnValue = this.startService(new Intent(this,
						BluetoothScanService.class));
				if (returnValue != null) {
					this.btToggle.setChecked(true);
					this.btTV.setText(R.string.bt_enabled);
				} else {
					this.btToggle.setChecked(false);
					this.btTV.setText(R.string.bt_disabled);
				}
			}
		}
		else{
			this.btToggle.setChecked(false);
			this.btToggle.setEnabled(false);
			this.btTV.setText(R.string.bt_not_available);
		}
		
		this.btToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					Log.d(TAG, "BT: isChecked");
					startService(new Intent(MainActivity.this,
							BluetoothScanService.class));
				} else {
					Log.d(TAG, "BT: !isChecked");
					stopService(new Intent(MainActivity.this,
							BluetoothScanService.class));
				}

			}
		});

	}
}
