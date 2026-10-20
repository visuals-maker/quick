// Original binary: sg/ec/TwoAR2kbhzKkF77Y
package quick.core;

// $VF: Compiled from s
public abstract class Event {
   private boolean cancelled = false;
   public EventBus.Nested10 listenerCache;

   public void post() {
      EventListener[] var1 = EventBus.getListeners(this);

      for (int sx = 0; sx < var1.length; sx++) {
         var1[sx].fire(this);
      }
   }

   protected boolean method05694() {
      if (!this.cancelled) {
         return false;
      }

      this.cancelled = false;
      return true;
   }

   public void setCancelled(boolean var1) {
      this.cancelled = var1;
   }

   public void cancel() {
      this.cancelled = true;
   }

   public boolean isCancelled() {
      return this.cancelled;
   }

   public final boolean method05698() {
      return EventBus.getListeners(this).length != 0;
   }
}
