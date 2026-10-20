// Original binary: hex/api/baritone/command/defaults/SelCommand
package hex.api.baritone.command.defaults;

import hex.api.baritone.Baritone;
import hex.api.baritone.api.IBaritone;
import hex.api.baritone.api.command.Command;
import hex.api.baritone.api.command.argument.IArgConsumer;
import hex.api.baritone.api.command.datatypes.ForAxis;
import hex.api.baritone.api.command.datatypes.ForBlockOptionalMeta;
import hex.api.baritone.api.command.datatypes.ForDirection;
import hex.api.baritone.api.command.datatypes.RelativeBlockPos;
import hex.api.baritone.api.command.exception.CommandException;
import hex.api.baritone.api.command.exception.CommandInvalidStateException;
import hex.api.baritone.api.command.exception.CommandInvalidTypeException;
import hex.api.baritone.api.command.helpers.TabCompleteHelper;
import hex.api.baritone.api.event.events.RenderEvent;
import hex.api.baritone.api.event.listener.AbstractGameEventListener;
import hex.api.baritone.api.schematic.CompositeSchematic;
import hex.api.baritone.api.schematic.FillSchematic;
import hex.api.baritone.api.schematic.ISchematic;
import hex.api.baritone.api.schematic.MaskSchematic;
import hex.api.baritone.api.schematic.ReplaceSchematic;
import hex.api.baritone.api.schematic.ShellSchematic;
import hex.api.baritone.api.schematic.WallsSchematic;
import hex.api.baritone.api.schematic.mask.shape.CylinderMask;
import hex.api.baritone.api.schematic.mask.shape.SphereMask;
import hex.api.baritone.api.selection.ISelection;
import hex.api.baritone.api.selection.ISelectionManager;
import hex.api.baritone.api.utils.BetterBlockPos;
import hex.api.baritone.api.utils.BlockOptionalMeta;
import hex.api.baritone.api.utils.BlockOptionalMetaLookup;
import hex.api.baritone.utils.BlockStateInterface;
import hex.api.baritone.utils.IRenderer;
import hex.api.baritone.utils.schematic.StaticSchematic;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import quick.core.BaritoneVertexConsumerAccess;

// $VF: Compiled from s
public class SelCommand extends Command {
   private ISelectionManager manager = this.baritone.getSelectionManager();
   private BetterBlockPos pos1 = null;
   private ISchematic clipboard = null;
   private Vec3i clipboardOffset = null;
   private static String бЖя = "";
   private static String бЖI = "";
   private static String бЖы = "";
   private static String бЖу = "";
   private static String бЖг = "";
   private static String бЖю = "";
   private static String бЖ4 = "";
   private static String бЖС = "";
   private static String бЖъ = "";
   private static String бЖБ = "";
   private static String бЖв = "";
   private static String бЖр = "";
   private static String бЖй = "";
   private static String бЖк = "";
   private static String бЖп = "";
   private static String бЖ2 = "";
   private static String бЖ西 = "";
   private static String бЖт = "";
   private static String бЖд = "";
   private static String бЖе = "";
   private static String бЖ6 = "";
   private static String бЖl = "";
   private static String бЖ必 = "";
   private static String бЖЗ = "";
   private static String бЖн = "";
   private static String бЖД = "";
   private static String бЖН = "";
   private static String бЖш = "";
   private static String бЖл = "";
   private static String бЖи = "";
   private static String бЖщ = "";
   private static String боб = "";
   private static String боА = "";
   private static String бо诶 = "";
   private static String боЫ = "";
   private static String боз = "";
   private static String боР = "";
   private static String бох = "";
   private static String боЦ = "";
   private static String бо9 = "";
   private static String боЮ = "";
   private static String боO = "";
   private static String бо弟 = "";
   private static String боВ = "";
   private static String бож = "";
   private static String бо8 = "";
   private static String боТ = "";

   @Override
   public String getShortDesc() {
      return бЖ必;
   }

   static {
      }

   @Override
   public void execute(String var1, IArgConsumer var2) throws CommandException {
      SelCommand.Action var3 = SelCommand.Action.getByName(var2.getString());
      if (var3 == null) {
         throw new CommandInvalidTypeException(var2.consumed(), бЖу);
      }

      if (var3 != SelCommand.Action.POS1 && var3 != SelCommand.Action.POS2) {
         if (var3 == SelCommand.Action.CLEAR) {
            var2.requireMax(0);
            this.pos1 = null;
            this.logDirect(String.format(бЖС, this.manager.removeAllSelections().length));
         } else if (var3 == SelCommand.Action.UNDO) {
            var2.requireMax(0);
            if (this.pos1 != null) {
               this.pos1 = null;
               this.logDirect(бЖъ);
            } else {
               ISelection[] sx = this.manager.getSelections();
               if (sx.length < 1) {
                  throw new CommandInvalidStateException(бЖБ);
               }

               this.pos1 = this.manager.removeSelection(sx[sx.length - 1]).pos1();
               this.logDirect(бЖв);
            }
         } else if (var3.isFillAction()) {
            BlockOptionalMeta sx = var3 == SelCommand.Action.CLEARAREA ? new BlockOptionalMeta(Blocks.AIR) : var2.getDatatypeFor(ForBlockOptionalMeta.INSTANCE);
            Axis sxx;
            BlockOptionalMetaLookup sxxx;
            if (var3 != SelCommand.Action.REPLACE) {
               if (var3 != SelCommand.Action.CYLINDER && var3 != SelCommand.Action.HCYLINDER) {
                  var2.requireMax(0);
                  sxxx = null;
                  sxx = null;
               } else {
                  var2.requireMax(1);
                  sxx = var2.hasAny() ? var2.getDatatypeFor(ForAxis.INSTANCE) : Axis.Y;
                  sxxx = null;
               }
            } else {
               var2.requireMin(1);
               List<BlockOptionalMeta> sxxxx = new ArrayList<>();
               sxxxx.add(sx);

               while (var2.has(2)) {
                  sxxxx.add(var2.getDatatypeFor(ForBlockOptionalMeta.INSTANCE));
               }

               sx = var2.getDatatypeFor(ForBlockOptionalMeta.INSTANCE);
               sxxx = new BlockOptionalMetaLookup(sxxxx.toArray(new BlockOptionalMeta[0]));
               sxx = null;
            }

            ISelection[] sxxxx = this.manager.getSelections();
            if (sxxxx.length == 0) {
               throw new CommandInvalidStateException(бЖр);
            }

            BetterBlockPos sxxxxx = sxxxx[0].min();
            CompositeSchematic sxxxxxx = new CompositeSchematic(0, 0, 0);

            for (ISelection sxxxxxxx : sxxxx) {
               BetterBlockPos sxxxxxxxx = sxxxxxxx.min();
               sxxxxx = new BetterBlockPos(Math.min(sxxxxx.x, sxxxxxxxx.x), Math.min(sxxxxx.y, sxxxxxxxx.y), Math.min(sxxxxx.z, sxxxxxxxx.z));
            }

            for (ISelection sxxxxxxx : sxxxx) {
               Vec3i sxxxxxxxx = sxxxxxxx.size();
               BetterBlockPos sxxxxxxxxx = sxxxxxxx.min();
               final Axis sxxF = sxx;
               final BlockOptionalMetaLookup sxxxF = sxxx;
               UnaryOperator<ISchematic> sxxxxxxxxxx = var3x -> {
                  int sxxxxxxxxxxx = var3x.widthX();
                  int sxxxxxxxxxxxx = var3x.heightY();
                  int sxxxxxxxxxxxxx = var3x.lengthZ();
                  switch (var3) {
                     case WALLS:
                        return new WallsSchematic((ISchematic)var3x);
                     case SHELL:
                        return new ShellSchematic((ISchematic)var3x);
                     case SPHERE:
                        return MaskSchematic.create((ISchematic)var3x, new SphereMask(sxxxxxxxxxxx, sxxxxxxxxxxxx, sxxxxxxxxxxxxx, true).compute());
                     case HSPHERE:
                        return MaskSchematic.create((ISchematic)var3x, new SphereMask(sxxxxxxxxxxx, sxxxxxxxxxxxx, sxxxxxxxxxxxxx, false).compute());
                     case CYLINDER:
                         return MaskSchematic.create((ISchematic)var3x, new CylinderMask(sxxxxxxxxxxx, sxxxxxxxxxxxx, sxxxxxxxxxxxxx, true, sxxF).compute());
                     case HCYLINDER:
                         return MaskSchematic.create((ISchematic)var3x, new CylinderMask(sxxxxxxxxxxx, sxxxxxxxxxxxx, sxxxxxxxxxxxxx, false, sxxF).compute());
                     case CLEARAREA:
                     default:
                        return var3x;
                     case REPLACE:
                         return new ReplaceSchematic(var3x, sxxxF);
                  }
               };
               ISchematic sxxxxxxxxxxx = (ISchematic)sxxxxxxxxxx.apply(new FillSchematic(sxxxxxxxx.getX(), sxxxxxxxx.getY(), sxxxxxxxx.getZ(), sx));
               sxxxxxx.put(sxxxxxxxxxxx, sxxxxxxxxx.x - sxxxxx.x, sxxxxxxxxx.y - sxxxxx.y, sxxxxxxxxx.z - sxxxxx.z);
            }

            this.baritone.getBuilderProcess().build(бЖй, sxxxxxx, sxxxxx);
            this.logDirect(бЖк);
         } else if (var3 == SelCommand.Action.COPY) {
            BetterBlockPos sx = this.ctx.viewerPos();
            BetterBlockPos sxx = var2.hasAny() ? var2.getDatatypePost(RelativeBlockPos.INSTANCE, sx) : sx;
            var2.requireMax(0);
            ISelection[] sxxx = this.manager.getSelections();
            if (sxxx.length < 1) {
               throw new CommandInvalidStateException(бЖп);
            }

            BlockStateInterface sxxxx = new BlockStateInterface(this.ctx);
            BetterBlockPos sxxxxx = sxxx[0].min();
            CompositeSchematic sxxxxxx = new CompositeSchematic(0, 0, 0);

            for (ISelection sxxxxxxx : sxxx) {
               BetterBlockPos sxxxxxxxx = sxxxxxxx.min();
               sxxxxx = new BetterBlockPos(Math.min(sxxxxx.x, sxxxxxxxx.x), Math.min(sxxxxx.y, sxxxxxxxx.y), Math.min(sxxxxx.z, sxxxxxxxx.z));
            }

            for (ISelection sxxxxxxx : sxxx) {
               Vec3i sxxxxxxxx = sxxxxxxx.size();
               BetterBlockPos sxxxxxxxxx = sxxxxxxx.min();
               BlockState[][][] sxxxxxxxxxx = new BlockState[sxxxxxxxx.getX()][sxxxxxxxx.getZ()][sxxxxxxxx.getY()];

               for (int sxxxxxxxxxxx = 0; sxxxxxxxxxxx < sxxxxxxxx.getX(); sxxxxxxxxxxx++) {
                  for (int sxxxxxxxxxxxx = 0; sxxxxxxxxxxxx < sxxxxxxxx.getY(); sxxxxxxxxxxxx++) {
                     for (int sxxxxxxxxxxxxx = 0; sxxxxxxxxxxxxx < sxxxxxxxx.getZ(); sxxxxxxxxxxxxx++) {
                        sxxxxxxxxxx[sxxxxxxxxxxx][sxxxxxxxxxxxxx][sxxxxxxxxxxxx] = sxxxx.get0(
                           sxxxxxxxxx.x + sxxxxxxxxxxx, sxxxxxxxxx.y + sxxxxxxxxxxxx, sxxxxxxxxx.z + sxxxxxxxxxxxxx
                        );
                     }
                  }
               }

               ISchematic sxxxxxxxxxxx = new StaticSchematic(sxxxxxxxxxx);
               sxxxxxx.put(sxxxxxxxxxxx, sxxxxxxxxx.x - sxxxxx.x, sxxxxxxxxx.y - sxxxxx.y, sxxxxxxxxx.z - sxxxxx.z);
            }

            this.clipboard = sxxxxxx;
            this.clipboardOffset = sxxxxx.subtract(sxx);
            this.logDirect(бЖ2);
         } else if (var3 == SelCommand.Action.PASTE) {
            BetterBlockPos sx = this.ctx.viewerPos();
            BetterBlockPos sxx = var2.hasAny() ? var2.getDatatypePost(RelativeBlockPos.INSTANCE, sx) : sx;
            var2.requireMax(0);
            if (this.clipboard == null) {
               throw new CommandInvalidStateException(бЖ西);
            }

            this.baritone.getBuilderProcess().build(бЖт, this.clipboard, sxx.offset(this.clipboardOffset));
            this.logDirect(бЖд);
         } else if (var3 == SelCommand.Action.EXPAND || var3 == SelCommand.Action.CONTRACT || var3 == SelCommand.Action.SHIFT) {
            var2.requireExactly(3);
            SelCommand.TransformTarget sx = SelCommand.TransformTarget.getByName(var2.getString());
            if (sx == null) {
               throw new CommandInvalidStateException(бЖе);
            }

            Direction sxx = var2.getDatatypeFor(ForDirection.INSTANCE);
            int sxxx = var2.getAs(Integer.class);
            ISelection[] sxxxx = this.manager.getSelections();
            if (sxxxx.length < 1) {
               throw new CommandInvalidStateException(бЖ6);
            }

            sxxxx = sx.transform(sxxxx);

            for (ISelection sxxxxx : sxxxx) {
               if (var3 == SelCommand.Action.EXPAND) {
                  this.manager.expand(sxxxxx, sxx, sxxx);
               } else if (var3 == SelCommand.Action.CONTRACT) {
                  this.manager.contract(sxxxxx, sxx, sxxx);
               } else {
                  this.manager.shift(sxxxxx, sxx, sxxx);
               }
            }

            this.logDirect(String.format(бЖl, sxxxx.length));
         }
      } else {
         if (var3 == SelCommand.Action.POS2 && this.pos1 == null) {
            throw new CommandInvalidStateException(бЖг);
         }

         BetterBlockPos sx = this.ctx.viewerPos();
         BetterBlockPos sxx = var2.hasAny() ? var2.getDatatypePost(RelativeBlockPos.INSTANCE, sx) : sx;
         var2.requireMax(0);
         if (var3 == SelCommand.Action.POS1) {
            this.pos1 = sxx;
            this.logDirect(бЖю);
         } else {
            this.manager.addSelection(this.pos1, sxx);
            this.pos1 = null;
            this.logDirect(бЖ4);
         }
      }
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) throws CommandException {
      if (var2.hasExactlyOne()) {
         return new TabCompleteHelper().append(SelCommand.Action.getAllNames()).filterPrefix(var2.getString()).sortAlphabetically().stream();
      }

      SelCommand.Action var3 = SelCommand.Action.getByName(var2.getString());
      if (var3 != null) {
         if (var3 != SelCommand.Action.POS1 && var3 != SelCommand.Action.POS2) {
            if (var3.isFillAction()) {
               if (var2.hasExactlyOne() || var3 == SelCommand.Action.REPLACE) {
                  while (var2.has(2)) {
                     var2.get();
                  }

                  return var2.tabCompleteDatatype(ForBlockOptionalMeta.INSTANCE);
               }

               if (var2.hasExactly(2) && (var3 == SelCommand.Action.CYLINDER || var3 == SelCommand.Action.HCYLINDER)) {
                  var2.get();
                  return var2.tabCompleteDatatype(ForAxis.INSTANCE);
               }
            } else if (var3 == SelCommand.Action.EXPAND || var3 == SelCommand.Action.CONTRACT || var3 == SelCommand.Action.SHIFT) {
               if (var2.hasExactlyOne()) {
                  return new TabCompleteHelper().append(SelCommand.TransformTarget.getAllNames()).filterPrefix(var2.getString()).sortAlphabetically().stream();
               }

               SelCommand.TransformTarget sx = SelCommand.TransformTarget.getByName(var2.getString());
               if (sx != null && var2.hasExactlyOne()) {
                  return var2.tabCompleteDatatype(ForDirection.INSTANCE);
               }
            }
         } else if (var2.hasAtMost(3)) {
            return var2.tabCompleteDatatype(RelativeBlockPos.INSTANCE);
         }
      }

      return Stream.empty();
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(
         бЖЗ, "", бЖн, "", бЖД, "", бЖН, бЖш, бЖл, бЖи, бЖщ, "", боб, боА, бо诶, боЫ, боз, боР, бох, боЦ, бо9, боЮ, боO, бо弟, боВ, "", бож, бо8, боТ
      );
   }

   public SelCommand(IBaritone var1) {
      super(var1, бЖя, бЖI, бЖы);
      var1.getGameEventHandler().registerEventListener(new AbstractGameEventListener()      // $VF: Compiled from s
 {
         @Override
         public void onRenderPass(RenderEvent var1) {
            if (Baritone.settings().renderSelectionCorners.value && SelCommand.this.pos1 != null) {
               Color var2 = Baritone.settings().colorSelectionPos1.value;
               float sx = Baritone.settings().selectionOpacity.value;
               float sxx = Baritone.settings().selectionLineWidth.value;
               boolean sxxx = Baritone.settings().renderSelectionIgnoreDepth.value;
               BaritoneVertexConsumerAccess sxxxx = IRenderer.startLines(var2, sx);
               IRenderer.emitAABB(sxxxx, var1.getModelViewStack(), new AABB(SelCommand.this.pos1), sxx);
               IRenderer.endLines(sxxxx, sxxx);
            }
         }
      });
   }

   // $VF: Compiled from s
   enum Action {
      POS1("pos1", "p1", "1"),
      POS2("pos2", "p2", "2"),
      CLEAR("clear", "c"),
      UNDO("undo", "u"),
      SET("set", "fill", "s", "f"),
      WALLS("walls", "w"),
      SHELL("shell", "shl"),
      SPHERE("sphere", "sph"),
      HSPHERE("hsphere", "hsph"),
      CYLINDER("cylinder", "cyl"),
      HCYLINDER("hcylinder", "hcyl"),
      CLEARAREA("cleararea", "ca"),
      REPLACE("replace", "r"),
      EXPAND("expand", "ex"),
      COPY("copy", "cp"),
      PASTE("paste", "p"),
      CONTRACT("contract", "ct"),
      SHIFT("shift", "sh");

      private final String[] names;

      Action(String... var3) {
         this.names = var3;
      }

      public static SelCommand.Action getByName(String var0) {
         for (SelCommand.Action var4 : values()) {
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

         for (SelCommand.Action sx : values()) {
            var0.addAll(Arrays.asList(sx.names));
         }

         return (String[])var0.toArray(new String[0]);
      }

      public final boolean isFillAction() {
         return this == SET
            || this == WALLS
            || this == SHELL
            || this == SPHERE
            || this == HSPHERE
            || this == CYLINDER
            || this == HCYLINDER
            || this == CLEARAREA
            || this == REPLACE;
      }
   }

   // $VF: Compiled from s
   enum TransformTarget {
      ALL(var0 -> var0, "all", "a"),
      NEWEST(var0 -> new ISelection[]{var0[var0.length - 1]}, "newest", "n"),
      OLDEST(var0 -> new ISelection[]{var0[0]}, "oldest", "o");

      private final Function<ISelection[], ISelection[]> transform;
      private final String[] names;

      TransformTarget(Function<ISelection[], ISelection[]> var3, String... var4) {
         this.transform = var3;
         this.names = var4;
      }

      public ISelection[] transform(ISelection[] var1) {
         return this.transform.apply(var1);
      }

      public static SelCommand.TransformTarget getByName(String var0) {
         for (SelCommand.TransformTarget var4 : values()) {
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

         for (SelCommand.TransformTarget sx : values()) {
            var0.addAll(Arrays.asList(sx.names));
         }

         return (String[])var0.toArray(new String[0]);
      }
   }
}
