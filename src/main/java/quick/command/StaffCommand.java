// Original binary: sg/ec/xcvbmmXHTX5ZGOY2
package quick.command;

import quick.core.*;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;

// $VF: Compiled from s
@CommandInfo(name = "staff", description = "")
public class StaffCommand extends AbstractCommand {
   private static final String field26015 = "add";
   private static final String field26016 = "name";
   private static final String field26017 = "remove";
   private static final String field26018 = "name";
   private static final String field26019 = "clear";
   private static final String field26020 = "list";
   private static final String field26021 = "List of staff.";
   private static final String field26022 = "the staff list is empty.";
   private static final String field26023 = "The staff list has been cleared.";
   private static final String field26024 = "There is nothing to clear the staff list is empty.";
   private static final String field26025 = "name";
   private static final String field26026 = "name";

   @Override
   public void build(LiteralArgumentBuilder<ClientSuggestionProvider> var1) {
      StaffManager var2 = StaffManager.INSTANCE;
      var1.then(this.literal(field26015).then(this.argument(field26016, new PlayerNameArgument()).executes(var2x -> {
         String var3 = (String)var2x.getArgument(field26026, String.class);
         if (!var2.method02175(var3)) {
            var2.method02172(var3);
            this.sendMessage("Player with nickname: " + var3 + "Was added to staff.");
         } else {
            this.sendMessage("Player with nickname: " + var3 + "Already in staff.");
         }

         return 1;
      })));
      var1.then(this.literal(field26017).then(this.argument(field26018, new StaffNameArgument()).executes(var2x -> {
         String var3 = (String)var2x.getArgument(field26025, String.class);
         if (var2.method02175(var3)) {
            var2.method02173(var3);
            this.sendMessage("Player with nickname: " + var3 + "Has been removed from staff.");
         } else {
            this.sendMessage("Player with nickname: " + var3 + "No staff.");
         }

         return 1;
      })));
      var1.then(this.literal(field26019).executes(var2x -> {
         if (!var2.method02176().isEmpty()) {
            var2.method02174();
            this.sendMessage(field26023);
         } else {
            this.sendMessage(field26024);
         }

         return 1;
      }));
      var1.then(this.literal(field26020).executes(var2x -> {
         if (!var2.method02176().isEmpty()) {
            this.sendMessage(field26021);

            for (String var4 : var2.method02176()) {
               this.sendMessage(var4);
            }
         } else {
            this.sendMessage(field26022);
         }

         return 1;
      }));
   }
}
