package potentialgames.controller;

import java.util.Observable;

import potentialgames.data.ICell;
import potentialgames.data.IPin;
import potentialgames.data.IPlayer;

public class PinActionMove extends Observable implements PinAction {
	static final int SIX = 6;
	
	public boolean pinMove(GameController gc, IPlayer player, int pin, int steps) {
		ICell[] tmp = gc.getWay();
		int pos = player.getPinPos(pin);
		int newPos = steps + pos;

		if (newPos > tmp.length - 1) {
			/* Check for field end */
			newPos = newPos - tmp.length;
		}

		if (tmp[newPos].getPlayer() == null) {
			// target field is free
			if (player.getExt() == newPos) {
				gc.getField().getTarget(player.getPlayerNr()).add(player.getPin(pin));
				tmp[pos].setPin(null);
				tmp[pos].setPlayer(null);
				gc.setReturnarg("Pin entered Target");
			} else {
					move(tmp, pos, newPos, player, pin, gc);
					gc.setReturnarg("Pin moved");

			}

			return true;
		} else if (!tmp[newPos].getPlayer().equals(player)) {
			// other player on this field
			pinKickback(tmp[newPos].getPlayer(), tmp[newPos].getPin(), gc);

			if (player.getExt() == newPos) {
				gc.getField().getTarget(player.getPlayerNr())
						.add(player.getPin(pin));
				gc.setReturnarg("Pin entered Target");
			} else {
				move(tmp, pos, newPos, player, pin, gc);

			}

			return true;
		} else if (tmp[newPos].getPlayer().equals(player)) {
			// same player on this field
			gc.setReturnarg("Same Player on Filed Select an other one ");
			return false;
		}
		/* to make eclipse happy */
		return false;
	}

	public boolean pinEntry(GameController gc, IPlayer player, int steps) {
		ICell[] tmp = gc.getWay();
		ICell entry = tmp[player.getEntry()];
		if (steps == SIX) {
			if (entry.getPlayer() == null) {
				// kick in
				IPin toSet = gc.getField().getHome(player.getPlayerNr()).poll();
				entry.setCell(player, toSet, player.getEntry());
				gc.setReturnarg("Pin is on the road");
				return true;

			} else if (!entry.getPlayer().equals(player)) {
				// kick out
				pinKickback(entry.getPlayer(), entry.getPin(), gc);
				IPin toSet = gc.getField().getHome(player.getPlayerNr()).poll();
				entry.setCell(player, toSet, player.getEntry());
				gc.setReturnarg("Pin Kicked, You are the road");
				return true;

			} else {
				// own player on entry field need to move
				gc.setReturnarg("DON'T KICK YOUR HOMIES, select an other one");
				return pinMove(gc, player, entry.getPin().getPinNr(), steps);
			}
		} else {
			gc.setReturnarg("You can only enter with a 6");
			return false;
		}
	}

	private boolean pinKickback(IPlayer player, IPin pin, GameController gc) {
		// TODO: Pin in das Haus zurück setzen.
		return gc.getField().getHome(player.getPlayerNr()).add(pin);
	}


	private void move(ICell[] tmp, int pos, int newPos, IPlayer player,	int pin, GameController gc) {
		tmp[pos].setPin(null);
		tmp[pos].setPlayer(null);
		for (int i = pos; i < newPos; i++) {
			if(i == tmp.length){
				i = 0;
			}
			if (pos + i == player.getExt()) {
				gc.getField().getTarget(player.getPlayerNr()).add(player.getPin(pin));
				tmp[pos].setPin(null);
				tmp[pos].setPlayer(null);
				return;
			}
		}
		tmp[newPos].setPin(player.getPin(pin));
		tmp[newPos].setPlayer(player);
		player.setPinPos(pin, newPos);
	}
}
