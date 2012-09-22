package org.p2plib.peer.selection;

import org.p2plib.R;
import org.p2plib.util.Lifecycle;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public class WiFiDirectPeerSelectionList extends FrameLayout {

	private Activity context;
	private String targetComponent;

	public WiFiDirectPeerSelectionList(Activity context, String targetComponent, Lifecycle lifecycle) {
		super(context);
		this.targetComponent = targetComponent;
		LayoutInflater.from(context)
				.inflate(R.layout.peer_selection_list, this);
	}

}
