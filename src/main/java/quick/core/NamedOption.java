// Original binary: sg/ec/fMH3pSkD8M6kuhxv
package quick.core;

import java.util.function.Supplier;

// $VF: Compiled from s
public class NamedOption<T> {
   private final String field18916;
   private final T field18917;
   private T field18918;
   private Supplier<Boolean> field18919 = () -> true;

   public T getDefaultValue() {
      return this.field18917;
   }

   public void method08071(Supplier<Boolean> var1) {
      this.field18919 = var1;
   }

   public void setValue(T var1) {
      this.field18918 = (T)var1;
   }

   public T getValue() {
      return this.field18918;
   }

   public boolean isVisible() {
      return this.field18919.get();
   }

   public NamedOption<T> visibleSupplier(Supplier<Boolean> var1) {
      if (var1 != null) {
         this.field18919 = var1;
      }

      return this;
   }

   public static <R> NamedOption<R> createNamedOption(String var0, R var1) {
      return (NamedOption<R>)(new NamedOption<>(var0, var1));
   }

   private NamedOption(String var1, T var2) {
      this.field18916 = var1;
      this.field18917 = (T)var2;
      this.field18918 = (T)var2;
   }

   public void reset() {
      this.field18918 = this.field18917;
   }

   public Supplier<Boolean> method08079() {
      return this.field18919;
   }

   public String getName() {
      return this.field18916;
   }
}
