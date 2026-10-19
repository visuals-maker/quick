// Original binary: hex/api/baritone/command/defaults/WaypointsCommand
package hex.api.baritone.command.defaults;

import hex.api.baritone.Baritone;
import hex.api.baritone.api.IBaritone;
import hex.api.baritone.api.cache.IWaypoint;
import hex.api.baritone.api.cache.IWorldData;
import hex.api.baritone.api.cache.Waypoint;
import hex.api.baritone.api.command.Command;
import hex.api.baritone.api.command.IBaritoneChatControl;
import hex.api.baritone.api.command.argument.IArgConsumer;
import hex.api.baritone.api.command.datatypes.ForWaypoints;
import hex.api.baritone.api.command.datatypes.RelativeBlockPos;
import hex.api.baritone.api.command.exception.CommandException;
import hex.api.baritone.api.command.exception.CommandInvalidStateException;
import hex.api.baritone.api.command.exception.CommandInvalidTypeException;
import hex.api.baritone.api.command.helpers.Paginator;
import hex.api.baritone.api.command.helpers.TabCompleteHelper;
import hex.api.baritone.api.pathing.goals.Goal;
import hex.api.baritone.api.pathing.goals.GoalBlock;
import hex.api.baritone.api.utils.BetterBlockPos;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.ClickEvent.RunCommand;
import net.minecraft.network.chat.ClickEvent.SuggestCommand;
import net.minecraft.network.chat.HoverEvent.ShowText;
import ru.dreamix.fabricloader.VMBridge;

// $VF: Compiled from s
public class WaypointsCommand extends Command {
   private Map<IWorldData, List<IWaypoint>> deletedWaypoints = new HashMap<>();
   private static String бяД = "";
   private static String бяН = "";
   private static String бяш = "";
   private static String бял = "";
   private static String бяи = "";
   private static String бящ = "";
   private static String бIб = "";
   private static String бIА = "";
   private static String бI诶 = "";
   private static String бIЫ = "";
   private static String бIз = "";
   private static String бIР = "";
   private static String бIх = "";
   private static String бIЦ = "";
   private static String бI9 = "";
   private static String бIЮ = "";
   private static String бIO = "";
   private static String бI弟 = "";
   private static String бIВ = "";
   private static String бIж = "";
   private static String бI8 = "";
   private static String бIТ = "";
   private static String бIЙ = "";
   private static String бIэ = "";
   private static String бIО = "";
   private static String бIИ = "";
   private static String бIП = "";
   private static String бI_ = "";
   private static String бIс = "";
   private static String бIЛ = "";
   private static String бIь = "";
   private static String бIЯ = "";
   private static String бIХ = "";
   private static String бIШ = "";
   private static String бIа = "";
   private static String бI7 = "";
   private static String бIУ = "";
   private static String бI0 = "";
   private static String бIГ = "";
   private static String бIф = "";
   private static String бIЕ = "";
   private static String бIч = "";
   private static String бI衣 = "";
   private static String бIц = "";
   private static String бIМ = "";
   private static String бI5 = "";
   private static String бIЖ = "";
   private static String бIо = "";
   private static String бIК = "";
   private static String бIЭ = "";
   private static String бIЪ = "";
   private static String бIЧ = "";

   @Override
   public void execute(String var1, IArgConsumer var2) throws CommandException {
      WaypointsCommand.Action var3 = var2.hasAny() ? WaypointsCommand.Action.getByName(var2.getString()) : WaypointsCommand.Action.LIST;
      if (var3 == null) {
         throw new CommandInvalidTypeException(var2.consumed(), бял);
      }

      BiFunction<IWaypoint, WaypointsCommand.Action, MutableComponent> sx = (var1x, var2x) -> {
         MutableComponent sxx = Component.literal("");
         MutableComponent sxa = Component.literal(var1x.getTag().name() + " ");
         sxa.setStyle(sxa.getStyle().withColor(ChatFormatting.GRAY));
         String sxb = var1x.getName();
         MutableComponent sxxx = Component.literal(!sxb.isEmpty() ? sxb : бIЭ);
         sxxx.setStyle(sxxx.getStyle().withColor(!sxb.isEmpty() ? ChatFormatting.GRAY : ChatFormatting.DARK_GRAY));
         MutableComponent sxxxx = Component.literal(" @ " + new Date(var1x.getCreationTimestamp()));
         sxxxx.setStyle(sxxxx.getStyle().withColor(ChatFormatting.DARK_GRAY));
         sxx.append(sxa);
         sxx.append(sxxx);
         sxx.append(sxxxx);
         sxx.setStyle(
            sxx.getStyle()
               .withHoverEvent(new ShowText(Component.literal(бIЪ)))
               .withClickEvent(
                  new RunCommand(
                     String.format(бIЧ, IBaritoneChatControl.FORCE_COMMAND_PREFIX, var1, var2x.names[0], var1x.getTag().getName(), var1x.getCreationTimestamp())
                  )
               )
         );
         return sxx;
      };
      Function<IWaypoint, Component> sxx = var2x -> sx.apply(var2x, var3 == WaypointsCommand.Action.LIST ? WaypointsCommand.Action.INFO : var3);
      if (var3 == WaypointsCommand.Action.LIST) {
         IWaypoint.Tag sxxx = var2.hasAny() ? IWaypoint.Tag.getByName(var2.peekString()) : null;
         if (sxxx != null) {
            var2.get();
         }

         IWaypoint[] sxxxx = sxxx != null ? ForWaypoints.getWaypointsByTag(this.baritone, sxxx) : ForWaypoints.getWaypoints(this.baritone);
         if (sxxxx.length <= 0) {
            var2.requireMax(0);
            throw new CommandInvalidStateException(sxxx != null ? бящ : бIб);
         }

         var2.requireMax(1);
         Paginator.paginate(
            var2,
            sxxxx,
            () -> this.logDirect(sxxx != null ? String.format(бIо, sxxx.name()) : бIК),
            sxx,
            String.format(бяи, IBaritoneChatControl.FORCE_COMMAND_PREFIX, var1, var3.names[0], sxxx != null ? " " + sxxx.getName() : "")
         );
      } else if (var3 == WaypointsCommand.Action.SAVE) {
         IWaypoint.Tag sxxx = var2.hasAny() ? IWaypoint.Tag.getByName(var2.peekString()) : null;
         if (sxxx == null) {
            sxxx = IWaypoint.Tag.USER;
         } else {
            var2.get();
         }

         String sxxxx = !var2.hasExactlyOne() && !var2.hasExactly(4) ? "" : var2.getString();
         BetterBlockPos sxxxxx = var2.hasAny() ? var2.getDatatypePost(RelativeBlockPos.INSTANCE, this.ctx.playerFeet()) : this.ctx.playerFeet();
         var2.requireMax(0);
         IWaypoint sxxxxxx = new Waypoint(sxxxx, sxxx, sxxxxx);
         ForWaypoints.waypoints(this.baritone).addWaypoint(sxxxxxx);
         MutableComponent sxxxxxxx = Component.literal(бIА);
         sxxxxxxx.setStyle(sxxxxxxx.getStyle().withColor(ChatFormatting.GRAY));
         sxxxxxxx.append(sx.apply(sxxxxxx, WaypointsCommand.Action.INFO));
         this.logDirect(sxxxxxxx);
      } else if (var3 == WaypointsCommand.Action.CLEAR) {
         var2.requireMax(1);
         String sxxx = var2.getString();
         IWaypoint.Tag sxxxx = IWaypoint.Tag.getByName(sxxx);
         if (sxxxx == null) {
            throw new CommandInvalidStateException("Invalid tag, \"" + sxxx + "\"");
         }

         IWaypoint[] sxxxxx = ForWaypoints.getWaypointsByTag(this.baritone, sxxxx);

         for (IWaypoint sxxxxxx : sxxxxx) {
            ForWaypoints.waypoints(this.baritone).removeWaypoint(sxxxxxx);
         }

         this.deletedWaypoints.computeIfAbsent(this.baritone.getWorldProvider().getCurrentWorld(), var0 -> new ArrayList<>()).addAll(Arrays.asList(sxxxxx));
         MutableComponent sxxxxxx = Component.literal(String.format(бI诶, sxxxxx.length));
         sxxxxxx.setStyle(
            sxxxxxx.getStyle()
               .withClickEvent(
                  new RunCommand(
                     String.format(
                        бIЫ,
                        IBaritoneChatControl.FORCE_COMMAND_PREFIX,
                        var1,
                        Stream.of(sxxxxx).map(var0 -> Long.toString(var0.getCreationTimestamp())).collect(Collectors.joining(бIз))
                     )
                  )
               )
         );
         this.logDirect(sxxxxxx);
      } else if (var3 == WaypointsCommand.Action.RESTORE) {
         List<IWaypoint> sxxx = new ArrayList<>();
         List sxxxx = this.deletedWaypoints.getOrDefault(this.baritone.getWorldProvider().getCurrentWorld(), Collections.emptyList());
         if (!var2.peekString().equals(бIР)) {
            var2.requireExactly(1);
            int sxxxxx = sxxxx.size();
            int sxxxxxx = Math.min(sxxxxx, var2.getAs(Integer.class));
            sxxx = new ArrayList<>(sxxxx.subList(sxxxxx - sxxxxxx, sxxxxx));
         } else {
            var2.get();

            while (var2.hasAny()) {
               long sxxxxx = var2.getAs(Long.class);

               for (IWaypoint sxxxxxx : (Iterable<IWaypoint>)sxxxx) {
                  if (sxxxxxx.getCreationTimestamp() == sxxxxx) {
                     sxxx.add(sxxxxxx);
                     break;
                  }
               }
            }
         }

         sxxx.forEach(ForWaypoints.waypoints(this.baritone)::addWaypoint);
         sxxxx.removeIf(sxxx::contains);
         this.logDirect(String.format(бIх, sxxx.size()));
      } else {
         IWaypoint[] sxxx = var2.getDatatypeFor(ForWaypoints.INSTANCE);
         IWaypoint sxxxx = null;
         if (var2.hasAny() && var2.peekString().equals(бIЦ)) {
            var2.requireExactly(2);
            var2.get();
            long sxxxxx = var2.getAs(Long.class);

            for (IWaypoint sxxxxxx : sxxx) {
               if (sxxxxxx.getCreationTimestamp() == sxxxxx) {
                  sxxxx = sxxxxxx;
                  break;
               }
            }

            if (sxxxx == null) {
               throw new CommandInvalidStateException(бI9);
            }
         } else {
            switch (sxxx.length) {
               case 0:
                  throw new CommandInvalidStateException(бIЮ);
               case 1:
                  sxxxx = sxxx[0];
            }
         }

         if (sxxxx == null) {
            var2.requireMax(1);
            Paginator.paginate(
               var2,
               sxxx,
               () -> this.logDirect(бIЖ),
               sxx,
               String.format(бIO, IBaritoneChatControl.FORCE_COMMAND_PREFIX, var1, var3.names[0], var2.consumedString())
            );
         } else if (var3 == WaypointsCommand.Action.INFO) {
            this.logDirect(sxx.apply(sxxxx));
            this.logDirect(String.format(бI弟, sxxxx.getLocation()));
            MutableComponent sxxxxx = Component.literal(бIВ);
            sxxxxx.setStyle(
               sxxxxx.getStyle()
                  .withClickEvent(
                     new RunCommand(String.format(бIж, IBaritoneChatControl.FORCE_COMMAND_PREFIX, var1, sxxxx.getTag().getName(), sxxxx.getCreationTimestamp()))
                  )
            );
            MutableComponent sxxxxxx = Component.literal(бI8);
            sxxxxxx.setStyle(
               sxxxxxx.getStyle()
                  .withClickEvent(
                     new RunCommand(String.format(бIТ, IBaritoneChatControl.FORCE_COMMAND_PREFIX, var1, sxxxx.getTag().getName(), sxxxx.getCreationTimestamp()))
                  )
            );
            MutableComponent sxxxxxxx = Component.literal(бIЙ);
            sxxxxxxx.setStyle(
               sxxxxxxx.getStyle()
                  .withClickEvent(
                     new SuggestCommand(
                        String.format(
                           бIэ,
                           Baritone.settings().prefix.value,
                           var1,
                           sxxxx.getTag().getName(),
                           sxxxx.getName(),
                           sxxxx.getLocation().x,
                           sxxxx.getLocation().y,
                           sxxxx.getLocation().z
                        )
                     )
                  )
            );
            MutableComponent sxxxxxxxx = Component.literal(бIО);
            sxxxxxxxx.setStyle(sxxxxxxxx.getStyle().withClickEvent(new RunCommand(String.format(бIИ, IBaritoneChatControl.FORCE_COMMAND_PREFIX, var1))));
            this.logDirect(sxxxxx);
            this.logDirect(sxxxxxx);
            this.logDirect(sxxxxxxx);
            this.logDirect(sxxxxxxxx);
         } else if (var3 == WaypointsCommand.Action.DELETE) {
            ForWaypoints.waypoints(this.baritone).removeWaypoint(sxxxx);
            this.deletedWaypoints.computeIfAbsent(this.baritone.getWorldProvider().getCurrentWorld(), var0 -> new ArrayList<>()).add(sxxxx);
            MutableComponent sxxxxx = Component.literal(бIП);
            sxxxxx.setStyle(
               sxxxxx.getStyle()
                  .withClickEvent(new RunCommand(String.format(бI_, IBaritoneChatControl.FORCE_COMMAND_PREFIX, var1, sxxxx.getCreationTimestamp())))
            );
            this.logDirect(sxxxxx);
         } else if (var3 == WaypointsCommand.Action.GOAL) {
            Goal sxxxxx = new GoalBlock(sxxxx.getLocation());
            this.baritone.getCustomGoalProcess().setGoal(sxxxxx);
            this.logDirect(String.format(бIс, sxxxxx));
         } else if (var3 == WaypointsCommand.Action.GOTO) {
            Goal sxxxxx = new GoalBlock(sxxxx.getLocation());
            this.baritone.getCustomGoalProcess().setGoalAndPath(sxxxxx);
            this.logDirect(String.format(бIЛ, sxxxxx));
         }
      }
   }

   @Override
   public String getShortDesc() {
      return бIь;
   }

   static {
      VMBridge.identifyClass(WaypointsCommand.class, "34CLAp7M");
   }

   public WaypointsCommand(IBaritone var1) {
      super(var1, бяД, бяН, бяш);
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) throws CommandException {
      if (var2.hasAny()) {
         if (var2.hasExactlyOne()) {
            return new TabCompleteHelper().append(WaypointsCommand.Action.getAllNames()).sortAlphabetically().filterPrefix(var2.getString()).stream();
         }

         WaypointsCommand.Action var3 = WaypointsCommand.Action.getByName(var2.getString());
         if (var2.hasExactlyOne()) {
            if (var3 != WaypointsCommand.Action.LIST && var3 != WaypointsCommand.Action.SAVE && var3 != WaypointsCommand.Action.CLEAR) {
               if (var3 == WaypointsCommand.Action.RESTORE) {
                  return Stream.empty();
               }

               return var2.tabCompleteDatatype(ForWaypoints.INSTANCE);
            }

            return new TabCompleteHelper().append(IWaypoint.Tag.getAllNames()).sortAlphabetically().filterPrefix(var2.getString()).stream();
         }

         if (var2.has(3) && var3 == WaypointsCommand.Action.SAVE) {
            var2.get();
            var2.get();
            return var2.tabCompleteDatatype(RelativeBlockPos.INSTANCE);
         }
      }

      return Stream.empty();
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(бIЯ, "", бIХ, "", бIШ, "", бIа, "", бI7, бIУ, бI0, бIГ, бIф, бIЕ, бIч, бI衣, бIц, бIМ, бI5);
   }

   // $VF: Compiled from s
   private enum Action {
      LIST("list", "get", "l"),
      CLEAR("clear", "c"),
      SAVE("save", "s"),
      INFO("info", "show", "i"),
      DELETE("delete", "d"),
      RESTORE("restore"),
      GOAL("goal", "g"),
      GOTO("goto");

      private final String[] names;

      Action(String... var3) {
         this.names = var3;
      }

      public static WaypointsCommand.Action getByName(String var0) {
         for (WaypointsCommand.Action var4 : values()) {
            for (String sx : var4.names) {
               if (sx.equalsIgnoreCase(var0)) {
                  return var4;
               }
            }
         }

         return null;
      }

      public static String[] getAllNames() {
         Set var0 = new HashSet<>();

         for (WaypointsCommand.Action sx : values()) {
            var0.addAll(Arrays.asList(sx.names));
         }

         return (String[])var0.toArray(new String[0]);
      }
   }
}
