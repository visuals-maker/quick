const fs = require('fs');

// 1. Update ModuleConfigStorage.java
let mcs = fs.readFileSync('src/main/java/sg/ec/ModuleConfigStorage.java', 'utf8');

const m4442Old = /private static boolean method04442\(JsonObject var0\) \{[\s\S]*?return true;\s*\} catch \(Exception var9\) \{\s*return false;\s*\}\s*\}/;
const m4442New = `private static boolean method04442(JsonObject var0) {
      try {
         method04439();
         JsonObject var1 = var0.getAsJsonObject(field09911);
         if (var1 == null) {
            for (String k : var0.keySet()) {
               if (k.equalsIgnoreCase("modules") && var0.get(k).isJsonObject()) {
                  var1 = var0.getAsJsonObject(k);
                  break;
               }
            }
         }
         if (var1 == null) {
            return false;
         }

         for (ToggleableModule var3 : ModuleManager.INSTANCE.method05138()) {
            JsonObject var5 = ConfigTranslationHelper.findModuleObject(var1, var3.getName());
            if (var5 != null) {
               if (var5.has(field09912)) {
                  var3.setEnabled(var5.get(field09913).getAsBoolean());
               }

               if (var5.has(field09914)) {
                  JsonElement var6 = var5.get(field09915);
                  if (var6.isJsonPrimitive()) {
                     if (var6.getAsJsonPrimitive().isString()) {
                        var3.getBinding().method05872(KeyboardKey.method00361(var6.getAsString()));
                     } else if (var6.getAsJsonPrimitive().isNumber()) {
                        var3.getBinding().method05872(var6.getAsInt());
                     }
                  }
               }

               if (var5.has(field09916)) {
                  try {
                     var3.getBinding().method05870(KeyBindingMode.valueOf(var5.get(field09917).getAsString()));
                  } catch (Exception var8) {
                  }
               }

               for (Setting var7 : var3.getSettings()) {
                  method04446(var5, var7);
               }
            }
         }

         return true;
      } catch (Exception var9) {
         return false;
      }
   }`;

if (m4442Old.test(mcs)) {
  mcs = mcs.replace(m4442Old, m4442New);
  console.log('method04442 replaced successfully');
} else {
  console.error('method04442 match failed!');
}

const m4446Old = /private static void method04446\(JsonObject var0, Setting<\?> var1\) \{[\s\S]*?var11\.method00871\(ItemFilterSetting\.Nested29\.parse\(method04438\(var20, field09953\), method04438\(var20, field09954\), method04438\(var20, field09955\)\)\);\s*\}\s*\}\s*\}/;
const m4446New = `private static void method04446(JsonObject var0, Setting<?> var1) {
      JsonElement var2 = ConfigTranslationHelper.findElement(var0, var1.getDisplayName());
      if (var2 == null) {
         var2 = ConfigTranslationHelper.findElement(var0, var1.getName());
      }
      if (var2 != null && !var2.isJsonNull()) {
         if (var1 instanceof NumberSetting var3 && var2.isJsonPrimitive()) {
            var3.method10837(var2.getAsFloat());
         } else if (var1 instanceof SliderSetting var4 && var2.isJsonPrimitive()) {
            var4.setValue(var2.getAsFloat());
         } else if (var1 instanceof ColorSetting var5 && var2.isJsonPrimitive()) {
            var5.method11672(new Color(var2.getAsInt()));
         } else if (var1 instanceof ModeSetting var6 && var2.isJsonPrimitive()) {
            String rawVal = var2.getAsString();
            String enVal = ConfigTranslationHelper.toEnglish(rawVal);
            boolean matched = false;
            for (String mode : var6.method05598()) {
               if (mode.equalsIgnoreCase(enVal) || mode.equalsIgnoreCase(rawVal)) {
                  var6.method05603(mode);
                  matched = true;
                  break;
               }
            }
            if (!matched) {
               var6.method05603(rawVal);
            }
         } else if (var1 instanceof KeyBindSetting var7) {
            KeyBinding var12 = var7.getValue();
            if (var2.isJsonObject()) {
               JsonObject var13 = var2.getAsJsonObject();
               if (var13.has(field09935)) {
                  JsonElement var14 = var13.get(field09936);
                  if (var14.isJsonPrimitive() && var14.getAsJsonPrimitive().isString()) {
                     var12.method05872(KeyboardKey.method00361(var14.getAsString()));
                  } else if (var14.isJsonPrimitive() && var14.getAsJsonPrimitive().isNumber()) {
                     var12.method05872(var14.getAsInt());
                  }
               }

               if (var13.has(field09937)) {
                  try {
                     var12.method05870(KeyBindingMode.valueOf(var13.get(field09938).getAsString()));
                  } catch (Exception var18) {
                  }
               }

               if (var13.has(field09939)) {
                  var12.method05875(var13.get(field09940).getAsBoolean());
               }
            } else if (var2.isJsonPrimitive()) {
               if (var2.getAsJsonPrimitive().isString()) {
                  var12.method05872(KeyboardKey.method00361(var2.getAsString()));
               } else if (var2.getAsJsonPrimitive().isNumber()) {
                  var12.method05872(var2.getAsInt());
               }
            }
         } else if (var1 instanceof ToggleSetting var8) {
            if (var2.isJsonObject() && var2.getAsJsonObject().has(field09941)) {
               var8.setValue(var2.getAsJsonObject().get(field09942).getAsBoolean());
            } else if (var2.isJsonPrimitive()) {
               var8.setValue(var2.getAsBoolean());
            }

            if (var2.isJsonObject() && var2.getAsJsonObject().has(field09943)) {
               var8.getBinding().method05872(var2.getAsJsonObject().get(field09944).getAsInt());
            }
         } else if (var1 instanceof BooleanSetting var9) {
            if (var2.isJsonObject() && var2.getAsJsonObject().has(field09945)) {
               var9.setValue(var2.getAsJsonObject().get(field09946).getAsBoolean());
            } else if (var2.isJsonPrimitive()) {
               var9.setValue(var2.getAsBoolean());
            }

            if (var2.isJsonObject() && var2.getAsJsonObject().has(field09947)) {
               var9.method04704().method05872(var2.getAsJsonObject().get(field09948).getAsInt());
            }
         } else if (var1 instanceof MultiToggleSetting var10) {
            if (var2.isJsonArray()) {
               for (JsonElement var23 : var2.getAsJsonArray()) {
                  if (var23.isJsonObject()) {
                     JsonObject var15 = var23.getAsJsonObject();
                     if (var15.has(field09949)) {
                        String var16 = var15.get(field09950).getAsString();
                        boolean var17 = var15.has(field09951) && var15.get(field09952).getAsBoolean();
                        if (!var10.method04519(var16, var17)) {
                           String enOpt = ConfigTranslationHelper.toEnglish(var16);
                           if (!var10.method04519(enOpt, var17)) {
                              for (String optName : var10.method04515().keySet()) {
                                 if (optName.equalsIgnoreCase(var16) || optName.equalsIgnoreCase(enOpt)) {
                                    var10.method04519(optName, var17);
                                    break;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            } else if (var2.isJsonObject()) {
               JsonObject var22 = var2.getAsJsonObject();
               for (String var25 : var22.keySet()) {
                  JsonElement var26 = var22.get(var25);
                  if (var26 != null && var26.isJsonPrimitive()) {
                     boolean var17 = var26.getAsBoolean();
                     if (!var10.method04519(var25, var17)) {
                        String enOpt = ConfigTranslationHelper.toEnglish(var25);
                        if (!var10.method04519(enOpt, var17)) {
                           for (String optName : var10.method04515().keySet()) {
                              if (optName.equalsIgnoreCase(var25) || optName.equalsIgnoreCase(enOpt)) {
                                 var10.method04519(optName, var17);
                                 break;
                              }
                           }
                        }
                     }
                  }
               }
            }
         } else if (var1 instanceof ItemFilterSetting var11 && var2.isJsonObject()) {
            JsonObject var20 = var2.getAsJsonObject();
            var11.method00871(ItemFilterSetting.Nested29.parse(method04438(var20, field09953), method04438(var20, field09954), method04438(var20, field09955)));
         }
      }
   }`;

if (m4446Old.test(mcs)) {
  mcs = mcs.replace(m4446Old, m4446New);
  console.log('method04446 replaced successfully');
} else {
  console.error('method04446 match failed!');
}
fs.writeFileSync('src/main/java/sg/ec/ModuleConfigStorage.java', mcs, 'utf8');

// 2. Update StoredContentImporter.java
let sci = fs.readFileSync('src/main/java/sg/ec/StoredContentImporter.java', 'utf8');
const sciOld = /public static boolean method07240\(StorageCategory var0, String var1, byte\[\] var2\) \{[\s\S]*?return true;\s*\}/;
const sciNew = `public static boolean method07240(StorageCategory var0, String var1, byte[] var2) {
      if (var0 == null || var2 == null || var2.length == 0) {
         return false;
      }

      if (var0 == StorageCategory.PROXY) {
         return ProxyStorage.method07614(var1, CompressedFrameCodec.method00058(var2));
      }

      byte[] decompressed = CompressedFrameCodec.method00058(var2);
      method07241(var0, decompressed);
      if (var0 == StorageCategory.CONFIG) {
         ActiveConfigTracker.field06857.method03062(var1);
         ModuleConfigStorage.method04450(decompressed);
         try {
            java.io.File customDir = CustomResourceManager.INSTANCE.field05980;
            if (!customDir.exists()) {
               customDir.mkdirs();
            }
            java.io.File localFile = new java.io.File(customDir, var1 + ".file");
            java.nio.file.Files.write(localFile.toPath(), decompressed);
         } catch (Exception ignored) {
         }
      }

      return true;
   }`;

if (sciOld.test(sci)) {
  sci = sci.replace(sciOld, sciNew);
  fs.writeFileSync('src/main/java/sg/ec/StoredContentImporter.java', sci, 'utf8');
  console.log('StoredContentImporter updated successfully');
} else {
  console.error('StoredContentImporter match failed!');
}

// 3. Update GlobalPacketHandlerImpl.java
let gph = fs.readFileSync('src/main/java/sg/ec/GlobalPacketHandlerImpl.java', 'utf8');
gph = gph.replace('ChatTextUtils.method05101(field09752, "Uploaded:" + var1.method03838());', 'ChatTextUtils.method05101(field09752, "Config \\"" + var1.method03838() + "\\" loaded from cloud.");');
fs.writeFileSync('src/main/java/sg/ec/GlobalPacketHandlerImpl.java', gph, 'utf8');
console.log('GlobalPacketHandlerImpl updated successfully');
