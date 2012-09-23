package org.p2plib.peer.selection.wifidirect;

import java.util.List;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WiFiDirectListAdapter extends BaseAdapter {

	private List<WifiP2pDevice> devices;
	private Context context;

	public WiFiDirectListAdapter(List<WifiP2pDevice> devices, Context context) {
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
			convertView = inflater.inflate(android.R.layout.simple_list_item_2,
					parent, false);
		}

		TextView deviceName = (TextView) convertView
				.findViewById(android.R.id.text1);

		TextView status = (TextView) convertView
				.findViewById(android.R.id.text2);

		return convertView;
	}
	
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}
}
