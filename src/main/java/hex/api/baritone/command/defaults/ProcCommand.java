// Original binary: hex/api/baritone/command/defaults/ProcCommand
package hex.api.baritone.command.defaults;

import hex.api.baritone.api.IBaritone;
import hex.api.baritone.api.command.Command;
import hex.api.baritone.api.command.argument.IArgConsumer;
import hex.api.baritone.api.command.exception.CommandException;
import hex.api.baritone.api.command.exception.CommandInvalidStateException;
import hex.api.baritone.api.pathing.calc.IPathingControlManager;
import hex.api.baritone.api.process.IBaritoneProcess;
import hex.api.baritone.api.process.PathingCommand;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import ru.dreamix.fabricloader.VMBridge;

// $VF: Compiled from s
public class ProcCommand extends Command {
   private static String АжЭ = "";
   private static String АжЪ = "";
   private static String АжЧ = "";
   private static String АжЩ = "";
   private static String Ажм = "";
   private static String АжФ = "";
   private static String Аж3 = "";
   private static String АжЬ = "";
   private static String Аж1 = "";

   public ProcCommand(IBaritone var1) {
      super(var1, АжЭ);
   }

   @Override
   public void execute(String var1, IArgConsumer var2) throws CommandException {
      var2.requireMax(0);
      IPathingControlManager var3 = this.baritone.getPathingControlManager();
      IBaritoneProcess sx = var3.mostRecentInControl().orElse(null);
      if (sx == null) {
         throw new CommandInvalidStateException(АжЪ);
      }

      this.logDirect(
         String.format(
            АжЧ,
            sx.getClass().getTypeName(),
            sx.priority(),
            sx.isTemporary(),
            sx.displayName(),
            var3.mostRecentCommand().map(PathingCommand::toString).orElse(АжЩ)
         )
      );
   }

   static {
      VMBridge.identifyClass(ProcCommand.class, "kOae2sXV");
   }

   @Override
   public String getShortDesc() {
      return Ажм;
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(АжФ, "", Аж3, "", АжЬ, Аж1);
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }
}
