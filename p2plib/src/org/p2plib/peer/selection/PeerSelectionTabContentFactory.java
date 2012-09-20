package org.p2plib.peer.selection;

import org.p2plib.PeerConnectionTechnology;

import android.app.Activity;
import android.view.View;
import android.widget.TabHost.TabContentFactory;

public class PeerSelectionTabContentFactory implements TabContentFactory {
	private Activity context;
	private String targetComponent;

	public PeerSelectionTabContentFactory(Activity context,
			String targetComponent) {
		this.context = context;
		this.targetComponent = targetComponent;
	}

	public View createTabContent(String tag) {
		int peerConnectionTechnology = Integer.parseInt(tag);
		View view = null;
		switch (peerConnectionTechnology) {
		case PeerConnectionTechnology.BLUETOOTH:
			view = new BluetoothPeerSelectionList(context, targetComponent);
			break;

		default:
			view = new View(context);
			break;
		}
		return view;
	}

}
