package org.p2plib.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class LifecycleImpl implements Lifecycle {

	private Context context;
	private List<OnResumeListener> onResumeListeners = new ArrayList<OnResumeListener>();
	private List<OnPauseListener> onPauseListeners = new ArrayList<OnPauseListener>();
	
	public LifecycleImpl(Context context) {
		this.context = context;
	}

	public void addOnResumeListener(OnResumeListener listener) {
		onResumeListeners.add(listener);
	}

	public void removeOnResumeListener(OnResumeListener listener) {
		onResumeListeners.remove(listener);
	}

	public void fireOnResumeEvent() {
		for (OnResumeListener onResumeListener : onResumeListeners) {
			onResumeListener.onResume(context);
		}
	}

	public void addOnPauseListener(OnPauseListener listener) {
		onPauseListeners.add(listener);
	}

	public void removeOnPauseListener(OnPauseListener listener) {
		onPauseListeners.remove(listener);
	}

	public void fireOnPauseEvent() {
		for (OnPauseListener onPauseListener : onPauseListeners) {
			onPauseListener.onPause(context);
		}
	}

}
