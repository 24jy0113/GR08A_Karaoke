package action;

import java.time.LocalTime;

public class ExtendPreviewResult {
	private final LocalTime currentLeaving;
	private final LocalTime newLeaving;

	public ExtendPreviewResult(LocalTime currentLeaving, LocalTime newLeaving) {
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
