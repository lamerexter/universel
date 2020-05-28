package org.orthodox.universel.compiler;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import java.util.Map;
import java.util.TreeMap;

import static org.objectweb.asm.Opcodes.*;

/**
 * Helper to generate the code for casting between two primitive types.
 * 
 * @author Gary Watson
 */
public class PrimitiveCastingCodeGenerator {
   private static final Map<String, MethodCodeGenerator> CASTING_CODE_GENERATORS = new TreeMap<>();
   private static final MethodCodeGenerator NO_OP = mv -> {};
   
   private static final MethodCodeGenerator INT_CMPEQ_BOOLEAN = mv -> {
      Label trueLabel = new Label();
      Label falseLabel = new Label();
      mv.visitJumpInsn(IFEQ, falseLabel);
      mv.visitInsn(ICONST_1);
      mv.visitJumpInsn(GOTO, trueLabel);
      mv.visitLabel(falseLabel);
      mv.visitInsn(ICONST_0);
      mv.visitLabel(trueLabel);
   };
   
   private static final MethodCodeGenerator ICONST_O = mv -> mv.visitInsn(ICONST_0);

   static {
      // boolean to ...
      CASTING_CODE_GENERATORS.put("boolean_to_boolean", NO_OP);
      CASTING_CODE_GENERATORS.put("boolean_to_byte", mv -> mv.visitInsn(I2B));
      CASTING_CODE_GENERATORS.put("boolean_to_char", mv -> mv.visitInsn(I2C));
      CASTING_CODE_GENERATORS.put("boolean_to_double", mv -> mv.visitInsn(I2D));
      CASTING_CODE_GENERATORS.put("boolean_to_float", mv -> mv.visitInsn(I2F));
      CASTING_CODE_GENERATORS.put("boolean_to_int", NO_OP);
      CASTING_CODE_GENERATORS.put("boolean_to_long", mv -> mv.visitInsn(I2L));
      CASTING_CODE_GENERATORS.put("boolean_to_short", mv -> mv.visitInsn(I2S));
      CASTING_CODE_GENERATORS.put("boolean_to_void", NO_OP);

      // byte to ...
      CASTING_CODE_GENERATORS.put("byte_to_boolean", INT_CMPEQ_BOOLEAN);
      CASTING_CODE_GENERATORS.put("byte_to_byte", NO_OP);
      CASTING_CODE_GENERATORS.put("byte_to_char", CASTING_CODE_GENERATORS.get("boolean_to_char"));
      CASTING_CODE_GENERATORS.put("byte_to_double", CASTING_CODE_GENERATORS.get("boolean_to_double"));
      CASTING_CODE_GENERATORS.put("byte_to_float", CASTING_CODE_GENERATORS.get("boolean_to_float"));
      CASTING_CODE_GENERATORS.put("byte_to_int", NO_OP);
      CASTING_CODE_GENERATORS.put("byte_to_long", CASTING_CODE_GENERATORS.get("boolean_to_long"));
      CASTING_CODE_GENERATORS.put("byte_to_short", CASTING_CODE_GENERATORS.get("boolean_to_short"));
      CASTING_CODE_GENERATORS.put("byte_to_void", NO_OP);

      // char to ...
      CASTING_CODE_GENERATORS.put("char_to_boolean", INT_CMPEQ_BOOLEAN);
      CASTING_CODE_GENERATORS.put("char_to_byte", CASTING_CODE_GENERATORS.get("boolean_to_byte"));
      CASTING_CODE_GENERATORS.put("char_to_char", NO_OP);
      CASTING_CODE_GENERATORS.put("char_to_double", CASTING_CODE_GENERATORS.get("boolean_to_double"));
      CASTING_CODE_GENERATORS.put("char_to_float", mv -> mv.visitInsn(I2F));
      CASTING_CODE_GENERATORS.put("char_to_int", NO_OP);
      CASTING_CODE_GENERATORS.put("char_to_long", CASTING_CODE_GENERATORS.get("boolean_to_long"));
      CASTING_CODE_GENERATORS.put("char_to_short", CASTING_CODE_GENERATORS.get("boolean_to_short"));
      CASTING_CODE_GENERATORS.put("char_to_void", NO_OP);

      // double to ...
      CASTING_CODE_GENERATORS.put("double_to_boolean", new CompositeMethodCodeGenerator(mv -> {
         mv.visitInsn(DCONST_0);
         mv.visitInsn(DCMPL);
      }, INT_CMPEQ_BOOLEAN));
      CASTING_CODE_GENERATORS.put("double_to_byte", mv -> {
         mv.visitInsn(D2I);
         mv.visitInsn(I2B);
      });
      CASTING_CODE_GENERATORS.put("double_to_char", mv -> {
         mv.visitInsn(D2I);
         mv.visitInsn(I2C);
      });
      CASTING_CODE_GENERATORS.put("double_to_double", NO_OP);
      CASTING_CODE_GENERATORS.put("double_to_float", mv -> mv.visitInsn(D2F));
      CASTING_CODE_GENERATORS.put("double_to_int", mv -> mv.visitInsn(D2I));
      CASTING_CODE_GENERATORS.put("double_to_long", mv -> mv.visitInsn(D2L));
      CASTING_CODE_GENERATORS.put("double_to_short", mv -> {
         mv.visitInsn(D2I);
         mv.visitInsn(I2S);
      });
      CASTING_CODE_GENERATORS.put("double_to_void", NO_OP);

      // float to ...
      CASTING_CODE_GENERATORS.put("float_to_boolean", new CompositeMethodCodeGenerator(mv -> {
         mv.visitInsn(FCONST_0);
         mv.visitInsn(FCMPL);
      }, INT_CMPEQ_BOOLEAN));
      CASTING_CODE_GENERATORS.put("float_to_byte", mv -> {
         mv.visitInsn(F2I);
         mv.visitInsn(I2B);
      });
      CASTING_CODE_GENERATORS.put("float_to_char", mv -> {
         mv.visitInsn(F2I);
         mv.visitInsn(I2C);
      });
      CASTING_CODE_GENERATORS.put("float_to_double", mv -> mv.visitInsn(F2D));
      CASTING_CODE_GENERATORS.put("float_to_float", NO_OP);
      CASTING_CODE_GENERATORS.put("float_to_int", mv -> mv.visitInsn(F2I));
      CASTING_CODE_GENERATORS.put("float_to_long", mv -> mv.visitInsn(F2L));
      CASTING_CODE_GENERATORS.put("float_to_short", mv -> {
         mv.visitInsn(F2I);
         mv.visitInsn(I2S);
      });
      CASTING_CODE_GENERATORS.put("float_to_void", NO_OP);

      // int to ...
      CASTING_CODE_GENERATORS.put("int_to_boolean", INT_CMPEQ_BOOLEAN);
      CASTING_CODE_GENERATORS.put("int_to_byte", mv -> mv.visitInsn(I2B));
      CASTING_CODE_GENERATORS.put("int_to_char", mv -> mv.visitInsn(I2C));
      CASTING_CODE_GENERATORS.put("int_to_double", mv -> mv.visitInsn(I2D));
      CASTING_CODE_GENERATORS.put("int_to_float", mv -> mv.visitInsn(I2F));
      CASTING_CODE_GENERATORS.put("int_to_int", NO_OP);
      CASTING_CODE_GENERATORS.put("int_to_long", mv -> mv.visitInsn(I2L));
      CASTING_CODE_GENERATORS.put("int_to_short", mv -> mv.visitInsn(I2S));
      CASTING_CODE_GENERATORS.put("int_to_void", NO_OP);

      // long to ...
      CASTING_CODE_GENERATORS.put("long_to_boolean", new CompositeMethodCodeGenerator(mv -> {
         mv.visitInsn(LCONST_0);
         mv.visitInsn(LCMP);
      }, INT_CMPEQ_BOOLEAN));
      CASTING_CODE_GENERATORS.put("long_to_byte", mv -> {
         mv.visitInsn(L2I);
         mv.visitInsn(I2B);
      });
      CASTING_CODE_GENERATORS.put("long_to_char", mv -> {
         mv.visitInsn(L2I);
         mv.visitInsn(I2C);
      });
      CASTING_CODE_GENERATORS.put("long_to_double", mv -> mv.visitInsn(L2D));
      CASTING_CODE_GENERATORS.put("long_to_float", mv -> mv.visitInsn(L2F));
      CASTING_CODE_GENERATORS.put("long_to_int", mv -> mv.visitInsn(L2I));
      CASTING_CODE_GENERATORS.put("long_to_long", NO_OP);
      CASTING_CODE_GENERATORS.put("long_to_short", mv -> {
         mv.visitInsn(L2I);
         mv.visitInsn(I2S);
      });
      CASTING_CODE_GENERATORS.put("long_to_void", NO_OP);
   
      // short to ...
      CASTING_CODE_GENERATORS.put("short_to_boolean", INT_CMPEQ_BOOLEAN);
      CASTING_CODE_GENERATORS.put("short_to_byte", mv -> mv.visitInsn(I2B));
      CASTING_CODE_GENERATORS.put("short_to_char", mv -> mv.visitInsn(I2C));
      CASTING_CODE_GENERATORS.put("short_to_double", mv -> mv.visitInsn(I2D));
      CASTING_CODE_GENERATORS.put("short_to_float", mv -> mv.visitInsn(I2F));
      CASTING_CODE_GENERATORS.put("short_to_int", NO_OP);
      CASTING_CODE_GENERATORS.put("short_to_long", mv -> mv.visitInsn(I2L));
      CASTING_CODE_GENERATORS.put("short_to_short", NO_OP);
      CASTING_CODE_GENERATORS.put("short_to_void", NO_OP);

      // void to ...
      CASTING_CODE_GENERATORS.put("void_to_boolean", ICONST_O);
      CASTING_CODE_GENERATORS.put("void_to_byte", ICONST_O);
      CASTING_CODE_GENERATORS.put("void_to_char", ICONST_O);
      CASTING_CODE_GENERATORS.put("void_to_double", mv -> mv.visitInsn(DCONST_0));
      CASTING_CODE_GENERATORS.put("void_to_float", mv -> mv.visitInsn(FCONST_0));
      CASTING_CODE_GENERATORS.put("void_to_int", ICONST_O);
      CASTING_CODE_GENERATORS.put("void_to_long", mv -> mv.visitInsn(LCONST_0));
      CASTING_CODE_GENERATORS.put("void_to_short", ICONST_O);
      CASTING_CODE_GENERATORS.put("void_to_void", NO_OP);
   }

   public static void cast(MethodVisitor mv, Class<?> fromType, Class<?> toType) {
      MethodCodeGenerator generator = CASTING_CODE_GENERATORS.get(fromType.getName() + "_to_" + toType.getName());
      if (generator == null)
         return;

      generator.generate(mv);
   }
}
