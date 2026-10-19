// Original binary: sg/ec/V9fe92ZvhamCL3S9
package quick.module.misc;


import quick.setting.ModeSetting;
import quick.setting.NumberSetting;
import sg.ec.*;

// $VF: Compiled from s
public class Blink extends ClientModule {
   private final ModeSetting field12686;
   private final NumberSetting<Integer> field12687;
   int field12688;
   private static final String field12689 = "Mode";
   private static final String field12690 = "Toggle Mode";
   private static final String field12691 = "Ripple";
   private static final String field12692 = "Toggle Mode";
   private static final String field12693 = "Delay in Ticks";
   private static final String field12694 = "Blink";
   private static final String field12695 = "Ripple";
   private static final String field12696 = "Blink";
   private static final String field12697 = "Blink";
   private static final String field12698 = "Toggle Mode";
   private static final String field12699 = "Toggle Mode";

   @Subscribe
   private void method05929(PlayerUpdateEvent var1) {
      if (this.field12686.isMode(field12695)) {
         this.field12688++;
         if (!PacketBufferManager.INSTNCE.method03607()) {
            this.method05931();
         }

         if (this.field12688 > this.field12687.getValue()) {
            this.method05932();
            this.method05930();
         }
      }
   }

   private void method05930() {
      PacketBufferManager.INSTNCE.method03606(field12697);
   }

   private void method05931() {
      PacketBufferManager.INSTNCE.method03604(field12696);
   }

   @Override
   public void onDisable() {
      if (this.field12686.isMode(field12699) || PacketBufferManager.INSTNCE.method03607()) {
         this.method05930();
      }

      this.method05932();
      super.onDisable();
   }

   public Blink() {
      super(ModuleCategory.Misc, ModuleSubCategory.Utility);
      this.field12686 = new ModeSetting(field12689).setModes(field12690, field12691).setDefaultValue(field12692).setContainer(this);
      this.field12687 = new NumberSetting<Integer>(field12693).setMin(2).setMax(10).setStep(1).setDefaultValue(5).setContainer(this);
      this.field12688 = 0;
      this.setName(field12694);
   }

   private void method05932() {
      this.field12688 = 0;
   }

   @Override
   public void onEnable() {
      if (this.field12686.isMode(field12698)) {
         this.method05931();
      }

      this.method05932();
      super.onEnable();
   }
}
