// Original binary: sg/mx/Tn59HLlytfMjZI0p
package quick.mixin;

import net.minecraft.client.renderer.blockentity.state.SkullBlockRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import quick.core.SkullTextureAccess;

// $VF: Compiled from s
@Mixin(SkullBlockRenderState.class)
public class SkullBlockRenderStateMixin implements SkullTextureAccess {
   @Unique
   private int hex$slot;

   @Override
   public int hex$headSlot() {
      return this.hex$slot;
   }

   @Override
   public void hex$headSlot(int var1) {
      this.hex$slot = var1;
   }
}
