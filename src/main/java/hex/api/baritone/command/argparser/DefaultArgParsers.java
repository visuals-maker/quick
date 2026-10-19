// Original binary: hex/api/baritone/command/argparser/DefaultArgParsers
package hex.api.baritone.command.argparser;

import hex.api.baritone.api.command.argparser.IArgParser;
import hex.api.baritone.api.command.argument.ICommandArgument;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import ru.dreamix.fabricloader.VMBridge;

// $VF: Compiled from s
public class DefaultArgParsers {
   public static final List<IArgParser<?>> ALL = Arrays.asList(
      DefaultArgParsers.IntArgumentParser.INSTANCE,
      DefaultArgParsers.LongArgumentParser.INSTANCE,
      DefaultArgParsers.FloatArgumentParser.INSTANCE,
      DefaultArgParsers.DoubleArgumentParser.INSTANCE,
      DefaultArgParsers.BooleanArgumentParser.INSTANCE
   );

   // $VF: Compiled from s
   public static class BooleanArgumentParser implements IArgParser.Stateless<Boolean> {
      public static final DefaultArgParsers.BooleanArgumentParser INSTANCE = new DefaultArgParsers.BooleanArgumentParser();
      public static final List<String> TRUTHY_VALUES = Arrays.asList(
         DefaultArgParsers.BooleanArgumentParser.Ауа,
         DefaultArgParsers.BooleanArgumentParser.Ау7,
         DefaultArgParsers.BooleanArgumentParser.АуУ,
         DefaultArgParsers.BooleanArgumentParser.Ау0,
         DefaultArgParsers.BooleanArgumentParser.АуГ,
         DefaultArgParsers.BooleanArgumentParser.Ауф,
         DefaultArgParsers.BooleanArgumentParser.АуЕ
      );
      public static final List<String> FALSY_VALUES = Arrays.asList(
         DefaultArgParsers.BooleanArgumentParser.Ауч,
         DefaultArgParsers.BooleanArgumentParser.Ау衣,
         DefaultArgParsers.BooleanArgumentParser.Ауц,
         DefaultArgParsers.BooleanArgumentParser.АуМ,
         DefaultArgParsers.BooleanArgumentParser.Ау5,
         DefaultArgParsers.BooleanArgumentParser.АуЖ,
         DefaultArgParsers.BooleanArgumentParser.Ауо
      );
      private static String АуШ = "";
      private static String Ауа = "";
      private static String Ау7 = "";
      private static String АуУ = "";
      private static String Ау0 = "";
      private static String АуГ = "";
      private static String Ауф = "";
      private static String АуЕ = "";
      private static String Ауч = "";
      private static String Ау衣 = "";
      private static String Ауц = "";
      private static String АуМ = "";
      private static String Ау5 = "";
      private static String АуЖ = "";
      private static String Ауо = "";

      static {
         VMBridge.identifyClass(DefaultArgParsers.BooleanArgumentParser.class, "GOjb52Ax");
      }

      public Boolean parseArg(ICommandArgument var1) throws RuntimeException {
         String var2 = var1.getValue();
         if (TRUTHY_VALUES.contains(var2.toLowerCase(Locale.US))) {
            return true;
         } else if (FALSY_VALUES.contains(var2.toLowerCase(Locale.US))) {
            return false;
         } else {
            throw new IllegalArgumentException(АуШ);
         }
      }

      @Override
      public Class<Boolean> getTarget() {
         return Boolean.class;
      }
   }

   // $VF: Compiled from s
   public enum DoubleArgumentParser implements IArgParser.Stateless<Double> {
      INSTANCE;

      @Override
      public Class<Double> getTarget() {
         return Double.class;
      }

      public Double parseArg(ICommandArgument var1) throws RuntimeException {
         String var2 = var1.getValue();
         if (!var2.matches("^([+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)|)$")) {
            throw new IllegalArgumentException("failed double format check");
         } else {
            return Double.parseDouble(var2);
         }
      }
   }

   // $VF: Compiled from s
   public enum FloatArgumentParser implements IArgParser.Stateless<Float> {
      INSTANCE;

      @Override
      public Class<Float> getTarget() {
         return Float.class;
      }

      public Float parseArg(ICommandArgument var1) throws RuntimeException {
         String var2 = var1.getValue();
         if (!var2.matches("^([+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)|)$")) {
            throw new IllegalArgumentException("failed float format check");
         } else {
            return Float.parseFloat(var2);
         }
      }
   }

   // $VF: Compiled from s
   public enum IntArgumentParser implements IArgParser.Stateless<Integer> {
      INSTANCE;

      @Override
      public Class<Integer> getTarget() {
         return Integer.class;
      }

      public Integer parseArg(ICommandArgument var1) throws RuntimeException {
         return Integer.parseInt(var1.getValue());
      }
   }

   // $VF: Compiled from s
   public enum LongArgumentParser implements IArgParser.Stateless<Long> {
      INSTANCE;

      @Override
      public Class<Long> getTarget() {
         return Long.class;
      }

      public Long parseArg(ICommandArgument var1) throws RuntimeException {
         return Long.parseLong(var1.getValue());
      }
   }
}
