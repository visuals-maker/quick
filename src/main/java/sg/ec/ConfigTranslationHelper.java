package sg.ec;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public final class ConfigTranslationHelper {
   private static final Map<String, String> RU_TO_EN = new HashMap<>();
   private static final Map<String, String> EN_TO_RU = new HashMap<>();
   private static boolean initialized = false;

   static {
      init();
   }

   public static synchronized void init() {
      if (initialized) {
         return;
      }
      try (InputStream in = ConfigTranslationHelper.class.getResourceAsStream("/translations.json")) {
         if (in != null) {
            try (InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
               JsonElement root = JsonParser.parseReader(reader);
               if (root != null && root.isJsonObject()) {
                  JsonObject obj = root.getAsJsonObject();
                  for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
                     String ru = entry.getKey().trim();
                     String en = entry.getValue().getAsString().trim();
                     if (!ru.isEmpty() && !en.isEmpty()) {
                        RU_TO_EN.put(ru, en);
                        EN_TO_RU.put(en, ru);
                     }
                  }
               }
            }
         }
      } catch (Exception ignored) {
      }
      initialized = true;
   }

   public static String toEnglish(String ru) {
      if (ru == null || ru.isBlank()) return ru;
      String mapped = RU_TO_EN.get(ru.trim());
      return mapped != null ? mapped : ru;
   }

   public static String toRussian(String en) {
      if (en == null || en.isBlank()) return en;
      String mapped = EN_TO_RU.get(en.trim());
      return mapped != null ? mapped : en;
   }

   public static JsonElement findElement(JsonObject obj, String key) {
      if (obj == null || key == null || key.isBlank()) return null;
      if (obj.has(key)) return obj.get(key);

      String ru = toRussian(key);
      if (ru != null && obj.has(ru)) return obj.get(ru);

      String en = toEnglish(key);
      if (en != null && obj.has(en)) return obj.get(en);

      for (String k : obj.keySet()) {
         if (k.equalsIgnoreCase(key) || (ru != null && k.equalsIgnoreCase(ru)) || (en != null && k.equalsIgnoreCase(en))) {
            return obj.get(k);
         }
      }
      return null;
   }

   public static JsonObject findModuleObject(JsonObject modulesObj, String moduleName) {
      if (modulesObj == null || moduleName == null) return null;
      String lower = moduleName.toLowerCase();
      if (modulesObj.has(lower) && modulesObj.get(lower).isJsonObject()) {
         return modulesObj.getAsJsonObject(lower);
      }
      if (modulesObj.has(moduleName) && modulesObj.get(moduleName).isJsonObject()) {
         return modulesObj.getAsJsonObject(moduleName);
      }

      String ru = toRussian(moduleName);
      if (ru != null) {
         String ruLower = ru.toLowerCase();
         if (modulesObj.has(ruLower) && modulesObj.get(ruLower).isJsonObject()) {
            return modulesObj.getAsJsonObject(ruLower);
         }
         if (modulesObj.has(ru) && modulesObj.get(ru).isJsonObject()) {
            return modulesObj.getAsJsonObject(ru);
         }
      }

      for (String k : modulesObj.keySet()) {
         if (k.equalsIgnoreCase(lower) || k.equalsIgnoreCase(moduleName) || (ru != null && (k.equalsIgnoreCase(ru) || k.equalsIgnoreCase(ru.toLowerCase())))) {
            JsonElement el = modulesObj.get(k);
            if (el != null && el.isJsonObject()) {
               return el.getAsJsonObject();
            }
         }
      }
      return null;
   }

   private ConfigTranslationHelper() {}
}
