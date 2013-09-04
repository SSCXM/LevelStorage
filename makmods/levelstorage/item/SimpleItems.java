package makmods.levelstorage.item;

import java.util.List;

import makmods.levelstorage.LevelStorage;
import makmods.levelstorage.LevelStorageCreativeTab;
import makmods.levelstorage.proxy.ClientProxy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Very, very convenient System for automatic dummyitems-adding. <br />
 * It automatically does all the stuff for you <br />
 * The only thing you need to do is add something like:
 * 
 * <pre>
 * 	<code>
 * 		addItem("textureName", "Name of your item that will be displayed-ingame");
 *  </code>
 * </pre>
 * 
 * to the initItems() method and draw a texture. In order to access created
 * items, just call {@link ItemCraftingIngredients.getIngredient()} passing meta
 * of the item you wanna get.
 * 
 * @author mak326428
 * 
 */
public class SimpleItems extends Item {

	public static SimpleItems instance = null;

	public SimpleItems() {
		// TODO: make more smart
		super(LevelStorage.getAndIncrementCurrId());
		this.setHasSubtypes(true);
		if (FMLCommonHandler.instance().getSide().isClient()) {
			this.setCreativeTab(LevelStorageCreativeTab.instance);
		}
		initItems();
	}

	public void initItems() {
		addItem("dustTinyOsmium", EnumRarity.uncommon); // 0
		addItem("dustOsmium", EnumRarity.rare); // 1
		addItem("itemOsmiridiumAlloy", EnumRarity.rare); // 2
		addItem("itemOsmiridiumPlate", EnumRarity.epic); // 3
		addItem("ingotOsmium", EnumRarity.rare); // 4
		addItem("ingotIridium", EnumRarity.uncommon); // 5
		addItem("itemUltimateCircuit", EnumRarity.rare); // 6
		addItem("itemEnergizedStar", EnumRarity.epic); // 7
	}

	/**
	 * Gets the ingredient
	 * 
	 * @param metadata
	 *            Metadata of item you want to get
	 * @return ItemStack containing item you requested, if not found, null
	 */
	public ItemStack getIngredient(int metadata) {
		if (metadata < 0)
			return null;
		if (metadata >= itemNames.size())
			return null;
		return new ItemStack(this.itemID, 1, metadata);
	}

	private List<Icon> itemIcons = Lists.newArrayList();
	public List<String> itemNames = Lists.newArrayList();
	private List<EnumRarity> itemRarities = Lists.newArrayList();

	public void addItem(String name, EnumRarity rarity) {
		itemNames.add(name);
		itemRarities.add(rarity);
		ItemStack currStack = new ItemStack(this.itemID, 1,
		        itemNames.size() - 1);
		// LanguageRegistry.instance().addStringLocalization(name + ".name",
		// localizedName);
		OreDictionary.registerOre(name, currStack);
	}

	public EnumRarity getRarity(ItemStack par1ItemStack) {
		try {
			return itemRarities.get(par1ItemStack.getItemDamage());
		} catch (Throwable t) {
			return EnumRarity.common;
		}
	}

	public String getUnlocalizedName(ItemStack stack) {
		try {
			return (String) itemNames.get(stack.getItemDamage());
		} catch (Throwable t) {
			return null;
		}
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		for (String name : itemNames) {
			itemIcons.add(iconRegister.registerIcon(ClientProxy
			        .getTexturePathFor(name)));
		}
	}

	public Icon getIconFromDamage(int par1) {
		try {
			return (Icon) itemIcons.get(par1);
		} catch (Throwable t) {
			return null;
		}
	}

	public void getSubItems(int par1, CreativeTabs par2CreativeTabs,
	        List par3List) {
		for (int i = 0; i < itemNames.size(); i++) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}

}
