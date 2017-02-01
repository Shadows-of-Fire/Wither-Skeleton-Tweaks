package shadows.soul.core;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import shadows.soul.common.ItemImmolationBlade;
import shadows.soul.common.ItemWitherFragment;


public class ModRegistry {
	public static ItemWitherFragment fragment;
	public static ItemImmolationBlade blade;
	public static ItemImmolationBlade blade2;

	
	public static void init(){ 
		fragment = new ItemWitherFragment();
		blade = new ItemImmolationBlade("blade", ToolMaterial.valueOf("immolation"));
		blade2 = new ItemImmolationBlade("blade2", ToolMaterial.valueOf("immolation"));
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels(){
		fragment.initModel();
		blade.initModel();
		blade2.initModel();
	}
	
	
}
