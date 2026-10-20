// Original binary: hex/api/baritone/api/BaritoneAPI
package hex.api.baritone.api;

import hex.api.baritone.BaritoneProvider;
import hex.api.baritone.api.utils.SettingsUtil;


// $VF: Compiled from s
public final class BaritoneAPI {
   private static IBaritoneProvider provider;
   private static final Settings settings = new Settings();
   private static final String DEFAULT_SETTINGS_FILE = "settings.txt";

   public static Settings getSettings() {
      return settings;
   }

   public static void initBaritone() {
      SettingsUtil.readAndApply(settings, DEFAULT_SETTINGS_FILE);
      provider = new BaritoneProvider();
   }

   static {
      }

   public static IBaritoneProvider getProvider() {
      return provider;
   }
}
