// Original binary: sg/ec/y5DiOHE67ZvwUtXi
package quick.module.misc;


import quick.setting.MultiToggleSetting;
import quick.core.*;

// $VF: Compiled from s
public class ClientHelper extends ClientModule {
   private static final String field26376 = "ReallyWorld";
   private final MultiToggleSetting field26377;
   private final MultiToggleSetting field26378;
   private static final String field26379 = "What to turn off";
   private static final String field26380 = "Slot scrolling";
   private static final String field26381 = "Replace prefixes";
   private static final String field26382 = "ReallyWorld";
   private static final String field26383 = "Client Helper";
   private static final String field26384 = "ReallyWorld";
   private static final String field26385 = "Slot scrolling";

   @Subscribe
   private void method11551(MouseGrabEvent var1) {
      if (this.field26377.isOptionSelected(field26385)) {
         var1.cancel();
      }
   }

   public ClientHelper() {
      super(ModuleCategory.Misc, ModuleSubCategory.Utility);
      this.field26377 = new MultiToggleSetting(field26379).addOption(NamedOption.createNamedOption(field26380, false)).setContainer(this);
      this.field26378 = new MultiToggleSetting(field26381).addOption(NamedOption.createNamedOption(field26382, false)).setContainer(this);
      this.setName(field26383);
   }

   @Subscribe
   private void method11552(TextDecorationEvent var1) {
      var1.method09829(ServerRank.INSTANCE.method07289());
   }

   @Subscribe
   private void method11553(PlayerUpdateEvent var1) {
      ServerRank.INSTANCE.method07288(this.field26378.isOptionSelected(field26384));
   }

   @Override
   public void onDisable() {
      super.onDisable();
      ServerRank.INSTANCE.method07288(false);
   }
}
