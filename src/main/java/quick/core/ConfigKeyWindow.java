// Original binary: sg/ec/uvnvWTu899VDTXr3
package quick.core;

import java.util.List;

// $VF: Compiled from s
public class ConfigKeyWindow extends ConfigActionWindow {
   public static final String field24608 = "configs.key";
   public static float field24609 = 0.0F;
   public static float field24610 = 0.0F;
   private static int field24611 = 0;
   private static String field24612 = "";
   private static String field24613 = "";
   private static String field24614 = "";
   private static String field24615 = "";
   private static String field24616 = "";
   private final String field24617;
   private final TextInputWidget field24618;
   private final List<SettingsListWindow.Nested200> field24619;
   private boolean field24620 = true;
   private static final String field24621 = "configs.key";
   private static final float field24622 = 140.0F;
   private static final float field24623 = 84.0F;
   private static final String field24624 = "all";
   private static final String field24625 = "To whom";
   private static final String field24626 = "No postage";
   private static final String field24627 = "Create Key";

   private void method10975() {
      String var1 = this.field24618.method06672();
      GlobalRequestSender.method09378(var2 -> var2.method01825(this.field24617, var1, this.field24620));
      this.method09550();
   }

   public ConfigKeyWindow(String var1) {
      super("Key ·" + var1, field24621, field24622, field24623);
      this.field24617 = var1;
      this.field24618 = this.method06849(field24624, 24, var1x -> this.method10975());
      this.field24618.method06716();
      this.field24619 = List.of(
         this.method06858(field24625, this.field24618),
         this.method06850(field24626, () -> this.field24620, var1x -> this.field24620 = var1x),
         this.method06830(() -> field24627, () -> GuiThemeStyle.Nested40.NORMAL, this::method10975)
      );
   }

   @Override
   protected List<SettingsListWindow.Nested200> method04173() {
      return this.field24619;
   }
}
