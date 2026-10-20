// Original binary: sg/ec/teuZLX22W809mSnd
package quick.command;

import quick.core.*;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.ArrayList;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

// $VF: Compiled from s
@CommandInfo(name = "nuker", description = "")
public class NukerCommand extends AbstractCommand {
   public final ArrayList<Block> field24123 = new ArrayList<>();
   private static final String field24124 = "add";
   private static final String field24125 = "name";
   private static final String field24126 = "remove";
   private static final String field24127 = "name";
   private static final String field24128 = "list";
   private static final String field24129 = "clear";
   private static final String field24130 = "The block list has been cleared.";
   private static final String field24131 = "The block list is empty.";
   private static final String field24132 = "List of blocks:";
   private static final String field24133 = "block.minecraft.";
   private static final String field24134 = "name";
   private static final String field24135 = "block.minecraft.";
   private static final String field24136 = "block.minecraft.";
   private static final String field24137 = "name";
   private static final String field24138 = "block.minecraft.";
   private static final String field24139 = "block.minecraft.";

   @Override
   public void build(LiteralArgumentBuilder<ClientSuggestionProvider> var1) {
      var1.then(this.literal(field24124).then(this.argument(field24125, new BlockStateArgument()).executes(var1x -> {
         BlockState var2 = (BlockState)var1x.getArgument(field24137, BlockState.class);
         Block var3 = var2.getBlock();
         if (this.field24123.contains(var3)) {
            this.sendMessage("Block with the name: " + var3.getDescriptionId().replace(field24138, "") + "already added.");
            return 1;
         } else {
            this.field24123.add(var3);
            this.sendMessage("Block with the name: " + var3.getDescriptionId().replace(field24139, "") + "has been added.");
            return 1;
         }
      })));
      var1.then(this.literal(field24126).then(this.argument(field24127, new NukerBlockArgument()).executes(var1x -> {
         Block var2 = (Block)var1x.getArgument(field24134, Block.class);
         if (!this.field24123.contains(var2)) {
            this.sendMessage("Block with the name: " + var2.getDescriptionId().replace(field24135, "") + "not found in the list.");
            return 1;
         } else {
            this.field24123.remove(var2);
            this.sendMessage("Block with the name: " + var2.getDescriptionId().replace(field24136, "") + "has been deleted.");
            return 1;
         }
      })));
      var1.then(this.literal(field24128).executes(var1x -> {
         if (this.field24123.isEmpty()) {
            this.sendMessage(field24131);
            return 1;
         }

         this.sendMessage(field24132);

         for (Block var3 : this.field24123) {
            this.sendMessage(var3.getDescriptionId().replace(field24133, ""));
         }

         return 1;
      }));
      var1.then(this.literal(field24129).executes(var1x -> {
         this.field24123.clear();
         this.sendMessage(field24130);
         return 1;
      }));
   }
}
