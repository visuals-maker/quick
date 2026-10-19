// Original binary: sg/ec/EFCUzPtBZwDzTVC2
package sg.ec;

// $VF: Compiled from s
public class TickEvent extends Event {
   public static final TickEvent field07044 = new TickEvent();
   private static int field07045;

   @Override
   public void post() {
      field07045++;
      super.post();
   }

   public static int method03209() {
      return field07045;
   }
}
