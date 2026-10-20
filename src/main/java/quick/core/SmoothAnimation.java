// Original binary: sg/ec/X3hwsWjAPZ17ZLuO
package quick.core;

// $VF: Compiled from s
public class SmoothAnimation {
   private long field13881;
   private double field13882;
   private double field13883;
   private double field13884;
   private double field13885;
   private double field13886;
   private EasingFunction field13887 = Easings.field19394;
   private Runnable field13888;
   private static final double field13889 = 1000.0;

   public SmoothAnimation method06147(EasingFunction var1) {
      this.field13887 = var1;
      return this;
   }

   public double method06148(double var1, double var3, double var5) {
      return var1 + (var3 - var1) * var5;
   }

   public SmoothAnimation method06149(double var1) {
      this.field13886 = var1;
      return this;
   }

   public boolean method06150(boolean var1, double var2) {
      return var1 && this.isAnimating() && (var2 == this.method06160() || var2 == this.getTarget() || var2 == this.getValue());
   }

   public double method06151() {
      return this.method06167() <= 0.0 ? 1.0 : (System.currentTimeMillis() - this.method06174()) / this.method06167();
   }

   public boolean isAnimating() {
      return !this.method06171();
   }

   public double method06153() {
      return this.field13886;
   }

   public SmoothAnimation method06154(double var1) {
      this.field13882 = var1;
      return this;
   }

   public SmoothAnimation setValue(double var1) {
      this.field13885 = var1;
      return this;
   }

   public SmoothAnimation method06156(double var1, double var3, boolean var5) {
      return this.animate(var1, var3, Easings.field19394, var5);
   }

   public void method06157(double var1) {
      this.method06166(var1, 0.0);
      this.update();
      this.setValue(var1);
   }

   public SmoothAnimation method06158(double var1, double var3, EasingFunction var5) {
      return this.animate(var1, var3, var5, false);
   }

   public double getValue() {
      return this.field13885;
   }

   public double method06160() {
      return this.field13883;
   }

   public double getTarget() {
      return this.field13884;
   }

   public boolean update() {
      this.method06149(this.getValue());
      boolean var1 = this.isAnimating();
      if (var1) {
         this.setValue(this.method06148(this.method06160(), this.getTarget(), this.method06164().method07086(this.method06151())));
      } else {
         this.method06163(0L);
         this.setValue(this.getTarget());
         if (this.field13888 != null) {
            this.field13888.run();
            this.field13888 = null;
         }
      }

      return var1;
   }

   public SmoothAnimation method06163(long var1) {
      this.field13881 = var1;
      return this;
   }

   public EasingFunction method06164() {
      return this.field13887;
   }

   public Runnable method06165() {
      return this.field13888;
   }

   public SmoothAnimation method06166(double var1, double var3) {
      return this.animate(var1, var3, Easings.field19394, false);
   }

   public double method06167() {
      return this.field13882;
   }

   public SmoothAnimation animate(double var1, double var3, EasingFunction var5, boolean var6) {
      if (!this.method06150(var6, var1)) {
         this.method06147(var5).method06154(var3 * field13889).method06163(System.currentTimeMillis()).method06172(this.getValue()).method06175(var1);
      }

      return this;
   }

   public float getPreviousFloat() {
      return (float)this.method06153();
   }

   public SmoothAnimation method06170(Runnable var1) {
      this.field13888 = var1;
      return this;
   }

   public boolean method06171() {
      return this.method06151() >= 1.0;
   }

   public SmoothAnimation method06172(double var1) {
      this.field13883 = var1;
      return this;
   }

   public float getValueFloat() {
      return (float)this.getValue();
   }

   public long method06174() {
      return this.field13881;
   }

   public SmoothAnimation method06175(double var1) {
      this.field13884 = var1;
      return this;
   }
}
