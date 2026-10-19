// Original binary: sg/ec/dn46hqRSw8KpMNZA
package quick.command;


import quick.module.player.NoTrace;
import sg.ec.*;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.ArrayList;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

// $VF: Compiled from s
@CommandInfo(name = "notrace", description = "")
public class NotraceCommand extends AbstractCommand {
   public final ArrayList<Block> field18407 = new ArrayList<>();
   private static final String field18408 = "add";
   private static final String field18409 = "name";
   private static final String field18410 = "remove";
   private static final String field18411 = "name";
   private static final String field18412 = "list";
   private static final String field18413 = "clear";
   private static final String field18414 = "The list has been cleared.";
   private static final String field18415 = "NoTrace";
   private static final String field18416 = "The Block list is empty.";
   private static final String field18417 = "List of Blocks:";
   private static final String field18418 = "block.minecraft.";
   private static final String field18419 = "name";
   private static final String field18420 = "block.minecraft.";
   private static final String field18421 = "block.minecraft.";
   private static final String field18422 = "NoTrace";
   private static final String field18423 = "name";
   private static final String field18424 = "block.minecraft.";
   private static final String field18425 = "block.minecraft.";
   private static final String field18426 = "NoTrace";

   @Override
   public void build(LiteralArgumentBuilder<ClientSuggestionProvider> var1) {
      var1.then(this.literal(field18408).then(this.argument(field18409, new BlockStateArgument()).executes(var1x -> {
         BlockState var2 = (BlockState)var1x.getArgument(field18423, BlockState.class);
         Block sx = var2.getBlock();
         if (this.field18407.contains(sx)) {
            this.sendMessage("Block with the name: " + sx.getDescriptionId().replace(field18424, "") + "is already on the list.");
            return 1;
         } else {
            this.field18407.add(sx);
            this.sendMessage("Block with the name: " + sx.getDescriptionId().replace(field18425, "") + "has been added to the list.");
            CustomResourceManager.INSTANCE.method02465(field18426);
            return 1;
         }
      })));
      var1.then(this.literal(field18410).then(this.argument(field18411, new NoTraceBlockArgument()).executes(var1x -> {
         Block var2 = (Block)var1x.getArgument(field18419, Block.class);
         if (!this.field18407.contains(var2)) {
            this.sendMessage("Block with the name: " + var2.getDescriptionId().replace(field18420, "") + "not listed.");
            return 1;
         } else {
            this.field18407.remove(var2);
            this.sendMessage("Block with the name: " + var2.getDescriptionId().replace(field18421, "") + "has been removed from the list.");
            CustomResourceManager.INSTANCE.method02465(field18422);
            return 1;
         }
      })));
      var1.then(this.literal(field18412).executes(var1x -> {
         if (this.field18407.isEmpty()) {
            this.sendMessage(field18416);
            return 1;
         }

         this.sendMessage(field18417);

         for (Block var3 : this.field18407) {
            this.sendMessage(var3.getDescriptionId().replace(field18418, ""));
         }

         return 1;
      }));
      var1.then(this.literal(field18413).executes(var1x -> {
         this.field18407.clear();
         this.sendMessage(field18414);
         CustomResourceManager.INSTANCE.method02465(field18415);
         return 1;
      }));
   }
}
