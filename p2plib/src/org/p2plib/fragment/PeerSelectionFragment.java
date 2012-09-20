package org.p2plib.fragment;

import org.p2plib.PeerConnectionTechnology;
import org.p2plib.R;
import org.p2plib.peer.selection.PeerSelectionTabContentFactory;

import android.app.Fragment;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;

public class PeerSelectionFragment extends Fragment {

	public static final String ENABLED_CONNECTION_TECHNOLOGIES_ARG = "enabledConnectionTechnologies";
	public static final String TARGET_COMPONENT_ARG = "targetComponent";

	private int enabledConnectionTechnologies;
	private TabContentFactory tabContentFactory;
	private String targetComponent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() == null) {
			enabledConnectionTechnologies = PeerConnectionTechnology.ALL;
		} else {
			enabledConnectionTechnologies = getArguments().getInt(
					ENABLED_CONNECTION_TECHNOLOGIES_ARG,
					PeerConnectionTechnology.ALL);
			targetComponent = getArguments().getString(TARGET_COMPONENT_ARG);
		}
		tabContentFactory = new PeerSelectionTabContentFactory(getActivity(),
				targetComponent);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.peer_selection_fragment_tab_host,
				null);

		TabHost tabHost = (TabHost) view.findViewById(android.R.id.tabhost);
		tabHost.setup();

		if (PeerConnectionTechnology.isEnabled(enabledConnectionTechnologies,
				PeerConnectionTechnology.BLUETOOTH)) {
			TabSpec bluetoothTabSpec = tabHost.newTabSpec(String
					.valueOf(PeerConnectionTechnology.BLUETOOTH));
			bluetoothTabSpec.setIndicator(getString(PeerConnectionTechnology
					.getStringId(PeerConnectionTechnology.BLUETOOTH)));
			bluetoothTabSpec.setContent(tabContentFactory);
			tabHost.addTab(bluetoothTabSpec);
		}

		return view;
	}
}
