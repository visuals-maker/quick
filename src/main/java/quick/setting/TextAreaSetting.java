// Original binary: sg/ec/U1oG68VRwXKXYvO8
package quick.setting;

import quick.core.*;

// $VF: Compiled from s
public class TextAreaSetting extends Setting<String> {
   private String field12386 = "";
   private int field12387 = 256;

   public TextAreaSetting method05826(int var1) {
      this.field12387 = Math.max(1, var1);
      return this;
   }

   public int method05827() {
      return this.field12387;
   }

   public TextAreaSetting(String var1) {
      super(var1);
   }

   public TextAreaSetting method05828(String var1) {
      this.field12386 = var1 == null ? "" : var1;
      return this;
   }

   public String method05829() {
      return this.field12386;
   }

   public String method05830() {
      return this.getValue() == null ? "" : this.getValue();
   }
}
