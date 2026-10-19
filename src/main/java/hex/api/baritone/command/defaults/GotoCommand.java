// Original binary: hex/api/baritone/command/defaults/GotoCommand
package hex.api.baritone.command.defaults;

import hex.api.baritone.api.IBaritone;
import hex.api.baritone.api.command.Command;
import hex.api.baritone.api.command.argument.IArgConsumer;
import hex.api.baritone.api.command.datatypes.ForBlockOptionalMeta;
import hex.api.baritone.api.command.datatypes.RelativeCoordinate;
import hex.api.baritone.api.command.datatypes.RelativeGoal;
import hex.api.baritone.api.command.exception.CommandException;
import hex.api.baritone.api.pathing.goals.Goal;
import hex.api.baritone.api.utils.BetterBlockPos;
import hex.api.baritone.api.utils.BlockOptionalMeta;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import ru.dreamix.fabricloader.VMBridge;

// $VF: Compiled from s
public class GotoCommand extends Command {
   private static String А8й = "";
   private static String А8к = "";
   private static String А8п = "";
   private static String А82 = "";
   private static String А8西 = "";
   private static String А8т = "";
   private static String А8д = "";
   private static String А8е = "";
   private static String А86 = "";
   private static String А8l = "";

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) throws CommandException {
      var2.requireMax(1);
      return var2.tabCompleteDatatype(ForBlockOptionalMeta.INSTANCE);
   }

   protected GotoCommand(IBaritone var1) {
      super(var1, А8й);
   }

   @Override
   public void execute(String var1, IArgConsumer var2) throws CommandException {
      if (var2.peekDatatypeOrNull(RelativeCoordinate.INSTANCE) != null) {
         var2.requireMax(3);
         BetterBlockPos var5 = this.ctx.playerFeet();
         Goal sx = var2.getDatatypePost(RelativeGoal.INSTANCE, var5);
         this.logDirect(String.format(А8к, sx.toString()));
         this.baritone.getCustomGoalProcess().setGoalAndPath(sx);
      } else {
         var2.requireMax(1);
         BlockOptionalMeta var3 = var2.getDatatypeFor(ForBlockOptionalMeta.INSTANCE);
         this.baritone.getGetToBlockProcess().getToBlock(var3);
      }
   }

   @Override
   public String getShortDesc() {
      return А8п;
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(А82, "", А8西, "", А8т, А8д, А8е, А86, А8l);
   }

   static {
      VMBridge.identifyClass(GotoCommand.class, "WPbCgyGS");
   }
}
