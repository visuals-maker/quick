// Original binary: sg/ec/Nk7nKI0E1tnhINKl
package quick.module.render;


import quick.setting.ModeSetting;
import quick.core.*;

// $VF: Compiled from s
public class Panic extends ClientModule {
   private final ModeSetting field10302;
   private static final String field10303 = "Hide mode";
   private static final String field10304 = "Hide window";
   private static final String field10305 = "Black screen";
   private static final String field10306 = "Black screen";
   private static final String field10307 = "Panic";
   private static final String field10308 = "Black screen";

   @Subscribe
   private void method04724(BlockCollisionEvent var1) {
      PanicController.method05867();
   }

   public Panic() {
      super(ModuleCategory.Render, ModuleSubCategory.Interface);
      this.field10302 = new ModeSetting(field10303).setModes(field10304, field10305).setDefaultValue(field10306).setContainer(this);
      this.setName(field10307);
   }

   @Override
   public void onDisable() {
      PanicController.method05863();
      super.onDisable();
   }

   @Override
   public void onEnable() {
      PanicController.method05864(this.field10302.isMode(field10308));
      super.onEnable();
   }
}
