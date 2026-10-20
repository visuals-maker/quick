// Original binary: sg/mx/OCaptifQViwayows
package quick.mixin;

import java.util.Map;
import java.util.UUID;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.LerpingBossEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

// $VF: Compiled from s
@Mixin(BossHealthOverlay.class)
public interface BossHealthOverlayMixin {
   @Accessor("events")
   Map<UUID, LerpingBossEvent> events();
}
