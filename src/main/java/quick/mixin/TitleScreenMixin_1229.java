// Original binary: sg/mx/W46Z10xsyy0G9ad4
package quick.mixin;

import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import quick.core.TitleScreenHooks;

// $VF: Compiled from s
@Mixin(TitleScreen.class)
public class TitleScreenMixin_1229 {
   @Inject(method = "init()V", at = @At("HEAD"), cancellable = true)
   private void init(CallbackInfo var1) {
      TitleScreenHooks.method11007(var1);
   }
}
