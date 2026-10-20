// Original binary: sg/ec/NokA56NmaoLZ9DZh
package quick.core;

// $VF: Compiled from s
public record WardenBotOptions(boolean useSpeed, boolean reportKillers, String lootPriority, int lootLimit, boolean debug) {
   public static final String PRIORITY_LOW = "Low";
   public static String PRIORITY_MEDIUM = "";
   public static String PRIORITY_HIGH = "";

   public boolean priorityIs(String var1) {
      return this.lootPriority.equals(var1);
   }
}
