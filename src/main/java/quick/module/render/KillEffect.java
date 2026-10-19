// Original binary: sg/ec/Z7XpcqF1oaehMcA4
package quick.module.render;


import quick.setting.KeyBindSetting;
import quick.setting.NumberSetting;
import quick.setting.ToggleSetting;
import sg.ec.*;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

// $VF: Compiled from s
public class KillEffect extends ClientModule {
   public static final int field15335 = 30;
   private final ToggleSetting field15336;
   private final NumberSetting<Float> field15337;
   private final KeyBindSetting field15338;
   private static final String field15339 = "Sound effect";
   private static final String field15340 = "Volume";
   private static final float field15341 = 0.5F;
   private static final float field15342 = 0.1F;
   private static final String field15343 = "Preview of the effect";
   private static final String field15344 = "Kill Effect";
   private static final String field15345 = "killsound";

   @Subscribe
   private void method06805(PlayerUpdateEvent var1) {
      if (mc.level == null) {
         CapturedPlayerManager.method03886();
      } else {
         CapturedPlayerManager.method03887().forEach(CapturedPlayer::tickAge);
      }
   }

   @Subscribe
   private void method06806(MouseButtonEvent var1) {
      if (var1.method06919() != InputAction.RELEASE) {
         if (this.field15338.getValue().getKeyCode() == var1.method06914()) {
            this.method06809();
         }
      }
   }

   @Subscribe
   private void method06807(Render3DEvent var1) {
   }

   @Subscribe
   private void method06808(KeyInputEvent var1) {
      if (var1.method05884() != InputAction.RELEASE) {
         if (this.field15338.getValue().getKeyCode() == var1.method05887()) {
            this.method06809();
         }
      }
   }

   private void method06809() {
      if (mc.player != null && mc.level != null) {
         this.method06812(mc.player);
         CapturedPlayer var1 = CapturedPlayerManager.method03888(mc.player);
         ClientParticleManager.INSTANCE.method08837(new PlayerDissolveEmitter(var1, PlayerModelVertices.method02510(var1, 350)));
      }
   }

   public ToggleSetting method06810() {
      return this.field15336;
   }

   public KillEffect() {
      super(ModuleCategory.Render, ModuleSubCategory.World);
      this.field15336 = new ToggleSetting(field15339).setDefaultValue(true).setContainer(this);
      this.field15337 = new NumberSetting<Float>(field15340)
         .setMin(field15341)
         .setMax(1.0F)
         .setStep(field15342)
         .setDefaultValue(1.0F)
         .setVisibleSupplier(this.field15336::getValue)
         .setContainer(this);
      this.field15338 = new KeyBindSetting(field15343).setDefaultValue(new KeyBinding()).setContainer(this);
      this.setName(field15344);
   }

   @Subscribe
   private void method06811(EntityDeathEvent var1) {
      Entity var2 = var1.method11303();
      if (!(var2 instanceof LocalPlayer)) {
         if (mc.level != null) {
            if (var2 instanceof AbstractClientPlayer var3) {
               this.method06812(var3);
               CapturedPlayer var4 = CapturedPlayerManager.method03888(var3);
               ClientParticleManager.INSTANCE.method08837(new PlayerDissolveEmitter(var4, PlayerModelVertices.method02510(var4, 350)));
            }
         }
      }
   }

   private void method06812(Entity var1) {
      if (this.field15336.isEnabled()) {
         ClientSoundManager.INSTANCE.method03828(field15345, this.field15337.getValue(), var1.getX(), var1.getY(), var1.getZ());
      }
   }

   public NumberSetting<Float> method06813() {
      return this.field15337;
   }

   public KeyBindSetting method06814() {
      return this.field15338;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      CapturedPlayerManager.method03886();
   }
}
