// Original binary: sg/ec/dldyNCE8JsQbnG1e
package quick.module.render;


import quick.setting.ModeSetting;
import quick.setting.NumberSetting;
import quick.module.combat.AttackAura;
import sg.ec.*;

import net.minecraft.world.phys.Vec3;

// $VF: Compiled from s
public class ViewModel extends ClientModule {
   private final NumberSetting<Float> field18353;
   private final NumberSetting<Float> field18354;
   private final NumberSetting<Float> field18355;
   private final NumberSetting<Float> field18356;
   private final NumberSetting<Float> field18357;
   private final NumberSetting<Float> field18358;
   private final ModeSetting field18359;
   private boolean field18360;
   private static final String field18361 = "Right X";
   private static final float field18362 = -3.0F;
   private static final float field18363 = 3.0F;
   private static final float field18364 = 0.01F;
   private static final String field18365 = "Right Y";
   private static final float field18366 = -3.0F;
   private static final float field18367 = 3.0F;
   private static final float field18368 = 0.01F;
   private static final String field18369 = "Right Z";
   private static final float field18370 = -3.0F;
   private static final float field18371 = 3.0F;
   private static final float field18372 = 0.01F;
   private static final String field18373 = "Left X";
   private static final float field18374 = -3.0F;
   private static final float field18375 = 3.0F;
   private static final float field18376 = 0.01F;
   private static final String field18377 = "Left Y";
   private static final float field18378 = -3.0F;
   private static final float field18379 = 3.0F;
   private static final float field18380 = 0.01F;
   private static final String field18381 = "Left Z";
   private static final float field18382 = -3.0F;
   private static final float field18383 = 3.0F;
   private static final float field18384 = 0.01F;
   private static final String field18385 = "Work";
   private static final String field18386 = "With Aura";
   private static final String field18387 = "On Swing";
   private static final String field18388 = "Always";
   private static final String field18389 = "Always";
   private static final String field18390 = "View Model";
   private static final String field18391 = "Always";
   private static final String field18392 = "On Swing";
   private static final String field18393 = "With Aura";

   @Subscribe
   private void method07504(TickEvent var1) {
      this.field18360 = this.method07506();
   }

   @Subscribe
   private void method07505(ViewModelOffsetEvent var1) {
      if (!this.field18360) {
         var1.method04139(
            new Vec3(this.field18353.getValue().floatValue(), this.field18354.getValue().floatValue(), this.field18355.getValue().floatValue())
         );
         var1.method04140(
            new Vec3(this.field18356.getValue().floatValue(), this.field18357.getValue().floatValue(), this.field18358.getValue().floatValue())
         );
         var1.setCancelled(true);
      }
   }

   public boolean method07506() {
      if (this.field18359.isMode(field18391)) {
         return false;
      } else if (!this.isEnabled()) {
         return true;
      } else if (!this.field18359.isMode(field18392)) {
         return !this.field18359.isMode(field18393) ? false : AttackAura.method00456() == null;
      } else {
         return mc.player == null || !mc.player.swinging;
      }
   }

   public ViewModel() {
      super(ModuleCategory.Render, ModuleSubCategory.Interface);
      this.field18353 = new NumberSetting<Float>(field18361)
         .setMin(field18362)
         .setMax(field18363)
         .setStep(field18364)
         .setDefaultValue(0.0F)
         .setContainer(this);
      this.field18354 = new NumberSetting<Float>(field18365)
         .setMin(field18366)
         .setMax(field18367)
         .setStep(field18368)
         .setDefaultValue(0.0F)
         .setContainer(this);
      this.field18355 = new NumberSetting<Float>(field18369)
         .setMin(field18370)
         .setMax(field18371)
         .setStep(field18372)
         .setDefaultValue(0.0F)
         .setContainer(this);
      this.field18356 = new NumberSetting<Float>(field18373)
         .setMin(field18374)
         .setMax(field18375)
         .setStep(field18376)
         .setDefaultValue(0.0F)
         .setContainer(this);
      this.field18357 = new NumberSetting<Float>(field18377)
         .setMin(field18378)
         .setMax(field18379)
         .setStep(field18380)
         .setDefaultValue(0.0F)
         .setContainer(this);
      this.field18358 = new NumberSetting<Float>(field18381)
         .setMin(field18382)
         .setMax(field18383)
         .setStep(field18384)
         .setDefaultValue(0.0F)
         .setContainer(this);
      this.field18359 = new ModeSetting(field18385).setModes(field18386, field18387, field18388).setDefaultValue(field18389).setContainer(this);
      this.setName(field18390);
   }
}
