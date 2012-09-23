package org.p2plib.peer.selection;

import java.util.ArrayList;
import java.util.List;

import org.p2plib.R;
import org.p2plib.util.Lifecycle;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
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

	public BluetoothPeerSelectionList(Activity context, String targetComponent,
			Lifecycle lifecycle) {
		super(context);
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
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
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
		((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
	}

}
