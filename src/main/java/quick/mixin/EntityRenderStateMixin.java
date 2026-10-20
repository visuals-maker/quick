// Original binary: sg/mx/uMJyo2HhofXAAK1l
package quick.mixin;

import java.lang.ref.WeakReference;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import quick.core.EntityRenderStateHooks;
import quick.core.EntityRenderStateAccess;

// $VF: Compiled from s
@Mixin(EntityRenderState.class)
public abstract class EntityRenderStateMixin implements EntityRenderStateAccess {
   @Unique
   private WeakReference<Entity> hexEntity;

   @Override
   public Entity getHexEntity() {
      return EntityRenderStateHooks.method02380(this.hexEntity);
   }

   @Override
   public void setHexEntity(Entity var1) {
      this.hexEntity = EntityRenderStateHooks.method02381(var1);
   }
}
