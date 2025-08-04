package com.app.billingapi.dto;

import java.time.DayOfWeek;

public class TimeInsightDto {
    private int peakHour;
    private DayOfWeek bestDay;
    private DayOfWeek worstDay;

    public TimeInsightDto(int peakHour, DayOfWeek bestDay, DayOfWeek worstDay) {
        this.peakHour = peakHour;
        this.bestDay = bestDay;
        this.worstDay = worstDay;
    }

	public int getPeakHour() {
		return peakHour;
	}

	public void setPeakHour(int peakHour) {
		this.peakHour = peakHour;
	}

	public DayOfWeek getBestDay() {
		return bestDay;
	}

	public void setBestDay(DayOfWeek bestDay) {
		this.bestDay = bestDay;
	}

	public DayOfWeek getWorstDay() {
		return worstDay;
	}

	public void setWorstDay(DayOfWeek worstDay) {
		this.worstDay = worstDay;
	}

}
