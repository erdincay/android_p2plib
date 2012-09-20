package org.p2plib;

import org.p2plib.R;

public final class PeerConnectionTechnology {

	public static final int ALL = 0xFFFFFFFF;
	public static final int WIFI_DIRECT = 0x00000001;
	public static final int BLUETOOTH = 0x00000002;

	private PeerConnectionTechnology() {
	}

	public static boolean isEnabled(int enabledTechnologies, int technology) {
		return (enabledTechnologies & technology) != 0;
	}

	public static int getStringId(int technology) {
		switch (technology) {
		case WIFI_DIRECT:
			return R.string.wifi_direct;

		case BLUETOOTH:
			return R.string.bluetooth;

		default:
			return R.string.undefined_text;
		}
	}
}