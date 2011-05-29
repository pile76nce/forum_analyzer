package rs.etf.analyzer.parser.tokens;

import java.util.HashSet;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import rs.etf.analyzer.parser.DictionaryEntry;
import java.util.List;
import rs.etf.analyzer.parser.TokenDictionary;
import java.util.ArrayList;
import rs.etf.analyzer.parser.Position;
import rs.etf.analyzer.parser.ConfigParser;
import rs.etf.analyzer.parser.StemmedTokenDictionary;
import rs.etf.analyzer.parser.TokenDictionaryEntry;
import rs.etf.analyzer.parser.StemmedTokenDictionaryEntry;
import rs.etf.analyzer.parser.Stemmer;

/**
 * <p>Title: SubjectAnalyzer</p>
 *
 * <p>Description: Klasa za analizu teksta</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author Dejan Prodanovi?
 * @version 1.0
 */
public class TextTokenizer extends AbstractTokenizer
{
  private static final int AVG_TOKEN_LEN = 6;

  private HashSet<Character> ioDelimiterSet = new HashSet<Character> ();

  private String isText;
  private String isSearchText;

  private StringBuffer isDelimiters;

  private List<DictionaryEntry> ioList;

  private Stemmer ioStemmer;

  private int iiTokenNum;
  private int iiCountTokens;
  private int iiPos;

  private boolean ibUpperCase = false;
  private boolean ibNoiseWords = true;
  private boolean ibStemmedWords = true;

  private char[] delimiters = {' ', ',', '.', '!', '?', ':', '+', '-', '*', '/',
                               '\'', '(', ')', '[', ']', '{', '}', '\n', '\r', '\t'};

  private static TokenDictionary ioTokenDictionary;
  private static StemmedTokenDictionary ioStemmedTokenDictionary;

  private ConfigParser ioConfigParser;

  private ConceptMap ioConceptMap;

  public TextTokenizer(final String asConcept, final boolean abUpperCase, final boolean abNoiseWords, final boolean abStemmedWords)
  {
    ioStemmer = new Stemmer();
    ibStemmedWords = abStemmedWords;

    if (ibStemmedWords)
      isSearchText = ioStemmer.stemOneWord(asConcept);
    else
      isSearchText = asConcept;

    for (int i = 0; i < delimiters.length; i++)
      addDelimiter((Character)delimiters[i]);

    ioTokenDictionary = new TokenDictionary();
    ioStemmedTokenDictionary = new StemmedTokenDictionary();
  }

  public TextTokenizer()
  {
    ioStemmer = new Stemmer();

    for (int i = 0; i < delimiters.length; i++)
      addDelimiter((Character)delimiters[i]);

    ioTokenDictionary = new TokenDictionary();
    ioStemmedTokenDictionary = new StemmedTokenDictionary();
  }

  private void addDelimiter(Character aoDelimiter)
  {
    if (ioDelimiterSet.contains(aoDelimiter) == false)
      ioDelimiterSet.add(aoDelimiter);
  }

  /**
   *
   * @return String
   */
  public String nextToken()
  {
    return null;
  }

  /**
   * svi tokeni
   * @param asText String
   */
  public void getAllTokens(final String asText)
  {
    isText = asText;

    int liIndex = 0;

    int liEnd = isText.length();

    char char1 = 0;

    boolean lbEnd = false;

    int liTokenId = 0;
    int liStemmedTokenId = 0;

    Character c = null;

    StringBuffer loStringBuffer = new StringBuffer(6);

    if (isText.equals("") == true)
      return;

    lbEnd = false;

    int liStartIndex = 0;
    int liEndIndex = 0;

    String lsWord;
    String lsStemmedWord;

    while (true)
    {
      if (liIndex < liEnd - 1)
      {
        char1 = isText.charAt(liIndex);

//        liIndex++;

        char1 = (ibUpperCase == true) ? Character.toUpperCase(char1) : Character.toLowerCase(char1);

        c = new Character(char1);

        if ( (ioDelimiterSet.contains(c) == false) /* && (lbEnd == false)*/)
        {
          if (loStringBuffer.length() == 0)
            liStartIndex = liIndex;

//            ako je znak slovo ili cifra
          if (Character.isLetterOrDigit(char1) == true)
            loStringBuffer.append(char1);
        }
        else
        {
//          liEndIndex = liIndex;
//          if (lbEnd == true)
//          {
//            liIndex++;
//            loStringBuffer.append(char1);
//          }

//            delimiter ili kraj teksta
//              ako buffer nije prazan
          if (loStringBuffer.length() > 0)
          {
//          ubaci ParseResult
            lsWord = loStringBuffer.toString().toLowerCase();
            lsStemmedWord = ioStemmer.stemOneWord(lsWord).toLowerCase();

//            System.out.println(String.format("start: %d, end: %d, token: %s", liStartIndex, lsWord.length(), lsWord));

            liTokenId++;
            ioTokenDictionary.addEntry(new TokenDictionaryEntry(lsWord, liTokenId, liStartIndex));
            ioStemmedTokenDictionary.addEntry(new StemmedTokenDictionaryEntry(lsWord, liTokenId, liStartIndex));
          }

          loStringBuffer.delete(0, loStringBuffer.length());
        }

        liIndex++;
      }
      else
      {
//        System.out.println("---");
        if (loStringBuffer.length() > 0)
          {
//          ubaci ParseResult
            lsWord = loStringBuffer.toString();
            lsStemmedWord = ioStemmer.stemOneWord(lsWord);

//            System.out.println(String.format("start: %d, end: %d, token: %s", liStartIndex, lsWord.length(), lsWord));

            liTokenId++;
            ioTokenDictionary.addEntry(new TokenDictionaryEntry(lsWord, liTokenId, liStartIndex));

            ioStemmedTokenDictionary.addEntry(new StemmedTokenDictionaryEntry(lsWord, liTokenId, liStartIndex));
          }

        break;
      }
    }
  }

  public TokenDictionary getTokenDictionary()
  {
    return ioTokenDictionary;
  }

  /**
   * recnik sa stemovanim recima
   * @return StemmedTokenDictionary
   */
  public StemmedTokenDictionary getStemmedTokenDictionary()
  {
    return ioStemmedTokenDictionary;
  }

  public static void main(String[] args)
  {
    File file = new File("./test/pipeline.txt");

    Scanner loScanner = null;
    String lsTestString = null;

    try
    {
      loScanner = new Scanner(file).useDelimiter("\\Z");

      lsTestString = loScanner.next();
    }
    catch (FileNotFoundException ex)
    {
    }

    String lsSubString = "add".toUpperCase();
    TextTokenizer ioTokenizer = new TextTokenizer("ADD", false, true, false);

    ioTokenizer.getAllTokens(lsTestString);

    ArrayList<Position> ioPositionList = ioTokenDictionary.getEntry1("pipeline");
    for (int i = 0; i < ioPositionList.size(); i++)
      System.out.println(ioPositionList.get(i));

    ioPositionList = ioStemmedTokenDictionary.getEntry1("pipelin");
    for (int i = 0; i < ioPositionList.size(); i++)
      System.out.println(ioPositionList.get(i));

    System.out.println(ioPositionList.size());
  }
}
