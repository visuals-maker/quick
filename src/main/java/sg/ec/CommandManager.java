// Original binary: sg/ec/MpAh2kthrnJeF99K
package sg.ec;


import quick.command.AbstractCommand;
import quick.command.BindCommand;
import quick.command.BlockEspCommand;
import quick.command.BotCommand;
import quick.command.ConfigCommand;
import quick.command.FriendCommand;
import quick.command.GpsCommand;
import quick.command.HClipCommand;
import quick.command.IrcCommand;
import quick.command.MacroCommand;
import quick.command.NotraceCommand;
import quick.command.NukerCommand;
import quick.command.PartyCommand;
import quick.command.ScriptCommand;
import quick.command.StaffCommand;
import quick.command.VClipCommand;
import quick.command.WayCommand;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;

// $VF: Compiled from s
public enum CommandManager implements CommandRegistry {
   INSTANCE;

   public final List<AbstractCommand> field10215 = new ArrayList<>();
   public CommandDispatcher<ClientSuggestionProvider> field10216 = new CommandDispatcher();
   private final Map<Class<? extends AbstractCommand>, AbstractCommand> field10217 = new HashMap<>();

   public void method04643() {
      this.method04651(
         new FriendCommand(),
         new GpsCommand(),
         new VClipCommand(),
         new MacroCommand(),
         new PartyCommand(),
         new IrcCommand(),
         new BlockEspCommand(),
         new StaffCommand(),
         new NukerCommand(),
         new NotraceCommand(),
         new ConfigCommand(),
         new WayCommand(),
         new HClipCommand(),
         new BindCommand(),
         new BotCommand(),
         new ScriptCommand()
      );
      this.method04644();
      EventBus.register(this);
   }

   public void method04644() {
      CommandDispatcher var1 = new CommandDispatcher();
      this.field10215.forEach(var1x -> {
         if (var1x.getInfo() != null) {
            LiteralArgumentBuilder var2 = var1x.literal(var1x.getInfo().name());
            var1x.build(var2);
            var1.register(var2);
         }
      });
      this.field10216 = var1;
      CommandLineParser.method05586().forEach(var1::register);
   }

   @Subscribe
   private void method04645(ChatSendEvent var1) {
      if (!ClientFeaturePolicyEvent.field14592.method06426(ClientFeaturePolicyEvent.Nested186.CHAT_COMMAND)) {
         String var2 = var1.method11711();
         if (var2.startsWith(".")) {
            var2 = var2.substring(1);
            if (var2.isEmpty()) {
               return;
            }

            var1.setCancelled(true);
            this.method04646(var2);
         }
      }
   }

   public void method04646(String var1) {
      if (var1 != null && !var1.isEmpty()) {
         try {
            this.field10216.execute(var1, null);
         } catch (CommandSyntaxException var3) {
            ChatTextUtils.sendQuickMessage(var3.getRawMessage().getString());
         } catch (Throwable var4) {
            ChatTextUtils.sendQuickMessage(this.method04647(var4));
         }
      }
   }

   private String method04647(Throwable var1) {
      StringBuilder var2 = new StringBuilder(var1.toString());
      StackTraceElement[] var3 = var1.getStackTrace();

      for (int var4 = 0; var4 < Math.min(3, var3.length); var4++) {
         var2.append(" | ").append(var3[var4]);
      }

      return var2.toString();
   }

   @Subscribe(1)
   private void method04648(HudRenderEvent var1) {
      if (GpsManager.method07830() != null) {
         GpsManager.method07826(var1.method05424());
      }
   }

   @Subscribe
   private void method04649(PlayerUpdateEvent var1) {
      if (GpsManager.method07830() != null) {
         GpsManager.method07829();
      }
   }

   @Override
   public <T extends AbstractCommand> T method04650(Class<T> var1) {
      AbstractCommand var2 = this.field10217.get(var1);
      return (T)(var2 == null ? null : var1.cast(var2));
   }

   @Override
   public void method04651(AbstractCommand... var1) {
      for (AbstractCommand var5 : var1) {
         this.field10215.add(var5);
         this.field10217.put((Class<? extends AbstractCommand>)var5.getClass(), var5);
      }
   }

   public void method04652(AbstractCommand var1) {
      this.field10215.add(var1);
      this.field10217.put((Class<? extends AbstractCommand>)var1.getClass(), var1);
   }

   public List<AbstractCommand> method04653() {
      return this.field10215;
   }

   public CommandDispatcher<ClientSuggestionProvider> method04654() {
      return this.field10216;
   }

   public Map<Class<? extends AbstractCommand>, AbstractCommand> method04655() {
      return this.field10217;
   }
}
