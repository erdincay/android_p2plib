package org.p2plib.peer.selection.wifidirect;

import org.p2plib.util.Lifecycle;
import org.p2plib.util.OnPauseListener;
import org.p2plib.util.OnResumeListener;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class WiFiDirectPeerSelectionController extends BroadcastReceiver
		implements OnCheckedChangeListener, OnPauseListener, OnResumeListener,
		PeerListListener {

	private WiFiDirectPeerSelectionView view;
	private Activity context;
	private WifiP2pManager wifiP2pManager;
	private Channel channel;

	public WiFiDirectPeerSelectionController(Activity context,
			WiFiDirectPeerSelectionView view, Lifecycle lifecycle) {
		this.context = context;
		this.view = view;

		lifecycle.addOnPauseListener(this);
		lifecycle.addOnResumeListener(this);

		wifiP2pManager = (WifiP2pManager) context
				.getSystemService(Context.WIFI_P2P_SERVICE);
		channel = wifiP2pManager.initialize(context, context.getMainLooper(),
				null);

		onUpdateList();
		onResume(context);
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO
		// if (isChecked) {
		// bluetoothAdapter.enable();
		// } else {
		// bluetoothAdapter.disable();
		// }
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
			int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
			if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
				view.setWiFiDirectSwitchEnabled(true);
			} else {
				view.setWiFiDirectSwitchEnabled(false);
			}
		} else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
			wifiP2pManager.requestPeers(channel, this);
		} else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION
				.equals(action)) {
			WifiP2pDevice device = (WifiP2pDevice) intent
					.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
			view.setWiFiDirectDeviceName(device.deviceName);
		}
	}

	public void onUpdateList() {
		wifiP2pManager.discoverPeers(channel, new ActionListener() {

			public void onSuccess() {
				Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
			}

			public void onFailure(int reason) {
				Toast.makeText(context, "Failed: " + reason, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	public void onResume(Context context) {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
		intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
		intentFilter
				.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
		intentFilter
				.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
		context.registerReceiver(this, intentFilter);
	}

	public void onPause(Context context) {
		context.unregisterReceiver(this);
		// TODO
		// bluetoothAdapter.cancelDiscovery();
	}

	public void onPeersAvailable(WifiP2pDeviceList peers) {
		for (WifiP2pDevice device : peers.getDeviceList()) {
			if (!view.isDeviceInList(device)) {
				view.addDevice(device);
			}
		}
	}
}
