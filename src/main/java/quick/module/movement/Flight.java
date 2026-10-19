// Original binary: sg/ec/txl6thTa6tGHhx0T
package quick.module.movement;


import quick.setting.NumberSetting;
import quick.module.combat.Velocity;
import sg.ec.*;

import net.minecraft.world.phys.Vec3;

// $VF: Compiled from s
public class Flight extends ClientModule {
   private final NumberSetting<Float> field24191;
   private final NumberSetting<Float> field24192;
   private static final String field24193 = "XZ Velocity";
   private static final float field24194 = 0.1F;
   private static final float field24195 = 10.0F;
   private static final float field24196 = 0.1F;
   private static final float field24197 = 1.5F;
   private static final String field24198 = "Y Velocity";
   private static final float field24199 = 0.1F;
   private static final float field24200 = 10.0F;
   private static final float field24201 = 0.1F;
   private static final float field24202 = 1.5F;
   private static final String field24203 = "Flight";

   @Subscribe
   private void method10888(EntityMoveEvent var1) {
      this.method10889(var1);
   }

   private void method10889(EntityMoveEvent var1) {
      Vec3 var2 = var1.method10059();
      float var3 = this.field24191.getValue();
      float var4 = this.field24192.getValue();
      double var5 = MovementUtils.method05723(mc.options.keyJump.isDown(), mc.options.keyShift.isDown(), var4);
      var1.method10060(new Vec3(var2.x(), var5, var2.z()));
      MovementUtils.method05720(var1, var3, var4);
   }

   public Flight() {
      super(ModuleCategory.Movement, ModuleSubCategory.Main);
      this.field24191 = new NumberSetting<Float>(field24193)
         .setMin(field24194)
         .setMax(field24195)
         .setStep(field24196)
         .setDefaultValue(field24197)
         .setContainer(this);
      this.field24192 = new NumberSetting<Float>(field24198)
         .setMin(field24199)
         .setMax(field24200)
         .setStep(field24201)
         .setDefaultValue(field24202)
         .setContainer(this);
      this.setName(field24203);
   }
}
