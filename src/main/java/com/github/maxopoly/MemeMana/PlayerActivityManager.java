package com.github.maxopoly.MemeMana;

import com.github.maxopoly.MemeMana.model.ManaGainStat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.programmerdan.minecraft.banstick.data.BSPlayer;

public class PlayerActivityManager {

	private Map<UUID, ManaGainStat> stats;
	private MemeManaManager manaManager;

	public PlayerActivityManager(MemeManaManager manaManager) {
		this.manaManager = manaManager;
		reloadFromDB();
	}

	public void reloadFromDB() {
		// TODO actually reload from DB
		stats = new HashMap<UUID, ManaGainStat>();
	}

	public void updatePlayer(UUID thisAlt) {
		UUID banstickID = BSPlayer.byUUID(thisAlt).getUUID();
		// TODO mirror this in DB
		stats.putIfAbsent(banstickID, new ManaGainStat(0,0));
		ManaGainStat stat = stats.get(banstickID);
		if (stat.update()) {
			giveOutReward(thisAlt, stat.getStreak());
		}
	}

	public void giveOutReward(UUID player, int amount) {
		MemeManaPlugin.getInstance().getManaManager().getPouch(player).addNewUnit(manaManager.getNextManaID(), amount);
		// TODO send message to player?
	}
}
