package org.p2plib.peer.selection;

import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BluetoothListAdapter extends BaseAdapter {

	private List<BluetoothDevice> devices;
	private Context context;

	public BluetoothListAdapter(List<BluetoothDevice> devices, Context context) {
		this.devices = devices;
		this.context = context;
	}

	public int getCount() {
		return devices.size();
	}

	public Object getItem(int position) {
		return devices.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(android.R.layout.simple_list_item_1,
					parent, false);
		}

		TextView deviceName = (TextView) convertView
				.findViewById(android.R.id.text1);
		deviceName.setText(devices.get(position).getName());

		return convertView;
	}
}
