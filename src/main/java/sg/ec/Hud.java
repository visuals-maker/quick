// Original binary: sg/ec/P3SgAHV1hn5zXJQe
package sg.ec;


import quick.setting.ActionSetting;
import quick.setting.ModeSetting;
import quick.setting.MultiToggleSetting;
import quick.setting.NumberSetting;
import quick.setting.ToggleSetting;
import quick.module.player.ItemHelper;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;

// $VF: Compiled from s
public class Hud extends ClientModule {
   public final MultiToggleSetting field10873;
   public final ActionSetting field10874;
   private final ModeSetting field10875;
   private final ToggleSetting field10876;
   private final NumberSetting<Float> field10877;
   public final ToggleSetting field10878;
   public final ModeSetting field10879;
   private int field10880;
   private int field10881;
   private float field10882;
   private boolean field10883;
   public boolean field10884;
   private final HudManager field10885;
   private static final int field10886 = 8;
   private String field10887;
   private int field10888;
   private static final String field10889 = "6";
   private static final String field10890 = "Components";
   private static final String field10891 = "WaterMark";
   private static final String field10892 = "HotKeys";
   private static final String field10893 = "StaffList";
   private static final String field10894 = "Potions";
   private static final String field10895 = "TargetHUD";
   private static final String field10896 = "Notification";
   private static final String field10897 = "CoolDowns";
   private static final String field10898 = "KillFeed";
   private static final String field10899 = "ArrayList";
   private static final String field10900 = "Information";
   private static final String field10901 = "ItemHelper";
   private static final String field10902 = "Armor";
   private static final String field10903 = "MediaPlayer";
   private static final String field10904 = "Schedules";
   private static final String field10905 = "Timer";
   private static final String field10906 = "Customize Theme";
   private static final String field10907 = "Hood style";
   private static final String field10908 = "Old";
   private static final String field10909 = "New";
   private static final String field10910 = "Old";
   private static final String field10911 = "Blur";
   private static final String field10912 = "Smoothing rects";
   private static final float field10913 = 0.1F;
   private static final float field10914 = 1.5F;
   private static final String field10915 = "Notification sounds";
   private static final String field10916 = "Notification sound";
   private static final String field10917 = "1";
   private static final String field10918 = "2";
   private static final String field10919 = "3";
   private static final String field10920 = "4";
   private static final String field10921 = "5";
   private static final String field10922 = "6";
   private static final String field10923 = "1";
   private static final String field10924 = "Hud";
   private static final String field10925 = "Old";

   @Subscribe
   private void method04934(PreMovementPacketEvent var1) {
      this.method04953().method00021(var1);
   }

   @Subscribe
   private void method04935(PlayerUpdateEvent var1) {
      if (HudElement.field14601 != null) {
         this.method04953().method00024();
      }
   }

   public NumberSetting<Float> method04936() {
      return this.field10877;
   }

   @Subscribe
   private void method04937(ChatMouseButtonEvent var1) {
      float var2 = GuiScaleUtils.method06752(var1.method06510());
      float var3 = GuiScaleUtils.method06749(var1.method06505());
      int var4 = var1.method06509();
      switch (var1.method06511()) {
         case PRESS:
            this.method04953().method00025(var4, var2, var3);
            break;
         case RELEASE:
            this.method04953().method00031(var4, var2, var3);
      }
   }

   public float method04938() {
      return this.field10882;
   }

   @Subscribe
   private void method04939(HotbarRenderEvent var1) {
      this.method04953().method00020(var1);
   }

   @Subscribe
   private void method04940(TotemPopEvent var1) {
      this.method04953().method00036(var1);
   }

   @Subscribe
   private void method04941(AttackEntityEvent var1) {
      if (var1.method09431() != AttackEntityEvent.Nested264.POST) {
         this.method04953().method00026(var1);
      }
   }

   public int method04942() {
      return this.field10881;
   }

   public ActionSetting method04943() {
      return this.field10874;
   }

   @Subscribe
   private void method04944(FrameDeltaEvent var1) {
      if (HudElement.field14601 != null) {
         this.method04953().method00013(var1);
      }
   }

   @Subscribe
   private void method04945(ChatClosedEvent var1) {
      this.method04953().method00018();
   }

   public int method04946() {
      return this.field10888;
   }

   @Subscribe
   private void method04947(EntityDeathEvent var1) {
      if (!(var1.method11303() instanceof LocalPlayer)) {
         this.method04953().method00029(var1);
      }
   }

   public boolean method04948() {
      return this.field10883;
   }

   private void method04949() {
      String var1 = this.field10879.getValue();
      if (this.field10887 == null) {
         this.field10887 = var1;
      } else {
         if (!var1.equals(this.field10887)) {
            this.field10887 = var1;
            if ((mc.gui.screen() != null || ClientScreenManager.INSTANCE.method08316()) && this.field10878.isEnabled()) {
               ClientSoundManager.INSTANCE.method03827("enable" + this.method04952(), 1.0F);
               this.field10888 = 8;
            }
         }

         if (this.field10888 > 0 && --this.field10888 == 0) {
            ClientSoundManager.INSTANCE.method03827("disable" + this.method04952(), 1.0F);
         }
      }
   }

   public int method04950() {
      return this.field10880;
   }

   public ToggleSetting method04951() {
      return this.field10876;
   }

   public String method04952() {
      return this.field10879.isMode(field10889) ? "" : this.field10879.getValue();
   }

   public HudManager method04953() {
      return this.field10885;
   }

   @Subscribe(4)
   private void method04954(HudRenderEvent var1) {
      if (HudElement.field14601 == null) {
         HudElement.field14601 = Fonts.field00109;
      } else {
         ThemeSettings var2 = ThemeManager.INSTANCE.method09611();
         this.field10880 = var2.method09795().method11676();
         this.field10881 = var2.method09811().method11676();
         this.method04953().method00027(var1.method05424());
      }
   }

   @Subscribe
   private void method04955(ChatMouseScrollEvent var1) {
      double var2 = GuiScaleUtils.method06745(var1.method06518());
      double var4 = GuiScaleUtils.method06744(var1.method06521());
      double var6 = var1.method06520();
      this.method04953().method00030(var2, var4, var6);
   }

   @Subscribe
   private void method04956(TickEvent var1) {
      this.method04949();
      if (HudElement.field14601 != null) {
         this.field10884 = this.field10875.isMode(field10925);
         this.field10882 = Mth.clamp(this.field10877.getValue(), 1.0F, 2.0F);
         this.field10883 = this.field10876.getValue();
         this.method04953().method00034(var1);
      }
   }

   @Subscribe
   private void method04957(MobEffectEvent var1) {
      if (HudElement.field14601 != null) {
         this.method04953().method00012(var1);
      }
   }

   public MultiToggleSetting method04958() {
      return this.field10873;
   }

   @Subscribe
   private void method04959(ItemUseStartEvent var1) {
      this.method04953().method00033(var1);
   }

   public ModeSetting method04960() {
      return this.field10875;
   }

   public ModeSetting method04961() {
      return this.field10879;
   }

   public String method04963() {
      return this.field10887;
   }

   @Subscribe
   private void method04964(PacketEvent var1) {
      this.method04953().method00032(var1);
   }

   @Subscribe
   private void method04965(ChatKeyEvent var1) {
      int var2 = var1.method09385();
      int var3 = var1.method09380();
      int var4 = var1.method09384();
      this.method04953().method00019(var2, var3, var4);
   }

   public Hud() {
      super(ModuleCategory.Render, ModuleSubCategory.Interface);
      this.field10873 = new MultiToggleSetting(field10890)
         .addOption(NamedOption.createNamedOption(field10891, true))
         .addOption(NamedOption.createNamedOption(field10892, true))
         .addOption(NamedOption.createNamedOption(field10893, true))
         .addOption(NamedOption.createNamedOption(field10894, true))
         .addOption(NamedOption.createNamedOption(field10895, true))
         .addOption(NamedOption.createNamedOption(field10896, true))
         .addOption(NamedOption.createNamedOption(field10897, false))
         .addOption(NamedOption.createNamedOption(field10898, false))
         .addOption(NamedOption.createNamedOption(field10899, false))
         .addOption(NamedOption.createNamedOption(field10900, true))
         .addOption(NamedOption.createNamedOption(field10901, false))
         .addOption(NamedOption.createNamedOption(field10902, false))
         .addOption(NamedOption.createNamedOption(field10903, false))
         .addOption(NamedOption.createNamedOption(field10904, false))
         .addOption(NamedOption.createNamedOption(field10905, false))
         .setContainer(this);
      this.field10874 = new ActionSetting(field10906).setDefaultValue(() -> {
         ClickGuiScreen var0 = QuickClient.getInstance().getMenuScreen();
         if (var0 != null) {
            var0.method09979();
         }
      }).setContainer(this);
      this.field10875 = new ModeSetting(field10907).setModes(field10908, field10909).setDefaultValue(field10910).setContainer(this);
      this.field10876 = new ToggleSetting(field10911).setDefaultValue(false).setContainer(this);
      this.field10877 = new NumberSetting<Float>(field10912)
         .setMin(1.0F)
         .setMax(2.0F)
         .setStep(field10913)
         .setDefaultValue(field10914)
         .setContainer(this);
      this.field10878 = new ToggleSetting(field10915).setDefaultValue(false).setContainer(this);
      this.field10879 = new ModeSetting(field10916)
         .setModes(field10917, field10918, field10919, field10920, field10921, field10922)
         .setDefaultValue(field10923)
         .setVisibleSupplier(this.field10878::getValue)
         .setContainer(this);
      this.field10880 = -1;
      this.field10881 = -1;
      this.field10882 = 0.0F;
      this.field10888 = -1;
      this.field10885 = new HudManager();
      this.setName(field10924);
   }

   @Subscribe
   private void method04966(SplashPotionEffectsEvent var1) {
      this.method04953().method00015(var1);
   }
}
