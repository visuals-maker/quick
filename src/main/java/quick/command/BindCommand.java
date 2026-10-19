// Original binary: sg/ec/1IQ2EtKgp84mIdXC
package quick.command;

import sg.ec.*;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.List;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.ClickEvent.SuggestCommand;
import net.minecraft.network.chat.HoverEvent.ShowText;

// $VF: Compiled from s
@CommandInfo(name = "bind", description = "")
public class BindCommand extends AbstractCommand {
   private static final String field00542 = "add";
   private static final String field00543 = "name";
   private static final String field00544 = "key";
   private static final String field00545 = "remove";
   private static final String field00546 = "name";
   private static final String field00547 = "list";
   private static final String field00548 = "clear";
   private static final String field00549 = "The list of binds has been cleared.";
   private static final String field00550 = "List of binds:";
   private static final String field00551 = " ";
   private static final String field00552 = "[x]";
   private static final String field00553 = " ";
   private static final String field00554 = "The list of binds is empty:";
   private static final String field00555 = "name";
   private static final String field00556 = "name";
   private static final String field00557 = "key";

   @Override
   public void build(LiteralArgumentBuilder<ClientSuggestionProvider> var1) {
      List var2 = ModuleManager.INSTANCE.method05138().stream().toList();
      var1.then(
         this.literal(field00542)
            .then(
               this.argument(field00543, new ModuleArgument())
                  .then(
                     this.argument(field00544, new KeyNameArgument())
                        .executes(
                           var1x -> {
                              ToggleableModule var2x = (ToggleableModule)var1x.getArgument(field00556, ToggleableModule.class);
                              String var3 = (String)var1x.getArgument(field00557, String.class);
                              Integer var4 = null;

                              try {
                                 var4 = KeyboardKey.getKeyCode(var3);
                              } catch (Exception var6) {
                              }

                              if (var4 != null) {
                                 var2x.getBinding().setKeyCode(var4);
                              }

                              this.sendMessage(
                                 "Button: "
                                    + KeyboardKey.getKeyName(var2x.getBinding().getKeyCode())
                                    + " was tied to the module: "
                                    + var2x.getName()
                                    + "."
                              );
                              return 1;
                           }
                        )
                  )
            )
      );
      var1.then(
         this.literal(field00545)
            .then(
               this.argument(field00546, new BindableModuleArgument())
                  .executes(
                     var1x -> {
                        ToggleableModule var2x = (ToggleableModule)var1x.getArgument(field00555, ToggleableModule.class);
                        this.sendMessage(
                           "Button: "
                              + KeyNames.method06800(var2x.getBinding().getKeyCode())
                              + " has been decoupled from the module: "
                              + var2x.getName()
                              + "."
                        );
                        var2x.getBinding().setKeyCode(-1);
                        return 1;
                     }
                  )
            )
      );
      var1.then(
         this.literal(field00547)
            .executes(
               var2x -> {
                  if (!var2.isEmpty()) {
                     this.sendMessage(field00550);

                     for (ToggleableModule var4 : (Iterable<ToggleableModule>)var2) {
                        if (var4.getBinding().getKeyCode() != -1) {
                           MutableComponent var5 = Component.literal(
                              "Module: " + var4.getName() + " Button: [" + KeyNames.method06800(var4.getBinding().getKeyCode()) + "]"
                           );
                           MutableComponent var6 = Component.literal(field00551)
                              .append(field00552)
                              .setStyle(
                                 Style.EMPTY
                                    .withColor(ColorUtils.method04604(255, 100, 100))
                                    .withHoverEvent(new ShowText(Component.literal("Click to delete the bind [" + var4.getName() + "]")))
                                    .withClickEvent(new SuggestCommand(".bind remove " + var4.getName().replace(field00553, "")))
                              );
                           var5.append(var6);
                           ChatTextUtils.method05098(var5);
                        }
                     }

                     return 1;
                  } else {
                     this.sendMessage(field00554);
                     return 0;
                  }
               }
            )
      );
      var1.then(this.literal(field00548).executes(var2x -> {
         for (ToggleableModule var4 : (Iterable<ToggleableModule>)var2) {
            if (var4.getBinding().getKeyCode() == -1) {
               var4.getBinding().setKeyCode(-1);
            }
         }

         this.sendMessage(field00549);
         return 1;
      }));
   }
}
