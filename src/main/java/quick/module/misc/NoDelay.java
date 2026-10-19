// Original binary: sg/ec/iBkkTAt8PR1MZotU
package quick.module.misc;


import quick.setting.MultiToggleSetting;
import quick.setting.ToggleSetting;
import sg.ec.*;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import sg.mx.LivingEntityMixin_1248;

// $VF: Compiled from s
public class NoDelay extends ClientModule {
   public final MultiToggleSetting field20026;
   public final ToggleSetting field20027;
   private static final String field20028 = "Remove delay by";
   private static final String field20029 = "Jumping";
   private static final String field20030 = "Per pkm";
   private static final String field20031 = "Selecting conditions for removing delay";
   private static final String field20032 = "Only with experience";
   private static final String field20033 = "No Delay";
   private static final String field20034 = "Jumping";
   private static final String field20035 = "Per pkm";
   private static final String field20036 = "Per pkm";

   public NoDelay() {
      super(ModuleCategory.Misc, ModuleSubCategory.Other);
      this.field20026 = new MultiToggleSetting(field20028)
         .addOption(NamedOption.createNamedOption(field20029, false))
         .addOption(NamedOption.createNamedOption(field20030, false))
         .setDescription(field20031)
         .setContainer(this);
      this.field20027 = new ToggleSetting(field20032).setValue(false).setVisibleSupplier(() -> this.field20026.isOptionSelected(field20036)).setContainer(this);
      this.setName(field20033);
   }

   @Subscribe
   private void method08783(PlayerUpdateEvent var1) {
      Minecraft var2 = mc;
      if (var2.player != null) {
         if (this.field20026.isOptionSelected(field20034)) {
            ((LivingEntityMixin_1248)var2.player).setjumpingCooldown(0);
         }

         if (this.method08785(var2)) {
            ((MinecraftInteractionAccess)var2).setitemUseCooldown(0);
         }
      }
   }

   private boolean method08784(Minecraft var1) {
      ItemStack var2 = var1.player.getMainHandItem();
      if (var2.is(Items.EXPERIENCE_BOTTLE)) {
         return !var1.player.getCooldowns().isOnCooldown(var2);
      }

      ItemStack var3 = var1.player.getOffhandItem();
      return var3.is(Items.EXPERIENCE_BOTTLE) ? !var1.player.getCooldowns().isOnCooldown(var3) : false;
   }

   private boolean method08785(Minecraft var1) {
      return this.field20026.isOptionSelected(field20035) && var1.player != null ? !this.field20027.getValue() || this.method08784(var1) : false;
   }
}
