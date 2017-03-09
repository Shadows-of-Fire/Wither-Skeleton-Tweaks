package shadows.soul.core;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import shadows.soul.common.ItemImmolationBlade;
import shadows.soul.common.ItemWitherFragment;

public class ModRegistry {
	public static ItemWitherFragment fragment;
	public static ItemImmolationBlade lava_sword;
	public static ItemImmolationBlade blaze_sword;

	public static void init() {
		fragment = new ItemWitherFragment();
		if (ConfigFile.enableLava) lava_sword = new ItemImmolationBlade("blade", ToolMaterial.valueOf("immolation"));
		if (ConfigFile.enableBlaze) blaze_sword = new ItemImmolationBlade("blade2", ToolMaterial.valueOf("immolation"));
	}

	@SideOnly(Side.CLIENT)
	public static void initModels() {
		fragment.initModel();
		if (ConfigFile.enableLava) lava_sword.initModel();
		if (ConfigFile.enableBlaze) blaze_sword.initModel();
	}

}
