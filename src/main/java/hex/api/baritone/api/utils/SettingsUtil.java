// Original binary: hex/api/baritone/api/utils/SettingsUtil
package hex.api.baritone.api.utils;

import hex.api.baritone.api.BaritoneAPI;
import hex.api.baritone.api.Settings;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Vec3i;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;


// $VF: Compiled from s
public class SettingsUtil {
   public static final String SETTINGS_DEFAULT_NAME = "settings.txt";
   private static final Pattern SETTING_PATTERN = Pattern.compile(SettingsUtil.SETTING_PATTERN_SOURCE);
   private static final String COMMENT_PREFIX = "#";
   private static final String COMMENT_ALT_PREFIX = "//";
   private static final String SETTINGS_FILE_NOT_FOUND_MESSAGE = "Baritone settings file not found, resetting.";
   private static final String SETTINGS_READ_ERROR_MESSAGE = "Exception while reading Baritone settings, some settings may be reset to default values!";
   private static final String SETTINGS_FILE_NAME = "settings.txt";
   private static final String SETTINGS_SAVE_ERROR_MESSAGE = "Exception thrown while saving Baritone settings!";
   private static final String BARITONE_DIRECTORY = "baritone";
   private static final String PACKAGE_NAME_PATTERN = "(?:\\w+\\.)+(\\w+)";
   private static final String PACKAGE_NAME_REPLACEMENT = "$1";
   private static final String CENSORED_COORDINATE = "<censored>";
   private static final String UNKNOWN_SETTING_MESSAGE = "No setting by that name";
   private static final String SETTING_GROUP = "setting";
   private static final String VALUE_GROUP = "value";
   private static final String LEGACY_JUMP_SETTING = "allowjumpat256";
   private static final String BUILD_LIMIT_SETTING = "allowjumpatbuildlimit";
   private static final String SETTING_PATTERN_SOURCE = "^(?<setting>[^ ]+) +(?<value>.+)";

   public static String settingDefaultToString(Settings.Setting var0) throws IllegalArgumentException {
      return settingValueToString(var0, var0.defaultValue);
   }

   public static String settingTypeToString(Settings.Setting var0) {
      return var0.getType().getTypeName().replaceAll(PACKAGE_NAME_PATTERN, PACKAGE_NAME_REPLACEMENT);
   }

   public static void readAndApply(Settings var0, String var1) {
      try {
         forEachLine(settingsByName(var1), var1x -> {
            Matcher var2 = SETTING_PATTERN.matcher(var1x);
            if (!var2.matches()) {
               Helper.HELPER.logDirect("Invalid syntax in setting file: " + var1x);
            } else {
               String sx = var2.group(SETTING_GROUP).toLowerCase();
               String sxx = var2.group(VALUE_GROUP);
               if (LEGACY_JUMP_SETTING.equals(sx)) {
                  sx = BUILD_LIMIT_SETTING;
               }

               try {
                  parseAndApply(var0, sx, sxx);
               } catch (Exception sxxx) {
                  Helper.HELPER.logDirect("Unable to parse line " + var1x);
               }
            }
         });
      } catch (NoSuchFileException var3) {
         Helper.HELPER.logDirect(SETTINGS_FILE_NOT_FOUND_MESSAGE);
      } catch (Exception var4) {
         Helper.HELPER.logDirect(SETTINGS_READ_ERROR_MESSAGE);
      }
   }

   public static void parseAndApply(Settings var0, String var1, String var2) throws IllegalStateException, NumberFormatException {
      Settings.Setting var3 = var0.byLowerName.get(var1);
      if (var3 == null) {
         throw new IllegalStateException(UNKNOWN_SETTING_MESSAGE);
      }

      Class sx = var3.getValueClass();
      SettingsUtil.ISettingParser sxx = SettingsUtil.Parser.getParser(var3.getType());
      Object sxxx = sxx.parse(var3.getType(), var2);
      if (!sx.isInstance(sxxx)) {
         throw new IllegalStateException(sxx + " parser returned incorrect type, expected " + sx + " got " + sxxx + " which is " + sxxx.getClass());
      }

      var3.value = sxxx;
   }

   public static String settingToString(Settings.Setting var0) throws IllegalStateException {
      return var0.isJavaOnly() ? var0.getName() : var0.getName() + " " + settingValueToString(var0);
   }

   @Deprecated
   public static boolean javaOnlySetting(Settings.Setting var0) {
      return var0.isJavaOnly();
   }

   public static List<Settings.Setting> modifiedSettings(Settings var0) {
      List var1 = new ArrayList<>();

      for (Settings.Setting sx : var0.allSettings) {
         if (sx.value != null && !sx.isJavaOnly() && sx.value != sx.defaultValue) {
            var1.add(sx);
         }
      }

      return var1;
   }

   public static synchronized void save(Settings var0) {
      try (BufferedWriter var1 = Files.newBufferedWriter(settingsByName(SETTINGS_FILE_NAME))) {
         for (Settings.Setting sx : modifiedSettings(var0)) {
            var1.write(settingToString(sx) + "\n");
         }
      } catch (Exception var6) {
         Helper.HELPER.logDirect(SETTINGS_SAVE_ERROR_MESSAGE);
      }
   }

   public static String maybeCensor(int var0) {
      return BaritoneAPI.getSettings().censorCoordinates.value ? CENSORED_COORDINATE : Integer.toString(var0);
   }

   private static Path settingsByName(String var0) {
      return Minecraft.getInstance().gameDirectory.toPath().resolve(BARITONE_DIRECTORY).resolve(var0);
   }

   public static String settingValueToString(Settings.Setting var0) throws IllegalArgumentException {
      return settingValueToString(var0, var0.value);
   }

   private static void forEachLine(Path var0, Consumer<String> var1) throws IOException {
      String var3;
      try (BufferedReader sx = Files.newBufferedReader(var0)) {
         while ((var3 = sx.readLine()) != null) {
            if (!var3.isEmpty() && !isComment(var3)) {
               var1.accept(var3);
            }
         }
      }
   }

   static {
      }

   private static boolean isComment(String var0) {
      return var0.startsWith(COMMENT_PREFIX) || var0.startsWith(COMMENT_ALT_PREFIX);
   }

   public static <T> String settingValueToString(Settings.Setting<T> var0, T var1) throws IllegalArgumentException {
      SettingsUtil.Parser var2 = SettingsUtil.Parser.getParser(var0.getType());
      if (var2 == null) {
         throw new IllegalStateException("Missing " + var0.getValueClass() + " " + var0.getName());
      } else {
         return var2.toString(var0.getType(), var1);
      }
   }

   // $VF: Compiled from s
   private interface ISettingParser<T> {
      boolean accepts(Type var1);

      String toString(Type var1, T var2);

      T parse(Type var1, String var2);
   }

   // $VF: Compiled from s
   private enum Parser implements SettingsUtil.ISettingParser {
      DOUBLE(Double.class, Double::parseDouble),
      BOOLEAN(Boolean.class, Boolean::parseBoolean),
      INTEGER(Integer.class, Integer::parseInt),
      FLOAT(Float.class, Float::parseFloat),
      LONG(Long.class, Long::parseLong),
      STRING(String.class, String::new),
      MIRROR(Mirror.class, Mirror::valueOf, Enum::name),
      ROTATION(net.minecraft.world.level.block.Rotation.class, net.minecraft.world.level.block.Rotation::valueOf, Enum::name),
      COLOR(
         Color.class,
         var0 -> new Color(Integer.parseInt(var0.split(",")[0]), Integer.parseInt(var0.split(",")[1]), Integer.parseInt(var0.split(",")[2])),
         var0 -> var0.getRed() + "," + var0.getGreen() + "," + var0.getBlue()
      ),
      VEC3I(
         Vec3i.class,
         var0 -> new Vec3i(Integer.parseInt(var0.split(",")[0]), Integer.parseInt(var0.split(",")[1]), Integer.parseInt(var0.split(",")[2])),
         var0 -> var0.getX() + "," + var0.getY() + "," + var0.getZ()
      ),
      BLOCK(Block.class, var0 -> BlockUtils.stringToBlockRequired(var0.trim()), BlockUtils::blockToString),
      ITEM(
         Item.class,
         var0 -> BuiltInRegistries.ITEM.get(Identifier.parse(var0.trim())).map(Reference::value).orElse(null),
         var0 -> BuiltInRegistries.ITEM.getKey(var0).toString()
      ),
      LIST      // $VF: Compiled from s
 {
          private static final String LIST_SEPARATOR = ",";
          private static final String LIST_JOINER = ",";

         @Override
         public Object parse(Type var1, String var2) {
            Type var3 = ((ParameterizedType)var1).getActualTypeArguments()[0];
            SettingsUtil.Parser sx = SettingsUtil.Parser.getParser(var3);
            return Stream.of(var2.split(LIST_SEPARATOR)).map(var2x -> sx.parse(var3, var2x)).collect(Collectors.toList());
         }

         @Override
         public String toString(Type var1, Object var2) {
            Type var3 = ((ParameterizedType)var1).getActualTypeArguments()[0];
            SettingsUtil.Parser sx = SettingsUtil.Parser.getParser(var3);
            return ((List<?>)var2).stream().map(var2x -> sx.toString(var3, var2x)).collect(Collectors.joining(LIST_JOINER));
         }

         @Override
         public boolean accepts(Type var1) {
            return List.class.isAssignableFrom(TypeUtils.resolveBaseClass(var1));
         }

         static {
            }
      },
      MAPPING      // $VF: Compiled from s
 {
          private static final String MAPPING_SPLIT_PATTERN = ",(?=[^,]*->)";
          private static final String MAPPING_ENTRY_JOINER = ",";
          private static final String MAPPING_ENTRY_SEPARATOR = "->";

         @Override
         public Object parse(Type var1, String var2) {
            Type var3 = ((ParameterizedType)var1).getActualTypeArguments()[0];
            Type sx = ((ParameterizedType)var1).getActualTypeArguments()[1];
            SettingsUtil.Parser sxx = SettingsUtil.Parser.getParser(var3);
            SettingsUtil.Parser sxxx = SettingsUtil.Parser.getParser(sx);
            return Stream.of(var2.split(MAPPING_SPLIT_PATTERN))
               .map(var0 -> var0.split(MAPPING_ENTRY_SEPARATOR))
               .collect(Collectors.toMap(var2x -> sxx.parse(var3, var2x[0]), var2x -> sxxx.parse(sx, var2x[1])));
         }

         @Override
         public String toString(Type var1, Object var2) {
            Type var3 = ((ParameterizedType)var1).getActualTypeArguments()[0];
            Type sx = ((ParameterizedType)var1).getActualTypeArguments()[1];
            SettingsUtil.Parser sxx = SettingsUtil.Parser.getParser(var3);
            SettingsUtil.Parser sxxx = SettingsUtil.Parser.getParser(sx);
            return ((Map<?, ?>)var2)
               .entrySet()
               .stream()
               .map(var4x -> sxx.toString(var3, var4x.getKey()) + "->" + sxxx.toString(sx, var4x.getValue()))
               .collect(Collectors.joining(MAPPING_ENTRY_JOINER));
         }

         @Override
         public boolean accepts(Type var1) {
            return Map.class.isAssignableFrom(TypeUtils.resolveBaseClass(var1));
         }

         static {
            }
      };

      private final Class<?> cla$$;
      private final Function<String, Object> parser;
      private final Function<Object, String> toString;

      Parser() {
         this.cla$$ = null;
         this.parser = null;
         this.toString = null;
      }

      <T> Parser(Class<T> var3, Function<String, T> var4) {
         this(var3, var4, Object::toString);
      }

      <T> Parser(Class<T> var3, Function<String, T> var4, Function<T, String> var5) {
         this.cla$$ = var3;
         this.parser = var4::apply;
         this.toString = var1x -> var5.apply((T)var1x);
      }

      @Override
      public Object parse(Type var1, String var2) {
         Object var3 = this.parser.apply(var2);
         Objects.requireNonNull(var3);
         return var3;
      }

      @Override
      public String toString(Type var1, Object var2) {
         return this.toString.apply(var2);
      }

      @Override
      public boolean accepts(Type var1) {
         return var1 instanceof Class && this.cla$$.isAssignableFrom((Class<?>)var1);
      }

      public static SettingsUtil.Parser getParser(Type var0) {
         return Stream.of(values()).filter(var1 -> var1.accepts(var0)).findFirst().orElse(null);
      }
   }
}
