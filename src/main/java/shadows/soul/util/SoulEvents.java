package shadows.soul.util;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.init.Biomes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shadows.soul.core.ConfigFile;
import shadows.soul.core.ModRegistry;

@Mod.EventBusSubscriber
public class SoulEvents {

	private static final int kx = Math.max(0, ConfigFile.extraWitherSkeletons);
	
    @SubscribeEvent
    public void skeleFixer(LivingSpawnEvent event) {
    	if (event.getEntity() instanceof EntitySkeleton){
    	if (!event.getEntity().world.isRemote){
    		Entity entity = event.getEntity();
    		World world = entity.world;
    		double x = entity.posX;
    		double y = entity.posY;
    		double z = entity.posZ;
    	if (world.getBiome(new BlockPos(x, y, z)) == Biomes.HELL){
    	entity.setDropItemsWhenDead(false);
    	entity.setDead();
    	for(int i = 0; i < kx; i++){
    	SoulMethods.spawnCreature(world, new EntityWitherSkeleton(world), x, y, z);
    	}}}}
    	else if (event.getEntity() instanceof EntityWitherSkeleton){
    	if (!event.getEntity().world.isRemote){
    		Entity entity = event.getEntity();
    		World world = entity.world;
    		double x = entity.posX;
    		double y = entity.posY;
    		double z = entity.posZ;
    	if (world.getBiome(new BlockPos(x, y, z)) == Biomes.HELL){
        	for(int i = 0; i < kx; i++){
            	SoulMethods.spawnCreature(world, new EntityWitherSkeleton(world), x, y, z);
           }}}}}
    
   @SubscribeEvent
   public void addFrags(LivingDropsEvent event){
	   if(event.getEntity().world.rand.nextInt(ConfigFile.shardDropChance) == 0){
	   if (!event.getEntity().world.isRemote && event.getEntity() instanceof EntityWitherSkeleton && !(event.getSource() == DamageSource.field_191552_t)){
   			List<EntityItem> drops = event.getDrops();
   			ItemStack stack = new ItemStack(Items.SKULL, 1, 1);
   			if (!SoulMethods.dropSearchFinder(drops, stack)){
   				event.getDrops().add(new EntityItem(event.getEntity().world, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, new ItemStack(ModRegistry.fragment)));
   			}}}}
   
   @SubscribeEvent
   public void immolate(LivingDropsEvent event){
	   if (!event.getEntity().world.isRemote && event.getSource() == DamageSource.field_191552_t){
		   System.out.println(event.getEntity().toString());
   			List<EntityItem> drops = event.getDrops();
   			ItemStack stack = new ItemStack(Items.SKULL, 1, 1);
   			if (event.getEntity() instanceof EntityWitherSkeleton){
   			if (!SoulMethods.dropSearchFinder(drops, stack)){
   				event.getDrops().add(new EntityItem(event.getEntity().world, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, stack));
   			}}
   			if (event.getEntity() instanceof EntitySkeleton){
   				event.getDrops().clear();
   				event.getDrops().add(new EntityItem(event.getEntity().world, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, stack));
   				if(event.getEntity().world.rand.nextBoolean()){event.getDrops().add(new EntityItem(event.getEntity().world, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, new ItemStack(Items.ARROW, 3)));}
   				if(event.getEntity().world.rand.nextBoolean()){event.getDrops().add(new EntityItem(event.getEntity().world, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, new ItemStack(Items.BONE, 2)));}
   			}
   			
   			}}
	
}
