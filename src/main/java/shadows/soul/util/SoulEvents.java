package shadows.soul.util;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import shadows.soul.core.ConfigFile;
import shadows.soul.core.ModRegistry;

@Mod.EventBusSubscriber
public class SoulEvents {

	private static final int tries = Math.max(0, ConfigFile.extraWitherSkeletons);
	
    @SubscribeEvent
    public void skeleFixer(LivingSpawnEvent.CheckSpawn event) {
    	if (event.getEntity() instanceof EntitySkeleton){
    	if (!event.getEntity().world.isRemote){
    		Entity entity = event.getEntity();
    		World world = entity.world;
    		double x = entity.posX;
    		double y = entity.posY;
    		double z = entity.posZ;
    	if (world.getBiome(new BlockPos(x, y, z)) == Biomes.HELL || ConfigFile.allowAllBiomes){
    	event.setResult(Result.DENY);
    	for(int i = -1; i < tries; i++){
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
        	for(int i = 0; i < tries; i++){
            	SoulMethods.spawnCreature(world, new EntityWitherSkeleton(world), x, y, z);
           }}}}
    	else if(event.getEntity() instanceof EntityBlaze || event.getEntity() instanceof EntityPigZombie){
    		if(ConfigFile.extraSpawns){
    		World world = event.getWorld();
    		Entity entity = event.getEntity();
    		BlockPos pos = entity.getPosition().down();
    		if(pos != null && world.getBlockState(pos).getBlock() == Blocks.NETHER_BRICK){
    	    	for(int i = -1; i < tries; i++){
    	        SoulMethods.spawnCreature(world, new EntityWitherSkeleton(world), entity.posX, entity.posY, entity.posZ);
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
