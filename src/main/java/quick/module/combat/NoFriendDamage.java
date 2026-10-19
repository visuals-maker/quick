// Original binary: sg/ec/VzcLpy7fVXIVlCwD
package quick.module.combat;

import sg.ec.*;

import net.minecraft.world.entity.player.Player;

// $VF: Compiled from s
public class NoFriendDamage extends ClientModule {
   private static final String field12734 = "No Friend Damage";

   public NoFriendDamage() {
      super(ModuleCategory.Combat, ModuleSubCategory.Other);
      this.setName(field12734);
   }

   @Subscribe
   private void method05958(HitResultEvent var1) {
      if (var1.method03289() == HitResultEvent.Nested91.ENTITY
         && var1.method03286() instanceof Player var3
         && FriendManager.INSTANCE.method00172(var3)) {
         var1.cancel();
      }
   }
}
