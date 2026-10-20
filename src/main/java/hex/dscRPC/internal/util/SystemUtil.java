// Original binary: hex/dscRPC/internal/util/SystemUtil
package hex.dscRPC.internal.util;

import java.lang.management.ManagementFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;


// $VF: Compiled from s
public class SystemUtil {
   private static final String OS_NAME = System.getProperty(SystemUtil.衣ш);
   private static Long cachedProcessId = null;
   private static String 衣l = "";
   private static String 衣必 = "";
   private static String 衣З = "";
   private static String 衣н = "";
   private static String 衣Д = "";
   private static String 衣Н = "";
   private static String 衣ш = "";

   public static long getProcessId() {
      if (cachedProcessId != null) {
         return cachedProcessId;
      }

      String var0 = ManagementFactory.getRuntimeMXBean().getName();
      String sx = var0.split(衣Н)[0];
      long sxx = Long.parseLong(sx);
      cachedProcessId = sxx;
      return sxx;
   }

   private SystemUtil() {
   }

   public static Path getTemporaryDirectory() {
      String var0 = System.getenv(衣必);
      if (var0 != null) {
         return Paths.get(var0);
      }

      String sx = System.getenv(衣З);
      if (sx != null) {
         return Paths.get(sx);
      }

      String sxx = System.getenv(衣н);
      return sxx != null ? Paths.get(sxx) : Paths.get(衣Д);
   }

   static {
      }

   public static boolean isWindows() {
      return OS_NAME.toLowerCase(Locale.ROOT).startsWith(衣l);
   }
}
