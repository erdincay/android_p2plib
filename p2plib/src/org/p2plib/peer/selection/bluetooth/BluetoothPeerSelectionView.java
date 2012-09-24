package org.p2plib.peer.selection.bluetooth;

import android.bluetooth.BluetoothDevice;

public interface BluetoothPeerSelectionView {
	void setBluetoothSwitchOn(boolean on);

	void setBluetoothSwitchEnabled(boolean enabled);

	void setBluetoothDeviceName(String name);

	void addDevice(BluetoothDevice device);

	void removeDevice(BluetoothDevice device);

	boolean isDeviceInList(BluetoothDevice device);

	void clearDevices();

	void setDiscoveryInProgress(boolean isInProgress);
}
