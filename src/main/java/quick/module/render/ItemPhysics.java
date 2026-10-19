// Original binary: sg/ec/LIYdegn2M0CwSS6A
package quick.module.render;


import quick.setting.NumberSetting;
import quick.setting.ToggleSetting;
import sg.ec.*;

// $VF: Compiled from s
public class ItemPhysics extends ClientModule {
   private final NumberSetting<Float> field09882;
   private final ToggleSetting field09883;
   private static final String field09884 = "Rotation speed";
   private static final float field09885 = 10.0F;
   private static final float field09886 = 0.1F;
   private static final String field09887 = "Old spin";
   private static final String field09888 = "Item Physics";
   private static final String field09889 = "Objects lie on the ground and spin when they fall.";

   public boolean method04425() {
      return this.field09883.isEnabled();
   }

   public ItemPhysics() {
      super(ModuleCategory.Render, ModuleSubCategory.World);
      this.field09882 = new NumberSetting<Float>(field09884)
         .setMin(0.0F)
         .setMax(field09885)
         .setStep(field09886)
         .setDefaultValue(1.0F)
         .setContainer(this);
      this.field09883 = new ToggleSetting(field09887).setDefaultValue(false).setContainer(this);
      this.setName(field09888);
      this.setDescription(field09889);
   }

   public float method04426() {
      return this.field09882.getValue();
   }

   public static ItemPhysics method04427() {
      return ModuleManager.INSTANCE.method05142(ItemPhysics.class);
   }

   public static boolean method04428() {
      ItemPhysics var0 = method04427();
      return var0 != null && var0.isEnabled();
   }
}
