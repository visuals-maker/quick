// Original binary: sg/ec/MEOzoJQACGBd7zpj
package quick.setting;

import sg.ec.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// $VF: Compiled from s
public class MultiToggleSetting extends Setting<List<NamedOption<Boolean>>> {
   private int field10041 = 0;
   private final Map<String, NamedOption<Boolean>> field10042 = new HashMap<>();

   @Override
   public void reset() {
      for (NamedOption var2 : this.getValue()) {
         var2.reset();
      }
   }

   public int getSelectedCount() {
      int var1 = 0;

      for (NamedOption var3 : this.getValue()) {
         if (Boolean.TRUE.equals(var3.getValue())) {
            var1++;
         }
      }

      return var1;
   }

   public Map<String, NamedOption<Boolean>> getOptionsMap() {
      return this.field10042;
   }

   public <S extends Setting<List<NamedOption<Boolean>>>> S method04516(List<NamedOption<Boolean>> var1) {
      if (this.getDefaultValue() == null && this.getValue() == null) {
         return super.setDefaultValue(var1);
      } else {
         throw new UnsupportedOperationException("Default value not supported for MultiSelecting (" + this.getClass().getName() + ")");
      }
   }

   public MultiToggleSetting addOption(NamedOption<Boolean> var1) {
      this.getValue().add(var1);
      this.field10042.put(var1.getName(), var1);
      return this;
   }

   public <S extends Setting<List<NamedOption<Boolean>>>> S setOptions(List<NamedOption<Boolean>> var1) {
      if (this.getValue() != null) {
         throw new UnsupportedOperationException("Setting the value is not supported for not supported for MultiSelecting (" + this.getClass().getName() + ")");
      } else {
         return super.setValue(var1);
      }
   }

   public boolean setOptionState(String var1, boolean var2) {
      NamedOption var3 = this.field10042.get(var1);
      if (var3 == null) {
         return false;
      }

      var3.setValue(var2);
      return true;
   }

   public MultiToggleSetting(String var1) {
      super(var1);
      this.setOptions(new ArrayList<>());
   }

   public boolean isOptionSelected(String var1) {
      NamedOption var2 = this.field10042.get(var1);
      return var2 == null ? false : Boolean.TRUE.equals(var2.getValue());
   }

   public MultiToggleSetting method04521(int var1) {
      this.field10041 = Math.max(0, var1);
      return this;
   }

   public int method04522() {
      return this.field10041;
   }

   public Optional<NamedOption<Boolean>> method04523(String var1) {
      return Optional.ofNullable(this.field10042.get(var1));
   }
}
