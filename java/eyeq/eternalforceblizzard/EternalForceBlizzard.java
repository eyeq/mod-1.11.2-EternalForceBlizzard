package eyeq.eternalforceblizzard;

import eyeq.eternalforceblizzard.entity.EntityDragonDummy;
import eyeq.eternalforceblizzard.item.ItemBurningDarkFlame;
import eyeq.eternalforceblizzard.item.ItemEternalForce;
import eyeq.util.client.model.UModelCreator;
import eyeq.util.client.model.UModelLoader;
import eyeq.util.client.model.gson.ItemmodelJsonFactory;
import eyeq.util.client.renderer.ResourceLocationFactory;
import eyeq.util.client.resource.ULanguageCreator;
import eyeq.util.client.resource.lang.LanguageResourceManager;
import eyeq.util.common.registry.UEntityRegistry;
import eyeq.util.oredict.UOreDictionary;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.io.File;

import static eyeq.eternalforceblizzard.EternalForceBlizzard.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
@Mod.EventBusSubscriber
public class EternalForceBlizzard {
    public static final String MOD_ID = "eyeq_eternalforceblizzard";

    @Mod.Instance(MOD_ID)
    public static EternalForceBlizzard instance;

    private static final ResourceLocationFactory resource = new ResourceLocationFactory(MOD_ID);

    public static Item eternalForce;
    public static Item burningDarkFlame;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        addRecipes();
        registerEntities();
        if(event.getSide().isServer()) {
            return;
        }
        renderItemModels();
        registerEntityRenderings();
        createFiles();
    }

    @SubscribeEvent
    protected static void registerItems(RegistryEvent.Register<Item> event) {
        eternalForce = new ItemEternalForce().setUnlocalizedName("eternalForce");
        burningDarkFlame = new ItemBurningDarkFlame().setUnlocalizedName("burningDarkFlame");

        GameRegistry.register(eternalForce, resource.createResourceLocation("efb"));
        GameRegistry.register(burningDarkFlame, resource.createResourceLocation("bdf"));
    }

    public static void addRecipes() {
        GameRegistry.addShapelessRecipe(new ItemStack(eternalForce), Blocks.DEADBUSH, Items.DIAMOND_HORSE_ARMOR, Blocks.ICE);
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(burningDarkFlame),
                Items.FLINT_AND_STEEL, UOreDictionary.OREDICT_OBSIDIAN, Items.FIRE_CHARGE, UOreDictionary.OREDICT_TORCH));
    }

    public static void registerEntities() {
        UEntityRegistry.registerModEntity(resource, EntityDragonDummy.class, "Dragon", 0, instance);
    }

	@SideOnly(Side.CLIENT)
    public static void renderItemModels() {
        UModelLoader.setCustomModelResourceLocation(eternalForce);
        ModelLoader.setCustomModelResourceLocation(burningDarkFlame, 0, ResourceLocationFactory.createModelResourceLocation(Items.FLINT_AND_STEEL));
    }

	@SideOnly(Side.CLIENT)
    public static void registerEntityRenderings() {
        RenderingRegistry.registerEntityRenderingHandler(EntityDragonDummy.class, RenderDragon::new);
    }
	
    public static void createFiles() {
    	File project = new File("../1.11.2-EternalForceBlizzard");
    	
        LanguageResourceManager language = new LanguageResourceManager();

        language.register(LanguageResourceManager.EN_US, eternalForce, "Eternal Force Blizzard");
        language.register(LanguageResourceManager.JA_JP, eternalForce, "エターナルフォースブリザード");
        language.register(LanguageResourceManager.EN_US, burningDarkFlame, "Burning dark flame of December");
        language.register(LanguageResourceManager.JA_JP, burningDarkFlame, "バーニング・ダーク・フレイム・オブ・ディッセンバー");

        language.register(LanguageResourceManager.EN_US, EntityDragonDummy.class, "Ancient Legend's Dark Magic Dragon");
        language.register(LanguageResourceManager.JA_JP, EntityDragonDummy.class, "古代伝説の暗黒魔竜");

        ULanguageCreator.createLanguage(project, MOD_ID, language);

        UModelCreator.createItemJson(project, eternalForce, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
    }
}
