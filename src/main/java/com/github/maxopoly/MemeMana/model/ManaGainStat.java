package com.github.maxopoly.MemeMana.model;

import com.github.maxopoly.MemeMana.MemeManaPlugin;

public class ManaGainStat {

	// Starts at lastDay and goes backward in time
	private boolean[] recentLogins;
	private long lastDay;

	public ManaGainStat(boolean[] recentLogins, long lastDay) {
		this.recentLogins = recentLogins;
		this.lastDay = lastDay;
	}

	/**
	 * Updates the streak and last registered day. If the day has changed, meaning the stats were actually updated, true
	 * will be returned, false otherwise
	 *
	 * @return True if the day has changed since the last check
	 */
	public boolean update() {
		long daysPast = getDayFromTimeStamp(System.currentTimeMillis()) - lastDay;
		int roll = MemeManaPlugin.getInstance().getManaConfig().getMaximumDailyMana();
		if (daysPast == 0) {
			return false;
		}
		
		// If we haven't completely reset
		if (daysPast < roll) {
			// Shift the old days over, discarding as needed
			System.arraycopy(recentLogins,0,recentLogins,daysPast,roll - daysPast);
		}
		// We didn't log in between the current day and the last day
		System.arraycopy(new boolean[Math.max(daysPast,roll)],0,recentLogins,0,Math.max(daysPast,roll));
		// We logged in today
		recentLogins[0] = true;
		lastDay = currentDay;
		return true;
	}

	public int getStreak() {
		int streak = 0;
		for (boolean login : recentLogins) {
			streak += login ? 1 : 0;
		}
		return streak;
	}

	public long getLastDay() {
		return lastDay;
	}

	public static long getDayFromTimeStamp(long timeStamp) {
		return timeStamp % (24 * 60 * 60 * 1000);
	}

}
