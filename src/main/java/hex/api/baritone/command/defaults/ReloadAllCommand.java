// Original binary: hex/api/baritone/command/defaults/ReloadAllCommand
package hex.api.baritone.command.defaults;

import hex.api.baritone.api.IBaritone;
import hex.api.baritone.api.command.Command;
import hex.api.baritone.api.command.argument.IArgConsumer;
import hex.api.baritone.api.command.exception.CommandException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import ru.dreamix.fabricloader.VMBridge;

// $VF: Compiled from s
public class ReloadAllCommand extends Command {
   private static String А诶Х = "";
   private static String А诶Ш = "";
   private static String А诶а = "";
   private static String А诶7 = "";
   private static String А诶У = "";
   private static String А诶0 = "";

   @Override
   public void execute(String var1, IArgConsumer var2) throws CommandException {
      var2.requireMax(0);
      this.ctx.worldData().getCachedWorld().reloadAllFromDisk();
      this.logDirect(А诶Ш);
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   public ReloadAllCommand(IBaritone var1) {
      super(var1, А诶Х);
   }

   static {
      VMBridge.identifyClass(ReloadAllCommand.class, "3NtySulB");
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(А诶7, "", А诶У, А诶0);
   }

   @Override
   public String getShortDesc() {
      return А诶а;
   }
}
