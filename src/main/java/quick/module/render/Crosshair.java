// Original binary: sg/ec/90Fj1Xd5GjraP16n
package quick.module.render;


import quick.setting.ColorSetting;
import quick.setting.NumberSetting;
import quick.setting.ToggleSetting;
import quick.core.*;

import java.awt.Color;
import net.minecraft.client.CameraType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.phys.EntityHitResult;

// $VF: Compiled from s
public class Crosshair extends ClientModule {
   private static final float field05120 = 0.5F;
   private final NumberSetting<Float> field05121;
   private final NumberSetting<Float> field05122;
   private final NumberSetting<Float> field05123;
   private final ToggleSetting field05124;
   private final NumberSetting<Float> field05125;
   private final ToggleSetting field05126;
   private final ColorSetting field05127;
   private final ColorSetting field05128;
   private boolean field05129;
   private final SmoothAnimation field05130;
   private static final String field05131 = "Width";
   private static final float field05132 = 5.0F;
   private static final float field05133 = 0.1F;
   private static final String field05134 = "Length";
   private static final float field05135 = 5.0F;
   private static final float field05136 = 0.1F;
   private static final String field05137 = "Space";
   private static final float field05138 = 5.0F;
   private static final float field05139 = 0.1F;
   private static final String field05140 = "Dynamic";
   private static final String field05141 = "Expansion force";
   private static final float field05142 = 10.0F;
   private static final float field05143 = 0.1F;
   private static final String field05144 = "Change color on hover";
   private static final String field05145 = "Color";
   private static final String field05146 = "Hover Color";
   private static final String field05147 = "Crosshair";
   private static final double field05148 = 0.1F;
   private static final float field05149 = 0.5F;
   private static final float field05150 = 0.5F;
   private static final float field05151 = 0.5F;
   private static final float field05152 = 0.5F;

   public Crosshair() {
      super(ModuleCategory.Render, ModuleSubCategory.Interface);
      this.field05121 = new NumberSetting<Float>(field05131)
         .setMin(1.0F)
         .setMax(field05132)
         .setStep(field05133)
         .setDefaultValue(1.0F)
         .setContainer(this);
      this.field05122 = new NumberSetting<Float>(field05134)
         .setMin(1.0F)
         .setMax(field05135)
         .setStep(field05136)
         .setDefaultValue(1.0F)
         .setContainer(this);
      this.field05123 = new NumberSetting<Float>(field05137)
         .setMin(1.0F)
         .setMax(field05138)
         .setStep(field05139)
         .setDefaultValue(1.0F)
         .setContainer(this);
      this.field05124 = new ToggleSetting(field05140).setDefaultValue(false).setContainer(this);
      this.field05125 = new NumberSetting<Float>(field05141)
         .setMin(1.0F)
         .setMax(field05142)
         .setStep(field05143)
         .setDefaultValue(1.0F)
         .setVisibleSupplier(this.field05124::getValue)
         .setContainer(this);
      this.field05126 = new ToggleSetting(field05144).setDefaultValue(false).setContainer(this);
      this.field05127 = new ColorSetting(field05145).setDefaultValue(new Color(ColorUtils.method04604(255, 255, 255))).setContainer(this);
      this.field05128 = new ColorSetting(field05146)
         .setDefaultValue(new Color(ColorUtils.method04604(255, 255, 255)))
         .setVisibleSupplier(this.field05126::getValue)
         .setContainer(this);
      this.field05129 = false;
      this.field05130 = new SmoothAnimation();
      this.setName(field05147);
   }

   private void method02152(GuiGraphicsExtractor var1, float var2, float var3, float var4, float var5, int var6, int var7) {
      this.method02155(var1, var2 - field05151, var3 - field05152, var4 + 1.0F, var5 + 1.0F, var7);
      this.method02155(var1, var2, var3, var4, var5, var6);
   }

   private static float method02153(float var0, float var1, float var2) {
      return var0 + (1.0F - var1) * var2;
   }

   @Subscribe
   private void method02154(CrosshairRenderEvent var1) {
      if (mc.options.getCameraType() == CameraType.FIRST_PERSON) {
         if (mc.player != null) {
            GuiGraphicsExtractor var2 = var1.method08629();
            this.field05130.update();
            this.field05130.method06166(this.field05129 ? 1.0 : 0.0, field05148);
            float var3 = this.field05123.getValue();
            float var4 = this.field05121.getValue();
            float var5 = this.field05122.getValue();
            float var6 = mc.getWindow().getGuiScaledWidth() / 2.0F - field05149;
            float var7 = mc.getWindow().getGuiScaledHeight() / 2.0F - field05150;
            int var8 = ColorUtils.method04627(this.field05128.method11676(), this.field05127.method11676(), this.field05130.getValueFloat());
            int var9 = ColorUtils.method04604(0, 0, 0);
            if (this.field05124.isEnabled()) {
               var3 = method02153(var3, mc.player.getAttackStrengthScale(var1.method08625().getGameTimeDeltaPartialTick(true)), this.field05125.getValue());
            }

            float var10 = var4 / 2.0F;
            this.method02152(var2, var6 - var3 - var5, var7 - var10, var5, var4, var8, var9);
            this.method02152(var2, var6 + var3, var7 - var10, var5, var4, var8, var9);
            this.method02152(var2, var6 - var10, var7 - var3 - var5, var4, var5, var8, var9);
            this.method02152(var2, var6 - var10, var7 + var3, var4, var5, var8, var9);
            var1.cancel();
         }
      }
   }

   public void method02155(GuiGraphicsExtractor var1, float var2, float var3, float var4, float var5, int var6) {
      RoundedRectRenderer.method01272(var1, var2, var3, var4, var5, 0.0F, 0.0F, var6);
   }

   @Subscribe
   private void method02156(PlayerUpdateEvent var1) {
      this.field05129 = false;
      if (this.field05126.isEnabled() && mc.hitResult instanceof EntityHitResult var2 && var2.getEntity() != null) {
         this.field05129 = true;
      }
   }
}
