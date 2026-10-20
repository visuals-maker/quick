// Original binary: sg/ec/aNQd4Ira6LMiWlU0
package quick.command;

import quick.core.*;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;

// $VF: Compiled from s
@CommandInfo(name = "way", description = "")
public class WayCommand extends AbstractCommand {
   private static final String field15590 = "add";
   private static final String field15591 = "name";
   private static final String field15592 = "x";
   private static final String field15593 = "y";
   private static final String field15594 = "z";
   private static final String field15595 = "remove";
   private static final String field15596 = "name";
   private static final String field15597 = "list";
   private static final String field15598 = "clear";
   private static final String field15599 = "[\\s_\\-]+";
   private static final String field15600 = "The list of tags has been cleared.";
   private static final String field15601 = "The list of tags is already empty.";
   private static final String field15602 = "The list of tags is empty.";
   private static final String field15603 = "List of tags:";
   private static final String field15604 = "name";
   private static final String field15605 = "x";
   private static final String field15606 = "y";
   private static final String field15607 = "z";
   private static final String field15608 = "name";
   private static final String field15609 = "The label name cannot be empty.";
   private static final String field15610 = "A tag with a similar name already exists.";

   @Override
   public void build(LiteralArgumentBuilder<ClientSuggestionProvider> var1) {
      var1.then(
         this.literal(field15590)
            .then(
               this.argument(field15591, StringArgumentType.string())
                  .then(
                     this.argument(field15592, IntegerArgumentType.integer())
                        .then(
                           this.argument(field15593, IntegerArgumentType.integer())
                              .then(this.argument(field15594, IntegerArgumentType.integer()).executes(var1x -> {
                                 int var2 = IntegerArgumentType.getInteger(var1x, field15605);
                                 int var3 = IntegerArgumentType.getInteger(var1x, field15606);
                                 int var4 = IntegerArgumentType.getInteger(var1x, field15607);
                                 String var5 = StringArgumentType.getString(var1x, field15608);
                                 if (var5 == null || var5.isBlank()) {
                                    this.sendMessage(field15609);
                                    return 0;
                                 } else if (this.method06921(var5)) {
                                    this.sendMessage(field15610);
                                    return 0;
                                 } else {
                                    WaypointManager.INSTANCE.method09832(var2, var3, var4, var5);
                                    this.sendMessage("Label with name: " + var5 + "has been added.");
                                    return 1;
                                 }
                              }))
                        )
                  )
            )
      );
      var1.then(this.literal(field15595).then(this.argument(field15596, new WaypointNameArgument()).executes(var1x -> {
         String var2 = (String)var1x.getArgument(field15604, String.class);
         int var3 = WaypointManager.INSTANCE.method09834(var2);
         if (var3 > 0) {
            this.sendMessage("Label with name: " + var2 + "was deleted.");
         } else {
            this.sendMessage("Label with name: " + var2 + "not found.");
         }

         return 1;
      })));
      var1.then(
         this.literal(field15597)
            .executes(
               var1x -> {
                  List var2 = WaypointManager.INSTANCE.method09837();
                  if (var2.isEmpty()) {
                     this.sendMessage(field15602);
                     return 1;
                  }

                  this.sendMessage(field15603);

                  for (Waypoint var4 : (Iterable<Waypoint>)var2) {
                     this.sendMessage(
                        "Title: "
                           + var4.method11262()
                           + ", x: "
                           + (int)var4.method11264()
                           + ", y: "
                           + (int)var4.method11260()
                           + ", z: "
                           + (int)var4.method11269()
                     );
                  }

                  return 1;
               }
            )
      );
      var1.then(this.literal(field15598).executes(var1x -> {
         int var2 = WaypointManager.INSTANCE.method09835();
         if (var2 > 0) {
            this.sendMessage(field15600);
         } else {
            this.sendMessage(field15601);
         }

         return 1;
      }));
   }

   private boolean method06921(String var1) {
      String var2 = this.method06925(var1);
      if (var2.isBlank()) {
         return true;
      }

      for (String var4 : WaypointManager.INSTANCE.method09839()) {
         if (this.method06925(var4).equals(var2)) {
            return true;
         }
      }

      return false;
   }

   private String method06925(String var1) {
      return var1 == null ? "" : var1.toLowerCase(Locale.ROOT).replaceAll(field15599, "");
   }
}
