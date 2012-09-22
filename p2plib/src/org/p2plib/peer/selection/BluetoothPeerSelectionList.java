package org.p2plib.peer.selection;

import org.p2plib.R;
import org.p2plib.util.Lifecycle;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Switch;

public class BluetoothPeerSelectionList extends FrameLayout implements
		BluetoothPeerSelectionView {

	private String targetComponent;
	private Switch blueToothSwitch;

	public BluetoothPeerSelectionList(Activity context, String targetComponent,
			Lifecycle lifecycle) {
		super(context);
		this.targetComponent = targetComponent;
		LayoutInflater.from(context)
				.inflate(R.layout.peer_selection_list, this);

		this.blueToothSwitch = (Switch) findViewById(R.id.peer_connection_technology_switch);

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

}
