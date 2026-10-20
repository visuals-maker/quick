// Original binary: sg/ec/ryk91HZrLtlLDUoe
package quick.module.misc;


import quick.setting.MultiToggleSetting;
import quick.core.*;

// $VF: Compiled from s
public class StreamerMode extends ClientModule {
   private static final String field23532 = "Hide Nicknames";
   private static String field23533 = "";
   private final MultiToggleSetting field23534;
   private boolean field23535;
   private boolean field23536;
   private static final String field23537 = "What to do";
   private static final String field23538 = "Hide Nicknames";
   private static final String field23539 = "Custom Scorboard";
   private static final String field23540 = "Streamer Mode";
   private static final String field23541 = "Custom Scorboard";
   private static final String field23542 = "Hide Nicknames";

   public boolean method10540() {
      return this.isEnabled() && this.field23535;
   }

   @Subscribe
   private void method10541(PlayerUpdateEvent var1) {
      boolean var2 = this.field23534.isOptionSelected(field23541);
      this.field23535 = this.field23534.isOptionSelected(field23542);
      if (var2) {
         ScoreboardTextFormatter.INSTANCE.method05635();
      } else if (this.field23536) {
         ScoreboardTextFormatter.INSTANCE.method05642();
      }

      this.field23536 = var2;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      ScoreboardTextFormatter.INSTANCE.method05642();
   }

   public StreamerMode() {
      super(ModuleCategory.Misc, ModuleSubCategory.Other);
      this.field23534 = new MultiToggleSetting(field23537)
         .addOption(NamedOption.createNamedOption(field23538, true))
         .addOption(NamedOption.createNamedOption(field23539, false))
         .setContainer(this);
      this.field23535 = false;
      this.field23536 = false;
      this.setName(field23540);
   }

   @Subscribe
   private void method10542(PacketEvent var1) {
      if (this.field23536 && var1.method10904() && ScoreboardTextFormatter.INSTANCE.method05641(var1.method10897())) {
         var1.cancel();
      }
   }
}
