package org.p2plib.peer.selection;

import org.p2plib.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public class BluetoothPeerSelectionList extends FrameLayout {

	private Activity context;
	private String targetComponent;

	public BluetoothPeerSelectionList(Activity context, String targetComponent) {
		super(context);
		this.targetComponent = targetComponent;
		LayoutInflater.from(context)
				.inflate(R.layout.peer_selection_list, this);
	}

}
