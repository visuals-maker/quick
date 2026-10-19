// Original binary: sg/ec/5AnwGLAbrlDG2b9A
package quick.command;


import quick.setting.BooleanSetting;
import quick.setting.ColorSetting;
import quick.setting.ItemFilterSetting;
import quick.setting.KeyBindSetting;
import quick.setting.ModeSetting;
import quick.setting.MultiToggleSetting;
import quick.setting.NumberSetting;
import quick.setting.ToggleSetting;
import quick.module.render.ClickGUI;
import sg.ec.*;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.commands.SharedSuggestionProvider;

// $VF: Compiled from s
@CommandInfo(name = "cfg", description = "")
public class ConfigCommand extends AbstractCommand {
   private static final String field02090 = "load";
   private static final String field02091 = "name";
   private static final String field02092 = "loadKey";
   private static final String field02093 = "key";
   private static final String field02094 = "key";
   private static final String field02095 = "name";
   private static final String field02096 = "nick";
   private static final String field02097 = "save";
   private static final String field02098 = "name";
   private static final String field02099 = "reset";
   private static final String field02100 = "remove";
   private static final String field02101 = "name";
   private static final String field02102 = "list";
   private static final String field02103 = "Loading config by key...";
   private static final String field02104 = "Globals is not connected.";
   private static final String field02105 = "There are no cloud configs.";
   private static final String field02106 = "name";
   private static final String field02107 = "Globals is not connected.";
   private static final String field02108 = "name";
   private static final String field02109 = "name";
   private static final String field02110 = "nick";
   private static final String field02111 = "name";
   private static final String field02112 = "key";
   private static final String field02113 = "name";

   @Override
   public void build(LiteralArgumentBuilder<ClientSuggestionProvider> var1) {
      var1.then(
         this.literal(field02090)
            .then(
               this.argument(field02091, StringArgumentType.string())
                  .suggests((var1x, var2) -> SharedSuggestionProvider.suggest(this.method00922(), var2))
                  .executes(var1x -> this.method00931(StringArgumentType.getString(var1x, field02113)))
            )
      );
      var1.then(
         this.literal(field02092)
            .then(
               this.argument(field02093, StringArgumentType.string()).executes(var1x -> this.method00930(StringArgumentType.getString(var1x, field02112)))
            )
      );
      var1.then(
         this.literal(field02094)
            .then(
               ((RequiredArgumentBuilder)this.argument(field02095, StringArgumentType.string())
                     .suggests((var1x, var2) -> SharedSuggestionProvider.suggest(this.method00922(), var2))
                     .executes(var1x -> this.method00928(StringArgumentType.getString(var1x, field02111), "")))
                  .then(
                     this.argument(field02096, StringArgumentType.greedyString())
                        .executes(
                           var1x -> this.method00928(StringArgumentType.getString(var1x, field02109), StringArgumentType.getString(var1x, field02110).trim())
                        )
                  )
            )
      );
      var1.then(this.literal(field02097).then(this.argument(field02098, StringArgumentType.string()).executes(var1x -> {
         String var2 = (String)var1x.getArgument(field02108, String.class);
         if (CustomResourceManager.INSTANCE.method02465(var2)) {
            this.sendMessage("Config with the name:" + var2 + "Was saved.");
         }

         GlobalWebSocketClient var3 = QuickClient.getInstance().getClient();
         if (var3 != null && var3.isOpen()) {
            byte[] var4 = ModuleConfigStorage.method04447();
            if (var4.length > 0) {
               var3.getManager().method01779(var2, var4);
               StoredContentCache.method05219(StorageCategory.CONFIG, var2, var4);
            }
         }

         return 1;
      })));
      var1.then(this.literal(field02099).executes(var1x -> {
         this.method00924();
         return 1;
      }));
      var1.then(
         this.literal(field02100)
            .then(
               this.argument(field02101, StringArgumentType.string())
                  .suggests((var1x, var2) -> SharedSuggestionProvider.suggest(this.method00922(), var2))
                  .executes(var1x -> {
                     String var2 = StringArgumentType.getString(var1x, field02106);
                     boolean sx = ConfigFileUtils.method07558(var2);
                     GlobalWebSocketClient sxx = QuickClient.getInstance().getClient();
                     if (sxx != null && sxx.isOpen()) {
                        sxx.getManager().method01782(StorageCategory.CONFIG, var2);
                        this.sendMessage("Config \"" + var2 + "\' deleted.");
                     } else if (sx) {
                        this.sendMessage("Config \"" + var2 + "\' deleted locally (Globals not connected).");
                     } else {
                        this.sendMessage(field02107);
                     }

                     return 1;
                  })
            )
      );
      var1.then(this.literal(field02102).executes(var1x -> {
         GlobalWebSocketClient var2 = this.method00923();
         if (var2 == null) {
            return 0;
         }

         var2.getManager().method01844();
         List var3 = var2.getManager().method01793();
         if (var3.isEmpty()) {
            this.sendMessage(field02105);
            return 1;
         }

         this.sendMessage("Cloud configs (" + var3.size() + "):");

         for (StoredContentInfo var5 : (Iterable<StoredContentInfo>)var3) {
            this.sendMessage("- " + var5.name());
         }

         return 1;
      }));
   }

   private List<String> method00922() {
      ArrayList var1 = new ArrayList();
      GlobalWebSocketClient var2 = QuickClient.getInstance().getClient();
      if (var2 == null) {
         return var1;
      }

      for (StoredContentInfo var4 : var2.getManager().method01793()) {
         var1.add(var4.name());
      }

      return var1;
   }

   private GlobalWebSocketClient method00923() {
      GlobalWebSocketClient var1 = QuickClient.getInstance().getClient();
      if (var1 != null && var1.isOpen()) {
         return var1;
      }

      this.sendMessage(field02104);
      return null;
   }

   private void method00924() {
      for (ToggleableModule var2 : ModuleManager.INSTANCE.method05138()) {
         if (!(var2 instanceof ClickGUI)) {
            var2.setBinding(new KeyBinding());
            var2.setEnabled(false);

            for (Setting var4 : var2.getSettings()) {
               if (var4 instanceof NumberSetting var5) {
                  var5.setValueFromFloat(((Number)var5.getDefaultValue()).floatValue());
               } else if (var4 instanceof ToggleSetting var6) {
                  var6.setValue(var6.getDefaultValue());
               } else if (var4 instanceof KeyBindSetting var7) {
                  var7.setValue(var7.getDefaultValue());
               } else if (var4 instanceof BooleanSetting var8) {
                  var8.setValue(var8.getDefaultValue());
               } else if (var4 instanceof ColorSetting var9) {
                  var9.method11672(var9.getDefaultValue());
               } else if (var4 instanceof MultiToggleSetting var10) {
                  for (NamedOption var14 : var10.getValue()) {
                     var14.reset();
                  }
               } else if (var4 instanceof ModeSetting var11) {
                  var11.setMode(var11.getDefaultValue());
               } else if (var4 instanceof ItemFilterSetting var12) {
                  var12.reset();
               }
            }
         }
      }
   }

   private int method00928(String var1, String var2) {
      GlobalWebSocketClient var3 = this.method00923();
      if (var3 == null) {
         return 0;
      }

      var3.getManager().method01810(var1, var2);
      this.sendMessage("Create key for \"" + var1 + "\"" + (var2.isBlank() ? "" : "For" + var2) + "...");
      return 1;
   }

   private int method00930(String var1) {
      GlobalWebSocketClient var2 = this.method00923();
      if (var2 == null) {
         return 0;
      }

      var2.getManager().method01853(var1);
      this.sendMessage(field02103);
      return 1;
   }

   private int method00931(String var1) {
      GlobalWebSocketClient var2 = this.method00923();
      if (var2 == null) {
         return 0;
      }

      var2.getManager().method01817(StorageCategory.CONFIG, var1);
      this.sendMessage("Loading config \"" + var1 + "\'from the server...");
      return 1;
   }
}
