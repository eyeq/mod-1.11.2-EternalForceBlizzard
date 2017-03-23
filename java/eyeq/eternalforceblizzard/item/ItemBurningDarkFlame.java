package eyeq.eternalforceblizzard.item;

import eyeq.eternalforceblizzard.entity.EntityDragonDummy;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemBurningDarkFlame extends Item {
    public ItemBurningDarkFlame() {
        this.setFull3D();
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemStack = player.getHeldItem(hand);
        if(!player.canPlayerEdit(pos, facing, itemStack)) {
            return EnumActionResult.FAIL;
        }
        if(world.isRemote) {
            return EnumActionResult.SUCCESS;
        }
        ITextComponent text = new TextComponentTranslation(this.getUnlocalizedName() + ".name");
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(text);

        pos = pos.offset(facing).add(0, 5, 0);
        EntityDragonDummy entity = new EntityDragonDummy(world);
        entity.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 0.0F);
        entity.rotationYawHead = entity.rotationYaw;
        entity.renderYawOffset = entity.rotationYaw;
        entity.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity)), null);
        entity.playLivingSound();
        world.spawnEntity(entity);
        return EnumActionResult.SUCCESS;
    }

    @Override
    public boolean hasEffect(ItemStack itemStack) {
        return true;
    }
}
