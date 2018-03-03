package shadows.wstweaks.common;

import net.minecraft.creativetab.CreativeTabs;
import shadows.placebo.item.ItemBase;
import shadows.wstweaks.WitherSkeletonTweaks;

public class ItemWitherFragment extends ItemBase {

	public ItemWitherFragment() {
		super("fragment", WitherSkeletonTweaks.INFO);
		setCreativeTab(CreativeTabs.MATERIALS);
	}

}
