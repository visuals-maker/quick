// Original binary: hex/api/baritone/api/utils/Rotation
package hex.api.baritone.api.utils;

import ru.dreamix.fabricloader.VMBridge;

// $VF: Compiled from s
public class Rotation {
   private final float yaw;
   private final float pitch;
   private static double ук = 0.0;
   private static double уп = 0.0;
   private static double у2 = 0.0;
   private static float у西 = 0.0F;
   private static float ут = 0.0F;
   private static float уд = 0.0F;
   private static float уе = 0.0F;
   private static float у6 = 0.0F;
   private static float уl = 0.0F;
   private static float у必 = 0.0F;
   private static float уЗ = 0.0F;
   private static float ун = 0.0F;
   private static float уД = 0.0F;
   private static float уН = 0.0F;
   private static float уш = 0.0F;
   private static float ул = 0.0F;

   public boolean yawIsReallyClose(Rotation var1) {
      float var2 = Math.abs(normalizeYaw(this.yaw) - normalizeYaw(var1.yaw));
      return var2 < уп || var2 > у2;
   }

   public Rotation subtract(Rotation var1) {
      return new Rotation(this.yaw - var1.yaw, this.pitch - var1.pitch);
   }

   public static float yawDistanceFromOffset(float var0, float var1) {
      if (!(var0 > 0.0F ^ var1 > 0.0F) || !((var0 > уЗ || var0 < ун) ^ (var1 > уД || var1 < уН))) {
         return var0 - var1;
      } else {
         return var0 < 0.0F ? уш + (var0 - var1) : ул - (var0 - var1);
      }
   }

   public Rotation(float var1, float var2) {
      this.yaw = var1;
      this.pitch = var2;
      if (Float.isInfinite(var1) || Float.isNaN(var1) || Float.isInfinite(var2) || Float.isNaN(var2)) {
         throw new IllegalStateException(var1 + " " + var2);
      }
   }

   public float getPitch() {
      return this.pitch;
   }

   static {
      VMBridge.identifyClass(Rotation.class, "cuLgvcxL");
   }

   public static float clampPitch(float var0) {
      return Math.max(у西, Math.min(ут, var0));
   }

   @Override
   public String toString() {
      return "Yaw: " + this.yaw + ", Pitch: " + this.pitch;
   }

   public Rotation clamp() {
      return new Rotation(this.yaw, clampPitch(this.pitch));
   }

   public Rotation normalize() {
      return new Rotation(normalizeYaw(this.yaw), this.pitch);
   }

   public Rotation normalizeAndClamp() {
      return new Rotation(normalizeYaw(this.yaw), clampPitch(this.pitch));
   }

   public boolean isReallyCloseTo(Rotation var1) {
      return this.yawIsReallyClose(var1) && Math.abs(this.pitch - var1.pitch) < ук;
   }

   public Rotation add(Rotation var1) {
      return new Rotation(this.yaw + var1.yaw, this.pitch + var1.pitch);
   }

   public float getYaw() {
      return this.yaw;
   }

   public Rotation withPitch(float var1) {
      return new Rotation(this.yaw, var1);
   }

   public static float normalizeYaw(float var0) {
      float var1 = var0 % уд;
      if (var1 < уе) {
         var1 += у6;
      }

      if (var1 > уl) {
         var1 -= у必;
      }

      return var1;
   }
}
