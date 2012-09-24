package org.p2plib.fragment;

import org.p2plib.PeerConnectionTechnology;
import org.p2plib.R;
import org.p2plib.peer.selection.PeerSelectionEventBus;
import org.p2plib.peer.selection.PeerSelectionTabContentFactory;
import org.p2plib.util.Lifecycle;
import org.p2plib.util.LifecycleImpl;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;

public class PeerSelectionFragment extends Fragment {

	public static final String ENABLED_CONNECTION_TECHNOLOGIES_ARG = "enabledConnectionTechnologies";
	public static final String TARGET_COMPONENT_ARG = "targetComponent";

	public static final String TAB_KEY = "tab";

	private Lifecycle lifecycle;

	private int enabledConnectionTechnologies;
	private TabContentFactory tabContentFactory;
	private TabHost tabHost;
	private String targetComponent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		lifecycle = new LifecycleImpl(getActivity());
		if (getArguments() == null) {
			enabledConnectionTechnologies = PeerConnectionTechnology.ALL;
		} else {
			enabledConnectionTechnologies = getArguments().getInt(
					ENABLED_CONNECTION_TECHNOLOGIES_ARG,
					PeerConnectionTechnology.ALL);
			targetComponent = getArguments().getString(TARGET_COMPONENT_ARG);
		}
		tabContentFactory = new PeerSelectionTabContentFactory(getActivity(),
				targetComponent, lifecycle);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.peer_selection_fragment_tab_host,
				container, false);

		tabHost = (TabHost) view.findViewById(android.R.id.tabhost);
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

		if (PeerConnectionTechnology.isEnabled(enabledConnectionTechnologies,
				PeerConnectionTechnology.WIFI_DIRECT)) {
			TabSpec bluetoothTabSpec = tabHost.newTabSpec(String
					.valueOf(PeerConnectionTechnology.WIFI_DIRECT));
			bluetoothTabSpec.setIndicator(getString(PeerConnectionTechnology
					.getStringId(PeerConnectionTechnology.WIFI_DIRECT)));
			bluetoothTabSpec.setContent(tabContentFactory);
			tabHost.addTab(bluetoothTabSpec);
		}

		if (savedInstanceState != null) {
			tabHost.setCurrentTab(savedInstanceState.getInt(TAB_KEY));
		}

		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(TAB_KEY, tabHost.getCurrentTab());
	}

	@Override
	public void onResume() {
		super.onResume();
		lifecycle.fireOnResumeEvent();
	}

	@Override
	public void onPause() {
		super.onPause();
		lifecycle.fireOnPauseEvent();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.peer_selection_fragment_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		PeerSelectionEventBus eventBus = (PeerSelectionEventBus) tabHost
				.getTabContentView().getChildAt(tabHost.getCurrentTab());
		if (item.getItemId() == R.id.reload_peers) {
			eventBus.refreshPeers();
		}
		return super.onOptionsItemSelected(item);
	}
}
