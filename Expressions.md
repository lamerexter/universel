# Expression types

## Literals

- Boolean
  - Simply the classic `true` and `false` values.
- Integer
  - `int`          e.g. 123 (decimal), 0b111_1011 or 0b1111011 (binary), 0173 (octal)
  - `long`         e.g. 123L (decimal), 0b111_1011L or 0b1111011L (binary), 0173L (octal)
  - `short`        e.g. 123   // Automagically promoted from/tom int
  - `BigInteger`   e.g. 123I  // Notice the 'I'. signifying 'big' integer!
- Floating-Point
  - `float`        e.g. 123f
  - `double`       e.g. 123.0 or 123d
  - `BigDecimal`   e.g. 123D  // Notice the 'D'. signifying 'big' decimal!
- Strings / Text Blocks
  - `'Hi Fred, how\\'s it going?'`  - Single-quoted strings are uninterpolated string literals whose _character data_ must appear on a single
                                      line. Any embedded single quotes (') must be escaped using backslash (\\). 
  - `'''Hi Fred,  
       how's it going?'''`          - Triple single-quoted strings are uninterpolated string literals whose character data may
                                      appear over one or more lines. Single or even double single-quotes may appear unescaped. This
                                      string literal is very useful for working with text blocks cut-and-pasted from external
                                      sources (e.g. formatted SQL statements or JSON blocks).
  - `"Fred says, \"Hi!\", today "`  - Double-quoted strings are interpolated string literals whose _character data_ must appear on a single
                                      line. Any embedded double quotes (") must be escaped using backslash (\\). Such strings may contain
                                      interpolation expressions which are any valid universal expression:
    <pre><code>"Fred says ${1 + 1} equals 2"</code></pre>
  - `"""Fred says, "Hi", today """` - Triple double-quoted strings are interpolated string literals whose character data may
                                      appear over one or more lines. Single or even double double-quotes may appear unescaped. Such strings may contain
                                      interpolation expressions which are any valid universal expression: 
    <pre><code>"""Fred says ${1 + 1} 
       equals 2"""</code></pre>                                  
                                      
     This string literal is very useful for working with text blocks cut-and-pasted from external sources (e.g. formatted SQL statements or JSON blocks) and
     where variable or computed values need to be substituted, with careful attention to escaping any
     interpolation start character sequences (${) using backslash (\\).
