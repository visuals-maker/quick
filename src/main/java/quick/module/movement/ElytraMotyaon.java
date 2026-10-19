// Original binary: sg/ec/rbsUK9TviU27nC9w
package quick.module.movement;


import quick.setting.NumberSetting;
import quick.setting.ToggleSetting;
import quick.module.combat.AttackAura;
import sg.ec.*;

import net.minecraft.world.entity.LivingEntity;

// $VF: Compiled from s
public class ElytraMotyaon extends ClientModule {
   private final NumberSetting<Float> field23379;
   private final ToggleSetting field23380;
   private final ToggleSetting field23381;
   private final NumberSetting<Float> field23382;
   private final ElapsedTimer field23383;
   private boolean field23384;
   private static final String field23385 = "Distance";
   private static final float field23386 = 3.0F;
   private static final float field23387 = 0.1F;
   private static final float field23388 = 1.5F;
   private static final String field23389 = "Improve frieze";
   private static final String field23390 = "Auto fireworks";
   private static final String field23391 = "Deceleration force";
   private static final float field23392 = 0.3F;
   private static final float field23393 = 0.01F;
   private static final String field23394 = "Elytra Motyaon";
   private static final long field23395 = 1000L;

   private boolean method10426(LivingEntity var1) {
      return RaycastUtils.method09502(mc.player.getYRot(), mc.player.getXRot(), ModuleManager.INSTANCE.method08219().method00403(), var1);
   }

   public boolean method10427() {
      return this.field23384;
   }

   @Subscribe
   private void method10428(EntityMoveEvent var1) {
      if (mc.player != null && mc.level != null) {
         this.method10430(false);
         LivingEntity var2 = AttackAura.method00456();
         float var3 = this.field23382.getValue();
         boolean var4 = var2 != null
            && mc.player.isFallFlying()
            && ((EntityExtensionAccess)mc.player).getDistanceEyePos(var2) < this.field23379.getValue().floatValue()
            && !ModuleManager.INSTANCE.method08219().field00787;
         if (this.field23380.isEnabled()) {
            var3 = 0.0F;
            var4 = var4 && this.method10426(var2);
            PlayerVelocityTracker.INSTANCE.method02571(var4);
         }

         if (var4) {
            if (this.field23383.method04699(field23395) && this.field23381.isEnabled()) {
               InventoryUtils.method06119();
               this.field23383.method04694();
            }

            this.method10430(true);
            var1.method10060(var1.method10059().scale(var3));
         }
      }
   }

   public void method10430(boolean var1) {
      this.field23384 = var1;
   }

   @Override
   public void toggle() {
      this.method10430(false);
      super.toggle();
   }

   public ElytraMotyaon() {
      super(ModuleCategory.Movement, ModuleSubCategory.Main);
      this.field23379 = new NumberSetting<Float>(field23385)
         .setMin(1.0F)
         .setMax(field23386)
         .setStep(field23387)
         .setDefaultValue(field23388)
         .setContainer(this);
      this.field23380 = new ToggleSetting(field23389).setDefaultValue(false).setContainer(this);
      this.field23381 = new ToggleSetting(field23390).setValue(true).setVisibleSupplier(() -> !this.field23380.isEnabled()).setContainer(this);
      this.field23382 = new NumberSetting<Float>(field23391)
         .setMin(0.0F)
         .setMax(field23392)
         .setStep(field23393)
         .setDefaultValue(0.0F)
         .setVisibleSupplier(() -> !this.field23380.isEnabled())
         .setContainer(this);
      this.field23383 = new ElapsedTimer();
      this.field23384 = false;
      this.setName(field23394);
   }

   @Override
   public void onDisable() {
      PlayerVelocityTracker.INSTANCE.method02571(false);
      super.onDisable();
   }
}
