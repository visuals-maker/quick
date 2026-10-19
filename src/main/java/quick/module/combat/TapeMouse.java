// Original binary: sg/ec/StogZKDAhbKsCJ1K
package quick.module.combat;


import quick.setting.ModeSetting;
import quick.setting.NumberSetting;
import sg.ec.*;

import sg.mx.MinecraftMixin;

// $VF: Compiled from s
public class TapeMouse extends ClientModule {
   private final ModeSetting field11967;
   private final NumberSetting<Integer> field11968;
   private static final String field11969 = "Mouse button";
   private static final String field11970 = "Left";
   private static final String field11971 = "Right";
   private static final String field11972 = "Left";
   private static final String field11973 = "Delay in Ticks";
   private static final String field11974 = "Tape Mouse";
   private static final String field11975 = "Right";

   private static boolean method05577(int var0, int var1) {
      int var2 = Math.max(1, var1);
      return var0 % var2 == 0;
   }

   @Subscribe
   private void method05578(PlayerUpdateEvent var1) {
      if (mc.player != null) {
         if (method05577(mc.player.tickCount, this.field11968.getValue())) {
            if (this.field11967.isMode(field11975)) {
               ((MinecraftMixin)mc).IdoItemUse();
            } else {
               ((MinecraftMixin)mc).IdoAttack();
            }
         }
      }
   }

   public TapeMouse() {
      super(ModuleCategory.Combat, ModuleSubCategory.Utility);
      this.field11967 = new ModeSetting(field11969).setModes(field11970, field11971).setDefaultValue(field11972).setContainer(this);
      this.field11968 = new NumberSetting<Integer>(field11973).setMin(1).setMax(100).setStep(1).setDefaultValue(11).setContainer(this);
      this.setName(field11974);
   }
}
