// Original binary: sg/ec/j07AwEzRvaSz5Ujh
package quick.module.render;


import quick.setting.MultiToggleSetting;
import quick.setting.NumberSetting;
import quick.setting.ToggleSetting;
import sg.ec.*;

import java.util.List;
import net.minecraft.client.renderer.state.MapRenderState.MapDecorationRenderState;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

// $VF: Compiled from s
public class Removals extends ClientModule {
   private final MultiToggleSetting field20145;
   private final ToggleSetting field20146;
   private final MultiToggleSetting field20147;
   private final NumberSetting<Float> field20148;
   private static final int field20149 = 16;
   private static int field20150 = 0;
   private boolean field20151;
   private boolean field20152;
   private boolean field20153;
   private boolean field20154;
   private boolean field20155;
   private boolean field20156;
   private boolean field20157;
   private boolean field20158;
   private boolean field20159;
   private boolean field20160;
   private boolean field20161;
   private static final String field20162 = "Types";
   private static final String field20163 = "Fire on the screen";
   private static final String field20164 = "Shaking the screen";
   private static final String field20165 = "Constraint from f5";
   private static final String field20166 = "Subject Titles";
   private static final String field20167 = "Skorbord";
   private static final String field20168 = "Overlay Message";
   private static final String field20169 = "Totem Pop";
   private static final String field20170 = "List of effects";
   private static final String field20171 = "Bad effects";
   private static final String field20172 = "Blackout in water";
   private static final String field20173 = "Lava Blackout";
   private static final String field20174 = "Weather";
   private static final String field20175 = "Lag Machine";
   private static final String field20176 = "Remove sounds";
   private static final String field20177 = "Sounds";
   private static final String field20178 = "Water flow";
   private static final String field20179 = "Water dripping";
   private static final String field20180 = "Summoning the viser";
   private static final String field20181 = "Dragon Summoning";
   private static final String field20182 = "Launching fireworks";
   private static final String field20183 = "Bottle of experience";
   private static final String field20184 = "Muting power";
   private static final float field20185 = 0.1F;
   private static final float field20186 = 0.5F;
   private static final String field20187 = "Removals";
   private static final String field20188 = "Totem Pop";
   private static final String field20189 = "Lag Machine";
   private static final String field20190 = "Bad effects";
   private static final String field20191 = "Shaking the screen";
   private static final String field20192 = "Fire on the screen";
   private static final String field20193 = "Constraint from f5";
   private static final String field20194 = "Skorbord";
   private static final String field20195 = "Subject Titles";
   private static final String field20196 = "Overlay Message";
   private static final String field20197 = "List of effects";
   private static final String field20198 = "Blackout in water";
   private static final String field20199 = "Lava Blackout";
   private static final String field20200 = "Weather";
   private static final String field20201 = "Water flow";
   private static final String field20202 = "Water dripping";
   private static final String field20203 = "Dragon Summoning";
   private static final String field20204 = "Summoning the viser";
   private static final String field20205 = "Launching fireworks";
   private static final String field20206 = "Bottle of experience";

   @Subscribe
   private void method08862(VisualRemovalEvent var1) {
      boolean var2 = switch (var1.method08694()) {
         case FIRE -> this.field20152;
         case NOHURTCAM -> this.field20153;
         case F5_XUETA -> this.field20154;
         case SCOREBOARD -> this.field20155;
         case TOTEM -> this.field20145.isOptionSelected(field20188);
         case ITEM_TITLES -> this.field20156;
         case OVERLAY_MESSAGE -> this.field20157;
         case MINECRAFT_EFFECTS -> this.field20158;
         case WATER_FOG -> this.field20159;
         case LAVA_FOG -> this.field20160;
         case NIGHT_VISION_FOG -> false;
         case LAG_MACHINE -> this.method08870(var1.method08699());
      };
      if (var2) {
         var1.setCancelled(true);
      }
   }

   @Subscribe
   private void method08863(NauseaStrengthEvent var1) {
      if (this.field20151) {
         var1.setCancelled(true);
      }
   }

   @Subscribe
   private void method08864(MobEffectVisibilityEvent var1) {
      if (this.field20151) {
         MobEffect var2 = var1.method00609();
         if (var2 != null) {
            if (var2.equals(MobEffects.NAUSEA.value()) || var2.equals(MobEffects.DARKNESS.value())) {
               var1.setCancelled(true);
            }
         }
      }
   }

   @Subscribe
   private void method08865(WorldSoundEvent var1) {
      if (this.field20146.isEnabled()) {
         var1.method08881(this.field20148.getValue());

         boolean var2 = switch (var1.method08872()) {
            case WATER -> this.field20147.isOptionSelected(field20201);
            case WATER_DRIP -> this.field20147.isOptionSelected(field20202);
            case DRAGON_SUMMON -> this.field20147.isOptionSelected(field20203);
            case WITHER_RELEASED -> this.field20147.isOptionSelected(field20204);
            case FIREWORK -> this.field20147.isOptionSelected(field20205);
            case EXPERIENCE_BOTTLE -> this.field20147.isOptionSelected(field20206);
         };
         if (var2) {
            var1.cancel();
         }
      }
   }

   @Subscribe
   private void method08867(TickEvent var1) {
      this.field20151 = this.field20145.isOptionSelected(field20190);
      this.field20153 = this.field20145.isOptionSelected(field20191);
      this.field20152 = this.field20145.isOptionSelected(field20192);
      this.field20154 = this.field20145.isOptionSelected(field20193);
      this.field20155 = this.field20145.isOptionSelected(field20194);
      this.field20156 = this.field20145.isOptionSelected(field20195);
      this.field20157 = this.field20145.isOptionSelected(field20196);
      this.field20158 = this.field20145.isOptionSelected(field20197);
      this.field20159 = this.field20145.isOptionSelected(field20198);
      this.field20160 = this.field20145.isOptionSelected(field20199);
      this.field20161 = this.field20145.isOptionSelected(field20200);
   }

   @Subscribe
   private void method08868(WeatherStateEvent var1) {
      if (this.field20161) {
         var1.setCancelled(true);
      }
   }

   public Removals() {
      super(ModuleCategory.Render, ModuleSubCategory.Other);
      this.field20145 = new MultiToggleSetting(field20162)
         .addOption(NamedOption.createNamedOption(field20163, true))
         .addOption(NamedOption.createNamedOption(field20164, true))
         .addOption(NamedOption.createNamedOption(field20165, true))
         .addOption(NamedOption.createNamedOption(field20166, false))
         .addOption(NamedOption.createNamedOption(field20167, false))
         .addOption(NamedOption.createNamedOption(field20168, false))
         .addOption(NamedOption.createNamedOption(field20169, false))
         .addOption(NamedOption.createNamedOption(field20170, false))
         .addOption(NamedOption.createNamedOption(field20171, true))
         .addOption(NamedOption.createNamedOption(field20172, false))
         .addOption(NamedOption.createNamedOption(field20173, false))
         .addOption(NamedOption.createNamedOption(field20174, false))
         .addOption(NamedOption.createNamedOption(field20175, true))
         .setContainer(this);
      this.field20146 = new ToggleSetting(field20176).setValue(false).setContainer(this);
      this.field20147 = new MultiToggleSetting(field20177)
         .addOption(NamedOption.createNamedOption(field20178, false))
         .addOption(NamedOption.createNamedOption(field20179, false))
         .addOption(NamedOption.createNamedOption(field20180, false))
         .addOption(NamedOption.createNamedOption(field20181, true))
         .addOption(NamedOption.createNamedOption(field20182, false))
         .addOption(NamedOption.createNamedOption(field20183, false))
         .setVisibleSupplier(() -> this.field20146.isEnabled())
         .setContainer(this);
      this.field20148 = new NumberSetting<Float>(field20184)
         .setMin(0.0F)
         .setMax(1.0F)
         .setStep(field20185)
         .setDefaultValue(field20186)
         .setVisibleSupplier(() -> this.field20146.isEnabled())
         .setContainer(this);
      this.field20151 = false;
      this.field20152 = false;
      this.field20153 = false;
      this.field20154 = false;
      this.field20155 = false;
      this.field20156 = false;
      this.field20157 = false;
      this.field20158 = false;
      this.field20159 = false;
      this.field20160 = false;
      this.field20161 = false;
      this.setName(field20187);
   }

   private boolean method08870(List<?> var1) {
      if (this.field20145.isOptionSelected(field20189) && var1 != null) {
         if (var1.size() > 16) {
            var1.subList(16, var1.size()).clear();
         }

         for (Object var3 : var1) {
            MapDecorationRenderState var4 = (MapDecorationRenderState)var3;
            if (var4.name != null && var4.name.getString(33).length() > 32) {
               var4.name = null;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   @Subscribe
   private void method08871(NightVisionEvent var1) {
      if (this.field20151) {
         var1.setCancelled(true);
      }
   }
}
