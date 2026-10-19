// Original binary: sg/ec/QGwYNeY6jT68jo5V
package quick.module.combat;


import quick.setting.MultiToggleSetting;
import sg.ec.*;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;

// $VF: Compiled from s
public class Velocity extends ClientModule {
   private final MultiToggleSetting field11219;
   private static final String field11220 = "Turn off under conditions";
   private static final String field11221 = "On the elitists";
   private static final String field11222 = "On the ground";
   private static final String field11223 = "In the air";
   private static final String field11224 = "In water";
   private static final String field11225 = "Velocity";
   private static final String field11226 = "On the elitists";
   private static final String field11227 = "In water";
   private static final String field11228 = "On the ground";
   private static final String field11229 = "In the air";

   private boolean method05151() {
      if (mc.player == null) {
         return false;
      } else if (this.field11219.isOptionSelected(field11226) && mc.player.isFallFlying()) {
         return true;
      } else if (this.field11219.isOptionSelected(field11227) && mc.player.isInWater()) {
         return true;
      } else {
         return this.field11219.isOptionSelected(field11228) && mc.player.onGround()
            ? true
            : this.field11219.isOptionSelected(field11229) && !mc.player.onGround() && !mc.player.isInWater() && !mc.player.isFallFlying();
      }
   }

   public Velocity() {
      super(ModuleCategory.Combat, ModuleSubCategory.Utility);
      this.field11219 = new MultiToggleSetting(field11220)
         .addOption(NamedOption.createNamedOption(field11221, true))
         .addOption(NamedOption.createNamedOption(field11222, false))
         .addOption(NamedOption.createNamedOption(field11223, false))
         .addOption(NamedOption.createNamedOption(field11224, false))
         .setContainer(this);
      this.setName(field11225);
   }

   @Subscribe
   private void method05152(PacketEvent var1) {
      if (mc.player != null) {
         Packet var2 = var1.method10897();
         if (var1.method10904()) {
            if (var2 instanceof ClientboundSetEntityMotionPacket var3 && var3.id() != mc.player.getId()) {
               return;
            }

            if (var2 instanceof ClientboundSetEntityMotionPacket) {
               if (this.method05151()) {
                  return;
               }

               var1.setCancelled(true);
            }
         }
      }
   }
}
