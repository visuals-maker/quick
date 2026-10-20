// Original binary: sg/mx/t3DebQKdeOgnmHHe
package quick.mixin;

import net.minecraft.client.multiplayer.ServerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import quick.core.PromotedServerAccess;

// $VF: Compiled from s
@Mixin(ServerData.class)
public class ServerDataMixin implements PromotedServerAccess {
   @Unique
   private int hexPromotedColor;

   @Override
   public int hex$promotedColor() {
      return this.hexPromotedColor;
   }

   @Override
   public void hex$promotedColor(int var1) {
      this.hexPromotedColor = var1;
   }
}
