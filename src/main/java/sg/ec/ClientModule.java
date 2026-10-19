// Original binary: sg/ec/ItXvYo9ZMFQVK3Ux
package sg.ec;

import java.util.ArrayList;
import java.util.List;

// $VF: Compiled from s
public class ClientModule implements ToggleableModule, MinecraftAccess {
   private final List<Setting<?>> settings = new ArrayList<>();
   private boolean toggleable = true;
   private boolean bindable = true;
   private String name = this.getClass().getSimpleName();
   private String description;
   private boolean enabled;
   private KeyBinding keyBinding;
   private ModuleSubCategory subCategory;
   private final ModuleCategory category;
   private AnimationUtils animation;
   private static final String field08778 = "This module has no tooltips.";
   private static final long field08779 = 150L;

   @Override
   public void toggle() {
      this.method03941(!this.enabled);
      ToggleStateEvent.field15044
         .method06765(this, this.enabled ? ToggleStateEvent.Nested198.ENABLE : ToggleStateEvent.Nested198.DISABLE, ToggleStateEvent.Nested197.MODULE);
   }

   @Override
   public void setBinding(KeyBinding var1) {
      this.keyBinding = var1;
   }

   protected boolean method03923(Object var1) {
      return var1 instanceof ClientModule;
   }

   public void method03924(ModuleSubCategory var1) {
      this.subCategory = var1;
   }

   @Override
   public String getName() {
      return this.name;
   }

   @Override
   public String toString() {
      return "Module(settings="
         + this.getSettings()
         + ", toggleble="
         + this.isToggleable()
         + ", bindable="
         + this.isBindable()
         + ", name="
         + this.getName()
         + ", desc="
         + this.getDescription()
         + ", state="
         + this.isEnabled()
         + ", binding="
         + this.getBinding()
         + ", subCategory="
         + this.getSubCategory()
         + ", category="
         + this.getCategory()
         + ", anim="
         + this.getAnimation()
         + ")";
   }

   @Override
   public List<Setting<?>> getSettings() {
      return this.settings;
   }

   public ClientModule(ModuleCategory var1, ModuleSubCategory var2) {
      this.description = field08778;
      this.animation = new AnimationUtils(AnimationUtils.Nested95.LINEAR, field08779);
      this.category = var1;
      this.subCategory = var2;
      this.keyBinding = new KeyBinding();
   }

   public String getDescription() {
      return this.description;
   }

   @Override
   public AnimationUtils getAnimation() {
      return this.animation;
   }

   @Override
   public boolean isToggleable() {
      return this.toggleable;
   }

   @Override
   public void onEnable() {
      EventBus.register(this);
      this.animation.method03375(1.0);
   }

   public void setToggleable(boolean var1) {
      this.toggleable = var1;
   }

   @Override
   public KeyBinding getBinding() {
      return this.keyBinding;
   }

   public void setDescription(String var1) {
      this.description = var1;
   }

   @Override
   public List<Setting<?>> getSettingsList() {
      return this.settings;
   }

   @Override
   public void onDisable() {
      EventBus.unregister(this);
      this.animation.method03375(0.0);
   }

   public void setBindable(boolean var1) {
      this.bindable = var1;
   }

   public void method03934(AnimationUtils var1) {
      this.animation = var1;
   }

   public void setName(String var1) {
      this.name = var1;
   }

   @Override
   public boolean isBindable() {
      return this.bindable;
   }

   @Override
   public boolean isEnabled() {
      return this.enabled;
   }

   @Override
   public ModuleCategory getCategory() {
      return this.category;
   }

   @Override
   public final void setEnabled(boolean var1) {
      if (this.enabled != var1) {
         try {
            this.method03941(var1);
         } catch (Exception var3) {
         }
      }
   }

   @Override
   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof ClientModule var2)) {
         return false;
      } else {
         if (!var2.method03923(this)) {
            return false;
         }

         if (this.isToggleable() != var2.isToggleable()) {
            return false;
         }

         if (this.isBindable() != var2.isBindable()) {
            return false;
         }

         if (this.isEnabled() != var2.isEnabled()) {
            return false;
         }

         List var3 = this.getSettings();
         List var4 = var2.getSettings();
         if (var3 == null ? var4 == null : var3.equals(var4)) {
            String var5 = this.getName();
            String var6 = var2.getName();
            if (var5 == null ? var6 == null : var5.equals(var6)) {
               String var7 = this.getDescription();
               String var8 = var2.getDescription();
               if (var7 == null ? var8 == null : var7.equals(var8)) {
                  KeyBinding var9 = this.getBinding();
                  KeyBinding var10 = var2.getBinding();
                  if (var9 == null ? var10 == null : var9.equals(var10)) {
                     ModuleSubCategory var11 = this.getSubCategory();
                     ModuleSubCategory var12 = var2.getSubCategory();
                     if (var11 == null ? var12 == null : var11.equals(var12)) {
                        ModuleCategory var13 = this.getCategory();
                        ModuleCategory var14 = var2.getCategory();
                        if (var13 == null ? var14 == null : var13.equals(var14)) {
                           AnimationUtils var15 = this.getAnimation();
                           AnimationUtils var16 = var2.getAnimation();
                           return var15 == null ? var16 == null : var15.equals(var16);
                        } else {
                           return false;
                        }
                     } else {
                        return false;
                     }
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   @Override
   public ModuleSubCategory getSubCategory() {
      return this.subCategory;
   }

   @Override
   public int hashCode() {
      byte var1 = 59;
      int var2 = 1;
      var2 = var2 * 59 + (this.isToggleable() ? 79 : 97);
      var2 = var2 * 59 + (this.isBindable() ? 79 : 97);
      var2 = var2 * 59 + (this.isEnabled() ? 79 : 97);
      List var3 = this.getSettings();
      var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
      String var4 = this.getName();
      var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
      String var5 = this.getDescription();
      var2 = var2 * 59 + (var5 == null ? 43 : var5.hashCode());
      KeyBinding var6 = this.getBinding();
      var2 = var2 * 59 + (var6 == null ? 43 : var6.hashCode());
      ModuleSubCategory var7 = this.getSubCategory();
      var2 = var2 * 59 + (var7 == null ? 43 : var7.hashCode());
      ModuleCategory var8 = this.getCategory();
      var2 = var2 * 59 + (var8 == null ? 43 : var8.hashCode());
      AnimationUtils var9 = this.getAnimation();
      return var2 * 59 + (var9 == null ? 43 : var9.hashCode());
   }

   private void method03941(boolean var1) {
      this.enabled = var1;
      if (var1) {
         this.onEnable();
      } else {
         this.onDisable();
      }
   }
}
