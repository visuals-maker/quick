// Original binary: sg/ec/BH66FnKUIO1B0gFO
package quick.command;

import sg.ec.*;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import hex.script.api.Setting;
import java.io.Serializable;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;

// $VF: Compiled from s
@CommandInfo(name = "script", description = "Client scripts")
public class ScriptCommand extends AbstractCommand {
   private static final String field06236 = "list";
   private static final String field06237 = "reload";
   private static final String field06238 = "toggle";
   private static final String field06239 = "name";
   private static final String field06240 = "settings";
   private static final String field06241 = "name";
   private static final String field06242 = "set";
   private static final String field06243 = "name";
   private static final String field06244 = "setting";
   private static final String field06245 = "value";
   private static final String field06246 = "  ";
   private static final String field06247 = "name";
   private static final String field06248 = "setting";
   private static final String field06249 = "value";
   private static final String field06250 = "name";
   private static final String field06251 = "name";

   private String method02715(Setting<?> var1) {
      String var2 = var1.parent() == null ? "" : field06246;
      String var3 = var1.write();
      String var4 = var3.isEmpty() ? ChatFormatting.DARK_GRAY + "(button)" : ChatFormatting.RESET + var3;
      return var2 + ChatFormatting.GRAY + var1.name() + ": " + var4;
   }

   private LoadedScript method02716(String var1) {
      LoadedScript var2 = ScriptRuntime.INSTANCE.method06182(var1);
      if (var2 == null) {
         this.sendMessage(ChatFormatting.RED + "Script " + var1 + " not found");
      }

      return var2;
   }

   private String method02717(LoadedScript var1) {
      if (var1.method03391()) {
         return ChatFormatting.RED + "[error]" + ChatFormatting.RESET;
      } else {
         return var1.method03392() ? ChatFormatting.GREEN + "[on]" + ChatFormatting.RESET : ChatFormatting.GRAY + "[off]" + ChatFormatting.RESET;
      }
   }

   private String method02718(LoadedScript var1) {
      String var2 = ScriptRuntime.INSTANCE.method06178(var1) ? ChatFormatting.GOLD + "[needs restart]" + ChatFormatting.RESET : "";
      return this.method02717(var1) + var2;
   }

   @Override
   public void build(LiteralArgumentBuilder<ClientSuggestionProvider> var1) {
      var1.then(this.literal(field06236).executes(var1x -> {
         List var2 = ScriptRuntime.INSTANCE.method06229();
         if (var2.isEmpty()) {
            this.sendMessage(ChatFormatting.RED + "No scripts");
            return 1;
         }

         for (LoadedScript sx : (Iterable<LoadedScript>)var2) {
            String sxx = sx.method03396() ? ChatFormatting.DARK_GRAY + "(built-in)" : "";
            this.sendMessage(this.method02718(sx) + sx.method03400() + sxx);
         }

         return 1;
      }));
      var1.then(this.literal(field06237).executes(var1x -> {
         int var2 = ScriptRuntime.INSTANCE.method06177();
         this.sendMessage(ChatFormatting.GRAY + "Scripts loaded:" + ChatFormatting.RESET + var2);
         return 1;
      }));
      var1.then(this.literal(field06238).then(this.argument(field06239, StringArgumentType.word()).executes(var1x -> {
         String var2 = (String)var1x.getArgument(field06251, String.class);
         LoadedScript var3 = ScriptRuntime.INSTANCE.method06182(var2);
         if (var3 == null) {
            this.sendMessage(ChatFormatting.RED + "Script " + var2 + " not found");
            return 1;
         } else {
            ScriptRuntime.INSTANCE.method06183(var3);
            this.sendMessage(this.method02718(var3) + var3.method03400());
            return 1;
         }
      })));
      var1.then(this.literal(field06240).then(this.argument(field06241, StringArgumentType.word()).executes(var1x -> {
         LoadedScript var2 = this.method02716((String)var1x.getArgument(field06250, String.class));
         if (var2 == null) {
            return 1;
         }

         List var3 = ScriptRuntime.INSTANCE.method06224(var2);
         if (var3.isEmpty()) {
            this.sendMessage(ChatFormatting.GRAY + "У " + var2.method03400() + "no settings");
            return 1;
         }

         for (Setting var5 : (Iterable<Setting>)var3) {
            this.sendMessage(this.method02715(var5));
         }

         return 1;
      })));
      var1.then(
         this.literal(field06242)
            .then(
               this.argument(field06243, StringArgumentType.word())
                  .then(
                     this.argument(field06244, StringArgumentType.word())
                        .then(
                           this.argument(field06245, StringArgumentType.greedyString())
                              .executes(
                                 var1x -> {
                                    LoadedScript var2 = this.method02716((String)var1x.getArgument(field06247, String.class));
                                    if (var2 == null) {
                                       return 1;
                                    } else {
                                       String var3 = (String)var1x.getArgument(field06248, String.class);
                                       String var4 = (String)var1x.getArgument(field06249, String.class);
                                       if (!ScriptRuntime.INSTANCE.method06225(var2, var3, var4)) {
                                          this.sendMessage(ChatFormatting.RED + "Settings" + var3 + "not found or value does not match");
                                          return 1;
                                       } else {
                                          Setting var5 = ScriptRuntime.INSTANCE
                                             .method06224(var2)
                                             .stream()
                                             .filter(var1xx -> var1xx.id().equalsIgnoreCase(var3) || var1xx.name().equalsIgnoreCase(var3))
                                             .findFirst()
                                             .orElse(null);
                                          this.sendMessage(var5 == null ? ChatFormatting.GREEN + "done" : this.method02715(var5));
                                          return 1;
                                       }
                                    }
                                 }
                              )
                        )
                  )
            )
      );
   }
}
