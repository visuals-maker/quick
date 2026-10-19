// Original binary: sg/ec/RHVb1vKRqWdWZlGt
package quick.module.misc;


import quick.setting.MultiToggleSetting;
import sg.ec.*;

// $VF: Compiled from s
public class LegitMode extends ClientModule {
   private final MultiToggleSetting field11402;
   private static final String field11403 = "What to remove?";
   private static final String field11404 = "Customer Messages";
   private static final String field11405 = "Chat Teams";
   private static final String field11406 = "Discard All";
   private static final String field11407 = "Main Menu";
   private static final String field11408 = "Client Cloak/Elites";
   private static final String field11409 = "Chest search";
   private static final String field11410 = "Legit Mode";
   private static final String field11411 = "Customer Messages";
   private static final String field11412 = "Discard All";
   private static final String field11413 = "Chat Teams";
   private static final String field11414 = "Main Menu";
   private static final String field11415 = "Client Cloak/Elites";
   private static final String field11416 = "Chest search";

   @Subscribe
   private void method05268(ClientFeaturePolicyEvent var1) {
      boolean var2 = switch (var1.method06425()) {
         case CLIENT_MESSAGE -> this.field11402.isOptionSelected(field11411);
         case INVENTORY_BUTTON -> this.field11402.isOptionSelected(field11412);
         case CHAT_COMMAND -> this.field11402.isOptionSelected(field11413);
         case MAIN_MENU -> this.field11402.isOptionSelected(field11414);
         case CLIENT_CAPE -> this.field11402.isOptionSelected(field11415);
         case CHEST_SEARCH -> this.field11402.isOptionSelected(field11416);
      };
      if (var2) {
         var1.cancel();
      }
   }

   public LegitMode() {
      super(ModuleCategory.Misc, ModuleSubCategory.Other);
      this.field11402 = new MultiToggleSetting(field11403)
         .addOption(NamedOption.createNamedOption(field11404, false))
         .addOption(NamedOption.createNamedOption(field11405, true))
         .addOption(NamedOption.createNamedOption(field11406, true))
         .addOption(NamedOption.createNamedOption(field11407, false))
         .addOption(NamedOption.createNamedOption(field11408, false))
         .addOption(NamedOption.createNamedOption(field11409, true))
         .setContainer(this);
      this.setName(field11410);
   }
}
