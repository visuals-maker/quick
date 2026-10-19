// Original binary: sg/ec/CnCRyRTsaJon0uoO
package sg.ec;

import java.util.function.Supplier;

// $VF: Compiled from s
public abstract class Setting<T> {
   private final String name;
   private String id;
   private String description = "";
   private SettingsContainer container;
   private T value;
   private T defaultValue;
   private Supplier<Boolean> visibleSupplier = () -> true;

   public String getDisplayName() {
      return this.id != null && !this.id.isEmpty() ? this.id : this.name;
   }

   public Setting(String var1) {
      this.container = this.container;
      this.name = var1;
   }

   public void reset() {
      if (this.defaultValue != null) {
         this.setValue(this.defaultValue);
      }
   }

   public String getId() {
      return this.id;
   }

   protected void method03010(T var1) {
      this.defaultValue = (T)var1;
   }

   public SettingsContainer getContainer() {
      return this.container;
   }

   public String getDescription() {
      return this.description;
   }

   public Supplier<Boolean> getVisibleSupplier() {
      return this.visibleSupplier;
   }

   public <S extends Setting<T>> S setValue(T var1) {
      if (var1 == null) {
         return (S)this;
      }

      this.method00870((T)var1);
      return (S)this;
   }

   public T getDefaultValue() {
      return this.defaultValue;
   }

   public <S extends Setting<T>> S setId(String var1) {
      this.id = var1;
      return (S)this;
   }

   protected void method00870(T var1) {
      this.value = (T)var1;
   }

   public <S extends Setting<T>> S setDefaultValue(T var1) {
      if (var1 != null && this.defaultValue == null) {
         this.method03010((T)var1);
         this.method00870((T)var1);
         return (S)this;
      } else {
         return (S)this;
      }
   }

   public <S extends Setting<T>> S setDescription(String var1) {
      this.description = var1;
      return (S)this;
   }

   public boolean isVisible() {
      return this.visibleSupplier.get();
   }

   public void method03020(String var1) {
      this.id = var1;
   }

   public T getValue() {
      return this.value;
   }

   public <S extends Setting<T>> S setContainer(SettingsContainer var1) {
      if (var1 != null && this.container == null) {
         this.container = var1;
         this.container.getSettings().add(this);
         return (S)this;
      } else {
         return (S)this;
      }
   }

   public String getName() {
      return this.name;
   }

   public <S extends Setting<T>> S setVisibleSupplier(Supplier<Boolean> var1) {
      this.visibleSupplier = var1;
      return (S)this;
   }
}
