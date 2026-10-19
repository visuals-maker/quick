// Original binary: hex/dscRPC/exception/JDiscordIPCBuilderException
package hex.dscRPC.exception;

import ru.dreamix.fabricloader.VMBridge;

// $VF: Compiled from s
public class JDiscordIPCBuilderException extends RuntimeException {
   private JDiscordIPCBuilderException(String var1, Throwable var2) {
      super(var1, var2);
   }

   // $VF: Compiled from s
   public static class MissingSystemSocketFactoryException extends JDiscordIPCBuilderException {
      private static String Акр = "";

      public MissingSystemSocketFactoryException() {
         super(Акр, null);
      }

      static {
         VMBridge.identifyClass(JDiscordIPCBuilderException.MissingSystemSocketFactoryException.class, "dZa4ym19");
      }
   }
}
