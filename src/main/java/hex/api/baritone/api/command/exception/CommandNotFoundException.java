// Original binary: hex/api/baritone/api/command/exception/CommandNotFoundException
package hex.api.baritone.api.command.exception;

import hex.api.baritone.api.command.ICommand;
import hex.api.baritone.api.command.argument.ICommandArgument;
import hex.api.baritone.api.utils.Helper;
import java.util.List;
import ru.dreamix.fabricloader.VMBridge;

// $VF: Compiled from s
public class CommandNotFoundException extends CommandException {
   public final String command;
   private static String бУН = "";

   static {
      VMBridge.identifyClass(CommandNotFoundException.class, "Z883zNxY");
   }

   public CommandNotFoundException(String var1) {
      super(String.format(бУН, var1));
      this.command = var1;
   }

   @Override
   public void handle(ICommand var1, List<ICommandArgument> var2) {
      Helper.HELPER.logDirect(this.getMessage());
   }
}
