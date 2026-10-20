// Original binary: sg/ec/zC0XiVWavMUfU3V1
package quick.module.render;

import quick.core.*;

// $VF: Compiled from s
public class ClickGUI extends ClientModule {
   private static final String field27853 = "Click GUI";

   public ClickGUI() {
      super(ModuleCategory.Render, ModuleSubCategory.Other);
      this.setBinding(new KeyBinding(344));
      this.setToggleable(false);
      this.setName(field27853);
   }

   @Override
   public void toggle() {
      ClientScreenManager.INSTANCE.method08319(QuickClient.getInstance().getMenuScreen());
   }
}
