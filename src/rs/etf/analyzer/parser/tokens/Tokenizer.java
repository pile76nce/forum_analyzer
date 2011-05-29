package rs.etf.analyzer.parser.tokens;

import java.util.HashSet;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import rs.etf.analyzer.parser.Stemmer;

/**
 * <p>Title: SubjectAnalyzer</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author Dejan Prodanovi?
 * @version 1.0
 */
public class Tokenizer
{
  private HashSet<Character> ioDelimiterSet = new HashSet<Character> ();

  private String isText;
  private String isSearchText;

  private StringBuffer isDelimiters;

  private Stemmer ioStemmer;

  private int iiTokenNum;
  private int iiCountTokens;
  private int iiPos;

  private boolean ibUpperCase = false;
  private boolean ibNoiseWords = true;
  private boolean ibStemmedWords = true;

  private char[] delimiters = {' ', ',', '.', '!', '?', ':', '+', '-', '*', '/',
                               '(' ,')', '[', ']', '{', '}', '\n', '\r', '\t'};

  public Tokenizer(final String asConcept, final boolean abUpperCase, final boolean abNoiseWords, final boolean abStemmedWords)
  {
    ioStemmer = new Stemmer();
//    ioDelimiterSet.
//      isConcept = asConcept;
    ibStemmedWords = abStemmedWords;

    if (ibStemmedWords)
      isSearchText = ioStemmer.stemOneWord(asConcept); //isStemmedConcept;
    else
      isSearchText = asConcept;

    for (int i = 0; i < delimiters.length; i++)
      addDelimiter((Character)delimiters[i]);
  }

  public void addDelimiter(Character aoDelimiter)
  {
    if (ioDelimiterSet.contains(aoDelimiter) == false)
      ioDelimiterSet.add(aoDelimiter);
  }

  public void nextToken()
  {

  }

  /**
   * Metoda koja cita sve reci iz teksta i smesta ih u recnike
   * @param asText sadrzaj teksta
   */
  private void getAllTokens(final String asText)
  {
    isText = asText;

    int liIndex = 0;
    int liLen = isSearchText.length();

    int liEnd = isText.length();

    char char1 = 0;

    boolean lbEnd = false;
    int liCount = 0;

    char lcLow;
    char lcHigh;

    Character c = null;
    int liDiff;

    if (ibUpperCase == true)
    {
      lcLow = 'A';
      lcHigh = 'Z';
      liDiff = 'a' - 'A';
    }
    else
    {
      lcLow = 'a';
      lcHigh = 'z';
      liDiff = 'A' - 'a';
    }

    StringBuffer loStringBuffer = new StringBuffer(6);

    if (isSearchText.equals("") == true)
      return;

    lbEnd = false;

    int liStartIndex = 0;
    int liEndIndex = 0;

    while (true)
    {
      char1 = isText.charAt(liIndex++);

      if (liIndex == liEnd)
        lbEnd = true;

      if ('A' <= char1 && char1 <= 'Z' && ibUpperCase == false)
        char1 = (char) (char1 + 'a' - 'A');
      else if ('a' <= char1 && char1 <= 'z' && ibUpperCase == true)
        char1 = (char) (char1 + 'A' - 'a');

      c = new Character(char1);

      if ( (ioDelimiterSet.contains(c) == false) && (lbEnd == false))
      {
  //            cita se token
        if (loStringBuffer.length() == 0)
          liStartIndex = liIndex-1;
        loStringBuffer.append(char1);
      }
      else
      {
        liEndIndex = liIndex-1;
        if (lbEnd == true)
        {
          liIndex++;
          loStringBuffer.append(char1);
        }
  //            delimiter ili kraj teksta
  //              ako buffer nije prazan
        if (loStringBuffer.length() > 0)
        {
          System.out.println(String.format("start: %d, end: %d, token: %s", liStartIndex, liEndIndex, loStringBuffer));
//          stemming
//          ubaci ParseResult
        }

        loStringBuffer.delete(0, loStringBuffer.length());
      }

      if (lbEnd == true)
        break;
    }

    //    loResult.setCount(liCount);
  }
}
