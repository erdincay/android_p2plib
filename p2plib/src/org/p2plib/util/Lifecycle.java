package org.p2plib.util;

public interface Lifecycle {
	void addOnResumeListener(OnResumeListener listener);

	void removeOnResumeListener(OnResumeListener listener);

	void fireOnResumeEvent();

	void addOnPauseListener(OnPauseListener listener);

	void removeOnPauseListener(OnPauseListener listener);

	void fireOnPauseEvent();
}
