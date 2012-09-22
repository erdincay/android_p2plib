package org.p2plib.peer.selection;

import org.p2plib.PeerConnectionTechnology;
import org.p2plib.util.Lifecycle;

import android.app.Activity;
import android.view.View;
import android.widget.TabHost.TabContentFactory;

public class PeerSelectionTabContentFactory implements TabContentFactory {
	private Activity context;
	private String targetComponent;
	private Lifecycle lifecycle;

	public PeerSelectionTabContentFactory(Activity context,
			String targetComponent, Lifecycle lifecycle) {
		this.context = context;
		this.targetComponent = targetComponent;
		this.lifecycle = lifecycle;
	}

	public View createTabContent(String tag) {
		int peerConnectionTechnology = Integer.parseInt(tag);
		View view = null;
		switch (peerConnectionTechnology) {
		case PeerConnectionTechnology.BLUETOOTH:
			view = new BluetoothPeerSelectionList(context, targetComponent,
					lifecycle);
			break;

		case PeerConnectionTechnology.WIFI_DIRECT:
			view = new WiFiDirectPeerSelectionList(context, targetComponent,
					lifecycle);
			break;

		default:
			view = new View(context);
			break;
		}
		return view;
	}

}
