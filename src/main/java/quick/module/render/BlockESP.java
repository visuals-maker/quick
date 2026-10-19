// Original binary: sg/ec/FQfShysoyBcBY6n9
package quick.module.render;


import quick.setting.NumberSetting;
import quick.setting.ToggleSetting;
import sg.ec.*;

import java.util.List;
import net.minecraft.util.Mth;

// $VF: Compiled from s
public class BlockESP extends ClientModule {
   private static final int field07591 = 5;
   private final NumberSetting<Integer> field07592;
   public final ToggleSetting field07593;
   private final BlockEspConfigManager field07594;
   private static final String field07595 = "Radius";
   private static final String field07596 = "Show lava";
   private static final String field07597 = "Block ESP";

   public static int method03401() {
      BlockESP var0 = ModuleManager.INSTANCE.method08249();
      return var0 == null ? 9 : Mth.clamp(var0.field07592.getValue(), 0, 5) * 3;
   }

   @Subscribe
   private void method03402(PlayerUpdateEvent var1) {
      if (this.isEnabled()) {
         BlockEspWorldIndex.method09027();
      }
   }

   public List<BlockEspConfigCodec> method03403() {
      return this.field07594.method03036();
   }

   @Subscribe
   private void method03404(Render3DEvent var1) {
      if (this.isEnabled()) {
         BlockEspWorldRenderer.method03878(var1);
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      BlockEspWorldIndex.method09026();
   }

   public void method03405(List<BlockEspConfigCodec> var1) {
      this.field07594.method03035(var1);
   }

   public static int method03406() {
      return Math.max(0, method03401() / 2);
   }

   public BlockESP() {
      super(ModuleCategory.Render, ModuleSubCategory.World);
      this.field07592 = new NumberSetting<Integer>(field07595).setMin(1).setMax(5).setStep(1).setDefaultValue(3).setContainer(this);
      this.field07593 = new ToggleSetting(field07596).setValue(false).setContainer(this);
      this.field07594 = BlockEspConfigManager.method03030();
      this.setName(field07597);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      BlockEspWorldIndex.method09026();
   }

   public BlockEspTargets method03407() {
      return this.field07594.method03032();
   }
}
