// Original binary: sg/mx/EvtGVVnNcuBymjOc
package quick.mixin;

import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderPassDescriptor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import quick.core.GuiGpuRenderer;

// $VF: Compiled from s
@Mixin(CommandEncoder.class)
public class CommandEncoderMixin {
   @Inject(method = "createRenderPass(Lcom/mojang/blaze3d/systems/RenderPassDescriptor;)Lcom/mojang/blaze3d/systems/RenderPass;", at = @At("HEAD"))
   private void hex$closeMergedPassBeforeForeign(RenderPassDescriptor var1, CallbackInfoReturnable<RenderPass> var2) {
      GuiGpuRenderer.method00368();
   }
}
