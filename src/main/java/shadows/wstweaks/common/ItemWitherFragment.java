package shadows.wstweaks.common;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import shadows.wstweaks.WSTweaks;

public class ItemWitherFragment extends Item {
	public ItemWitherFragment() {
		setRegistryName("fragment");
		setUnlocalizedName(WSTweaks.MODID + ".fragment");
		setCreativeTab(CreativeTabs.MATERIALS);
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}

}
