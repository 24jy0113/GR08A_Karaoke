package model;

import java.time.LocalTime;

public class ExtendResult {
	private final LocalTime currentLeaving;
	private final LocalTime newLeaving;

	public ExtendResult(LocalTime currentLeaving, LocalTime newLeaving) {
		this.currentLeaving = currentLeaving;
		this.newLeaving = newLeaving;
	}

	public LocalTime getCurrentLeaving() {
		return currentLeaving;
	}

	public LocalTime getNewLeaving() {
		return newLeaving;
	}
}
