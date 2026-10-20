// Original binary: sg/ec/MPgfgQcQpBwMSVYt
package quick.module.render;


import quick.setting.NumberSetting;
import quick.setting.ToggleSetting;
import quick.core.*;

// $VF: Compiled from s
public class SeeInvisible extends ClientModule {
   private final ToggleSetting field10079;
   private final NumberSetting<Float> field10080;
   private static final String field10081 = "Show hands";
   private static final String field10082 = "Transparency";
   private static final float field10083 = 0.1F;
   private static final float field10084 = 0.05F;
   private static final float field10085 = 0.5F;
   private static final String field10086 = "See Invisible";

   public boolean method04538() {
      return this.isEnabled() && this.field10079.isEnabled() && mc.player != null && mc.player.isInvisible();
   }

   @Subscribe
   private void method04539(InvisibleEntityRenderEvent var1) {
      var1.method02607(ColorUtils.method04611(var1.method02604(), this.field10080.getValue()));
      var1.setCancelled(true);
   }

   public SeeInvisible() {
      super(ModuleCategory.Render, ModuleSubCategory.World);
      this.field10079 = new ToggleSetting(field10081).setDefaultValue(false).setContainer(this);
      this.field10080 = new NumberSetting<Float>(field10082)
         .setMin(field10083)
         .setMax(1.0F)
         .setStep(field10084)
         .setDefaultValue(field10085)
         .setContainer(this);
      this.setName(field10086);
   }

   public int method04540(int var1) {
      return this.method04538() ? ColorUtils.method04611(var1, this.field10080.getValue()) : var1;
   }
}
