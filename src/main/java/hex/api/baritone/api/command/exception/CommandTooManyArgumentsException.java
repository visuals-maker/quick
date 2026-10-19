// Original binary: hex/api/baritone/api/command/exception/CommandTooManyArgumentsException
package hex.api.baritone.api.command.exception;

import ru.dreamix.fabricloader.VMBridge;

// $VF: Compiled from s
public class CommandTooManyArgumentsException extends CommandErrorMessageException {
   private static final String Аl_ = "Too many arguments, expected at most %d";

   public CommandTooManyArgumentsException(int var1) {
      super(String.format(Аl_, var1));
   }

   static {
      VMBridge.identifyClass(CommandTooManyArgumentsException.class, "QDEX8TPJ");
   }
}
