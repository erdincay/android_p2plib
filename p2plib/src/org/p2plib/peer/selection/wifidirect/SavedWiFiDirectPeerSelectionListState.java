package org.p2plib.peer.selection.wifidirect;

import java.util.ArrayList;
import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View.BaseSavedState;

public class SavedWiFiDirectPeerSelectionListState extends BaseSavedState {

	private List<WifiP2pDevice> devices;

	public SavedWiFiDirectPeerSelectionListState(Parcelable parcelable) {
		super(parcelable);
	}

	public SavedWiFiDirectPeerSelectionListState(Parcel in) {
		super(in);
		Parcelable[] parcelables = in.readParcelableArray(getClass()
				.getClassLoader());
		devices = new ArrayList<WifiP2pDevice>();
		for (Parcelable parcelable : parcelables) {
			devices.add((WifiP2pDevice) parcelable);
		}
	}

	public List<WifiP2pDevice> getDevices() {
		return devices;
	}

	public void setDevices(List<WifiP2pDevice> devices) {
		this.devices = devices;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		super.writeToParcel(out, flags);
		out.writeParcelableArray(devices.toArray(new WifiP2pDevice[1]), 0);
	}

	public static final Parcelable.Creator<SavedWiFiDirectPeerSelectionListState> CREATOR = new Parcelable.Creator<SavedWiFiDirectPeerSelectionListState>() {
		public SavedWiFiDirectPeerSelectionListState createFromParcel(Parcel in) {
			return new SavedWiFiDirectPeerSelectionListState(in);
		}

		public SavedWiFiDirectPeerSelectionListState[] newArray(int size) {
			return new SavedWiFiDirectPeerSelectionListState[size];
		}
	};
}
