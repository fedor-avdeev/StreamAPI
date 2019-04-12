package org.fedoravdeev.task05.streamapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

public class PilotTest {

	@Test
	@DisplayName("should get string best time")
	public void shouldGetStringBestTime() {

		LocalDateTime startTime = LocalDateTime.of(2018, 5, 24, 12, 14, 12, 54 * 1000000);
		LocalDateTime endTime = LocalDateTime.of(2018, 5, 24, 12, 15, 24, 67 * 1000000);

		Pilot pilot = new Pilot("DDR", Duration.between(startTime, endTime), "Daniel Ricciardo",
				"RED BULL RACING TAG HEUER");
		String expected = "01:12.013";
		assertEquals(expected, pilot.getStringBestTime());
	}

	@Test
	@DisplayName("should get string zero time")
	public void shouldGetStringZeroTime() {

		LocalDateTime startTime = LocalDateTime.of(2018, 5, 24, 12, 14, 12, 54 * 1000000);
		LocalDateTime endTime = LocalDateTime.of(2018, 5, 24, 12, 14, 12, 54 * 1000000);

		Pilot pilot = new Pilot("GDR", Duration.between(startTime, endTime), null, null);
		String expected = "00:00.000";
		assertEquals(expected, pilot.getStringBestTime());
	}
}
