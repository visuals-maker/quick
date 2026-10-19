// Original binary: hex/api/baritone/cache/WorldData
package hex.api.baritone.cache;

import hex.api.baritone.Baritone;
import hex.api.baritone.api.cache.ICachedWorld;
import hex.api.baritone.api.cache.IWaypointCollection;
import hex.api.baritone.api.cache.IWorldData;
import java.nio.file.Path;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import ru.dreamix.fabricloader.VMBridge;

// $VF: Compiled from s
public class WorldData implements IWorldData {
   public final CachedWorld cache;
   private final WaypointCollection waypoints;
   public final Path directory;
   public final DimensionType dimension;
   private static String бщl = "";
   private static String бщ必 = "";

   public void onClose() {
      Baritone.getExecutor().execute(() -> this.cache.save());
   }

   @Override
   public ICachedWorld getCachedWorld() {
      return this.cache;
   }

   WorldData(Path var1, DimensionType var2, ResourceKey<Level> var3) {
      this.directory = var1;
      this.cache = new CachedWorld(var1.resolve(бщl), var2, var3);
      this.waypoints = new WaypointCollection(var1.resolve(бщ必));
      this.dimension = var2;
   }

   @Override
   public IWaypointCollection getWaypoints() {
      return this.waypoints;
   }

   static {
      VMBridge.identifyClass(WorldData.class, "0X2RbkId");
   }
}
