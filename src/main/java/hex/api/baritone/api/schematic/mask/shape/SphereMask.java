// Original binary: hex/api/baritone/api/schematic/mask/shape/SphereMask
package hex.api.baritone.api.schematic.mask.shape;

import hex.api.baritone.api.schematic.mask.AbstractMask;
import hex.api.baritone.api.schematic.mask.StaticMask;
import ru.dreamix.fabricloader.VMBridge;

// $VF: Compiled from s
public final class SphereMask extends AbstractMask implements StaticMask {
   private final double centerX;
   private final double centerY;
   private final double centerZ;
   private final double radiusSqX;
   private final double radiusSqY;
   private final double radiusSqZ;
   private final boolean filled;
   private static double б1и = 0.0;
   private static double б1щ = 0.0;
   private static double бяб = 0.0;
   private static double бяА = 0.0;
   private static double бя诶 = 0.0;
   private static double бяЫ = 0.0;

   @Override
   public boolean partOfMask(int var1, int var2, int var3) {
      double var4 = Math.abs(var1 + бяА - this.centerX);
      double sx = Math.abs(var2 + бя诶 - this.centerY);
      double sxx = Math.abs(var3 + бяЫ - this.centerZ);
      return this.outside(var4, sx, sxx)
         ? false
         : this.filled || this.outside(var4 + 1.0, sx, sxx) || this.outside(var4, sx + 1.0, sxx) || this.outside(var4, sx, sxx + 1.0);
   }

   private boolean outside(double var1, double var3, double var5) {
      return var1 * var1 / this.radiusSqX + var3 * var3 / this.radiusSqY + var5 * var5 / this.radiusSqZ > 1.0;
   }

   public SphereMask(int var1, int var2, int var3, boolean var4) {
      super(var1, var2, var3);
      this.centerX = var1 / б1и;
      this.centerY = var2 / б1щ;
      this.centerZ = var3 / бяб;
      this.radiusSqX = this.centerX * this.centerX;
      this.radiusSqY = this.centerY * this.centerY;
      this.radiusSqZ = this.centerZ * this.centerZ;
      this.filled = var4;
   }

   static {
      VMBridge.identifyClass(SphereMask.class, "9heQjAsL");
   }
}
