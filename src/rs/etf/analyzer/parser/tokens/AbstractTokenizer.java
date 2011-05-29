package rs.etf.analyzer.parser.tokens;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Hashtable;

public class AbstractTokenizer
{
  // IMPORTANT - All keyword matches should be between START and END
        public static final int START = 2048;
        public static final int END = START + 2048;
        // IMPORTANT -- This should be < END
        public static final int ID = END - 1;
        public static final int SAFE = END - 2;
        // Individial token classes.
        public static final int WHITESPACE = END + 1;
        public static final int DIGIT = END + 2;
        public static final int ALPHA = END + 3;
        public static final int BACKSLASH = (int) '\\';
        public static final int QUOTE = (int) '\'';
        public static final int AT = (int) '@';
        public static final int SP = (int) ' ';
        public static final int HT = (int) '\t';
        public static final int COLON = (int) ':';
        public static final int STAR = (int) '*';
        public static final int DOLLAR = (int) '$';
        public static final int PLUS = (int) '+';
        public static final int POUND = (int) '#';
        public static final int MINUS = (int) '-';
        public static final int DOUBLEQUOTE = (int) '\"';
        public static final int TILDE = (int) '~';
        public static final int BACK_QUOTE = (int) '`';
        public static final int NULL = (int) '\0';
        public static final int EQUALS = (int) '=';
        public static final int SEMICOLON = (int) ';';
        public static final int SLASH = (int) '/';
        public static final int L_SQUARE_BRACKET = (int) '[';
        public static final int R_SQUARE_BRACKET = (int) ']';
        public static final int R_CURLY = (int) '}';
        public static final int L_CURLY = (int) '{';
        public static final int HAT = (int) '^';
        public static final int BAR = (int) '|';
        public static final int DOT = (int) '.';
        public static final int EXCLAMATION = (int) '!';
        public static final int LPAREN = (int) '(';
        public static final int RPAREN = (int) ')';
        public static final int GREATER_THAN = (int) '>';
        public static final int LESS_THAN = (int) '<';
        public static final int PERCENT = (int) '%';
        public static final int QUESTION = (int) '?';
        public static final int AND = (int) '&';
        public static final int UNDERSCORE = (int) '_';

        public static final char ALPHA_VALID_CHARS = Character.MAX_VALUE;
        public static final char DIGIT_VALID_CHARS = Character.MAX_VALUE - 1;
        public static final char ALPHADIGIT_VALID_CHARS = Character.MAX_VALUE - 2;



  protected String isBuffer;

  protected int iiCurrentIndex;
  protected int iiBufferLength;

  protected int iiSavedIndex;

  private HashSet<Character> ioDelimiterSet = new HashSet<Character> ();
  private HashSet<Character> ioTokenSet = new HashSet<Character> ();

  private Hashtable<String, Integer> ioTypesTable = new Hashtable<String, Integer>();

  private char[] delimiters = {' ', ',', '.', '!', '?', ':', '+', '-', '*', '/',
                               '\'', '(', ')', '[', ']', '{', '}', '\n', '\r', '\t'};

  private char[] token = {'-', '.', '!', '%', '*', '_', '+', '`', '\'', '~'};

  public AbstractTokenizer()
  {
    isBuffer = "";
    iiBufferLength = isBuffer.length();

    iiCurrentIndex = 0;

    for (int i = 0; i < delimiters.length; i++)
      addDelimiter( (Character) delimiters[i]);

    for (int i = 0; i < token.length; i++)
      addTokenChar( (Character) token[i]);

  }

  public AbstractTokenizer(final String asBuffer)
  {
    isBuffer = asBuffer;
    iiBufferLength = isBuffer.length();

    iiCurrentIndex = 0;

    for (int i = 0; i < delimiters.length; i++)
      addDelimiter((Character)delimiters[i]);
  }

  /**
   * Scan until you see a slash or an EOL.
   *
   * @return substring containing no slash.
   */
  public String byteStringNoSlash()
  {
    StringBuffer retval = new StringBuffer();
    try
    {
      while (true)
      {
        char next = getOffsetChar(0);
        // bug fix from Ben Evans.
        if (next == '\0' || next == '\n' || next == '/')
        {
          break;
        }
        else
        {
          move(1);
          retval.append(next);
        }
      }
    }
    catch (ParseException ex)
    {
      return retval.toString();
    }
    return retval.toString();
  }

  /** Return a substring containing no commas
   *@return a substring containing no commas.
   */

  public String byteStringNoComma()
  {
    StringBuffer retval = new StringBuffer();
    try
    {
      while (true)
      {
        char next = getOffsetChar(0);
        if (next == '\n' || next == ',')
        {
          break;
        }
        else
        {
          move(1);
          retval.append(next);
        }
      }
    }
    catch (ParseException ex)
    {
    }

    return retval.toString();
  }


  /** Return a substring containing no semicolons.
   *@return a substring containing no semicolons.
   */
  public String byteStringNoSemicolon()
  {
    StringBuffer retval = new StringBuffer();
    try
    {
      while (true)
      {
        char next = getOffsetChar(0);
        // bug fix from Ben Evans.
        if (next == '\0' || next == '\n' || next == ';' || next == ',')
        {
          break;
        }
        else
        {
          move(1);
          retval.append(next);
        }
      }
    }
    catch (ParseException ex)
    {
      return retval.toString();
    }

    return retval.toString();
  }


  public void consumeValidChars(char[] validChars)
  {
    int validCharsLength = validChars.length;
    try
    {
      while (hasMoreChars())
      {
        char nextChar = getOffsetChar(0);
        boolean isValid = false;
        for (int i = 0; i < validCharsLength; i++)
        {
          char validChar = validChars[i];
          switch (validChar)
          {
            case ALPHA_VALID_CHARS:
              isValid = Character.isLetter(nextChar);
              break;
            case DIGIT_VALID_CHARS:
              isValid = Character.isDigit(nextChar);
              break;
            case ALPHADIGIT_VALID_CHARS:
              isValid = isAlphaDigit(nextChar);
              break;
            default:
              isValid = nextChar == validChar;
          }
          if (isValid)
          {
            break;
          }
        }
        if (isValid)
        {
          move(1);
        }
        else
        {
          break;
        }
      }
    }
    catch (ParseException ex)
    {
    }
  }


  public static boolean isHexDigit(char ch)
  {
    return (ch >= 'A' && ch <= 'F') ||
        (ch >= 'a' && ch <= 'f') || Character.isDigit(ch);
  }

  private void addDelimiter(Character aoDelimiter)
  {
    if (ioDelimiterSet.contains(aoDelimiter) == false)
      ioDelimiterSet.add(aoDelimiter);
  }

  private void addTokenChar(Character aoDelimiter)
  {
    if (ioTokenSet.contains(aoDelimiter) == false)
      ioTokenSet.add(aoDelimiter);
  }

  private void addKeyword(final String asName, int aiValue)
  {
    ioTypesTable.put(asName, aiValue);
  }

  public void setBuffer(final String asBuffer)
  {
    isBuffer = asBuffer;
    iiBufferLength = isBuffer.length();

    iiCurrentIndex = 0;
  }

  public void setCurrentIndex(final int aiCurrentIndex)
  {
    iiCurrentIndex = aiCurrentIndex;
  }

  public String getBuffer()
  {
    return isBuffer;
  }

  public int getCurrentIndex()
  {
    return iiCurrentIndex;
  }

  public String nextToken(final char asDelimiter) throws ParseException
  {
    int liStartIndex = iiCurrentIndex;
    char c;

    while (iiCurrentIndex < iiBufferLength)
    {
      c = isBuffer.charAt(iiCurrentIndex);
      if (c == asDelimiter)
        break;
      else
        iiCurrentIndex++;
    }

    return isBuffer.substring(liStartIndex, iiCurrentIndex);
  }

  public String nextToken(final char acFirstDelimiter, final char acSecondDelimiter) throws ParseException
  {
    int liStartIndex = iiCurrentIndex;
    char c;

    while (iiCurrentIndex < iiBufferLength)
    {
      c = isBuffer.charAt(iiCurrentIndex);
      iiCurrentIndex++;

      if (c == acFirstDelimiter)
      {
        liStartIndex = iiCurrentIndex;
        c = isBuffer.charAt(iiCurrentIndex);
        iiCurrentIndex++;
      }

      if (c == acSecondDelimiter)
        break;
    }

    return isBuffer.substring(liStartIndex, iiCurrentIndex);
  }

  public String nextToken(final int aiOffset, final int aiLength)
  {
    if ((iiCurrentIndex + aiOffset + aiLength) > iiBufferLength)
    {
      return "";
    }
    else
      return isBuffer.substring(iiCurrentIndex + aiOffset, iiCurrentIndex + aiOffset + aiLength);
  }

  public String nextToken() throws ParseException
  {
    int liStartIndex = iiCurrentIndex;
    boolean lbFirst = false;
    char c;

    while (iiCurrentIndex < iiBufferLength)
    {
      c = isBuffer.charAt(iiCurrentIndex);
      iiCurrentIndex++;
      if (ioDelimiterSet.contains(Character.valueOf(c)))
      {
        if (lbFirst == false)
        {
          liStartIndex = iiCurrentIndex;
          lbFirst = true;
        }
        else
          break;
      }
    }

    return isBuffer.substring(liStartIndex, iiCurrentIndex);
  }

  public String getField() throws ParseException
  {
    return nextToken(':');
  }

  public String getField(final char asDelimiter) throws ParseException
  {
    return nextToken(asDelimiter);
  }

  public long nextLong() throws ParseException
  {
    try
    {
      String lsTemp = nextToken().trim();

      return Long.parseLong(lsTemp);
    }
    catch (NumberFormatException e)
    {
      throw new ParseException("", 1);
    }
  }

  public int nextInt() throws ParseException
  {
    try
    {
      String lsTemp = nextToken();

      return Integer.parseInt(lsTemp);
    }
    catch (NumberFormatException e)
    {
      throw new ParseException("", 1);
    }
  }

  public void move(final int aiPos)
  {
    iiCurrentIndex += aiPos;
  }

  public void moveTo(final char acToChar)
  {
    try
    {
      while (iiCurrentIndex <= iiBufferLength)
      {
        if (isBuffer.charAt(iiCurrentIndex++) == acToChar)
          break;
      }
    }
    catch (Exception e)
    {
    }
  }

  public int find(final String asFindStr)
  {
    return isBuffer.indexOf(asFindStr, iiCurrentIndex);
  }

  public void move(final char acChar)
  {
    char c;
    while (iiCurrentIndex < iiBufferLength)
      if (isBuffer.charAt(iiCurrentIndex++) == acChar)
        break;
  }

  public char getOffsetChar(final int aiOffset) throws ParseException
  {
    try
    {
      return isBuffer.charAt(iiCurrentIndex + aiOffset);
    }
    catch (Exception e)
    {
      throw new ParseException("getOffsetChar: ", iiCurrentIndex + aiOffset);
    }
  }

  public char getNextChar()
  {
    return isBuffer.charAt(iiCurrentIndex++);
  }

  public char nextChar(final int charpos)
  {
    iiCurrentIndex += charpos;

    return isBuffer.charAt(iiCurrentIndex);
  }

  public char getChar() throws ParseException
  {
    try
    {
      return isBuffer.charAt(iiCurrentIndex);
    }
    catch (IndexOutOfBoundsException e)
    {
      return '\0';
    }
  }

  public String getRest()
  {
    if (iiCurrentIndex >= iiBufferLength)
      return null;
    else
      return isBuffer.substring(iiCurrentIndex);
  }

  public String subString(final int aiStartIndex, final int aiLength)
  {
    if (aiStartIndex + aiLength <= iiBufferLength)
      return isBuffer.substring(aiStartIndex, aiStartIndex + aiLength);
    else
      return "";
  }

  public String getLine()
  {
//    trim();
    char c;

    while (true)
    {
      c = isBuffer.charAt(iiCurrentIndex);;
      if (c < 0x20)
        iiCurrentIndex++;
      else
        break;
    }

    int startIdx = iiCurrentIndex;


    while (true)
    {
      c = isBuffer.charAt(iiCurrentIndex);
      if (c != '\r' && c!= '\n')
        iiCurrentIndex++;
      else
        break;
    }

    return isBuffer.substring(startIdx, iiCurrentIndex);
  }

  public void trim()
  {
    char c = isBuffer.charAt(iiCurrentIndex);
    while (c == ' ' || c == '\t')
    {
//      move(1);
      iiCurrentIndex++;
      c = isBuffer.charAt(iiCurrentIndex);
    }
  }

  public boolean isTokenChar(final char acTestChar)
  {
    if (Character.isLetterOrDigit(acTestChar))
      return true;
    else
      switch (acTestChar)
      {
        case '-':
        case '.':
        case '!':
        case '%':
        case '*':
        case '_':
        case '+':
        case '`':
        case '\'':
        case '~':
          return true;
        default:
          return false;
      }
  }

  public boolean hasMoreChars()
  {
    return (iiCurrentIndex <= iiBufferLength);
  }

  public boolean isAlphaDigit(final char acTestChar)
  {
    return Character.isLetterOrDigit(acTestChar);
  }





  /** Parse a comment string cursor is at a ". Leave cursor at closing "
   *@return the substring containing the quoted string excluding the
   * closing quote.
   */
  public String quotedString() throws ParseException
  {
    int startIdx = iiCurrentIndex + 1;
    if (getOffsetChar(0) != '\"')
      return null;
    move(1);
    while (true)
    {
      char next = getNextChar();
      if (next == '\"')
      {
        // Got to the terminating quote.
        break;
      }
      else if (next == '\0')
      {
        throw new ParseException(
            isBuffer + " :unexpected EOL",
            iiCurrentIndex);
      }
      else if (next == '\\')
      {
        move(1);
      }
    }
    return isBuffer.substring(startIdx, iiCurrentIndex - 1);
  }


  public String ttoken()
  {
    int startIdx = iiCurrentIndex;
    try
    {
      while (hasMoreChars())
      {
        char nextChar = getOffsetChar(0);
        if (isTokenChar(nextChar))
        {
          move(1);
        }
        else
        {
          break;
        }
      }
      return isBuffer.substring(startIdx, iiCurrentIndex);
    }
    catch (ParseException ex)
    {
      return null;
    }
  }

  public boolean startsId()
  {
    try
    {
      char nextChar = getChar();
      return isTokenChar(nextChar);
    }
    catch (ParseException ex)
    {
      return false;
    }
  }

  public String number() throws ParseException
  {
    int startIdx = iiCurrentIndex;
    try
    {
      if (!Character.isDigit(getOffsetChar(0)))
      {
        throw new ParseException(
            isBuffer + ": Unexpected token at " + getOffsetChar(0),
            iiCurrentIndex);
      }
      //move(1);
      while (true)
      {
        char next = getOffsetChar(0);
        if (Character.isDigit(next))
        {
          move(1);
        }
        else
          break;
      }
      return isBuffer.substring(startIdx, iiCurrentIndex);
    }
    catch (ParseException ex)
    {
      return isBuffer.substring(startIdx, iiCurrentIndex);
    }
    catch (Exception e)
    {
      return isBuffer.substring(startIdx, iiCurrentIndex);
    }

  }

}
