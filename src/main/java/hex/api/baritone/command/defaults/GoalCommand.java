// Original binary: hex/api/baritone/command/defaults/GoalCommand
package hex.api.baritone.command.defaults;

import hex.api.baritone.api.IBaritone;
import hex.api.baritone.api.command.Command;
import hex.api.baritone.api.command.argument.IArgConsumer;
import hex.api.baritone.api.command.datatypes.RelativeCoordinate;
import hex.api.baritone.api.command.datatypes.RelativeGoal;
import hex.api.baritone.api.command.exception.CommandException;
import hex.api.baritone.api.command.helpers.TabCompleteHelper;
import hex.api.baritone.api.pathing.goals.Goal;
import hex.api.baritone.api.process.ICustomGoalProcess;
import hex.api.baritone.api.utils.BetterBlockPos;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import ru.dreamix.fabricloader.VMBridge;

// $VF: Compiled from s
public class GoalCommand extends Command {
   private static String Акй = "";
   private static String Акк = "";
   private static String Акп = "";
   private static String Ак2 = "";
   private static String Ак西 = "";
   private static String Акт = "";
   private static String Акд = "";
   private static String Аке = "";
   private static String Ак6 = "";
   private static String Акl = "";
   private static String Ак必 = "";
   private static String АкЗ = "";
   private static String Акн = "";
   private static String АкД = "";
   private static String АкН = "";
   private static String Акш = "";
   private static String Акл = "";
   private static String Аки = "";
   private static String Акщ = "";
   private static String Апб = "";
   private static String АпА = "";

   @Override
   public void execute(String var1, IArgConsumer var2) throws CommandException {
      ICustomGoalProcess var3 = this.baritone.getCustomGoalProcess();
      if (var2.hasAny() && Arrays.asList(Акк, Акп, Ак2).contains(var2.peekString())) {
         var2.requireMax(1);
         if (var3.getGoal() != null) {
            var3.setGoal(null);
            this.logDirect(Ак西);
         } else {
            this.logDirect(Акт);
         }
      } else {
         var2.requireMax(3);
         BetterBlockPos sx = this.ctx.playerFeet();
         Goal sxx = var2.getDatatypePost(RelativeGoal.INSTANCE, sx);
         var3.setGoal(sxx);
         this.logDirect(String.format(Акд, sxx.toString()));
      }
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(АкД, "", АкН, "", Акш, Акл, Аки, Акщ, Апб, АпА);
   }

   static {
      VMBridge.identifyClass(GoalCommand.class, "C9U6w6Ri");
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) throws CommandException {
      TabCompleteHelper var3 = new TabCompleteHelper();
      if (var2.hasExactlyOne()) {
         var3.append(Аке, Ак6, Акl, Ак必);
      } else if (var2.hasAtMost(3)) {
         while (var2.has(2) && var2.peekDatatypeOrNull(RelativeCoordinate.INSTANCE) != null) {
            var2.get();
            if (!var2.has(2)) {
               var3.append(АкЗ);
            }
         }
      }

      return var3.filterPrefix(var2.getString()).stream();
   }

   @Override
   public String getShortDesc() {
      return Акн;
   }

   public GoalCommand(IBaritone var1) {
      super(var1, Акй);
   }
}
