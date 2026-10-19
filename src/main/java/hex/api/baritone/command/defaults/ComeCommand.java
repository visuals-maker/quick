// Original binary: hex/api/baritone/command/defaults/ComeCommand
package hex.api.baritone.command.defaults;

import hex.api.baritone.api.IBaritone;
import hex.api.baritone.api.command.Command;
import hex.api.baritone.api.command.argument.IArgConsumer;
import hex.api.baritone.api.command.exception.CommandException;
import hex.api.baritone.api.pathing.goals.GoalBlock;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import ru.dreamix.fabricloader.VMBridge;

// $VF: Compiled from s
public class ComeCommand extends Command {
   private static String бА7 = "";
   private static String бАУ = "";
   private static String бА0 = "";
   private static String бАГ = "";
   private static String бАф = "";
   private static String бАЕ = "";
   private static String бАч = "";

   @Override
   public String getShortDesc() {
      return бА0;
   }

   static {
      VMBridge.identifyClass(ComeCommand.class, "hqLZQXsq");
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   public ComeCommand(IBaritone var1) {
      super(var1, бА7);
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(бАГ, "", бАф, "", бАЕ, бАч);
   }

   @Override
   public void execute(String var1, IArgConsumer var2) throws CommandException {
      var2.requireMax(0);
      this.baritone.getCustomGoalProcess().setGoalAndPath(new GoalBlock(this.ctx.viewerPos()));
      this.logDirect(бАУ);
   }
}
