// Original binary: sg/ec/hyZVuiHN1PW1I8VN
package quick.module.movement;


import quick.setting.NumberSetting;
import quick.module.combat.Velocity;
import quick.core.*;

import net.minecraft.world.entity.player.Input;
import net.minecraft.world.phys.Vec3;

// $VF: Compiled from s
public class DragonFly extends ClientModule {
   private static final double field19963 = 0.98;
   private static double field19964 = 0.0;
   private final NumberSetting<Float> field19965;
   private final NumberSetting<Float> field19966;
   private static final String field19967 = "XZ Velocity";
   private static final float field19968 = 0.5F;
   private static final float field19969 = 10.0F;
   private static final float field19970 = 0.1F;
   private static final String field19971 = "Y Velocity";
   private static final float field19972 = 0.5F;
   private static final float field19973 = 10.0F;
   private static final float field19974 = 0.1F;
   private static final String field19975 = "Dragon Fly";
   private static final double field19976 = 3.0;
   private static final double field19977 = 2.0;
   private static final double field19978 = 0.98;
   private static final double field19979 = 0.08999999999999997;

   private Vec3 method08727(Vec3 var1, double var2) {
      double var4 = var1.horizontalDistance();
      if (var4 <= var2) {
         return var1;
      }

      double sx = var2 / var4;
      return new Vec3(var1.x * sx, var1.y, var1.z * sx);
   }

   public DragonFly() {
      super(ModuleCategory.Movement, ModuleSubCategory.Other);
      this.field19965 = new NumberSetting<Float>(field19967)
         .setMin(field19968)
         .setMax(field19969)
         .setStep(field19970)
         .setDefaultValue(1.0F)
         .setContainer(this);
      this.field19966 = new NumberSetting<Float>(field19971)
         .setMin(field19972)
         .setMax(field19973)
         .setStep(field19974)
         .setDefaultValue(1.0F)
         .setContainer(this);
      this.setName(field19975);
   }

   private double method08728() {
      float var1 = mc.player.getAbilities().getFlyingSpeed();
      return (mc.player.isSprinting() ? var1 * field19977 : var1) * field19978 / field19979;
   }

   @Subscribe
   private void method08729(DragonTravelEvent var1) {
      Input var2 = mc.player.input.keyPresses;
      boolean var3 = var2.forward() != var2.backward() || var2.left() != var2.right();
      boolean var4 = var2.jump() != var2.shift();
      boolean var5 = true;
      Vec3 var6 = var1.method07275();
      if (!var3) {
         var6 = new Vec3(0.0, var6.y, 0.0);
      } else if (var5 && !var4) {
         double[] var7 = MovementUtils.method05732();
         var6 = new Vec3(var7[0] * this.field19965.getValue().floatValue(), var6.y, var7[1] * this.field19965.getValue().floatValue());
      } else {
         var6 = this.method08727(var6, this.method08728());
      }

      if (!var4) {
         var6 = new Vec3(var6.x, 0.0, var6.z);
      } else if (!var3) {
         double var10 = var5 ? this.field19966.getValue().floatValue() : mc.player.getAbilities().getFlyingSpeed() * field19976;
         var6 = new Vec3(var6.x, var2.jump() ? var10 : -var10, var6.z);
      }

      var1.method07277(var6);
   }
}
