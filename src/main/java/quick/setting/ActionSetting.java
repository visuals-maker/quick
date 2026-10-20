// Original binary: sg/ec/8yXoTKnZtE3lKULz
package quick.setting;

import quick.core.*;

// $VF: Compiled from s
public class ActionSetting extends Setting<Runnable> {
   public ActionSetting(String var1) {
      super(var1);
   }

   public void method02151() {
      if (this.getValue() != null) {
         this.getValue().run();
      }
   }
}
