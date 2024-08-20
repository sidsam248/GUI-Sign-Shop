package me.liveinacupboard.gsshop.events;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.liveinacupboard.gsshop.GuiSignShop;
import me.liveinacupboard.gsshop.stickysign.StickySign;

public class StickySignBreakEvent implements Listener {

	private GuiSignShop pl;

	public StickySignBreakEvent(GuiSignShop pl) {

		this.pl = pl;

	}

	@EventHandler
	public void onStickySignBreak(BlockBreakEvent e) {

		if (!(e.getBlock().getState() instanceof Sign))
			return;

		Sign sign = (Sign) e.getBlock().getState();

		if (StickySign.getStickySigns().isEmpty())
			return;

		StickySign ss = null;

		for (StickySign s : StickySign.getStickySigns()) {

			if (s.getLocation().equals(sign.getLocation()))
				ss = s;

		}

		if (ss == null)
			return;

		StickySign.removeStickySign(ss);

		pl.getStickySignsFile().set(ss.getName(), null);
		pl.saveStickySignsFile();

	}

}
