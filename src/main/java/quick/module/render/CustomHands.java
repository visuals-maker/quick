// Original binary: sg/ec/jxmcqHgjWghE2OD4
package quick.module.render;


import quick.setting.ColorSetting;
import quick.setting.ModeSetting;
import quick.setting.NumberSetting;
import quick.setting.SliderSetting;
import quick.setting.ToggleSetting;
import quick.module.movement.Speed;
import quick.core.*;

import java.awt.Color;

// $VF: Compiled from s
public class CustomHands extends ClientModule {
   private final ToggleSetting field20586;
   private final ModeSetting field20587;
   private final ModeSetting field20588;
   private final ModeSetting field20589;
   private final ColorSetting field20590;
   private final ColorSetting field20591;
   private final SliderSetting field20592;
   private final NumberSetting<Float> field20593;
   private final NumberSetting<Float> field20594;
   private final NumberSetting<Float> field20595;
   private final NumberSetting<Float> field20596;
   private final ToggleSetting field20597;
   private final ModeSetting field20598;
   private final ToggleSetting field20599;
   private final ModeSetting field20600;
   private final ModeSetting field20601;
   private final ColorSetting field20602;
   private final ColorSetting field20603;
   private final SliderSetting field20604;
   private final NumberSetting<Float> field20605;
   private final NumberSetting<Float> field20606;
   private final NumberSetting<Float> field20607;
   private final NumberSetting<Float> field20608;
   private final ModeSetting field20609;
   private final ColorSetting field20610;
   private final ColorSetting field20611;
   private static final String field20612 = "Glow";
   private static final String field20613 = "Outline Type";
   private static final String field20614 = "Static";
   private static final String field20615 = "Gradient";
   private static final String field20616 = "Static";
   private static final String field20617 = "Color";
   private static final String field20618 = "Client";
   private static final String field20619 = "Custom";
   private static final String field20620 = "Textural";
   private static final String field20621 = "Client";
   private static final String field20622 = "Color";
   private static final String field20623 = "Client";
   private static final String field20624 = "Custom";
   private static final String field20625 = "Rainbow";
   private static final String field20626 = "glow.gradient_color";
   private static final String field20627 = "Client";
   private static final String field20628 = "Outline Color";
   private static final String field20629 = "Outline Color 2";
   private static final String field20630 = "glow.color2";
   private static final String field20631 = "Direction";
   private static final String field20632 = "glow.direction";
   private static final String field20633 = "Speed";
   private static final float field20634 = 3.0F;
   private static final float field20635 = 0.1F;
   private static final String field20636 = "glow.speed";
   private static final String field20637 = "Saturation";
   private static final float field20638 = 3.0F;
   private static final float field20639 = 0.1F;
   private static final float field20640 = 1.5F;
   private static final String field20641 = "Radius of illumination";
   private static final float field20642 = 25.0F;
   private static final float field20643 = 8.0F;
   private static final String field20644 = "Glow brightness";
   private static final float field20645 = 3.5F;
   private static final float field20646 = 0.1F;
   private static final float field20647 = 1.5F;
   private static final String field20648 = "Effect on item";
   private static final String field20649 = "Effect Type";
   private static final String field20650 = "Blur";
   private static final String field20651 = "Glossy";
   private static final String field20652 = "Blur";
   private static final String field20653 = "Blur Color";
   private static final String field20654 = "Color Type";
   private static final String field20655 = "Client";
   private static final String field20656 = "Custom";
   private static final String field20657 = "Client";
   private static final String field20658 = "Fill";
   private static final String field20659 = "Solid";
   private static final String field20660 = "Gradient";
   private static final String field20661 = "Solid";
   private static final String field20662 = "Your color";
   private static final String field20663 = "Your color 2";
   private static final String field20664 = "Direction";
   private static final String field20665 = "Strength";
   private static final float field20666 = 0.2F;
   private static final float field20667 = 3.0F;
   private static final float field20668 = 0.1F;
   private static final float field20669 = 1.2F;
   private static final String field20670 = "Speed";
   private static final float field20671 = 0.1F;
   private static final float field20672 = 3.0F;
   private static final float field20673 = 0.1F;
   private static final String field20674 = "Wave Density";
   private static final float field20675 = 40.0F;
   private static final float field20676 = 12.0F;
   private static final String field20677 = "Sharpness";
   private static final float field20678 = 8.0F;
   private static final float field20679 = 0.5F;
   private static final float field20680 = 3.0F;
   private static final String field20681 = "Color";
   private static final String field20682 = "Rainbow";
   private static final String field20683 = "Client";
   private static final String field20684 = "Custom";
   private static final String field20685 = "gloss.color";
   private static final String field20686 = "Rainbow";
   private static final String field20687 = "Color 1";
   private static final String field20688 = "Color 2";
   private static final String field20689 = "Custom Hands";
   private static final String field20690 = "Gradient";
   private static final String field20691 = "Custom";
   private static final int field20692 = -16777216;
   private static final int field20693 = 16777215;
   private static final String field20694 = "Static";
   private static final String field20695 = "Textural";
   private static final String field20696 = "Gradient";
   private static final String field20697 = "Rainbow";
   private static final String field20698 = "Custom";
   private static final int field20699 = 16777215;
   private static final String field20700 = "Custom";
   private static final int field20701 = 16777215;
   private static final String field20702 = "Glossy";
   private static final String field20703 = "Rainbow";
   private static final String field20704 = "Custom";
   private static final int field20705 = 16777215;
   private static final String field20706 = "Custom";
   private static final int field20707 = 16777215;
   private static final String field20708 = "Gradient";
   private static final String field20709 = "Custom";
   private static final int field20710 = 16777215;
   private static final String field20711 = "Custom";
   private static final int field20712 = 16777215;
   private static final float field20713 = 120.0F;
   private static final String field20714 = "Glossy";
   private static final String field20715 = "Custom";
   private static final String field20716 = "Glossy";
   private static final String field20717 = "Custom";
   private static final String field20718 = "Glossy";
   private static final String field20719 = "Glossy";
   private static final String field20720 = "Glossy";
   private static final String field20721 = "Glossy";
   private static final String field20722 = "Glossy";
   private static final String field20723 = "Blur";
   private static final String field20724 = "Gradient";
   private static final String field20725 = "Blur";
   private static final String field20726 = "Custom";
   private static final String field20727 = "Gradient";
   private static final String field20728 = "Blur";
   private static final String field20729 = "Custom";
   private static final String field20730 = "Blur";
   private static final String field20731 = "Blur";
   private static final String field20732 = "Blur";
   private static final String field20733 = "Static";
   private static final String field20734 = "Textural";
   private static final String field20735 = "Gradient";
   private static final String field20736 = "Gradient";
   private static final String field20737 = "Gradient";
   private static final String field20738 = "Custom";
   private static final String field20739 = "Custom";
   private static final String field20740 = "Gradient";
   private static final String field20741 = "Static";

   public int method09081() {
      int var1 = this.field20589.isMode(field20698) ? this.field20590.method11676() : ColorUtils.Nested138.method04636();
      return var1 & field20699;
   }

   public float method09085() {
      return this.field20593.getValue();
   }

   private ModeSetting method09086() {
      return this.field20587.isMode(field20690) ? this.field20589 : this.field20588;
   }

   public float method09088() {
      return this.field20608.getValue();
   }

   public float method09089() {
      return this.field20604.method00357();
   }

   public int method09092() {
      int var1 = this.field20609.isMode(field20706) ? this.field20611.method11676() : ColorUtils.Nested138.method04637();
      return var1 & field20707;
   }

   public int method09093() {
      if (!this.method09096() && !this.method09109()) {
         int var1 = this.field20588.isMode(field20691) ? this.field20590.method11676() : ColorUtils.Nested138.method04638(90);
         return field20692 | var1 & field20693;
      } else {
         return -1;
      }
   }

   public int method09095() {
      int var1 = this.field20589.isMode(field20700) ? this.field20591.method11676() : ColorUtils.Nested138.method04637();
      return var1 & field20701;
   }

   public CustomHands() {
      super(ModuleCategory.Render, ModuleSubCategory.Interface);
      this.field20586 = new ToggleSetting(field20612).setDefaultValue(false).setContainer(this);
      this.field20587 = new ModeSetting(field20613)
         .setModes(field20614, field20615)
         .setDefaultValue(field20616)
         .setVisibleSupplier(this.field20586::isEnabled)
         .setContainer(this);
      this.field20588 = new ModeSetting(field20617)
         .setModes(field20618, field20619, field20620)
         .setDefaultValue(field20621)
         .setVisibleSupplier(() -> this.field20586.isEnabled() && this.field20587.isMode(field20741))
         .setContainer(this);
      this.field20589 = new ModeSetting(field20622)
         .setModes(field20623, field20624, field20625)
         .setId(field20626)
         .setDefaultValue(field20627)
         .setVisibleSupplier(() -> this.field20586.isEnabled() && this.field20587.isMode(field20740))
         .setContainer(this);
      this.field20590 = new ColorSetting(field20628)
         .setDefaultValue(new Color(255, 255, 255))
         .setVisibleSupplier(() -> this.field20586.isEnabled() && this.method09086().isMode(field20739))
         .setContainer(this);
      this.field20591 = new ColorSetting(field20629)
         .setId(field20630)
         .setDefaultValue(new Color(0, 170, 255))
         .setVisibleSupplier(() -> this.field20586.isEnabled() && this.field20587.isMode(field20737) && this.field20589.isMode(field20738))
         .setContainer(this);
      this.field20592 = new SliderSetting(field20631)
         .setId(field20632)
         .setDefaultValue(0.0F)
         .setVisibleSupplier(() -> this.field20586.isEnabled() && this.field20587.isMode(field20736))
         .setContainer(this);
      this.field20593 = new NumberSetting<Float>(field20633)
         .setMin(0.0F)
         .setMax(field20634)
         .setStep(field20635)
         .setDefaultValue(1.0F)
         .setId(field20636)
         .setVisibleSupplier(() -> this.field20586.isEnabled() && this.field20587.isMode(field20735))
         .setContainer(this);
      this.field20594 = new NumberSetting<Float>(field20637)
         .setMin(1.0F)
         .setMax(field20638)
         .setStep(field20639)
         .setDefaultValue(field20640)
         .setVisibleSupplier(() -> this.field20586.isEnabled() && this.field20587.isMode(field20733) && this.field20588.isMode(field20734))
         .setContainer(this);
      this.field20595 = new NumberSetting<Float>(field20641)
         .setMin(1.0F)
         .setMax(field20642)
         .setStep(1.0F)
         .setDefaultValue(field20643)
         .setVisibleSupplier(this.field20586::isEnabled)
         .setContainer(this);
      this.field20596 = new NumberSetting<Float>(field20644)
         .setMin(1.0F)
         .setMax(field20645)
         .setStep(field20646)
         .setDefaultValue(field20647)
         .setVisibleSupplier(this.field20586::isEnabled)
         .setContainer(this);
      this.field20597 = new ToggleSetting(field20648).setDefaultValue(false).setContainer(this);
      this.field20598 = new ModeSetting(field20649)
         .setModes(field20650, field20651)
         .setDefaultValue(field20652)
         .setVisibleSupplier(this.field20597::isEnabled)
         .setContainer(this);
      this.field20599 = new ToggleSetting(field20653)
         .setDefaultValue(false)
         .setVisibleSupplier(() -> this.field20597.isEnabled() && this.field20598.isMode(field20732))
         .setContainer(this);
      this.field20600 = new ModeSetting(field20654)
         .setModes(field20655, field20656)
         .setDefaultValue(field20657)
         .setVisibleSupplier(() -> this.field20597.isEnabled() && this.field20598.isMode(field20731) && this.field20599.isEnabled())
         .setContainer(this);
      this.field20601 = new ModeSetting(field20658)
         .setModes(field20659, field20660)
         .setDefaultValue(field20661)
         .setVisibleSupplier(() -> this.field20597.isEnabled() && this.field20598.isMode(field20730) && this.field20599.isEnabled())
         .setContainer(this);
      this.field20602 = new ColorSetting(field20662)
         .setDefaultValue(new Color(255, 255, 255))
         .setVisibleSupplier(
            () -> this.field20597.isEnabled()
               && this.field20598.isMode(field20728)
               && this.field20599.isEnabled()
               && this.field20600.isMode(field20729)
         )
         .setContainer(this);
      this.field20603 = new ColorSetting(field20663)
         .setDefaultValue(new Color(0, 170, 255))
         .setVisibleSupplier(
            () -> this.field20597.isEnabled()
               && this.field20598.isMode(field20725)
               && this.field20599.isEnabled()
               && this.field20600.isMode(field20726)
               && this.field20601.isMode(field20727)
         )
         .setContainer(this);
      this.field20604 = new SliderSetting(field20664)
         .setDefaultValue(0.0F)
         .setVisibleSupplier(
            () -> this.field20597.isEnabled()
               && this.field20598.isMode(field20723)
               && this.field20599.isEnabled()
               && this.field20601.isMode(field20724)
         )
         .setContainer(this);
      this.field20605 = new NumberSetting<Float>(field20665)
         .setMin(field20666)
         .setMax(field20667)
         .setStep(field20668)
         .setDefaultValue(field20669)
         .setVisibleSupplier(() -> this.field20597.isEnabled() && this.field20598.isMode(field20722))
         .setContainer(this);
      this.field20606 = new NumberSetting<Float>(field20670)
         .setMin(field20671)
         .setMax(field20672)
         .setStep(field20673)
         .setDefaultValue(1.0F)
         .setVisibleSupplier(() -> this.field20597.isEnabled() && this.field20598.isMode(field20721))
         .setContainer(this);
      this.field20607 = new NumberSetting<Float>(field20674)
         .setMin(2.0F)
         .setMax(field20675)
         .setStep(1.0F)
         .setDefaultValue(field20676)
         .setVisibleSupplier(() -> this.field20597.isEnabled() && this.field20598.isMode(field20720))
         .setContainer(this);
      this.field20608 = new NumberSetting<Float>(field20677)
         .setMin(1.0F)
         .setMax(field20678)
         .setStep(field20679)
         .setDefaultValue(field20680)
         .setVisibleSupplier(() -> this.field20597.isEnabled() && this.field20598.isMode(field20719))
         .setContainer(this);
      this.field20609 = new ModeSetting(field20681)
         .setModes(field20682, field20683, field20684)
         .setId(field20685)
         .setDefaultValue(field20686)
         .setVisibleSupplier(() -> this.field20597.isEnabled() && this.field20598.isMode(field20718))
         .setContainer(this);
      this.field20610 = new ColorSetting(field20687)
         .setDefaultValue(new Color(0, 170, 255))
         .setVisibleSupplier(() -> this.field20597.isEnabled() && this.field20598.isMode(field20716) && this.field20609.isMode(field20717))
         .setContainer(this);
      this.field20611 = new ColorSetting(field20688)
         .setDefaultValue(new Color(170, 0, 255))
         .setVisibleSupplier(() -> this.field20597.isEnabled() && this.field20598.isMode(field20714) && this.field20609.isMode(field20715))
         .setContainer(this);
      this.setName(field20689);
   }

   public boolean method09096() {
      return this.field20587.isMode(field20694) && this.field20588.isMode(field20695);
   }

   public boolean method09098() {
      return this.field20597.isEnabled() && !this.method09128() && this.field20599.isEnabled();
   }

   public boolean method09099() {
      return this.field20601.isMode(field20708);
   }

   public boolean method09100() {
      return this.isEnabled();
   }

   public float method09101() {
      return this.field20604.method00359();
   }

   public float method09102() {
      return this.field20607.getValue();
   }

   public int method09103() {
      int var1 = this.field20600.isMode(field20709) ? this.field20602.method11676() : ColorUtils.Nested138.method04636();
      return var1 & field20710;
   }

   public float method09104() {
      return this.field20596.getValue();
   }

   public float method09105() {
      return this.field20606.getValue();
   }

   public float method09106() {
      return this.field20594.getValue();
   }

   public float method09107() {
      return this.field20592.method00357();
   }

   public boolean method09109() {
      return this.field20587.isMode(field20696);
   }

   public boolean method09110() {
      return this.field20609.isMode(field20703);
   }

   public boolean method09112() {
      return this.method09109() && this.field20589.isMode(field20697);
   }

   public float method09113() {
      return field20713;
   }

   public boolean method09117() {
      return this.field20597.isEnabled();
   }

   public float method09118() {
      return this.field20595.getValue();
   }

   public float method09120() {
      return this.field20592.method00359();
   }

   public int method09121() {
      int var1 = this.field20600.isMode(field20711) ? this.field20603.method11676() : ColorUtils.Nested138.method04637();
      return var1 & field20712;
   }

   public boolean method09123() {
      return this.field20586.isEnabled();
   }

   public int method09127() {
      int var1 = this.field20609.isMode(field20704) ? this.field20610.method11676() : ColorUtils.Nested138.method04636();
      return var1 & field20705;
   }

   public boolean method09128() {
      return this.field20598.isMode(field20702);
   }

   public float method09130() {
      return this.field20605.getValue();
   }
}
