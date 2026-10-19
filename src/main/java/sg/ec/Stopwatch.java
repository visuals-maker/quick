// Original binary: sg/ec/9sz3LWHkDQyrKHCC
package sg.ec;

// $VF: Compiled from s
public class Stopwatch {
   private long field05957 = System.currentTimeMillis();

   public long method02436() {
      return this.field05957;
   }

   public void getElapsedTime(long var1) {
      this.field05957 = var1;
   }

   public boolean hasElapsed(long var1) {
      return System.currentTimeMillis() - this.field05957 > var1;
   }

   public void resetTimer() {
      this.field05957 = System.currentTimeMillis();
   }
}
