package shadows.wstweaks.common;

import net.minecraft.creativetab.CreativeTabs;
import shadows.placebo.item.ItemBase;
import shadows.wstweaks.WSTweaks;

public class ItemWitherFragment extends ItemBase {

	public ItemWitherFragment() {
		super("fragment", WSTweaks.INFO);
		setCreativeTab(CreativeTabs.MATERIALS);
	}

}
