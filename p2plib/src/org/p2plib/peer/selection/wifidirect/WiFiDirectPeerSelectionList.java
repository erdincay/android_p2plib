package org.p2plib.peer.selection.wifidirect;

import java.util.ArrayList;
import java.util.List;

import org.p2plib.R;
import org.p2plib.peer.selection.PeerSelectionEventBus;
import org.p2plib.util.Lifecycle;
import org.p2plib.util.OnResumeListener;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

public class WiFiDirectPeerSelectionList extends FrameLayout implements
		WiFiDirectPeerSelectionView, PeerSelectionEventBus {

	private String targetComponent;
	private Switch wiFiDirectSwitch;
	private TextView deviceName;
	private ListView listView;
	private List<WifiP2pDevice> devices;
	private Activity context;
	private WiFiDirectPeerSelectionListEventListener listener;
	private ProgressBar progressIndicator;

	public WiFiDirectPeerSelectionList(Activity context,
			String targetComponent, Lifecycle lifecycle) {
		super(context);
		setId(667);
		this.context = context;
		devices = new ArrayList<WifiP2pDevice>();
		this.targetComponent = targetComponent;
		LayoutInflater.from(context)
				.inflate(R.layout.peer_selection_list, this);

		this.wiFiDirectSwitch = (Switch) findViewById(R.id.peer_connection_technology_switch);
		this.deviceName = (TextView) findViewById(R.id.device_name_text_view);
		this.listView = (ListView) findViewById(R.id.peer_list_view);
		this.progressIndicator = (ProgressBar) findViewById(R.id.scanning_progress);

		this.listView.setAdapter(new WiFiDirectListAdapter(devices, context));

		WiFiDirectPeerSelectionController controller = new WiFiDirectPeerSelectionController(
				context, this, lifecycle);

		wiFiDirectSwitch.setOnCheckedChangeListener(controller);

		listener = controller;

		lifecycle.addOnResumeListener(new OnResumeListener() {

			public void onResume(Context context) {
				fireDatasetChangedEvent();
			}
		});
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable state = super.onSaveInstanceState();

		SavedWiFiDirectPeerSelectionListState saveState = new SavedWiFiDirectPeerSelectionListState(
				state);

		saveState.setDevices(devices);

		return saveState;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {

		if (state instanceof SavedWiFiDirectPeerSelectionListState) {
			SavedWiFiDirectPeerSelectionListState saveState = (SavedWiFiDirectPeerSelectionListState) state;
			super.onRestoreInstanceState(saveState.getSuperState());
			this.devices.addAll(saveState.getDevices());
			fireDatasetChangedEvent();
		} else {
			super.onRestoreInstanceState(state);
		}
	}

	public void setWiFiDirectSwitchOn(boolean on) {
		wiFiDirectSwitch.setChecked(on);
	}

	public void setWiFiDirectSwitchEnabled(boolean enabled) {
		wiFiDirectSwitch.setEnabled(enabled);
	}

	public void setWiFiDirectDeviceName(String name) {
		deviceName.setText(name);
	}

	public void addDevice(WifiP2pDevice device) {
		devices.add(device);
		fireDatasetChangedEvent();
	}

	public void removeDevice(WifiP2pDevice device) {
		devices.remove(device);
		fireDatasetChangedEvent();
	}

	public void clearDevices() {
		devices.clear();
		fireDatasetChangedEvent();
	}

	public boolean isDeviceInList(WifiP2pDevice device) {
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

	public void refreshPeers() {
		listener.onRefresh();
	}

	public void setDiscoveryInProgress(boolean isInProgress) {
		if (isInProgress) {
			progressIndicator.setVisibility(VISIBLE);
		} else {
			progressIndicator.setVisibility(INVISIBLE);
		}
	}
}
