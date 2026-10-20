// Original binary: sg/ec/h8xOvKVHoPiCOZtC
package quick.module.combat;


import quick.setting.MultiToggleSetting;
import quick.setting.NumberSetting;
import quick.setting.ToggleSetting;
import quick.core.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundDamageEventPacket;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.vehicle.minecart.MinecartTNT;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

// $VF: Compiled from s
public class AutoTotem extends ClientModule {
   private static final String field19713 = "Hearts of Gold";
   private static String field19714 = "";
   private static String field19715 = "";
   private static String field19716 = "";
   private static String field19717 = "";
   private static String field19718 = "";
   private static String field19719 = "";
   private static int field19720 = 0;
   private static float field19721 = 0.0F;
   private static float field19722 = 0.0F;
   private static int field19723 = 0;
   private static int field19724 = 0;
   private static int field19725 = 0;
   private static int field19726 = 0;
   private static int field19727 = 0;
   private static int field19728 = 0;
   private static int field19729 = 0;
   private static int field19730 = 0;
   private static int field19731 = 0;
   private final MultiToggleSetting field19732;
   private final NumberSetting<Float> field19733;
   private final NumberSetting<Float> field19734;
   private final ToggleSetting field19735;
   private final NumberSetting<Integer> field19736;
   private final ToggleSetting field19737;
   private final NumberSetting<Float> field19738;
   private final ToggleSetting field19739;
   private final ToggleSetting field19740;
   private final NumberSetting<Float> field19741;
   private final NumberSetting<Float> field19742;
   private final NumberSetting<Float> field19743;
   private final NumberSetting<Float> field19744;
   private final BlockingQueue<AutoTotem.Nested240> field19745;
   private final MutableBlockPos field19746;
   private ItemStack field19747;
   private int field19748;
   private int field19749;
   private int field19750;
   private int field19751;
   private int field19752;
   private boolean field19753;
   private volatile int field19754;
   private volatile long field19755;
   private volatile ItemStack field19756;
   private int field19757;
   private InventoryActionSequence field19758;
   private boolean field19759;
   private int field19760;
   private boolean field19761;
   private volatile float field19762;
   private volatile boolean field19763;
   private static final String field19764 = "Checks for";
   private static final String field19765 = "Hearts of Gold";
   private static final String field19766 = "Crystals";
   private static final String field19767 = "Anchor";
   private static final String field19768 = "Obsidian";
   private static final String field19769 = "Fall";
   private static final String field19770 = "Normal TNT";
   private static final String field19771 = "TNT Minecart";
   private static final String field19772 = "Health level";
   private static final float field19773 = 20.0F;
   private static final float field19774 = 0.5F;
   private static final float field19775 = 4.0F;
   private static final String field19776 = "Health on the Elite";
   private static final float field19777 = 20.0F;
   private static final float field19778 = 0.5F;
   private static final float field19779 = 10.0F;
   private static final String field19780 = "Return item";
   private static final String field19781 = "Reverse swap speed";
   private static final String field19782 = "Do not change the ball";
   private static final String field19783 = "Health Level with Balloon";
   private static final float field19784 = 20.0F;
   private static final float field19785 = 0.5F;
   private static final float field19786 = 12.0F;
   private static final String field19787 = "Cancel item usage";
   private static final String field19788 = "Keep Enchanted";
   private static final String field19789 = "Distance from the crystal";
   private static final float field19790 = 8.0F;
   private static final float field19791 = 0.5F;
   private static final float field19792 = 8.0F;
   private static final String field19793 = "Distance to anchor";
   private static final float field19794 = 10.0F;
   private static final float field19795 = 0.5F;
   private static final float field19796 = 6.0F;
   private static final String field19797 = "Distance to trolley";
   private static final float field19798 = 10.0F;
   private static final float field19799 = 0.5F;
   private static final float field19800 = 6.0F;
   private static final String field19801 = "Distance to tnt";
   private static final float field19802 = 3.0F;
   private static final float field19803 = 50.0F;
   private static final float field19804 = 10.0F;
   private static final float field19805 = Float.MAX_VALUE;
   private static final String field19806 = "Auto Totem";
   private static final String field19807 = "Anchor";
   private static final double field19808 = 0.5;
   private static final double field19809 = 0.5;
   private static final double field19810 = 0.5;
   private static final String field19811 = "Crystals";
   private static final String field19812 = "Normal TNT";
   private static final String field19813 = "TNT Minecart";
   private static final String field19814 = "TNT Minecart";
   private static final String field19815 = "Normal TNT";
   private static final String field19816 = "Crystals";
   private static final String field19817 = "Anchor";
   private static final float field19818 = -1.0F;
   private static final double field19819 = 10.0;
   private static final String field19820 = "Obsidian";
   private static final double field19821 = 0.03;
   private static final String field19822 = "Fall";
   private static final double field19823 = 10.0;
   private static final String field19824 = "Fall";
   private static final double field19825 = 5.0;
   private static final String field19826 = "Hearts of Gold";
   private static final String field19827 = "request";
   private static final String field19828 = "restore";
   private static final float field19829 = Float.MAX_VALUE;
   private static final String field19830 = "Normal TNT";
   private static final String field19831 = "TNT Minecart";
   private static final String field19832 = "Anchor";
   private static final String field19833 = "Crystals";

   private boolean method08630() {
      return mc.gui.screen() instanceof AbstractContainerScreen;
   }

   private boolean method08631() {
      float var1 = mc.player.isFallFlying() ? this.field19734.getValue() : this.field19733.getValue();
      return this.method08635() <= var1;
   }

   private boolean method08632() {
      return mc.player.getOffhandItem().getItem() == Items.TOTEM_OF_UNDYING;
   }

   @Subscribe(0)
   private void method08633(TickEvent var1) {
      if (mc.player != null && mc.level != null && mc.player.isAlive()) {
         this.method08678();
         this.method08650();
         this.field19761 = this.method08656() || this.method08644();
         boolean var2 = this.field19763;
         this.field19763 = false;
         if (var2) {
            this.field19750 = 100;
         } else if (this.field19761) {
            this.field19750 = Math.max(this.field19750, 20);
         } else if (this.field19750 > 0) {
            this.field19750--;
         }

         if (this.field19761) {
            this.field19749 = -1;
            if (this.method08632() || this.method08666() || this.method08665()) {
               return;
            }
         }

         if (!this.method08648()) {
            if (this.field19735.isEnabled()) {
               this.method08638();
            } else {
               this.method08652();
            }
         }
      } else {
         this.method08645();
      }
   }

   private void method08634(int var1) {
      if (this.field19758 != null) {
         this.field19758.method07548(45, var1);
      }

      InventoryUtils.method06137(mc.player.containerMenu.containerId, var1, 40, ContainerInput.SWAP, mc.player);
   }

   private float method08635() {
      float var1 = Math.min(mc.player.getHealth(), this.field19762);
      if (this.field19732.isOptionSelected(field19826) && mc.player.hasEffect(MobEffects.ABSORPTION)) {
         var1 += mc.player.getAbsorptionAmount();
      }

      return var1;
   }

   private void method08636() {
      if (mc.player != null && !this.method08632()) {
         Slot var1 = this.method08662();
         if (var1 != null) {
            this.method08634(var1.index);
            this.method08669(var1.index);
         }
      }
   }

   private void method08637() {
      if (mc.player != null
         && !this.field19761
         && this.field19745.isEmpty()
         && !this.field19747.isEmpty()
         && !this.method08676()
         && !this.method08641()
         && !this.method08630()) {
         int var1 = this.method08674();
         if (var1 != -1) {
            this.field19752++;
            this.method08634(var1);
            this.field19757 = var1;
            this.field19753 = true;
         }
      }
   }

   private void method08638() {
      if (!this.field19747.isEmpty() && !this.method08630()) {
         if (this.field19753) {
            this.field19753 = false;
            if (!this.method08667()) {
               this.method08652();
               return;
            }
         }

         if (this.method08676()) {
            this.method08652();
         } else if (!this.method08641() && this.method08649()) {
            this.field19751 = 0;
            if (this.method08674() != -1 && this.field19750 <= 0) {
               if (this.field19749 < 0) {
                  this.field19749 = Math.max(0, 10 - this.field19736.getValue());
               }

               if (this.field19749 > 0) {
                  this.field19749--;
               } else {
                  this.field19749 = Math.min(100, 2 << Math.min(this.field19752, 6));
                  this.method08663(InventoryActionManager.INSTANCE.method10471(this::method08637, 1, this.method08673()).method07543(), false);
                  if (false) {
                     ;
                  }
               }
            }
         } else {
            if (++this.field19751 > 20) {
               this.method08652();
            }
         }
      }
   }

   private boolean method08640() {
      if (!this.field19732.isOptionSelected(field19820)) {
         return false;
      }

      AABB var1 = mc.player.getBoundingBox();
      int var2 = Mth.floor(var1.minY - field19821);

      for (int var3 = Mth.floor(var1.minX); var3 <= Mth.floor(var1.maxX); var3++) {
         for (int var4 = Mth.floor(var1.minZ); var4 <= Mth.floor(var1.maxZ); var4++) {
            for (int var5 = var2; var5 > var2 - 2; var5--) {
               if (mc.level.getBlockState(this.field19746.set(var3, var5, var4)).is(Blocks.OBSIDIAN)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   private boolean method08641() {
      ItemStack var1 = mc.player.getOffhandItem();
      return !var1.isEmpty() && var1.getItem() != Items.TOTEM_OF_UNDYING;
   }

   private boolean method08642() {
      if (!this.field19732.isOptionSelected(field19817)) {
         return false;
      }

      float var1 = this.field19742.getValue();
      double var2 = var1 * var1;
      int var4 = Mth.ceil(var1);
      Vec3 var5 = mc.player.getEyePosition();
      AABB var6 = mc.player.getBoundingBox();
      BlockPos var7 = mc.player.blockPosition();

      for (int var8 = -var4; var8 <= var4; var8++) {
         for (int var9 = -var4; var9 <= var4; var9++) {
            for (int var10 = -var4; var10 <= var4; var10++) {
               MutableBlockPos var11 = this.field19746.set(var7.getX() + var8, var7.getY() + var9, var7.getZ() + var10);
               BlockState var12 = mc.level.getBlockState(var11);
               if (var12.is(Blocks.RESPAWN_ANCHOR)
                  && var12.getValue(RespawnAnchorBlock.CHARGE) != 0
                  && !(ExplosionDamageUtils.method04381(var5, var11) > var2)
                  && this.method08658(var11)
                  && ExplosionDamageUtils.method04416(Vec3.atCenterOf(var11), mc.player, var6, field19818, field19819) > 0.0F) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public static boolean method08643() {
      AutoTotem var0 = ModuleManager.INSTANCE.method08227();
      return var0 != null && var0.method08668();
   }

   private boolean method08644() {
      return this.method08646()
         ? this.method08631()
         : this.method08631()
            || this.method08647()
            || this.method08642()
            || this.method08640()
            || this.field19732.isOptionSelected(field19814) && WorldCollisionUtils.method02826(MinecartTNT.class, this.field19743.getValue().floatValue())
            || this.field19732.isOptionSelected(field19815) && WorldCollisionUtils.method02826(PrimedTnt.class, this.field19744.getValue().floatValue())
            || this.method08675();
   }

   private void method08645() {
      this.field19758 = null;
      this.field19759 = false;
      this.field19760 = 0;
      this.field19761 = false;
      this.field19762 = field19829;
      this.field19763 = false;
      this.field19754 = -1;
      this.field19755 = 0L;
      this.field19756 = null;
      this.field19757 = -1;
      this.field19745.clear();
      this.method08652();
   }

   private boolean method08646() {
      if (!this.field19737.isEnabled() || mc.player.getOffhandItem().getItem() != Items.PLAYER_HEAD) {
         return false;
      } else {
         return this.field19732.isOptionSelected(field19824) && mc.player.fallDistance > field19825 ? false : this.method08635() > this.field19738.getValue();
      }
   }

   private boolean method08647() {
      return this.field19732.isOptionSelected(field19816) && WorldCollisionUtils.method02826(EndCrystal.class, this.field19741.getValue().floatValue());
   }

   public AutoTotem() {
      super(ModuleCategory.Combat, ModuleSubCategory.Other);
      this.field19732 = new MultiToggleSetting(field19764)
         .addOption(NamedOption.createNamedOption(field19765, true))
         .addOption(NamedOption.createNamedOption(field19766, true))
         .addOption(NamedOption.createNamedOption(field19767, true))
         .addOption(NamedOption.createNamedOption(field19768, false))
         .addOption(NamedOption.createNamedOption(field19769, false))
         .addOption(NamedOption.createNamedOption(field19770, false))
         .addOption(NamedOption.createNamedOption(field19771, false))
         .setContainer(this);
      this.field19733 = new NumberSetting<Float>(field19772)
         .setMin(1.0F)
         .setMax(field19773)
         .setStep(field19774)
         .setDefaultValue(field19775)
         .setContainer(this);
      this.field19734 = new NumberSetting<Float>(field19776)
         .setMin(1.0F)
         .setMax(field19777)
         .setStep(field19778)
         .setDefaultValue(field19779)
         .setContainer(this);
      this.field19735 = new ToggleSetting(field19780).setDefaultValue(false).setContainer(this);
      this.field19736 = new NumberSetting<Integer>(field19781)
         .setMin(1)
         .setMax(10)
         .setStep(1)
         .setDefaultValue(6)
         .setVisibleSupplier(this.field19735::isEnabled)
         .setContainer(this);
      this.field19737 = new ToggleSetting(field19782).setDefaultValue(false).setContainer(this);
      this.field19738 = new NumberSetting<Float>(field19783)
         .setMin(1.0F)
         .setMax(field19784)
         .setStep(field19785)
         .setDefaultValue(field19786)
         .setVisibleSupplier(this.field19737::isEnabled)
         .setContainer(this);
      this.field19739 = new ToggleSetting(field19787).setDefaultValue(false).setContainer(this);
      this.field19740 = new ToggleSetting(field19788).setDefaultValue(false).setContainer(this);
      this.field19741 = new NumberSetting<Float>(field19789)
         .setMin(1.0F)
         .setMax(field19790)
         .setStep(field19791)
         .setDefaultValue(field19792)
         .setVisibleSupplier(() -> this.field19732.isOptionSelected(field19833))
         .setContainer(this);
      this.field19742 = new NumberSetting<Float>(field19793)
         .setMin(1.0F)
         .setMax(field19794)
         .setStep(field19795)
         .setDefaultValue(field19796)
         .setVisibleSupplier(() -> this.field19732.isOptionSelected(field19832))
         .setContainer(this);
      this.field19743 = new NumberSetting<Float>(field19797)
         .setMin(1.0F)
         .setMax(field19798)
         .setStep(field19799)
         .setDefaultValue(field19800)
         .setVisibleSupplier(() -> this.field19732.isOptionSelected(field19831))
         .setContainer(this);
      this.field19744 = new NumberSetting<Float>(field19801)
         .setMin(field19802)
         .setMax(field19803)
         .setStep(1.0F)
         .setDefaultValue(field19804)
         .setVisibleSupplier(() -> this.field19732.isOptionSelected(field19830))
         .setContainer(this);
      Object var10001 = null;
      this.field19745 = new ArrayBlockingQueue<>(32);
      this.field19746 = new MutableBlockPos();
      this.field19747 = ItemStack.EMPTY;
      this.field19748 = -1;
      this.field19749 = -1;
      this.field19754 = -1;
      this.field19757 = -1;
      this.field19762 = field19805;
      this.setName(field19806);
   }

   private boolean method08648() {
      return this.field19758 != null && this.field19758.method07549() && this.field19760 <= 20;
   }

   private boolean method08649() {
      return this.method08670(false, true) != -1;
   }

   private void method08650() {
      if (this.field19755 != 0L) {
         if (System.currentTimeMillis() > this.field19755) {
            this.field19755 = 0L;
            this.field19754 = -1;
            this.field19756 = null;
         } else {
            ItemStack var1 = this.field19756;
            if (var1 != null) {
               this.field19756 = null;
               this.method08657(var1);
            }
         }
      }
   }

   @Subscribe(0)
   private void method08651(PacketEvent var1) {
      if (var1.method10904()) {
         if (var1.method10897() instanceof ClientboundSetHealthPacket var8) {
            this.field19762 = var8.getHealth();
         } else if (var1.method10897() instanceof ClientboundDamageEventPacket var7) {
            if (mc.player != null && var7.entityId() == mc.player.getId() && method08655(var7.sourceType())) {
               this.field19763 = true;
            }
         } else if (var1.method10897() instanceof ClientboundContainerSetContentPacket var6) {
            if (var6.containerId() == 0 && this.field19754 >= 0 && this.field19754 < var6.items().size()) {
               this.method08664(var6.items().get(this.field19754));
            }
         } else if (var1.method10897() instanceof ClientboundContainerSetSlotPacket var5) {
            if (var5.getContainerId() == 0 && var5.getSlot() == this.field19754) {
               this.method08664(var5.getItem());
            }
         } else if (var1.method10897() instanceof ClientboundBlockUpdatePacket var4) {
            BlockPos var15 = var4.getPos();
            if (this.field19732.isOptionSelected(field19807) && var4.getBlockState().is(Blocks.RESPAWN_ANCHOR) && this.method08658(var15)) {
               this.field19745
                  .offer(
                     new AutoTotem.Nested240(
                        var15.getX() + field19808, var15.getY() + field19809, var15.getZ() + field19810, this.field19742.getValue()
                     )
                  );
            }
         } else if (var1.method10897() instanceof ClientboundAddEntityPacket var2) {
            float var14 = this.method08653(var2.getType());
            if (var14 > 0.0F) {
               this.field19745.offer(new AutoTotem.Nested240(var2.getX(), var2.getY(), var2.getZ(), var14));
            }
         }
      }
   }

   private void method08652() {
      this.field19747 = ItemStack.EMPTY;
      this.field19750 = 0;
      this.field19748 = -1;
      this.field19749 = -1;
      this.field19751 = 0;
      this.field19752 = 0;
      this.field19753 = false;
   }

   private float method08653(EntityType<?> var1) {
      if (var1 == EntityTypes.END_CRYSTAL) {
         return this.field19732.isOptionSelected(field19811) ? this.field19741.getValue() : 0.0F;
      } else if (var1 == EntityTypes.TNT) {
         return this.field19732.isOptionSelected(field19812) ? this.field19744.getValue() : 0.0F;
      } else if (var1 == EntityTypes.TNT_MINECART) {
         return this.field19732.isOptionSelected(field19813) ? this.field19743.getValue() : 0.0F;
      } else {
         return 0.0F;
      }
   }

   private boolean method08654(ItemStack var1, boolean var2) {
      return !var1.isEmpty() && var1.getItem() == this.field19747.getItem() ? !var2 || ItemStack.isSameItemSameComponents(var1, this.field19747) : false;
   }

   private static boolean method08655(Holder<DamageType> var0) {
      return var0.is(DamageTypes.EXPLOSION) || var0.is(DamageTypes.PLAYER_EXPLOSION) || var0.is(DamageTypes.BAD_RESPAWN_POINT);
   }

   private boolean method08656() {
      boolean var1 = false;

      AutoTotem.Nested240 var2;
      while ((var2 = this.field19745.poll()) != null) {
         var1 |= mc.player.distanceToSqr(var2.field19834(), var2.field19835(), var2.field19836()) <= var2.range() * var2.range();
      }

      return var1 && !this.method08646();
   }

   private void method08657(ItemStack var1) {
      if (var1.getItem() != Items.TOTEM_OF_UNDYING) {
         if (var1.isEmpty()) {
            if (this.field19748 == this.field19754) {
               this.method08652();
            }
         } else {
            this.field19747 = var1.copy();
            this.field19748 = this.field19754;
            this.field19751 = 0;
            this.field19752 = 0;
            this.field19753 = false;
            this.field19749 = -1;
         }
      }
   }

   private boolean method08658(BlockPos var1) {
      return mc.level != null && !mc.level.environmentAttributes().getValue(EnvironmentAttributes.RESPAWN_ANCHOR_WORKS, var1);
   }

   private boolean method08659(Slot var1) {
      return var1.container == mc.player.getInventory() && var1.getContainerSlot() != 40;
   }

   private Slot method08662() {
      boolean var1 = this.field19740.isEnabled();
      Slot var2 = null;

      for (Slot var4 : mc.player.containerMenu.slots) {
         if (this.method08659(var4)) {
            ItemStack var5 = var4.getItem();
            if (var5.getItem() == Items.TOTEM_OF_UNDYING) {
               if (!var1 || !InventoryUtils.method06120(var5)) {
                  return var4;
               }

               if (var2 == null) {
                  var2 = var4;
               }
            }
         }
      }

      return var2;
   }

   private void method08663(InventoryActionSequence var1, boolean var2) {
      this.field19758 = var1;
      this.field19759 = var2;
      this.field19760 = 0;
   }

   private void method08664(ItemStack var1) {
      if (System.currentTimeMillis() <= this.field19755 && mc.player != null) {
         NonNullList var2 = mc.player.containerMenu.slots;
         int var3 = this.field19754;
         if (var3 >= 0 && var3 < var2.size() && !ItemStack.isSameItem(var1, ((Slot)var2.get(var3)).getItem())) {
            this.field19756 = var1.copy();
         }
      }
   }

   private boolean method08665() {
      Slot var1 = this.method08662();
      if (var1 == null) {
         return false;
      }

      if (this.field19739.isEnabled() && mc.player.isUsingItem()) {
         mc.gameMode.releaseUsingItem(mc.player);
      }

      this.method08663(InventoryActionManager.INSTANCE.method10472(this::method08636, 1, this.method08673()).method07543().method07535(), true);
      if (false) {
         ;
      }

      return true;
   }

   private boolean method08666() {
      return this.field19759 && this.method08648();
   }

   private boolean method08667() {
      Slot var1 = this.method08671(this.field19757);
      return var1 != null && this.method08654(var1.getItem(), false) && this.method08632();
   }

   public boolean method08668() {
      if (!this.isEnabled() || mc.player == null || mc.level == null) {
         return false;
      } else {
         return this.method08648() ? true : this.field19761 && !this.method08632() && this.method08662() != null;
      }
   }

   @Override
   public void onDisable() {
      this.method08645();
      super.onDisable();
   }

   private void method08669(int var1) {
      if (this.field19735.isEnabled()) {
         this.field19756 = null;
         this.field19754 = var1;
         this.field19755 = System.currentTimeMillis() + InventoryActionManager.INSTANCE.method10498();
         Slot var2 = this.method08671(var1);
         if (var2 != null) {
            this.method08657(var2.getItem());
         }
      }
   }

   private int method08670(boolean var1, boolean var2) {
      int var3 = mc.player.getInventory().getSelectedSlot();

      for (Slot var5 : mc.player.containerMenu.slots) {
         if (this.method08659(var5) && (var2 || var5.getContainerSlot() != var3) && this.method08654(var5.getItem(), var1)) {
            return var5.index;
         }
      }

      return -1;
   }

   private Slot method08671(int var1) {
      if (var1 < 0) {
         return null;
      }

      NonNullList var2 = mc.player.containerMenu.slots;
      if (var1 >= var2.size()) {
         return null;
      }

      Slot var3 = (Slot)var2.get(var1);
      return this.method08659(var3) ? var3 : null;
   }

   private boolean method08673() {
      return mc.player.containerMenu == mc.player.inventoryMenu;
   }

   private int method08674() {
      Slot var1 = this.method08671(this.field19748);
      if (var1 != null && this.method08654(var1.getItem(), true)) {
         return this.field19748;
      }

      int var2 = this.method08670(true, false);
      if (var2 == -1) {
         var2 = this.method08670(false, false);
      }

      if (var2 != -1) {
         this.field19748 = var2;
      }

      return var2;
   }

   private boolean method08675() {
      return this.field19732.isOptionSelected(field19822) && !mc.player.isInWater() && mc.player.fallDistance > field19823;
   }

   private boolean method08676() {
      return this.method08654(mc.player.getOffhandItem(), false);
   }

   @Subscribe
   private void method08677(WorldLoadEvent var1) {
      this.method08645();
   }

   private void method08678() {
      if (this.field19758 != null) {
         if (!this.field19758.method07549() || ++this.field19760 > 20) {
            this.field19758 = null;
            this.field19760 = 0;
         }
      }
   }

   // $VF: Compiled from s
   private record Nested240(double field19834, double field19835, double field19836, float range) {
   }
}
