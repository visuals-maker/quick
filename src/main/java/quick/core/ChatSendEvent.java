// Original binary: sg/ec/zExJBHVo8LM1nzYx
package quick.core;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// $VF: Compiled from s
public class ChatSendEvent extends Event {
   public static final ChatSendEvent field27858 = new ChatSendEvent();
   private String field27859;

   public void method11710(String var1) {
      this.field27859 = var1;
      super.post();
   }

   public String method11711() {
      return this.field27859;
   }

   public void method11712(String var1) {
      this.field27859 = var1;
   }

   public void method11713(String var1, CallbackInfo var2) {
      this.field27859 = var1;
      super.post();
      if (this.method05694()) {
         var2.cancel();
      }
   }
}
