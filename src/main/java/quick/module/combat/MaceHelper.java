// Original binary: sg/ec/5FBbAaRpWO7t6O5M
package quick.module.combat;


import quick.setting.ModeSetting;
import quick.setting.NumberSetting;
import quick.core.*;

import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;

// $VF: Compiled from s
public class MaceHelper extends ClientModule {
   private static final int field02114 = -1;
   private final ModeSetting field02115;
   private final NumberSetting<Integer> field02116;
   private final ModeSetting field02117;
   private int field02118;
   private int field02119;
   private int field02120;
   private int field02121;
   private int field02122;
   private boolean field02123;
   private boolean field02124;
   private boolean field02125;
   private static final String field02126 = "Mode of use";
   private static final String field02127 = "Breach";
   private static final String field02128 = "Swap";
   private static final String field02129 = "Breach";
   private static final String field02130 = "Hold the mace in ticks";
   private static final String field02131 = "Swap mode";
   private static final String field02132 = "Vanilla";
   private static final String field02133 = "Funtime";
   private static final String field02134 = "Vanilla";
   private static final String field02135 = "Mace Helper";
   private static final String field02136 = "Breach";
   private static final String field02137 = "Swap";
   private static final String field02138 = "Vanilla";
   private static final String field02139 = "Swap";
   private static final String field02140 = "Funtime";
   private static final String field02141 = "Breach";
   private static final String field02142 = "Swap";
   private static final String field02143 = "Breach";

   private boolean method00933() {
      if (this.field02115.isMode(field02136)) {
         return true;
      } else {
         return !this.method00951() ? AttackItemUtils.method05346(mc.player) : this.field02125 && AttackItemUtils.method05345() && this.method00936();
      }
   }

   private void method00934() {
      InventoryUtils.method06137(this.field02121, this.field02119, this.field02120, ContainerInput.SWAP, mc.player);
   }

   private void method00935() {
      if (this.field02124) {
         if (mc.player == null) {
            this.method00937();
         } else if (this.field02118 != -1) {
            InventoryUtils.method06091(mc.player.getInventory().getSelectedSlot());
            this.method00937();
         } else {
            this.method00941();
            this.method00937();
         }
      }
   }

   private boolean method00936() {
      return mc.player != null && mc.player.getDeltaMovement().y < 0.0;
   }

   private void method00937() {
      this.field02124 = false;
      this.field02123 = false;
      this.field02118 = -1;
      this.field02119 = -1;
      this.field02120 = -1;
   }

   private void method00938() {
      if (this.field02123) {
         this.method00935();
      } else if (!this.method00944()) {
         this.field02125 = true;
         this.method00935();
      } else {
         if (!this.field02124) {
            this.method00948(false);
         }
      }
   }

   private void method00939() {
      this.field02124 = true;
      this.field02122 = TickEvent.method03209();
   }

   public boolean method00940() {
      if (!this.isEnabled() || mc.player == null) {
         return false;
      } else if (mc.player.isUsingItem()) {
         return false;
      } else if (AttackItemUtils.method05343()) {
         return false;
      } else if (this.method00951() && !this.field02125) {
         return false;
      } else if (!AttackItemUtils.method05345()) {
         return false;
      } else if (this.method00951() && !this.method00936()) {
         return false;
      } else {
         return AttackItemUtils.method05342() != -1 ? true : mc.gui.screen() == null && AttackItemUtils.method05344() != null;
      }
   }

   @Override
   public void onDisable() {
      this.method00935();
      this.field02125 = true;
      super.onDisable();
   }

   private void method00941() {
      if (mc.player != null) {
         if (this.field02119 != -1 && this.field02120 != -1) {
            if (mc.player.containerMenu.containerId == this.field02121) {
               int var1 = this.field02121;
               int var2 = this.field02119;
               int var3 = this.field02120;
               InventoryActionManager.INSTANCE.method10470(() -> InventoryUtils.method06137(var1, var2, var3, ContainerInput.SWAP, mc.player), 1, false);
            }
         }
      }
   }

   private boolean method00944() {
      return AttackItemUtils.method05345() && this.method00936();
   }

   private int method00945() {
      return this.field02115.isMode(field02141) ? this.field02116.getValue() : 1;
   }

   public MaceHelper() {
      super(ModuleCategory.Combat, ModuleSubCategory.Combat);
      this.field02115 = new ModeSetting(field02126).setModes(field02127, field02128).setDefaultValue(field02129).setContainer(this);
      this.field02116 = new NumberSetting<Integer>(field02130)
         .setMin(1)
         .setMax(5)
         .setStep(1)
         .setDefaultValue(2)
         .setVisibleSupplier(() -> this.field02115.isMode(field02143))
         .setContainer(this);
      this.field02117 = new ModeSetting(field02131)
         .setModes(field02132, field02133)
         .setDefaultValue(field02134)
         .setVisibleSupplier(() -> this.field02115.isMode(field02142))
         .setContainer(this);
      this.field02118 = -1;
      this.field02119 = -1;
      this.field02120 = -1;
      this.field02125 = true;
      this.setName(field02135);
   }

   private boolean method00946() {
      return this.field02115.isMode(field02137) && this.field02117.isMode(field02138);
   }

   @Subscribe
   private void method00947(PlayerUpdateEvent var1) {
      if (mc.player == null || mc.level == null) {
         this.method00937();
         this.field02125 = true;
      } else if (this.method00951()) {
         this.method00938();
      } else if (this.field02124) {
         if (this.field02123 || TickEvent.method03209() - this.field02122 >= this.method00945()) {
            this.method00935();
         }
      }
   }

   private void method00948(boolean var1) {
      if (mc.player != null) {
         if (!mc.player.isUsingItem()) {
            if (this.field02124) {
               this.field02122 = TickEvent.method03209();
            } else if (!AttackItemUtils.method05343()) {
               if (this.method00933()) {
                  int var2 = AttackItemUtils.method05342();
                  if (var2 != -1) {
                     InventoryUtils.method06091(var2);
                     this.field02118 = var2;
                     this.method00939();
                  } else if (mc.gui.screen() == null) {
                     Slot var3 = AttackItemUtils.method05344();
                     if (var3 != null) {
                        this.field02121 = mc.player.containerMenu.containerId;
                        this.field02119 = var3.index;
                        this.field02120 = mc.player.getInventory().getSelectedSlot();
                        if (var1) {
                           this.method00934();
                        } else {
                           this.method00941();
                        }

                        this.method00939();
                     }
                  }
               }
            }
         }
      }
   }

   @Subscribe
   private void method00949(AttackEntityEvent var1) {
      if (var1.method09431() == AttackEntityEvent.Nested264.PRE) {
         this.method00948(true);
      } else if (this.field02124) {
         if (this.method00951()) {
            if (AttackItemUtils.method05346(mc.player)) {
               this.field02125 = false;
               this.field02123 = true;
            }
         } else {
            if (this.method00946()) {
               this.field02123 = true;
            }
         }
      }
   }

   private boolean method00951() {
      return this.field02115.isMode(field02139) && this.field02117.isMode(field02140);
   }
}
