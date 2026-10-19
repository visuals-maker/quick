// Original binary: hex/api/baritone/command/defaults/AxisCommand
package hex.api.baritone.command.defaults;

import hex.api.baritone.api.IBaritone;
import hex.api.baritone.api.command.Command;
import hex.api.baritone.api.command.argument.IArgConsumer;
import hex.api.baritone.api.command.exception.CommandException;
import hex.api.baritone.api.pathing.goals.Goal;
import hex.api.baritone.api.pathing.goals.GoalAxis;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import ru.dreamix.fabricloader.VMBridge;

// $VF: Compiled from s
public class AxisCommand extends Command {
   private static String бьщ = "";
   private static String бЯб = "";
   private static String бЯА = "";
   private static String бЯ诶 = "";
   private static String бЯЫ = "";
   private static String бЯз = "";
   private static String бЯР = "";

   public AxisCommand(IBaritone var1) {
      super(var1, бьщ, бЯб);
   }

   @Override
   public void execute(String var1, IArgConsumer var2) throws CommandException {
      var2.requireMax(0);
      Goal var3 = new GoalAxis();
      this.baritone.getCustomGoalProcess().setGoal(var3);
      this.logDirect(String.format(бЯА, var3.toString()));
   }

   @Override
   public String getShortDesc() {
      return бЯ诶;
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   static {
      VMBridge.identifyClass(AxisCommand.class, "9TbabCq5");
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(бЯЫ, "", бЯз, бЯР);
   }
}
