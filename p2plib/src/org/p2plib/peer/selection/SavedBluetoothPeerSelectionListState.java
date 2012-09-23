package org.p2plib.peer.selection;

import java.util.ArrayList;
import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View.BaseSavedState;

public class SavedBluetoothPeerSelectionListState extends BaseSavedState {

	private List<BluetoothDevice> devices;

	public SavedBluetoothPeerSelectionListState(Parcelable parcelable) {
		super(parcelable);
	}

	public SavedBluetoothPeerSelectionListState(Parcel in) {
		super(in);
		Parcelable[] parcelables = in.readParcelableArray(getClass()
				.getClassLoader());
		devices = new ArrayList<BluetoothDevice>();
		for (Parcelable parcelable : parcelables) {
			devices.add((BluetoothDevice) parcelable);
		}
	}

	public List<BluetoothDevice> getDevices() {
		return devices;
	}

	public void setDevices(List<BluetoothDevice> devices) {
		this.devices = devices;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		super.writeToParcel(out, flags);
		out.writeParcelableArray(devices.toArray(new BluetoothDevice[1]), 0);
	}

	public static final Parcelable.Creator<SavedBluetoothPeerSelectionListState> CREATOR = new Parcelable.Creator<SavedBluetoothPeerSelectionListState>() {
		public SavedBluetoothPeerSelectionListState createFromParcel(Parcel in) {
			return new SavedBluetoothPeerSelectionListState(in);
		}

		public SavedBluetoothPeerSelectionListState[] newArray(int size) {
			return new SavedBluetoothPeerSelectionListState[size];
		}
	};
}
