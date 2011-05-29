package rs.etf.analyzer.parser;

import rs.etf.analyzer.parser.DictionaryEntry;
import java.util.List;
import java.util.ArrayList;

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
public class ConceptParser
{
  private String isCategory;
  private String isConcept;
  private String isStemmedConcept;

  private String isText = null;

  private List<DictionaryEntry> ioEntry;

  private List<ParseResult> ioResultList = new ArrayList<ParseResult>();

  public ConceptParser()
  {
  }

  public ConceptParser(List<DictionaryEntry> aoEntry)
  {
    ioEntry = aoEntry;
  }

  public List<ParseResult> createResultList(final String asText)
  {
    isText = asText;
    DictionaryEntry<String> loEntry;

    for (int i = 0; i < ioEntry.size(); i++)
    {
      loEntry = ioEntry.get(i);

      isCategory = loEntry.getCategory();
      isConcept = loEntry.getConcept();
      isStemmedConcept = loEntry.getStemmedConcept();

      try
      {
        ioResultList.add(parse());
      }
      catch (Exception ex)
      {
      }
    }

    return ioResultList;
  }

  public ParseResult createResultList(final String asText, final String asSearchString)
  {
    isText = asText;
    isStemmedConcept = asSearchString;

    ParseResult pr = null;
    try
    {
      pr = parse();
    }
    catch (Exception ex)
    {
    }

    return pr;
  }

  private ParseResult parse() throws Exception
  {
    ParseResult loResult = new ParseResult(isCategory, isConcept);

    int liIndex = 0;
    int liLen = isStemmedConcept.length();

    int liEnd = isText.length();

    char char1;
    char char2 = isStemmedConcept.charAt(0);
    boolean lbMatch = true;
    boolean lbEnd = false;
    int liCount = 0;

    while (true)
    {
      lbMatch = true;
      for (int i = 0; i < liLen; i++)
      {
        char1 = isText.charAt(liIndex);
        char2 = isStemmedConcept.charAt(i);

        if ('A' <= char1 && char1 <= 'Z')
          char1 = (char)(char1 + 32);

        if (char1 != char2)
        {
          lbMatch = false;
        }

        if (++liIndex >= liEnd)
        {
          lbEnd = true;

          if (i < liLen - 1)
          {
            lbMatch = false;
            break;
          }
        }

        if ((lbMatch == false) || (lbEnd == true))
          break;
      }

      if (lbMatch == true)
      {
//        System.out.println(liIndex - liLen);
//        loResult.addPosition(liIndex-liLen, liIndex-1);

        loResult.addPosition(liIndex-liLen, liLen);
        liCount++;
      }

      if (lbEnd == true)
        break;
    }

    loResult.setCount(liCount);

  //    parsing
    return loResult;
  }
}
