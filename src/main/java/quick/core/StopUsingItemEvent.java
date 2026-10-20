// Original binary: sg/ec/fE5zndaDy6kx3CfH
package quick.core;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import quick.mixin.LivingEntityMixin_1248;

// $VF: Compiled from s
public class StopUsingItemEvent extends Event {
   public static final StopUsingItemEvent field18897 = new StopUsingItemEvent();
   private LivingEntity field18898;
   private ItemStack field18899;

   public LivingEntity method08030() {
      return this.field18898;
   }

   public void method08031(LivingEntity var1) {
      this.field18898 = var1;
   }

   public void method08032(LivingEntity var1, CallbackInfo var2) {
      this.setCancelled(false);
      this.field18898 = var1;
      this.field18899 = ((LivingEntityMixin_1248)var1).activeItemStack();
      super.post();
      if (this.method05694()) {
         var2.cancel();
      }
   }

   public ItemStack method08033() {
      return this.field18899;
   }

   public void method08034(ItemStack var1) {
      this.field18899 = var1;
   }
}
