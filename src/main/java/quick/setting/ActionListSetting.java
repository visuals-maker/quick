// Original binary: sg/ec/BJkfkYH9RbWdpHsT
package quick.setting;

import sg.ec.*;

import java.util.ArrayList;
import java.util.List;

// $VF: Compiled from s
public class ActionListSetting extends Setting<List<NamedOption<Runnable>>> {
   public ActionListSetting(String var1) {
      super(var1);
      this.setValue(new ArrayList<>());
   }

   public ActionListSetting method02723(String var1, Runnable var2) {
      this.getValue().add(NamedOption.createNamedOption(var1, var2));
      return this;
   }
}
