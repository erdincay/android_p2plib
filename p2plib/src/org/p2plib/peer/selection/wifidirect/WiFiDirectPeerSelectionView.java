package org.p2plib.peer.selection.wifidirect;

import android.net.wifi.p2p.WifiP2pDevice;

public interface WiFiDirectPeerSelectionView {
	void setWiFiDirectSwitchOn(boolean on);

	void setWiFiDirectSwitchEnabled(boolean enabled);

	void setWiFiDirectDeviceName(String name);

	void addDevice(WifiP2pDevice device);

	void removeDevice(WifiP2pDevice device);

	boolean isDeviceInList(WifiP2pDevice device);

	void clearDevices();

	void setDiscoveryInProgress(boolean isInProgress);
}
