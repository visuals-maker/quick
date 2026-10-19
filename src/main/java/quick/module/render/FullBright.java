// Original binary: sg/ec/c4Gu2aNTDEk2sQCm
package quick.module.render;


import quick.setting.ModeSetting;
import quick.setting.NumberSetting;
import quick.setting.ToggleSetting;
import sg.ec.*;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import org.joml.Vector3f;

// $VF: Compiled from s
public class FullBright extends ClientModule {
   private static final String field17625 = "Game Brightness";
   private static String field17626 = "";
   private final ModeSetting field17627;
   private final NumberSetting<Float> field17628;
   private final ToggleSetting field17629;
   private static final String field17630 = "Mode";
   private static final String field17631 = "Game Brightness";
   private static final String field17632 = "Potion";
   private static final String field17633 = "Game Brightness";
   private static final String field17634 = "Brightness";
   private static final float field17635 = 10.0F;
   private static final float field17636 = 0.1F;
   private static final float field17637 = 5.0F;
   private static final String field17638 = "Remove fog color";
   private static final String field17639 = "Full Bright";
   private static final String field17640 = "Potion";
   private static final String field17641 = "Game Brightness";
   private static final float field17642 = 10.0F;
   private static final String field17643 = "Potion";
   private static final String field17644 = "Potion";
   private static final String field17645 = "Game Brightness";

   @Subscribe
   private void method07229(VisualRemovalEvent var1) {
      if (var1.method08694() == VisualRemovalEvent.Nested242.NIGHT_VISION_FOG && this.field17627.isMode(field17643) && this.field17629.isEnabled()) {
         var1.setCancelled(true);
      }
   }

   public FullBright() {
      super(ModuleCategory.Render, ModuleSubCategory.Other);
      this.field17627 = new ModeSetting(field17630).setModes(field17631, field17632).setDefaultValue(field17633).setContainer(this);
      this.field17628 = new NumberSetting<Float>(field17634)
         .setMin(1.0F)
         .setMax(field17635)
         .setStep(field17636)
         .setDefaultValue(field17637)
         .setVisibleSupplier(() -> this.field17627.isMode(field17645))
         .setContainer(this);
      this.field17629 = new ToggleSetting(field17638).setDefaultValue(false).setVisibleSupplier(() -> this.field17627.isMode(field17644)).setContainer(this);
      this.setName(field17639);
   }

   private void method07230() {
      if (mc.player != null) {
         MobEffectInstance var1 = mc.player.getEffect(MobEffects.NIGHT_VISION);
         if (var1 != null && var1.isInfiniteDuration()) {
            mc.player.removeEffect(MobEffects.NIGHT_VISION);
         }
      }
   }

   @Subscribe
   private void method07231(WorldBrightnessEvent var1) {
      if (this.field17627.isMode(field17641)) {
         float var2 = this.field17628.getValue() / field17642;
         Vector3f var3 = var1.method01301();
         var3.set(Math.max(var3.x, var2), Math.max(var3.y, var2), Math.max(var3.z, var2));
      }
   }

   @Subscribe
   private void method07232(PlayerUpdateEvent var1) {
      if (mc.player != null) {
         if (this.field17627.isMode(field17640)) {
            if (!mc.player.hasEffect(MobEffects.NIGHT_VISION)) {
               mc.player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, -1));
            }
         } else {
            this.method07230();
         }
      }
   }

   @Override
   public void onDisable() {
      this.method07230();
      super.onDisable();
   }
}
