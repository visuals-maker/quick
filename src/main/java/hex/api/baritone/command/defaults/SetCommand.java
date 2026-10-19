// Original binary: hex/api/baritone/command/defaults/SetCommand
package hex.api.baritone.command.defaults;

import hex.api.baritone.Baritone;
import hex.api.baritone.api.IBaritone;
import hex.api.baritone.api.Settings;
import hex.api.baritone.api.command.Command;
import hex.api.baritone.api.command.IBaritoneChatControl;
import hex.api.baritone.api.command.argument.IArgConsumer;
import hex.api.baritone.api.command.datatypes.RelativeFile;
import hex.api.baritone.api.command.exception.CommandException;
import hex.api.baritone.api.command.exception.CommandInvalidStateException;
import hex.api.baritone.api.command.exception.CommandInvalidTypeException;
import hex.api.baritone.api.command.helpers.Paginator;
import hex.api.baritone.api.command.helpers.TabCompleteHelper;
import hex.api.baritone.api.utils.SettingsUtil;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.ClickEvent.RunCommand;
import net.minecraft.network.chat.ClickEvent.SuggestCommand;
import net.minecraft.network.chat.HoverEvent.ShowText;
import ru.dreamix.fabricloader.VMBridge;

// $VF: Compiled from s
public class SetCommand extends Command {
   private static final String cmdSet = "set";
   private static final String cmdSetting = "setting";
   private static final String cmdSettings = "settings";
   private static final String defaultArg = "list";
   private static final String saveAlias = "s";
   private static final String saveArg = "save";
   private static final String savedMsg = "Settings saved";
   private static final String loadArg = "load";
   private static final String loadAlias = "ld";
   private static final String settingsFileName = SettingsUtil.SETTINGS_DEFAULT_NAME;
   private static final String modifiedAlias = "m";
   private static final String modifiedAlias2 = "mod";
   private static final String modifiedArg = "modified";
   private static final String allArg = "all";
   private static final String listAlias = "l";
   private static final String listArg = "list";
   private static final String resetArg = "reset";
   private static final String toggleArg = "toggle";
   private static final String resetHelp1 = "Please specify 'all' as an argument to reset to confirm you'd really like to do this";
   private static final String resetHelp2 = "ALL settings will be reset. Use the 'set modified' or 'modified' commands to see what will be reset.";
   private static final String resetHelp3 = "Specify a setting name instead of 'all' to only reset one setting";
   private static final String allToken = "all";
   private static final String allResetMsg = "All settings have been reset to their default values";
   private static final String errValidSetting = "a valid setting";
   private static final String errApiOnly = "Setting %s can only be used via the api.";
   private static final String valueOfFmt = "Value of setting %s:";
   private static final String errToggleable = "a toggleable setting";
   private static final String errSomeOther = "some other setting";
   private static final String toggledFmt = "Toggled setting %s to %s";
   private static final String errValidValue = "a valid value";
   private static final String successFmt = "Successfully %s %s to %s";
   private static final String resetWord = "reset";
   private static final String setWord = "set";
   private static final String oldValueFmt = "Old value: %s";
   private static final String revertHint = "Click to set the setting back to this value";
   private static final String setOldFmt = "set %s %s";
   private static final String chatControlName = "chatControl";
   private static final String chatWarnMsg = "Warning: Chat commands will no longer work. If you want to revert this change, use prefix control (if enabled) or click the old value listed above.";
   private static final String saveAlias2 = "s";
   private static final String saveArg2 = "save";
   private static final String resetArg2 = "reset";
   private static final String allArg2 = "all";
   private static final String toggleArg2 = "toggle";
   private static final String loadAlias2 = "ld";
   private static final String loadArg2 = "load";
   private static final String baritoneDir = "baritone";
   private static final String trueArg = "true";
   private static final String falseArg = "false";
   private static final String falseArg2 = "false";
   private static final String trueArg2 = "true";
   private static final String tabList = "list";
   private static final String tabModified = "modified";
   private static final String tabReset = "reset";
   private static final String tabToggle = "toggle";
   private static final String tabSave = "save";
   private static final String tabLoad = "load";
   private static final String shortDesc = "View or change settings";
   private static final String longDescIntro = "Using the set command, you can manage all of Baritone's settings. Almost every aspect is controlled by these settings - go wild!";
   private static final String longDescUsage = "Usage:";
   private static final String longDesc1 = "> set - Same as `set list`";
   private static final String longDesc2 = "> set list [page] - View all settings";
   private static final String longDesc3 = "> set modified [page] - View modified settings";
   private static final String longDesc4 = "> set <setting> - View the current value of a setting";
   private static final String longDesc5 = "> set <setting> <value> - Set the value of a setting";
   private static final String longDesc6 = "> set reset all - Reset ALL SETTINGS to their defaults";
   private static final String longDesc7 = "> set reset <setting> - Reset a setting to its default";
   private static final String longDesc8 = "> set toggle <setting> - Toggle a boolean setting";
   private static final String longDesc9 = "> set save - Save all settings (this is automatic tho)";
   private static final String longDesc10 = "> set load - Load settings from settings.txt";
   private static final String longDesc11 = "> set load [filename] - Load settings from another file in your minecraft/baritone";
   private static final String typeFmt = " (%s)";
   private static final String hoverTypeFmt = "\nType: %s";
   private static final String hoverValueFmt = "\n\nValue:\n%s";
   private static final String hoverDefaultFmt = "\n\nDefault Value:\n%s";
   private static final String setSuggestFmt = "set %s ";
   private static final String allSearchFmt = "All %ssettings containing the string '%s':";
   private static final String modifiedPrefix = "modified ";
   private static final String allSettingsFmt = "All %ssettings:";
   private static final String modifiedPrefix2 = "modified ";

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) throws CommandException {
      if (var2.hasAny()) {
         String var3 = var2.getString();
         if (var2.hasExactlyOne() && !Arrays.asList(saveAlias2, saveArg2).contains(var2.peekString().toLowerCase(Locale.US))) {
            if (var3.equalsIgnoreCase(resetArg2)) {
               return new TabCompleteHelper().addModifiedSettings().prepend(allArg2).filterPrefix(var2.getString()).stream();
            }

            if (var3.equalsIgnoreCase(toggleArg2)) {
               return new TabCompleteHelper().addToggleableSettings().filterPrefix(var2.getString()).stream();
            }

            if (Arrays.asList(loadAlias2, loadArg2).contains(var3.toLowerCase(Locale.US))) {
               return RelativeFile.tabComplete(var2, Minecraft.getInstance().gameDirectory.toPath().resolve(baritoneDir).toFile());
            }

            Settings.Setting sx = Baritone.settings().byLowerName.get(var3.toLowerCase(Locale.US));
            if (sx != null) {
               if (sx.getType() == Boolean.class) {
                  TabCompleteHelper sxx = new TabCompleteHelper();
                  if ((Boolean)sx.value) {
                     sxx.append(trueArg, falseArg);
                  } else {
                     sxx.append(falseArg2, trueArg2);
                  }

                  return sxx.filterPrefix(var2.getString()).stream();
               }

               return Stream.of(SettingsUtil.settingValueToString(sx));
            }
         } else if (!var2.hasAny()) {
            return new TabCompleteHelper().addSettings().sortAlphabetically().prepend(tabList, tabModified, tabReset, tabToggle, tabSave, tabLoad).filterPrefix(var3).stream();
         }
      }

      return Stream.empty();
   }

   @Override
   public void execute(String var1, IArgConsumer var2) throws CommandException {
      String var3 = var2.hasAny() ? var2.getString().toLowerCase(Locale.US) : defaultArg;
      if (Arrays.asList(saveAlias, saveArg).contains(var3)) {
         SettingsUtil.save(Baritone.settings());
         this.logDirect(savedMsg);
      } else if (Arrays.asList(loadArg, loadAlias).contains(var3)) {
         String sx = settingsFileName;
         if (var2.hasAny()) {
            sx = var2.getString();
         }

         SettingsUtil.modifiedSettings(Baritone.settings()).forEach(Settings.Setting::reset);
         SettingsUtil.readAndApply(Baritone.settings(), sx);
         this.logDirect("Settings reloaded from " + sx);
      } else {
         boolean sx = Arrays.asList(modifiedAlias, modifiedAlias2, modifiedArg).contains(var3);
         boolean sxx = Arrays.asList(allArg, listAlias, listArg).contains(var3);
         boolean sxxx = sx || sxx;
         if (sxxx) {
            String sxxxx = var2.hasAny() && var2.peekAsOrNull(Integer.class) == null ? var2.getString() : "";
            var2.requireMax(1);
            List<Settings.Setting<?>> sxxxxx = (List<Settings.Setting<?>>)(sx ? SettingsUtil.modifiedSettings(Baritone.settings()) : Baritone.settings().allSettings)
               .stream()
               .filter(var0 -> !var0.isJavaOnly())
               .filter(var1x -> var1x.getName().toLowerCase(Locale.US).contains(sxxxx.toLowerCase(Locale.US)))
               .sorted((var0, var1x) -> String.CASE_INSENSITIVE_ORDER.compare(var0.getName(), var1x.getName()))
               .collect(Collectors.toList());
            Paginator.paginate(
               var2,
               new Paginator<>(sxxxxx),
               () -> this.logDirect(!sxxxx.isEmpty() ? String.format(allSearchFmt, sx ? modifiedPrefix : "", sxxxx) : String.format(allSettingsFmt, sx ? modifiedPrefix2 : "")),
               var0 -> {
                  MutableComponent sxxxxxx = Component.literal(String.format(typeFmt, SettingsUtil.settingTypeToString((Settings.Setting)var0)));
                  sxxxxxx.setStyle(sxxxxxx.getStyle().withColor(ChatFormatting.DARK_GRAY));
                  MutableComponent sxxxxxxx = Component.literal("");
                  sxxxxxxx.setStyle(sxxxxxxx.getStyle().withColor(ChatFormatting.GRAY));
                  sxxxxxxx.append(((Settings.Setting<?>)var0).getName());
                  sxxxxxxx.append(String.format(hoverTypeFmt, SettingsUtil.settingTypeToString((Settings.Setting)var0)));
                  sxxxxxxx.append(String.format(hoverValueFmt, SettingsUtil.settingValueToString((Settings.Setting)var0)));
                  sxxxxxxx.append(String.format(hoverDefaultFmt, SettingsUtil.settingDefaultToString((Settings.Setting)var0)));
                  String sxxxxxxxx = Baritone.settings().prefix.value + String.format(setSuggestFmt, ((Settings.Setting<?>)var0).getName());
                  MutableComponent sxxxxxxxxx = Component.literal(((Settings.Setting<?>)var0).getName());
                  sxxxxxxxxx.setStyle(sxxxxxxxxx.getStyle().withColor(ChatFormatting.GRAY));
                  sxxxxxxxxx.append(sxxxxxx);
                  sxxxxxxxxx.setStyle(sxxxxxxxxx.getStyle().withHoverEvent(new ShowText(sxxxxxxx)).withClickEvent(new SuggestCommand(sxxxxxxxx)));
                  return sxxxxxxxxx;
               },
               IBaritoneChatControl.FORCE_COMMAND_PREFIX + "set " + var3 + " " + sxxxx
            );
         } else {
            var2.requireMax(1);
            boolean sxxxx = var3.equalsIgnoreCase(resetArg);
            boolean sxxxxx = var3.equalsIgnoreCase(toggleArg);
            boolean sxxxxxx = sxxxx || sxxxxx;
            if (sxxxx) {
               if (!var2.hasAny()) {
                  this.logDirect(resetHelp1);
                  this.logDirect(resetHelp2);
                  this.logDirect(resetHelp3);
               } else if (var2.peekString().equalsIgnoreCase(allToken)) {
                  SettingsUtil.modifiedSettings(Baritone.settings()).forEach(Settings.Setting::reset);
                  this.logDirect(allResetMsg);
                  SettingsUtil.save(Baritone.settings());
                  return;
               }
            }

            if (sxxxxx) {
               var2.requireMin(1);
            }

            String sxxxxxxx = sxxxxxx ? var2.getString() : var3;
            Settings.Setting<?> sxxxxxxxx = Baritone.settings()
               .allSettings
               .stream()
               .filter(var1x -> var1x.getName().equalsIgnoreCase(sxxxxxxx))
               .findFirst()
               .orElse(null);
            if (sxxxxxxxx == null) {
               throw new CommandInvalidTypeException(var2.consumed(), errValidSetting);
            }

            if (sxxxxxxxx.isJavaOnly()) {
               throw new CommandInvalidStateException(String.format(errApiOnly, sxxxxxxxx.getName()));
            }

            if (!sxxxxxx && !var2.hasAny()) {
               this.logDirect(String.format(valueOfFmt, sxxxxxxxx.getName()));
               this.logDirect(SettingsUtil.settingValueToString((Settings.Setting)sxxxxxxxx));
            } else {
               String sxxxxxxxxx = SettingsUtil.settingValueToString((Settings.Setting)sxxxxxxxx);
               if (sxxxx) {
                  sxxxxxxxx.reset();
               } else if (sxxxxx) {
                  if (sxxxxxxxx.getValueClass() != Boolean.class) {
                     throw new CommandInvalidTypeException(var2.consumed(), errToggleable, errSomeOther);
                  }

                  Settings.Setting<Boolean> sxxxxxxxxxx = (Settings.Setting<Boolean>)sxxxxxxxx;
                  sxxxxxxxxxx.value = !sxxxxxxxxxx.value;
                  this.logDirect(String.format(toggledFmt, sxxxxxxxx.getName(), Boolean.toString(sxxxxxxxxxx.value)));
               } else {
                  String sxxxxxxxxxx = var2.getString();

                  try {
                     SettingsUtil.parseAndApply(Baritone.settings(), var3, sxxxxxxxxxx);
                  } catch (Throwable sxxxxxxxxxxx) {
                     throw new CommandInvalidTypeException(var2.consumed(), errValidValue, sxxxxxxxxxxx);
                  }
               }

               if (!sxxxxx) {
                  this.logDirect(String.format(successFmt, sxxxx ? resetWord : setWord, sxxxxxxxx.getName(), SettingsUtil.settingValueToString((Settings.Setting)sxxxxxxxx)));
               }

               MutableComponent sxxxxxxxxxx = Component.literal(String.format(oldValueFmt, sxxxxxxxxx));
               sxxxxxxxxxx.setStyle(
                  sxxxxxxxxxx.getStyle()
                     .withColor(ChatFormatting.GRAY)
                     .withHoverEvent(new ShowText(Component.literal(revertHint)))
                     .withClickEvent(new RunCommand(IBaritoneChatControl.FORCE_COMMAND_PREFIX + String.format(setOldFmt, sxxxxxxxx.getName(), sxxxxxxxxx)))
               );
               this.logDirect(sxxxxxxxxxx);
               if (sxxxxxxxx.getName().equals(chatControlName) && !(Boolean)sxxxxxxxx.value) {
                  this.logDirect(chatWarnMsg, ChatFormatting.RED);
               }
            }

            SettingsUtil.save(Baritone.settings());
         }
      }
   }

   static {
      VMBridge.identifyClass(SetCommand.class, "m72YxOgX");
   }

   @Override
   public String getShortDesc() {
      return shortDesc;
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(longDescIntro, "", longDescUsage, longDesc1, longDesc2, longDesc3, longDesc4, longDesc5, longDesc6, longDesc7, longDesc8, longDesc9, longDesc10, longDesc11);
   }

   public SetCommand(IBaritone var1) {
      super(var1, cmdSet, cmdSetting, cmdSettings);
   }
}
