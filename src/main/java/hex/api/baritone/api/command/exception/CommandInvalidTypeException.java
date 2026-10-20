// Original binary: hex/api/baritone/api/command/exception/CommandInvalidTypeException
package hex.api.baritone.api.command.exception;

import hex.api.baritone.api.command.argument.ICommandArgument;


// $VF: Compiled from s
public class CommandInvalidTypeException extends CommandInvalidArgumentException {
   private static final String field00001 = "The argument must be of type %s";
   private static final String field00002 = "The argument must be of type %s";
   private static final String field00003 = "The argument must be of type %s, not %s";
   private static final String field00004 = "The argument must be of type %s, not %s";

   public CommandInvalidTypeException(ICommandArgument var1, String var2) {
      super(var1, String.format(field00001, var2));
   }

   public CommandInvalidTypeException(ICommandArgument var1, String var2, Throwable var3) {
      super(var1, String.format(field00002, var2), var3);
   }

   public CommandInvalidTypeException(ICommandArgument var1, String var2, String var3, Throwable var4) {
      super(var1, String.format(field00004, var2, var3), var4);
   }

   public CommandInvalidTypeException(ICommandArgument var1, String var2, String var3) {
      super(var1, String.format(field00003, var2, var3));
   }

   static {
      }
}
