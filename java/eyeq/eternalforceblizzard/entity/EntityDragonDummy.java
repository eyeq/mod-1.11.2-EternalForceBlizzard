package eyeq.eternalforceblizzard.entity;

import eyeq.util.world.WorldUtils;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityDragonDummy extends EntityDragon {
    private static final DataParameter<Integer> FUSE = EntityDataManager.<Integer>createKey(EntityDragonDummy.class, DataSerializers.VARINT);

    private int fuse;

    public EntityDragonDummy(World world) {
        super(world);
        this.fuse = 80;
        this.preventEntitySpawning = true;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(FUSE, 80);
    }

    @Override
    public void onUpdate() {
        BlockPos pos = this.getPosition();
        super.onUpdate();
        fuse--;
        if(fuse <= 0) {
            this.setDead();
            this.fire(pos);
        }
    }

    public void fire(BlockPos pos) {
        if(world.isRemote) {
            return;
        }
        pos = pos.add(0, -5, 0);
        if(world.isAirBlock(pos)) {
            WorldUtils.playSound(world, this, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, rand);
            world.setBlockState(pos, Blocks.FIRE.getDefaultState(), 11);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setShort("Fuse", (short) this.getFuse());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setFuse(compound.getShort("Fuse"));
    }

    public void setFuse(int fuse) {
        this.dataManager.set(FUSE, fuse);
        this.fuse = fuse;
    }

    public int getFuse() {
        return this.fuse;
    }
}
