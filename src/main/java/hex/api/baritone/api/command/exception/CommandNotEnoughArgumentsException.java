// Original binary: hex/api/baritone/api/command/exception/CommandNotEnoughArgumentsException
package hex.api.baritone.api.command.exception;



// $VF: Compiled from s
public class CommandNotEnoughArgumentsException extends CommandErrorMessageException {
   private static final String АШь = "Not enough arguments, expected at least %d";

   static {
      }

   public CommandNotEnoughArgumentsException(int var1) {
      super(String.format(АШь, var1));
   }
}
