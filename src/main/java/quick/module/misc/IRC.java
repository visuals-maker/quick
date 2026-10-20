// Original binary: sg/ec/cTllnDf895iPTXbK
package quick.module.misc;


import quick.setting.ToggleSetting;
import quick.core.*;

// $VF: Compiled from s
public class IRC extends ClientModule {
   private final ToggleSetting field17748;
   private final ToggleSetting field17749;
   private static final String field17750 = "Turn off IRC";
   private static final String field17751 = "Hides client global chat messages.";
   private static final String field17752 = "Show prefix";
   private static final String field17753 = "Shows the sender's tag before his nickname.";
   private static final String field17754 = "IRC";
   private static final String field17755 = "custom";
   private static final String field17756 = "IRC";
   private static final String field17757 = "[IRC]";

   public IRC() {
      super(ModuleCategory.Misc, ModuleSubCategory.Other);
      this.field17748 = new ToggleSetting(field17750).setDefaultValue(false).setDescription(field17751).setContainer(this);
      this.field17749 = new ToggleSetting(field17752).setDefaultValue(true).setDescription(field17753).setContainer(this);
      this.setToggleable(false);
      this.setBindable(false);
      this.setName(field17754);
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void onDisable() {
   }

   public void method07279(ChatS2CPacket var1) {
      if (!this.field17748.getValue()) {
         if (var1 != null && var1.method02479() != null) {
            String var2 = this.field17749.getValue() ? var1.method02489() : "";
            String var3 = var1.method02479().method07586();
            boolean var4 = var1.method02484().containsKey(field17755) && var2 != null && !var2.isEmpty();
            if (var4) {
               ChatTextUtils.method05095(field17757, StyledTextParser.method11508(var2), var3, var1.method02486());
            } else {
               ChatTextUtils.method05081("[" + (var2 != null && !var2.isEmpty() ? var2 : field17756) + "]", var3, var1.method02486());
            }
         }
      }
   }
}
