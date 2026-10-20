// Original binary: sg/ec/fLG6ZhsE070h2rGQ
package quick.module.movement;


import quick.setting.NumberSetting;
import quick.core.*;

import net.minecraft.world.item.Items;
import quick.mixin.MinecraftMixin;

// $VF: Compiled from s
public class SpearStrafe extends ClientModule {
   private final NumberSetting<Integer> field18910;
   private int field18911;
   private static final String field18912 = "Delay in Ticks";
   private static final String field18913 = "Spear Strafe";
   private static final float field18914 = 1.5F;
   private static final float field18915 = 0.95F;

   @Subscribe
   private void method08069(PlayerUpdateEvent var1) {
      if (mc.player != null) {
         this.field18911++;
         if (this.field18911 >= this.field18910.getValue()) {
            this.field18911 = 0;
            if (mc.player.onGround()) {
               return;
            }

            if (AttackCooldownUtils.method00008(mc.player, field18914) < field18915) {
               return;
            }

            int var2 = mc.player.getInventory().getSelectedSlot();
            int sx = InventoryUtils.method06113(Items.DIAMOND_SPEAR);
            if (sx == -1) {
               return;
            }

            mc.player.getInventory().setSelectedSlot(sx);
            ((MinecraftMixin)mc).IdoAttack();
            mc.player.getInventory().setSelectedSlot(var2);
         }
      }
   }

   public SpearStrafe() {
      super(ModuleCategory.Movement, ModuleSubCategory.Other);
      this.field18910 = new NumberSetting<Integer>(field18912).setMin(3).setMax(20).setStep(1).setDefaultValue(10).setContainer(this);
      this.field18911 = 0;
      this.setName(field18913);
   }
}
