// Original binary: sg/mx/uBlm7I5YsF0U8ZEk
package quick.mixin;

import net.minecraft.client.multiplayer.PlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import quick.core.PlayerInfoHooks;
import quick.core.PlayerListEntryAccess;

// $VF: Compiled from s
@Mixin(PlayerInfo.class)
public class PlayerInfoMixin implements PlayerListEntryAccess {
   @Unique
   private PlayerInfoHooks hex$cache;

   @Override
   public PlayerInfoHooks hex$nameCache() {
      PlayerInfoHooks var1 = this.hex$cache;
      if (var1 == null) {
         var1 = new PlayerInfoHooks();
         this.hex$cache = var1;
      }

      return var1;
   }
}
