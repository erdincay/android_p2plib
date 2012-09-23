package org.p2plib.peer.selection.bluetooth;

import java.util.ArrayList;
import java.util.List;

import org.p2plib.R;
import org.p2plib.util.Lifecycle;
import org.p2plib.util.OnResumeListener;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

public class BluetoothPeerSelectionList extends FrameLayout implements
		BluetoothPeerSelectionView {

	private String targetComponent;
	private Switch blueToothSwitch;
	private TextView deviceName;
	private ListView listView;
	private List<BluetoothDevice> devices;
	private Activity context;

	public BluetoothPeerSelectionList(Activity context, String targetComponent,
			Lifecycle lifecycle) {
		super(context);
		setId(666);
		this.context = context;
		devices = new ArrayList<BluetoothDevice>();
		this.targetComponent = targetComponent;
		LayoutInflater.from(context)
				.inflate(R.layout.peer_selection_list, this);

		this.blueToothSwitch = (Switch) findViewById(R.id.peer_connection_technology_switch);
		this.deviceName = (TextView) findViewById(R.id.device_name_text_view);
		this.listView = (ListView) findViewById(R.id.peer_list_view);

		this.listView.setAdapter(new BluetoothListAdapter(devices, context));

		BluetoothPeerSelectionController controller = new BluetoothPeerSelectionController(
				context, this, lifecycle);

		blueToothSwitch.setOnCheckedChangeListener(controller);

		lifecycle.addOnResumeListener(new OnResumeListener() {

			public void onResume(Context context) {
				fireDatasetChangedEvent();
			}
		});
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable state = super.onSaveInstanceState();

		SavedBluetoothPeerSelectionListState saveState = new SavedBluetoothPeerSelectionListState(
				state);

		saveState.setDevices(devices);

		return saveState;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {

		if (state instanceof SavedBluetoothPeerSelectionListState) {
			SavedBluetoothPeerSelectionListState saveState = (SavedBluetoothPeerSelectionListState) state;
			super.onRestoreInstanceState(saveState.getSuperState());
			this.devices.addAll(saveState.getDevices());
			fireDatasetChangedEvent();
		} else {
			super.onRestoreInstanceState(state);
		}
	}

	public void setBluetoothSwitchOn(boolean on) {
		blueToothSwitch.setChecked(on);
	}

	public void setBluetoothSwitchEnabled(boolean enabled) {
		blueToothSwitch.setEnabled(enabled);
	}

	public void setBluetoothDeviceName(String name) {
		deviceName.setText(name);
	}

	public void addDevice(BluetoothDevice device) {
		devices.add(device);
		fireDatasetChangedEvent();
	}

	public void removeDevice(BluetoothDevice device) {
		devices.remove(device);
		fireDatasetChangedEvent();
	}

	public void clearDevices() {
		devices.clear();
		fireDatasetChangedEvent();
	}

	public boolean isDeviceInList(BluetoothDevice device) {
		return devices.indexOf(device) != -1;
	}

	private void fireDatasetChangedEvent() {
		if (listView != null) {
			context.runOnUiThread(new Runnable() {

				public void run() {
					((BaseAdapter) listView.getAdapter())
							.notifyDataSetChanged();
				}
			});
		}
	}
}
