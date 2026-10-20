// Original binary: sg/ec/JfPH6SAQ0rPmopRp
package quick.module.misc;


import quick.setting.ModeSetting;
import quick.setting.MultiToggleSetting;
import quick.core.*;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShovelItem;

// $VF: Compiled from s
public class AutoDropper extends ClientModule {
   private final ModeSetting field09195;
   private final MultiToggleSetting field09196;
   private static final String field09197 = "Throw away in";
   private static final String field09198 = "Chests/shulkers";
   private static final String field09199 = "Inventory";
   private static final String field09200 = "Together";
   private static final String field09201 = "Chests/shulkers";
   private static final String field09202 = "What to throw away";
   private static final String field09203 = "Boots";
   private static final String field09204 = "Leggings";
   private static final String field09205 = "Bibs";
   private static final String field09206 = "Helmets";
   private static final String field09207 = "Swords";
   private static final String field09208 = "Shovels";
   private static final String field09209 = "Pickaxes";
   private static final String field09210 = "Heads";
   private static final String field09211 = "Elytra";
   private static final String field09212 = "Fireworks";
   private static final String field09213 = "Totems";
   private static final String field09214 = "Booms";
   private static final String field09215 = "Luki Arbaleti";
   private static final String field09216 = "Crystals";
   private static final String field09217 = "Obsidian";
   private static final String field09218 = "Golden Apples";
   private static final String field09219 = "Charmed golden apples";
   private static final String field09220 = "Auto Dropper";
   private static final String field09221 = "Chests/shulkers";
   private static final String field09222 = "Together";
   private static final String field09223 = "Inventory";
   private static final String field09224 = "Together";
   private static final String field09225 = "Boots";
   private static final String field09226 = "boots";
   private static final String field09227 = "Leggings";
   private static final String field09228 = "leggings";
   private static final String field09229 = "Bibs";
   private static final String field09230 = "chestplate";
   private static final String field09231 = "Helmets";
   private static final String field09232 = "helmet";
   private static final String field09233 = "Swords";
   private static final String field09234 = "Shovels";
   private static final String field09235 = "Pickaxes";
   private static final String field09236 = "Heads";
   private static final String field09237 = "Elytra";
   private static final String field09238 = "Fireworks";
   private static final String field09239 = "Totems";
   private static final String field09240 = "Booms";
   private static final String field09241 = "Luki Arbaleti";
   private static final String field09242 = "Crystals";
   private static final String field09243 = "Obsidian";
   private static final String field09244 = "Golden Apples";
   private static final String field09245 = "Charmed golden apples";

   private static boolean method04067(String var0, String var1) {
      return var0.contains(var1) && var0.endsWith(var1);
   }

   private boolean method04068(ItemStack var1) {
      Item var2 = var1.getItem();
      if (this.field09196.isOptionSelected(field09225) && this.method04072(var2, field09226)) {
         return true;
      }

      if (this.field09196.isOptionSelected(field09227) && this.method04072(var2, field09228)) {
         return true;
      }

      if (this.field09196.isOptionSelected(field09229) && this.method04072(var2, field09230)) {
         return true;
      }

      if (this.field09196.isOptionSelected(field09231) && this.method04072(var2, field09232)) {
         return true;
      }

      if (this.field09196.isOptionSelected(field09233) && InventoryUtils.method06087(var1)) {
         return true;
      }

      if (this.field09196.isOptionSelected(field09234) && var2 instanceof ShovelItem) {
         return true;
      }

      if (this.field09196.isOptionSelected(field09235) && var1.is(ItemTags.PICKAXES)) {
         return true;
      }

      if (this.field09196.isOptionSelected(field09236) && this.method04071(var2)) {
         return true;
      }

      if (this.field09196.isOptionSelected(field09237) && var2 == Items.ELYTRA) {
         return true;
      }

      if (this.field09196.isOptionSelected(field09238) && var2 == Items.FIREWORK_ROCKET) {
         return true;
      }

      if (this.field09196.isOptionSelected(field09239) && var2 == Items.TOTEM_OF_UNDYING) {
         return true;
      }

      if (this.field09196.isOptionSelected(field09240) && var2 instanceof ArrowItem) {
         return true;
      }

      if (!this.field09196.isOptionSelected(field09241) || !(var2 instanceof BowItem) && !(var2 instanceof CrossbowItem)) {
         if (this.field09196.isOptionSelected(field09242) && var2 == Items.END_CRYSTAL) {
            return true;
         } else if (!this.field09196.isOptionSelected(field09243) || var2 != Items.OBSIDIAN && var2 != Items.CRYING_OBSIDIAN) {
            return this.field09196.isOptionSelected(field09244) && var2 == Items.GOLDEN_APPLE
               ? true
               : this.field09196.isOptionSelected(field09245) && var2 == Items.ENCHANTED_GOLDEN_APPLE;
         } else {
            return true;
         }
      } else {
         return true;
      }
   }

   public AutoDropper() {
      super(ModuleCategory.Misc, ModuleSubCategory.Servers);
      this.field09195 = new ModeSetting(field09197).setModes(field09198, field09199, field09200).setDefaultValue(field09201).setContainer(this);
      this.field09196 = new MultiToggleSetting(field09202)
         .addOption(NamedOption.createNamedOption(field09203, false))
         .addOption(NamedOption.createNamedOption(field09204, false))
         .addOption(NamedOption.createNamedOption(field09205, false))
         .addOption(NamedOption.createNamedOption(field09206, false))
         .addOption(NamedOption.createNamedOption(field09207, false))
         .addOption(NamedOption.createNamedOption(field09208, false))
         .addOption(NamedOption.createNamedOption(field09209, false))
         .addOption(NamedOption.createNamedOption(field09210, false))
         .addOption(NamedOption.createNamedOption(field09211, false))
         .addOption(NamedOption.createNamedOption(field09212, false))
         .addOption(NamedOption.createNamedOption(field09213, false))
         .addOption(NamedOption.createNamedOption(field09214, false))
         .addOption(NamedOption.createNamedOption(field09215, false))
         .addOption(NamedOption.createNamedOption(field09216, false))
         .addOption(NamedOption.createNamedOption(field09217, false))
         .addOption(NamedOption.createNamedOption(field09218, false))
         .addOption(NamedOption.createNamedOption(field09219, false))
         .setContainer(this);
      this.setName(field09220);
   }

   @Subscribe
   private void method04069(PlayerUpdateEvent var1) {
      if (mc.player != null && mc.player.containerMenu != null && mc.gameMode != null) {
         boolean var2 = this.field09195.isMode(field09221) || this.field09195.isMode(field09222);
         boolean var3 = this.field09195.isMode(field09223) || this.field09195.isMode(field09224);
         boolean var4 = mc.player.containerMenu instanceof ChestMenu;

         for (Slot var6 : mc.player.containerMenu.slots) {
            if (var6.hasItem() && this.method04070(var6, var2, var3, var4) && this.method04068(var6.getItem())) {
               mc.gameMode.handleContainerInput(mc.player.containerMenu.containerId, var6.index, 1, ContainerInput.THROW, mc.player);
            }
         }
      }
   }

   private boolean method04070(Slot var1, boolean var2, boolean var3, boolean var4) {
      boolean var5 = var1.container == mc.player.getInventory();
      return var5 ? var3 : var2 && var4;
   }

   private boolean method04071(Item var1) {
      return var1 == Items.SKELETON_SKULL
         || var1 == Items.WITHER_SKELETON_SKULL
         || var1 == Items.ZOMBIE_HEAD
         || var1 == Items.CREEPER_HEAD
         || var1 == Items.DRAGON_HEAD
         || var1 == Items.PIGLIN_HEAD
         || var1 == Items.PLAYER_HEAD;
   }

   private boolean method04072(Item var1, String var2) {
      return method04067(var1.getDescriptionId(), var2);
   }
}
