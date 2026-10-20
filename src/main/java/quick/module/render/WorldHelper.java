// Original binary: sg/ec/5Y3ziFwLpwmYegKH
package quick.module.render;


import quick.setting.ModeSetting;
import quick.core.*;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector2f;
import quick.mixin.BossHealthOverlayMixin;

// $VF: Compiled from s
public class WorldHelper extends ClientModule {
   private final ModeSetting field02425;
   private final List<TrapMarker> field02426;
   private static final double field02427 = 3.0;
   private final Set<UUID> field02428;
   private static long field02429 = 0L;
   private static int field02430 = 0;
   private static int field02431 = 0;
   private static int field02432 = 0;
   private static int field02433 = 0;
   private static double field02434 = 0.0;
   private static int field02435 = 0;
   private static int field02436 = 0;
   private static int field02437 = 0;
   private static double field02438 = 0.0;
   private static int field02439 = 0;
   private static int field02440 = 0;
   private static int field02441 = 0;
   private static int field02442 = 0;
   private static int field02443 = 0;
   private static int field02444 = 0;
   private static double field02445 = 0.0;
   private static int field02446 = 0;
   private static int field02447 = 0;
   private static int field02448 = 0;
   private static double field02449 = 0.0;
   private static int field02450 = 0;
   private static int field02451 = 0;
   private final List<RecentBlock> field02452;
   private long field02453;
   private int field02454;
   private final Map<Long, Block> field02455;
   private final Object field02456;
   private final AtomicBoolean field02457;
   private volatile boolean field02458;
   private static final String field02459 = "Server";
   private static final String field02460 = "Funtime";
   private static final String field02461 = "HolyWorld";
   private static final String field02462 = "ReallyWorld";
   private static final String field02463 = "Funtime";
   private static final String field02464 = "World Helper";
   private static final String field02465 = "ReallyWorld";
   private static final String field02466 = "Funtime";
   private static final float field02467 = 20.0F;
   private static final float field02468 = 40.0F;
   private static final float field02469 = 6.0F;
   private static final float field02470 = 4.0F;
   private static final float field02471 = 11.0F;
   private static final float field02472 = 7.0F;
   private static final float field02473 = 2.5F;
   private static final float field02474 = 3.0F;
   private static final float field02475 = 5.0F;
   private static final float field02476 = 0.65F;
   private static final float field02477 = 6.0F;
   private static final float field02478 = 6.0F;
   private static final double field02479 = 3.0;
   private static final double field02480 = 3.0;
   private static final String field02481 = "HolyWorld";
   private static final float field02482 = 0.5F;
   private static final float field02483 = 0.7F;
   private static final String field02484 = "ReallyWorld";
   private static final String field02485 = "Funtime";
   private static final long field02486 = 1000L;
   private static final double field02487 = 2.0;
   private static final double field02488 = 0.5;
   private static final double field02489 = 2.0;
   private static final double field02490 = 0.5;
   private static final double field02491 = 2.0;
   private static final double field02492 = 0.5;
   private static final double field02493 = 2.0;
   private static final double field02494 = 0.5;
   private static final double field02495 = 0.5;
   private static final double field02496 = 2.0;
   private static final double field02497 = 0.5;
   private static final double field02498 = 2.0;
   private static final double field02499 = 0.5;
   private static final double field02500 = 2.0;
   private static final double field02501 = 0.5;
   private static final double field02502 = 2.0;
   private static final double field02503 = 0.5;
   private static final double field02504 = 3.0;
   private static final double field02505 = 2.0;
   private static final double field02506 = 4.0;
   private static final double field02507 = 3.0;
   private static final double field02508 = 4.0;
   private static final double field02509 = 2.0;
   private static final double field02510 = 4.0;
   private static final double field02511 = 4.0;
   private static final double field02512 = 4.0;
   private static final double field02513 = 4.0;
   private static final long field02514 = 400L;

   private Vec3 method01153(List<RecentBlock> var1) {
      return this.method01161(var1, 4, 1, 4, 45, 4, 1, 4);
   }

   @Subscribe
   private void method01155(HudRenderEvent var1) {
      if (!this.field02426.isEmpty()) {
         GuiGraphicsExtractor var2 = var1.method05424();
         PoseStack var3 = var1.method05425();
         float var4 = field02467;
         float var5 = field02468;
         ItemRenderUtils.method08410();
         RoundedRectRenderer.method01277();
         FontRenderer.method09475();

         for (TrapMarker var7 : this.field02426) {
            ItemStack var8 = var7.item();
            Vector2f var9 = WorldProjectionUtils.method09460(var3, var7.position());
            int var10 = var7.totalTicks() - var7.elapsedTicks();
            String var11 = TextRenderUtils.method00680(var10);
            String var12 = var7.type().label();
            float var13 = var9.x;
            float var14 = var9.y;
            float var15 = field02469;
            float var16 = field02470;
            float var17 = field02471;
            float var18 = field02472;
            float var19 = field02473;
            float var20 = field02474;
            float var21 = Fonts.field00109.method09486(var12, var18);
            float var22 = Fonts.field00109.method09486(var11, var18);
            float var23 = var15 + var17 + var16 + var21 + var16 + var22 + var15;
            float var24 = var13 - var23 / 2.0F;
            float var25 = var14 - var4 / 2.0F;
            float var26 = var24 + var15;
            float var27 = var26 + var17 + var16;
            float var28 = var27 + var21 + var16;
            float var29 = var18 + var20 + var19;
            float var30 = var25 + (var4 - var29) / 2.0F;
            float var31 = var30 + var18 / 2.0F;
            float var32 = var30 + var18 + var20;
            RoundedRectRenderer.method01272(var2, var24, var25, var23, var4, field02475, 1.0F, ColorUtils.method04597(30, 30, 30, 200));
            ItemRenderUtils.method08418(var2, var8, var26 + var17 / 2.0F, var31, field02476, 1.0F, 1.0F);
            Fonts.field00109.method09480(var2, var12, var27, var30, -1, var18);
            Fonts.field00109.method09480(var2, var11, var28, var30, ColorUtils.method04604(170, 170, 170), var18);
            float var33 = var23 - var15 * 2.0F;
            float var34 = var7.totalTicks() <= 0 ? 0.0F : Math.max(0.0F, Math.min(1.0F, (float)var10 / var7.totalTicks()));
            RoundedRectRenderer.method01272(var2, var24 + var15, var32, var33, var19, field02477, 1.0F, ColorUtils.method04597(30, 30, 30, 200));
            RoundedRectRenderer.method01272(var2, var24 + var15, var32, var33 * var34, var19, field02478, 1.0F, -1);
         }

         FontRenderer.method09484();
         RoundedRectRenderer.method01278();
         ItemRenderUtils.method08420();
      }
   }

   private Vec3 method01156(List<RecentBlock> var1, boolean var2) {
      HashSet var3 = new HashSet();

      for (RecentBlock var5 : var1) {
         int var6 = var5.x();
         int var7 = var5.y();
         int var8 = var2 ? var5.x() + var5.z() : var5.x() - var5.z();
         int var9 = var6;
         int var10 = var6;
         int var11 = var7;
         int var12 = var7;
         int var13 = var5.z();
         int var14 = var5.z();
         int var15 = var8;
         int var16 = var8;
         int var17 = 0;
         var3.clear();

         for (RecentBlock var19 : var1) {
            int var20 = var2 ? var19.x() + var19.z() : var19.x() - var19.z();
            if (Math.abs(var20 - var8) <= 1 && var19.x() >= var6 && var19.x() <= var6 + 4 && var19.y() >= var7 && var19.y() <= var7 + 4) {
               var17++;
               var3.add(var19.block());
               var9 = Math.min(var9, var19.x());
               var10 = Math.max(var10, var19.x());
               var11 = Math.min(var11, var19.y());
               var12 = Math.max(var12, var19.y());
               var13 = Math.min(var13, var19.z());
               var14 = Math.max(var14, var19.z());
               var15 = Math.min(var15, var20);
               var16 = Math.max(var16, var20);
            }
         }

         if (var17 >= 40 && var3.size() <= 3 && var10 - var9 >= 4 && var12 - var11 >= 4 && var16 - var15 <= 1) {
            return new Vec3((var9 + var10) / field02487 + field02488, (var11 + var12) / field02489 + field02490, (var13 + var14) / field02491 + field02492);
         }
      }

      return null;
   }

   @Subscribe
   private void method01157(PlayerUpdateEvent var1) {
      for (TrapMarker var3 : this.field02426) {
         var3.tick();
      }

      this.field02426.removeIf(var0 -> var0.elapsed >= var0.lifetime);
      this.method01176();
   }

   @Subscribe
   private void method01158(Render3DEvent var1) {
      if (!this.field02426.isEmpty()) {
         ;
      }
   }

   private void method01159(TrapType var1, Vec3 var2) {
      if (var1 == TrapType.BIG_TRAP) {
         this.field02426.removeIf(var2x -> var2x.type() == TrapType.TRAP && this.method01181(var2x.position(), var2, field02512, field02513));
      } else if (var1 == TrapType.TRAP
         && this.field02426.stream().anyMatch(var2x -> var2x.type() == TrapType.BIG_TRAP && this.method01181(var2x.position(), var2, field02510, field02511))) {
         return;
      }

      boolean var3 = var1 == TrapType.TRAP || var1 == TrapType.BIG_TRAP;
      boolean var4 = var1 == TrapType.PLAST || var1 == TrapType.PLAST_WALL;
      boolean var5 = var1 == TrapType.REALLYWORLD_TRAP;
      double var6 = var3 ? field02504 : (var4 ? field02505 : (var5 ? field02506 : 0.0));
      double var8 = var3 ? field02507 : (var5 ? field02508 : field02509);
      if (this.field02426.stream().noneMatch(var7 -> var7.type() == var1 && this.method01181(var7.position(), var2, var6, var8))) {
         this.field02426.add(new TrapMarker(var1, var2));
      }
   }

   private void method01160(TrapType var1, Vec3 var2) {
      mc.execute(() -> {
         if (this.isEnabled()) {
            this.method01159(var1, var2);
         }
      });
   }

   private Vec3 method01161(List<RecentBlock> var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      HashSet var9 = new HashSet();

      for (RecentBlock var11 : var1) {
         int var12 = var11.x();
         int var13 = var11.x();
         int var14 = var11.y();
         int var15 = var11.y();
         int var16 = var11.z();
         int var17 = var11.z();
         int var18 = 0;
         var9.clear();

         for (RecentBlock var20 : var1) {
            if (var20.x() >= var11.x()
               && var20.x() <= var11.x() + var2
               && var20.y() >= var11.y()
               && var20.y() <= var11.y() + var3
               && var20.z() >= var11.z()
               && var20.z() <= var11.z() + var4) {
               var18++;
               var9.add(var20.block());
               var12 = Math.min(var12, var20.x());
               var13 = Math.max(var13, var20.x());
               var14 = Math.min(var14, var20.y());
               var15 = Math.max(var15, var20.y());
               var16 = Math.min(var16, var20.z());
               var17 = Math.max(var17, var20.z());
            }
         }

         if (var18 >= var5 && var9.size() <= 3 && var13 - var12 >= var6 && var15 - var14 >= var7 && var17 - var16 >= var8) {
            return new Vec3((var12 + var13) / field02498 + field02499, (var14 + var15) / field02500 + field02501, (var16 + var17) / field02502 + field02503);
         }
      }

      return null;
   }

   @Subscribe
   private void method01162(PacketEvent var1) {
      if (var1.method10904()) {
         if (var1.method10897() instanceof ClientboundSoundPacket var2) {
            SoundEvent var7 = var2.getSound().value();
            float var4 = var2.getVolume();
            float var5 = var2.getPitch();
            Vec3 var6 = new Vec3(var2.getX(), Math.floor(var2.getY()) + 1.0, var2.getZ());
            if (this.field02425.isMode(field02481)
               && var7 == SoundEvents.EVOKER_FANGS_ATTACK
               && (var4 == field02482 || var4 == field02483)
               && var5 == 1.0F) {
               this.method01159(TrapType.STAN, var6);
            }

            var1.cancel();
         }
      }
   }

   private Map<UUID, LerpingBossEvent> method01163() {
      return mc.gui != null && mc.gui.hud != null ? ((BossHealthOverlayMixin)mc.gui.hud.getBossOverlay()).events() : null;
   }

   private Vec3 method01164(List<RecentBlock> var1) {
      Vec3 var2 = this.method01156(var1, false);
      if (var2 == null) {
         var2 = this.method01156(var1, true);
      }

      return var2;
   }

   private Vec3 method01165(List<RecentBlock> var1) {
      Vec3 var2 = this.method01161(var1, 4, 4, 1, 45, 4, 4, 1);
      if (var2 == null) {
         var2 = this.method01161(var1, 1, 4, 4, 45, 1, 4, 4);
      }

      return var2;
   }

   private void method01166() {
      this.field02458 = true;
      if (this.field02457.compareAndSet(false, true)) {
         Util.backgroundExecutor().execute(this::method01177);
      }
   }

   @Subscribe
   private void method01167(BlockUpdateEvent var1) {
      boolean var2 = this.field02425.isMode(field02465);
      if (var2 || this.field02425.isMode(field02466)) {
         synchronized (this.field02456) {
            if (var2) {
               this.method01175(var1.method06654(), var1.method06658().getBlock());
            } else {
               BlockState var4 = var1.method06658();
               long var5 = var1.method06654().asLong();
               Block var7 = this.field02455.get(var5);
               if (var7 != null) {
                  if (var7 != var4.getBlock()) {
                     this.field02455.remove(var5);
                  }

                  return;
               }

               if (var4.isAir() || var4.getBlock() instanceof LeavesBlock) {
                  return;
               }

               this.field02455.put(var5, var4.getBlock());
               this.method01175(var1.method06654(), var4.getBlock());
            }
         }

         this.method01166();
      }
   }

   private boolean method01168(Map<Long, Block> var1, int var2, int var3, int var4, int var5, int var6) {
      int var7 = 0;

      for (int var8 = var2; var8 <= var3; var8++) {
         for (int var9 = var5; var9 <= var6; var9++) {
            if (var1.containsKey(BlockPos.asLong(var8, var4, var9))) {
               var7++;
            }
         }
      }

      return var7 >= 15;
   }

   private boolean method01173() {
      long var1 = System.currentTimeMillis();
      if (var1 - this.field02453 >= field02486) {
         this.field02453 = var1;
         this.field02454 = 0;
      }

      if (this.field02454 >= 400) {
         return false;
      }

      this.field02454++;
      return true;
   }

   private Vec3 method01174(List<RecentBlock> var1, Map<Long, Block> var2) {
      HashSet var3 = new HashSet();

      for (RecentBlock var5 : var1) {
         int var6 = var5.x();
         int var7 = var5.y();
         int var8 = var5.z();
         int var9 = var6;
         int var10 = var6;
         int var11 = var8;
         int var12 = var8;
         int var13 = 0;
         var3.clear();

         for (RecentBlock var15 : var1) {
            if (var15.y() == var7 && var15.x() >= var6 && var15.x() <= var6 + 4 && var15.z() >= var8 && var15.z() <= var8 + 4) {
               var13++;
               var3.add(var15.block());
               var9 = Math.min(var9, var15.x());
               var10 = Math.max(var10, var15.x());
               var11 = Math.min(var11, var15.z());
               var12 = Math.max(var12, var15.z());
            }
         }

         if (var13 >= 23
            && var3.size() <= 3
            && var10 - var9 >= 4
            && var12 - var11 >= 4
            && (this.method01168(var2, var9, var10, var7 - 1, var11, var12) || this.method01168(var2, var9, var10, var7 + 1, var11, var12))) {
            return new Vec3((var9 + var10) / field02493 + field02494, var7 + field02495, (var11 + var12) / field02496 + field02497);
         }
      }

      return null;
   }

   private void method01175(BlockPos var1, Block var2) {
      long var3 = System.currentTimeMillis();
      this.field02452.add(new RecentBlock(var1.getX(), var1.getY(), var1.getZ(), var3, var2));
      this.field02452.removeIf(var2x -> var3 - var2x.time() > field02514);

      while (this.field02452.size() > 512) {
         this.field02452.remove(0);
      }
   }

   private void method01176() {
      if (!this.field02426.isEmpty() || !this.field02428.isEmpty()) {
         Map var1 = this.method01163();
         if (var1 != null) {
            LocalPlayer var2 = mc.player;
            Iterator var3 = this.field02428.iterator();

            while (var3.hasNext()) {
               UUID var4 = (UUID)var3.next();
               if (var2 == null || this.method01180(var4, var2.position()) == null) {
                  var1.remove(var4);
                  var3.remove();
               }
            }

            if (var2 != null) {
               Vec3 var10 = var2.position();

               for (TrapMarker var6 : this.field02426) {
                  if (!(var10.distanceTo(var6.position()) > field02479)) {
                     UUID var7 = var6.id();
                     float var8 = Math.max(0.0F, Math.min(1.0F, (float)(var6.totalTicks() - var6.elapsedTicks()) / var6.totalTicks()));
                     LerpingBossEvent var9 = (LerpingBossEvent)var1.get(var7);
                     if (var9 == null) {
                        var1.put(
                           var7, new LerpingBossEvent(var7, Component.literal(var6.type().label()), var8, var6.type().color(), BossBarOverlay.PROGRESS, false, false, false)
                        );
                        this.field02428.add(var7);
                     } else {
                        var9.setProgress(var8);
                     }
                  }
               }
            }
         }
      }
   }

   public WorldHelper() {
      super(ModuleCategory.Render, ModuleSubCategory.World);
      this.field02425 = new ModeSetting(field02459).setModes(field02460, field02461, field02462).setDefaultValue(field02463).setContainer(this);
      this.field02426 = new ArrayList<>();
      this.field02428 = new HashSet<>();
      this.field02452 = new ArrayList<>();
      this.field02453 = 0L;
      this.field02454 = 0;
      this.field02455 = new BlockMap();
      this.field02456 = new Object();
      this.field02457 = new AtomicBoolean(false);
      this.field02458 = false;
      this.setName(field02464);
   }

   private void method01177() {
      while (this.field02458) {
         this.field02458 = false;
         this.method01182();
      }

      this.field02457.set(false);
      if (this.field02458 && this.field02457.compareAndSet(false, true)) {
         Util.backgroundExecutor().execute(this::method01177);
      }
   }

   @Override
   public void onDisable() {
      this.method01183();
      super.onDisable();
   }

   private void method01179(List<RecentBlock> var1, Map<Long, Block> var2) {
      Vec3 var3 = this.method01161(var1, 6, 6, 6, 100, 5, 5, 5);
      if (var3 != null) {
         this.method01160(TrapType.BIG_TRAP, var3);
      } else {
         Vec3 var4 = this.method01161(var1, 4, 4, 4, 60, 3, 3, 3);
         if (var4 != null) {
            this.method01160(TrapType.TRAP, var4);
         } else {
            Vec3 var5 = this.method01153(var1);
            if (var5 != null) {
               this.method01160(TrapType.PLAST, var5);
            } else {
               Vec3 var6 = this.method01165(var1);
               if (var6 != null) {
                  this.method01160(TrapType.PLAST_WALL, var6);
               } else {
                  Vec3 var7 = this.method01164(var1);
                  if (var7 != null) {
                     this.method01160(TrapType.PLAST_WALL, var7);
                  } else {
                     Vec3 var8 = this.method01174(var1, var2);
                     if (var8 != null) {
                        this.method01160(TrapType.PLAST, var8);
                     }
                  }
               }
            }
         }
      }
   }

   private TrapMarker method01180(UUID var1, Vec3 var2) {
      for (TrapMarker var4 : this.field02426) {
         if (var4.id().equals(var1) && var2.distanceTo(var4.position()) <= field02480) {
            return var4;
         }
      }

      return null;
   }

   private boolean method01181(Vec3 var1, Vec3 var2, double var3, double var5) {
      return Math.abs(Math.floor(var1.x) - Math.floor(var2.x)) <= var3
         && Math.abs(Math.floor(var1.z) - Math.floor(var2.z)) <= var3
         && Math.abs(var1.y - var2.y) <= var5;
   }

   private void method01182() {
      boolean var1 = this.field02425.isMode(field02484);
      boolean var2 = this.field02425.isMode(field02485);
      if (var1 || var2) {
         ArrayList var3;
         HashMap var4;
         synchronized (this.field02456) {
            if (this.field02452.isEmpty()) {
               return;
            }

            var3 = new ArrayList<>(this.field02452);
            var4 = var2 ? new HashMap<>(this.field02455) : null;
         }

         if (var1) {
            if (var3.size() >= 60) {
               if (this.method01173()) {
                  Vec3 var8 = this.method01161(var3, 6, 6, 6, 60, 3, 3, 3);
                  if (var8 != null) {
                     this.method01160(TrapType.REALLYWORLD_TRAP, var8);
                  }
               }
            }
         } else if (var3.size() >= 23) {
            if (this.method01173()) {
               this.method01179(var3, var4);
            }
         }
      }
   }

   private void method01183() {
      Map var1 = this.method01163();
      if (var1 != null) {
         for (UUID var3 : this.field02428) {
            var1.remove(var3);
         }
      }

      this.field02428.clear();
   }
enum TrapType {
      STAN("Status", BossBarColor.YELLOW),
      TRAP("Ladder", BossBarColor.RED),
      BIG_TRAP("Big ladder", BossBarColor.RED),
      PLAST("Reservoir", BossBarColor.BLUE),
      PLAST_WALL("Plast wall", BossBarColor.BLUE),
      REALLYWORLD_TRAP("Realworld gangway", BossBarColor.PURPLE);

      private final String label;
      private final BossBarColor color;

      TrapType(String var1, BossBarColor var2) {
         this.label = var1;
         this.color = var2;
      }

      public String label() {
         return this.label;
      }

      public BossBarColor color() {
         return this.color;
      }
   }

   static final class TrapMarker {
      private final TrapType type;
      private final Vec3 position;
      private final UUID id = UUID.randomUUID();
      private final int lifetime = 400;
      int elapsed;

      TrapMarker(TrapType var1, Vec3 var2) {
         this.type = var1;
         this.position = var2;
      }

      TrapType type() {
         return this.type;
      }

      Vec3 position() {
         return this.position;
      }

      UUID id() {
         return this.id;
      }

      int totalTicks() {
         return this.lifetime;
      }

      int elapsedTicks() {
         return this.elapsed;
      }

      void tick() {
         this.elapsed++;
      }

      ItemStack item() {
         return switch (this.type) {
            case STAN -> new ItemStack(Items.EVOKER_SPAWN_EGG);
            case TRAP, BIG_TRAP -> new ItemStack(Items.RESPAWN_ANCHOR);
            case PLAST, PLAST_WALL -> new ItemStack(Items.PISTON);
            case REALLYWORLD_TRAP -> new ItemStack(Items.END_CRYSTAL);
         };
      }
   }

   static final class RecentBlock {
      private final int x;
      private final int y;
      private final int z;
      private final long time;
      private final Block block;

      RecentBlock(int var1, int var2, int var3, long var4, Block var5) {
         this.x = var1;
         this.y = var2;
         this.z = var3;
         this.time = var4;
         this.block = var5;
      }

      int x() {
         return this.x;
      }

      int y() {
         return this.y;
      }

      int z() {
         return this.z;
      }

      long time() {
         return this.time;
      }

      Block block() {
         return this.block;
      }
   }

   static final class BlockMap extends HashMap<Long, Block> {
   }
}
