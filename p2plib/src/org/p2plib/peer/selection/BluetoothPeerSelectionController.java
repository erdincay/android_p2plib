package org.p2plib.peer.selection;

import org.p2plib.util.Lifecycle;
import org.p2plib.util.OnPauseListener;
import org.p2plib.util.OnResumeListener;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class BluetoothPeerSelectionController extends BroadcastReceiver
		implements OnCheckedChangeListener, OnPauseListener, OnResumeListener {

	private BluetoothPeerSelectionView view;
	private Activity context;
	private BluetoothAdapter bluetoothAdapter;

	public BluetoothPeerSelectionController(Activity context,
			BluetoothPeerSelectionView view, Lifecycle lifecycle) {
		this.context = context;
		this.view = view;

		lifecycle.addOnPauseListener(this);
		lifecycle.addOnResumeListener(this);

		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		view.setBluetoothSwitchOn(bluetoothAdapter.isEnabled());

	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			bluetoothAdapter.enable();
		} else {
			bluetoothAdapter.disable();
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		int state = intent.getExtras().getInt(BluetoothAdapter.EXTRA_STATE);
		switch (state) {
		case BluetoothAdapter.STATE_OFF:
			view.setBluetoothSwitchOn(false);
			view.setBluetoothSwitchEnabled(true);
			break;

		case BluetoothAdapter.STATE_TURNING_OFF:
			view.setBluetoothSwitchEnabled(false);
			break;

		case BluetoothAdapter.STATE_ON:
			view.setBluetoothSwitchOn(true);
			view.setBluetoothSwitchEnabled(true);
			break;

		case BluetoothAdapter.STATE_TURNING_ON:
			view.setBluetoothSwitchEnabled(false);
			break;

		default:
			break;
		}
	}

	public void onResume(Context context) {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		context.registerReceiver(this, intentFilter);
	}

	public void onPause(Context context) {
		context.unregisterReceiver(this);
	}
}
