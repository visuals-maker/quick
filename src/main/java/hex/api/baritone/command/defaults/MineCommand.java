// Original binary: hex/api/baritone/command/defaults/MineCommand
package hex.api.baritone.command.defaults;

import hex.api.baritone.api.BaritoneAPI;
import hex.api.baritone.api.IBaritone;
import hex.api.baritone.api.command.Command;
import hex.api.baritone.api.command.argument.IArgConsumer;
import hex.api.baritone.api.command.datatypes.ForBlockOptionalMeta;
import hex.api.baritone.api.command.exception.CommandException;
import hex.api.baritone.api.utils.BlockOptionalMeta;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import ru.dreamix.fabricloader.VMBridge;

// $VF: Compiled from s
public class MineCommand extends Command {
   private static final String ОЭ = "mine";
   private static final String ОЪ = "Mining %s";
   private static final String ОЧ = "Mine some blocks";
   private static final String ОЩ = "The mine command allows you to tell Baritone to search for and mine individual blocks.";
   private static final String Ом = "The specified blocks can be ores, or any other block.";
   private static final String ОФ = "Also see the legitMine settings (see #set l legitMine).";
   private static final String О3 = "Usage:";
   private static final String ОЬ = "> mine diamond_ore - Mines all diamonds it can find.";

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(ОЩ, "", Ом, "", ОФ, "", О3, ОЬ);
   }

   @Override
   public String getShortDesc() {
      return ОЧ;
   }

   public MineCommand(IBaritone var1) {
      super(var1, ОЭ);
   }

   static {
      VMBridge.identifyClass(MineCommand.class, "iffg34h9");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) throws CommandException {
      int var3 = var2.getAsOrDefault(Integer.class, 0);
      var2.requireMin(1);
      List<BlockOptionalMeta> sx = new ArrayList<>();

      while (var2.hasAny()) {
         sx.add(var2.getDatatypeFor(ForBlockOptionalMeta.INSTANCE));
      }

      BaritoneAPI.getProvider().getWorldScanner().repack(this.ctx);
      this.logDirect(String.format(ОЪ, sx.toString()));
      this.baritone.getMineProcess().mine(var3, sx.toArray(new BlockOptionalMeta[0]));
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) throws CommandException {
      var2.getAsOrDefault(Integer.class, 0);

      while (var2.has(2)) {
         var2.getDatatypeFor(ForBlockOptionalMeta.INSTANCE);
      }

      return var2.tabCompleteDatatype(ForBlockOptionalMeta.INSTANCE);
   }
}
