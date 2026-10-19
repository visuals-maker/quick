// Original binary: sg/ec/LADiwJWWbBodGh3A
package quick.module.misc;


import quick.setting.ActionSetting;
import sg.ec.*;

// $VF: Compiled from s
public class AutoBuy extends ClientModule {
   private final ActionSetting field09855;
   private static final String field09856 = "Open shopping list";
   private static final String field09857 = "Auto Buy";
   private static final String field09858 = "Buys up auction lots at specified price limits.";

   public AutoBuy() {
      super(ModuleCategory.Misc, ModuleSubCategory.Servers);
      this.field09855 = new ActionSetting(field09856).setDefaultValue(this::method04424).setContainer(this);
      this.setName(field09857);
      this.setDescription(field09858);
   }

   @Override
   public void onDisable() {
      super.onDisable();
      AutoBuyManager.INSTANCE.method09760(false);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      AutoBuyManager.INSTANCE.method09760(true);
   }

   private void method04424() {
      ClickGuiScreen var1 = QuickClient.getInstance().getMenuScreen();
      if (var1 != null) {
         var1.method09988(ModuleCategory.AutoBuy);
      }
   }
}
