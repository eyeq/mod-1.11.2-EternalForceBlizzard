package eyeq.eternalforceblizzard.item;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemEternalForce extends Item {
    public ItemEternalForce() {
        this.setFull3D();
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);
        if(!world.isRemote) {
            ITextComponent text = new TextComponentTranslation(this.getUnlocalizedName() + ".name");
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(text);
        }
        for(EntityLivingBase entity : world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().expand(16.0, 16.0, 16.0))) {
            if(entity == player) {
                continue;
            }
            AxisAlignedBB boundingBox = entity.getEntityBoundingBox();
            entity.setDead();
            if(world.isRemote) {
                continue;
            }
            BlockPos min = new BlockPos(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
            BlockPos max = new BlockPos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
            world.setBlockState(min, Blocks.ICE.getDefaultState());
            for(double x = min.getX(); x < max.getX(); x++) {
                for(double y = min.getY(); y < max.getY(); y++) {
                    for(double z = min.getZ(); z < max.getZ(); z++) {
                        world.setBlockState(new BlockPos(x, y, z), Blocks.ICE.getDefaultState());
                    }
                }
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
    }

    @Override
    public boolean hasEffect(ItemStack itemStack) {
        return true;
    }
}
