// Original binary: sg/ec/WIeDdbB8dqAqH9dw
package quick.setting;

import quick.core.*;

// $VF: Compiled from s
public class ToggleSetting extends Setting<Boolean> {
   public AnimationUtils field13573;
   public KeyBinding field13574;
   private static final long field13575 = 150L;

   public void toggle() {
      if (this.getValue() != null) {
         this.setValue(!this.getValue());
         if (this.getValue()) {
            ToggleStateEvent.field15044.method06765(this, ToggleStateEvent.Nested198.ENABLE, ToggleStateEvent.Nested197.CHECKBOX);
         } else {
            ToggleStateEvent.field15044.method06765(this, ToggleStateEvent.Nested198.DISABLE, ToggleStateEvent.Nested197.CHECKBOX);
         }
      }
   }

   public KeyBinding getBinding() {
      return this.field13574;
   }

   public AnimationUtils getAnimation() {
      return this.field13573;
   }

   public boolean isEnabled() {
      return this.getValue() == null ? false : this.getValue();
   }

   public ToggleSetting(String var1) {
      super(var1);
      this.field13573 = new AnimationUtils(AnimationUtils.Nested95.LINEAR, field13575);
      this.field13574 = new KeyBinding(-1);
   }
}
