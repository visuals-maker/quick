// Original binary: sg/ec/tSsS5BX2t10g9Uv6
package quick.setting;

import sg.ec.*;

// $VF: Compiled from s
public class NumberSetting<T extends Number> extends Setting<T> {
   private T field23972;
   private T field23973;
   private T field23974;
   private boolean field23975 = true;

   public NumberSetting<T> setStep(T var1) {
      if (this.field23974 == null) {
         this.field23974 = var1;
      }

      return this;
   }

   public NumberSetting<T> setMin(T var1) {
      if (this.field23972 == null) {
         this.field23972 = (T)var1;
      }

      return this;
   }

   public T getMin() {
      return this.field23972;
   }

   public void setValueFromFloat(float var1) {
      if (this.getDefaultValue() instanceof Integer) {
         this.setValue((T)(Object)Math.round(var1));
      } else if (this.getDefaultValue() instanceof Double) {
         this.setValue((T)(Object)((double)var1));
      } else if (this.getDefaultValue() instanceof Float) {
         this.setValue((T)(Object)var1);
      } else {
         if (!(this.getDefaultValue() instanceof Long)) {
            throw new UnsupportedOperationException("Type " + this.getDefaultValue().getClass() + " is not supported.");
         }

         this.setValue((T)(Object)((long)var1));
      }
   }

   public NumberSetting(String var1) {
      super(var1);
   }

   public NumberSetting<T> setMax(T var1) {
      if (this.field23973 == null) {
         this.field23973 = (T)var1;
      }

      return this;
   }

   public NumberSetting<T> method10839(boolean var1) {
      this.field23975 = var1;
      return this;
   }

   public boolean method10840() {
      return this.field23975;
   }

   public T getMax() {
      return this.field23974;
   }

   public T getStep() {
      return this.field23973;
   }
}
