// Original binary: sg/ec/NGAvdou3m286fZDp
package quick.core;

import java.util.List;

// $VF: Compiled from s
public interface ToggleableModule extends SettingsContainer {
   void onEnable();

   boolean isEnabled();

   default void enable() {
      this.setEnabled(true);
   }

   ModuleCategory getCategory();

   AnimationUtils getAnimation();

   default void method04701(boolean var1) {
      this.setEnabled(var1);
   }

   default void disable() {
      this.setEnabled(false);
   }

   ModuleSubCategory getSubCategory();

   List<Setting<?>> getSettingsList();

   boolean isBindable();

   String getName();

   void setEnabled(boolean var1);

   boolean isToggleable();

   void toggle();

   default boolean isEnabledCheck() {
      return this.isEnabled();
   }

   void onDisable();

   KeyBinding getBinding();

   void setBinding(KeyBinding var1);
}
