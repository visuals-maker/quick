// Original binary: sg/ec/X3SQUo4YkilELrBk
package quick.command;

import quick.core.*;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import org.joml.Vector2f;

// $VF: Compiled from s
@CommandInfo(name = "gps", description = "")
public class GpsCommand extends AbstractCommand {
   private static final String field13874 = "add";
   private static final String field13875 = "remove";
   private static final String field13876 = "x";
   private static final String field13877 = "z";
   private static final String field13878 = "x";
   private static final String field13879 = "z";
   private static final String field13880 = "The navigator is turned off.";

   @Override
   public void build(LiteralArgumentBuilder<ClientSuggestionProvider> var1) {
      var1.then(this.method06145());
      var1.then(this.literal(field13874).then(this.method06145()));
      var1.then(this.literal(field13875).executes(var1x -> {
         this.sendMessage(field13880);
         GpsManager.method07827();
         return 1;
      }));
   }

   private RequiredArgumentBuilder<ClientSuggestionProvider, Integer> method06145() {
      return (RequiredArgumentBuilder<ClientSuggestionProvider, Integer>)this.argument(field13876, IntegerArgumentType.integer())
         .then(this.argument(field13877, IntegerArgumentType.integer()).executes(this::method06146));
   }

   private int method06146(CommandContext<ClientSuggestionProvider> var1) {
      int var2 = (Integer)var1.getArgument(field13878, Integer.class);
      int var3 = (Integer)var1.getArgument(field13879, Integer.class);
      this.sendMessage("The navigator is on, your coordinates:" + var2 + ", " + var3);
      GpsManager.method07828(new Vector2f(var2, var3));
      return 1;
   }
}
