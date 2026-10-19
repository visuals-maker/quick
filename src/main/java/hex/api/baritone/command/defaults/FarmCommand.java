// Original binary: hex/api/baritone/command/defaults/FarmCommand
package hex.api.baritone.command.defaults;

import hex.api.baritone.api.IBaritone;
import hex.api.baritone.api.cache.IWaypoint;
import hex.api.baritone.api.command.Command;
import hex.api.baritone.api.command.argument.IArgConsumer;
import hex.api.baritone.api.command.datatypes.ForWaypoints;
import hex.api.baritone.api.command.exception.CommandException;
import hex.api.baritone.api.command.exception.CommandInvalidStateException;
import hex.api.baritone.api.utils.BetterBlockPos;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import ru.dreamix.fabricloader.VMBridge;

// $VF: Compiled from s
public class FarmCommand extends Command {
   private static String Иф = "";
   private static String ИЕ = "";
   private static String Ич = "";
   private static String И衣 = "";
   private static String Иц = "";
   private static String ИМ = "";
   private static String И5 = "";
   private static String ИЖ = "";
   private static String Ио = "";
   private static String ИК = "";

   @Override
   public String getShortDesc() {
      return Иц;
   }

   @Override
   public void execute(String var1, IArgConsumer var2) throws CommandException {
      var2.requireMax(2);
      int var3 = 0;
      BetterBlockPos sx = null;
      if (var2.has(1)) {
         var3 = var2.getAs(Integer.class);
      }

      if (var2.has(1)) {
         IWaypoint[] sxx = var2.getDatatypeFor(ForWaypoints.INSTANCE);
         IWaypoint sxxx = null;
         switch (sxx.length) {
            case 0:
               throw new CommandInvalidStateException(ИЕ);
            case 1:
               sxxx = sxx[0];
               sx = sxxx.getLocation();
               break;
            default:
               throw new CommandInvalidStateException(Ич);
         }
      }

      this.baritone.getFarmProcess().farm(var3, sx);
      this.logDirect(И衣);
   }

   static {
      VMBridge.identifyClass(FarmCommand.class, "CFkb8TUb");
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   public FarmCommand(IBaritone var1) {
      super(var1, Иф);
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(ИМ, "", И5, ИЖ, Ио, ИК);
   }
}
