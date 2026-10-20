// Original binary: sg/ec/tjqORQhchedH6gMQ
package quick.module.render;


import quick.setting.MultiToggleSetting;
import quick.setting.NumberSetting;
import quick.core.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.world.scores.DisplaySlot;

// $VF: Compiled from s
public class BetterTab extends ClientModule {
   private final MultiToggleSetting field24145;
   private final NumberSetting<Integer> field24146;
   private final NumberSetting<Integer> field24147;
   private final MultiToggleSetting field24148;
   private final Map<String, BetterTab.Nested315> field24149;
   private final Set<String> field24150;
   private String field24151;
   private boolean field24152;
   private boolean field24153;
   private boolean field24154;
   private boolean field24155;
   private boolean field24156;
   private int field24157;
   private static final String field24158 = "Mods";
   private static final String field24159 = "Tab Expansion";
   private static final String field24160 = "Highlight nicknames";
   private static final String field24161 = "Players in 1 table";
   private static final String field24162 = "Number of Tables";
   private static final String field24163 = "Whom to highlight";
   private static final String field24164 = "Self";
   private static final String field24165 = "Friends";
   private static final String field24166 = "Better Tab";
   private static final String field24167 = "Tab Expansion";
   private static final String field24168 = "Highlight nicknames";
   private static final String field24169 = "Self";
   private static final String field24170 = "Friends";
   private static final String field24171 = "Highlight nicknames";
   private static final String field24172 = "Tab Expansion";
   private static final String field24173 = "Tab Expansion";

   @Subscribe
   private void method10854(PlayerListRowColorEvent var1) {
      if (this.field24154 || false) {
         List<PlayerInfo> var2 = var1.method03806();
         if (var2 != null && !var2.isEmpty()) {
            int sx = this.method10859(var1.method03804(), var2.size());
            if (sx >= 0) {
               PlayerInfo sxx = var2.get(sx);
               if (sxx != null && sxx.getProfile() != null) {
                  String sxxx = sxx.getProfile().name();
                  if (sxxx != null && !sxxx.isEmpty()) {
                     String sxxxx = this.method10860(sxxx);
                     BetterTab.Nested315 sxxxxx = this.field24149.computeIfAbsent(sxxxx, this::method10865);
                     int sxxxxxx = 100;
                     if (this.field24154) {
                        if (sxxxxx == BetterTab.Nested315.SELF && this.field24155) {
                           var1.method03807(ColorUtils.method04597(100, 255, 255, sxxxxxx));
                           var1.setCancelled(true);
                           return;
                        }

                        if (sxxxxx == BetterTab.Nested315.FRIEND && this.field24156) {
                           var1.method03807(ColorUtils.method04597(100, 255, 100, sxxxxxx));
                           var1.setCancelled(true);
                           return;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void method10855() {
      this.field24149.clear();
      this.field24150.clear();
      this.field24151 = mc != null && mc.getGameProfile() != null ? this.method10860(mc.getGameProfile().name()) : "";

      for (String var2 : SocialStateUtils.method10612()) {
         this.field24150.add(this.method10860(var2));
      }
   }

   private boolean method10857() {
      if (mc == null || mc.options == null || !mc.options.keyPlayerList.isDown()) {
         return false;
      } else if (!mc.isLocalServer()) {
         return true;
      } else if (mc.player != null && mc.player.connection != null && mc.level != null) {
         return mc.player.connection.getListedOnlinePlayers().size() > 1 ? true : mc.level.getScoreboard().getDisplayObjective(DisplaySlot.LIST) != null;
      } else {
         return false;
      }
   }

   @Subscribe
   private void method10858(PlayerListRenderEvent var1) {
      if (var1.method04113() == PlayerListRenderEvent.Nested125.START) {
         this.field24157 = 0;
      }
   }

   private int method10859(int var1, int var2) {
      if (var2 <= 0) {
         return -1;
      }

      int var3 = var1;
      if (var3 < 0 || var3 >= var2) {
         var3 = this.field24157;
      }

      if (var3 < 0) {
         var3 = 0;
      } else if (var3 >= var2) {
         var3 %= var2;
      }

      this.field24157 = var3 + 1;
      if (this.field24157 >= var2) {
         this.field24157 = 0;
      }

      return var3;
   }

   private String method10860(String var1) {
      return var1.toLowerCase(Locale.ROOT);
   }

   @Subscribe
   private void method10862(TickEvent var1) {
      this.field24153 = this.field24145.isOptionSelected(field24167);
      this.field24154 = this.field24145.isOptionSelected(field24168);
      this.field24155 = this.field24148.isOptionSelected(field24169);
      this.field24156 = this.field24148.isOptionSelected(field24170);
      boolean var2 = this.method10857();
      if (var2 && !this.field24152) {
         this.method10855();
      }

      this.field24152 = var2;
   }

   public BetterTab() {
      super(ModuleCategory.Render, ModuleSubCategory.Interface);
      this.field24145 = new MultiToggleSetting(field24158)
         .addOption(NamedOption.createNamedOption(field24159, true))
         .addOption(NamedOption.createNamedOption(field24160, false))
         .setContainer(this);
      this.field24146 = new NumberSetting<Integer>(field24161)
         .setMin(20)
         .setMax(60)
         .setStep(5)
         .setDefaultValue(20)
         .setVisibleSupplier(() -> this.field24145.isOptionSelected(field24173))
         .setContainer(this);
      this.field24147 = new NumberSetting<Integer>(field24162)
         .setMin(4)
         .setMax(6)
         .setStep(1)
         .setDefaultValue(4)
         .setVisibleSupplier(() -> this.field24145.isOptionSelected(field24172))
         .setContainer(this);
      this.field24148 = new MultiToggleSetting(field24163)
         .addOption(NamedOption.createNamedOption(field24164, true))
         .addOption(NamedOption.createNamedOption(field24165, true))
         .setVisibleSupplier(() -> this.field24145.isOptionSelected(field24171))
         .setContainer(this);
      this.field24149 = new HashMap<>();
      this.field24150 = new HashSet<>();
      new HashSet();
      this.field24151 = "";
      this.setName(field24166);
   }

   @Subscribe
   private void method10864(PlayerListLayoutEvent var1) {
      if (this.field24153) {
         var1.method03627(this.field24146.getValue());
         var1.method03625(this.field24147.getValue());
         var1.method03624((long)this.field24146.getValue().intValue() * this.field24147.getValue().intValue());
      }
   }

   private BetterTab.Nested315 method10865(String var1) {
      if (var1 == null || var1.isEmpty()) {
         return BetterTab.Nested315.NONE;
      } else if (!this.field24151.isEmpty() && var1.equals(this.field24151)) {
         return BetterTab.Nested315.SELF;
      } else if (this.field24150.contains(var1)) {
         return BetterTab.Nested315.FRIEND;
      } else {
         return StreamerNameUtils.method03488(var1) ? BetterTab.Nested315.FRIEND : BetterTab.Nested315.NONE;
      }
   }

   // $VF: Compiled from s
   private enum Nested315 {
      NONE,
      SELF,
      FRIEND;
   }
}
