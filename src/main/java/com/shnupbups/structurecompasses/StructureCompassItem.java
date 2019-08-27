package com.shnupbups.structurecompasses;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.TagHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.List;

public class StructureCompassItem extends Item {
	public final StructureFeature structure;
	
	public StructureCompassItem(Settings settings, StructureFeature structure) {
		super(settings);
		this.structure = structure;
		this.addPropertyGetter(new Identifier("angle"), new ItemPropertyGetter() {
			@Environment(EnvType.CLIENT)
			public float call(ItemStack stack, World world, LivingEntity livingEntity) {
				if (livingEntity == null && !stack.isInFrame()) {
					return 0.0F;
				} else {
					boolean bool = livingEntity != null;
					Entity entity = bool ? livingEntity : stack.getFrame();
					if(entity == null) {
						return 0.0F;
					}
					if (world == null) {
						world = entity.world;
					}
					
					boolean show = true;
					double angle2 = 0.0;
					if(stack.hasTag()) {
						CompoundTag tags = stack.getTag();
						if(TagHelper.deserializeBlockPos(tags.getCompound("pos")).equals(world.getSpawnPos())) {
							show = false;
						} else if(tags.containsKey("dim")) {
							angle2 = this.getAngleFromTag(stack, entity);
							show = tags.getInt("dim") == entity.dimension.getRawId();
						}
					} else {
						show = false;
					}
					
					double doub3;
					if (show) {
						double doub = bool ? (double)entity.yaw : this.getYaw((ItemFrameEntity)entity);
						doub = MathHelper.floorMod(doub / 360.0D, 1.0D);
						double doub2 = angle2 / 6.2831854820251465D;
						doub3 = 0.5D - (doub - 0.25D - doub2);
					} else {
						doub3 = Math.random();
					}
					
					if (bool) {
						doub3 = this.getAngle(stack, world, doub3);
					}
					
					return MathHelper.floorMod((float)doub3, 1.0F);
				}
			}
			
			@Environment(EnvType.CLIENT)
			private double getAngle(ItemStack stack, World world, double doub) {
				if (world.getTime() != StructureCompassItem.getLastTick(stack)) {
					StructureCompassItem.setLastTick(stack, world, world.getTime());
					double doub2 = doub - StructureCompassItem.getAngle(stack);
					doub2 = MathHelper.floorMod(doub2 + 0.5D, 1.0D) - 0.5D;
					StructureCompassItem.setStep(stack, world, StructureCompassItem.getStep(stack) + (doub2 * 0.1D));
					StructureCompassItem.setStep(stack, world, StructureCompassItem.getStep(stack) * 0.8D);
					StructureCompassItem.setAngle(stack, world, MathHelper.floorMod(StructureCompassItem.getAngle(stack) + StructureCompassItem.getStep(stack), 1.0D));
				}
				
				return StructureCompassItem.getAngle(stack);
			}
			
			@Environment(EnvType.CLIENT)
			private double getYaw(ItemFrameEntity itemFrameEntity) {
				return MathHelper.wrapDegrees(180 + itemFrameEntity.getHorizontalFacing().getHorizontal() * 90);
			}
			
			@Environment(EnvType.CLIENT)
			private double getAngleToPos(BlockPos pos, Entity entity) {
				return Math.atan2((double)pos.getZ() - entity.z, (double)pos.getX() - entity.x);
			}
			
			@Environment(EnvType.CLIENT)
			private double getAngleFromTag(ItemStack stack, Entity entity) {
				return getAngleToPos(TagHelper.deserializeBlockPos(stack.getTag().getCompound("pos")), entity);
			}
		});
	}
	
	@Environment(EnvType.CLIENT)
	public static ItemStack setAngle(ItemStack stack, IWorld world, double angle) {
		CompoundTag tags = stack.getTag();
		if (tags == null) {
			tags = new CompoundTag();
			tags.put("pos", TagHelper.serializeBlockPos(world.getSpawnPos()));
			tags.putInt("dim", 0);
		}
		tags.putDouble("angle", angle);
		stack.setTag(tags);
		return stack;
	}
	
	@Environment(EnvType.CLIENT)
	public static ItemStack setStep(ItemStack stack, IWorld world, double step) {
		CompoundTag tags = stack.getTag();
		if (tags == null) {
			tags = new CompoundTag();
			tags.put("pos", TagHelper.serializeBlockPos(world.getSpawnPos()));
			tags.putInt("dim", 0);
		}
		tags.putDouble("step", step);
		stack.setTag(tags);
		return stack;
	}
	
	@Environment(EnvType.CLIENT)
	public static ItemStack setLastTick(ItemStack stack, IWorld world, long lastTick) {
		CompoundTag tags = stack.getTag();
		if (tags == null) {
			tags = new CompoundTag();
			tags.put("pos", TagHelper.serializeBlockPos(world.getSpawnPos()));
			tags.putInt("dim", 0);
		}
		tags.putLong("lasttick", lastTick);
		stack.setTag(tags);
		return stack;
	}
	
	@Environment(EnvType.CLIENT)
	public static double getAngle(ItemStack stack) {
		if(stack.hasTag()) {
			CompoundTag tag = stack.getTag();
			if (tag.containsKey("angle")) {
				return tag.getDouble("angle");
			}
		}
		return 0.0;
	}
	
	@Environment(EnvType.CLIENT)
	public static double getStep(ItemStack stack) {
		if(stack.hasTag()) {
			CompoundTag tag = stack.getTag();
			if (tag.containsKey("step")) {
				return tag.getDouble("step");
			}
		}
		return 0.0;
	}
	
	@Environment(EnvType.CLIENT)
	public static long getLastTick(ItemStack stack) {
		if(stack.hasTag()) {
			CompoundTag tag = stack.getTag();
			if (tag.containsKey("lasttick")) {
				return tag.getLong("lasttick");
			}
		}
		return 0;
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int i, boolean bool) {
		super.inventoryTick(stack, world, entity, i, bool);
		if(!world.isClient()) {
			BlockPos pos = world.locateStructure(structure.getName(), entity.getBlockPos(), 64, false);
			
			if(pos!=null) {
				CompoundTag tags = stack.getTag();
				if (tags == null)
					tags = new CompoundTag();
				
				tags.put("pos", TagHelper.serializeBlockPos(pos));
				tags.putInt("dim", world.getDimension().getType().getRawId());
				
				if(!stack.getTag().equals(tags))
					stack.setTag(tags);
			}
		}
	}
}
