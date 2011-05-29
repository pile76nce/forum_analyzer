package rs.etf.analyzer.post;

import rs.etf.analyzer.parser.tokens.TextTokenizer;
import rs.etf.analyzer.parser.TokenDictionary;
import java.util.Iterator;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import rs.etf.analyzer.parser.ConfigParser;
import rs.etf.analyzer.parser.ConceptDictionary;
import rs.etf.analyzer.parser.Concept;
import java.util.ArrayList;
import rs.etf.analyzer.parser.StemmedTokenDictionary;
import rs.etf.analyzer.parser.NoiseWords;
import java.util.Collections;

/**
 *
 * Klasa <code>SubjectAnalyzer</code> reprezentuje post</p
 *
 * @author Dejan Prodanovi?
 * @version 1.0
 */
public class PostObject implements Post
{
  private String isPost;
  private TextTokenizer ioTokenizer;

  private TokenDictionary ioTokenDictionary;
  private StemmedTokenDictionary ioStemmedTokenDictionary;

  /**
   * Recnik sa konceptima
   */
  private ConceptDictionary ioConceptDictionary;

  /**
   * Parser za citanje datoteka konfiguracije
   */
  private ConfigParser ioConfigParser = new ConfigParser();

  public PostObject(final String asPost) throws Exception
  {
    isPost = asPost;

    ioTokenizer = new TextTokenizer();

    ioConfigParser.parseOntology();

    ioConceptDictionary = ioConfigParser.getConceptDictionary();

    analyze();
  }

  public PostObject(File document) throws FileNotFoundException, Exception
  {
    this(new Scanner(document).useDelimiter("\\Z").next());
  }

  /**
   * Metoda u kojoj se vrsi analiza posta
   */
  private void analyze()
  {
//    iz posta se uzimaju svi tokeni
    ioTokenizer.getAllTokens(isPost);
//    recnik sa tokenima
    ioTokenDictionary = ioTokenizer.getTokenDictionary();
//    recnik sa modifikovanim tokenima
    ioStemmedTokenDictionary = ioTokenizer.getStemmedTokenDictionary();

    ArrayList<Concept> loConceptHierarchy = ioConfigParser.getConceptHierarchy();

    for (int i = 0; i < loConceptHierarchy.size(); i++)
    {
      Concept c = loConceptHierarchy.get(i);

      if (c != null)
      {
        c.setPost(this);
        c.calculate1();
      }
    }

    ArrayList<Concept> loConceptList = ioConceptDictionary.getList();
    Collections.sort(loConceptList, Collections.reverseOrder());

    for (int i = 0; i < loConceptList.size(); i++)
      System.out.println(loConceptList.get(i));

    System.out.println();
  }

  /**
   * Izmena koeficijenta odgovarajuceg koncepta
   * @param aoConcept Concept
   */
  public void updateConcept(Concept aoConcept)
  {
    if (aoConcept.getOntClass().getLabel(null) != null)
    {
      try
      {
        ioConceptDictionary.updateEntry(aoConcept);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }

  /**
   * Metoda koja vraca sadrzaj posta
   * @return String
   */
  public String getPost()
  {
    return isPost;
  }

  public PostType getPostType()
  {
    return null;
  }

  /**
   * Metoda koja vraca recnik sa tokenima
   * @return Recnik sa tokenima
   */
  public TokenDictionary getTokenDictionary()
  {
    return ioTokenDictionary;
  }

  public StemmedTokenDictionary getStemmedTokenDictionary()
  {
    return ioStemmedTokenDictionary;
  }

  public ConceptDictionary getConceptDictionary()
  {
    return ioConceptDictionary;
  }

  /**
   * Poredjenje posmatranog posta sa odredjenim postom
   * @param aoPost Post
   * @return float
   */
  public float compareTo(final Post aoPost)
  {
    /*long count = 0;
//    Map<String, Integer> map2 = aoPost.getStemMap();
    Iterator<String> iter = stemCountMap.keySet().iterator();
    while (iter.hasNext())
    {
      String key = iter.next();
      Integer count1 = stemCountMap.get(key);
      Integer count2 = map2.get(key);

      if (count1 != null && count2 != null)
      {
        count += count1 + count2;
        //System.out.println(key);
      }
    }
    //System.out.println("stem_count="+stem_count);
    return (float) Math.sqrt( ( (float) (count * count) /
                               (double) (stem_count * aoPost.getStemCount()))) / 2f;*/
    long count = 0;
    StemmedTokenDictionary loStemmedTokenDictionary = aoPost.getStemmedTokenDictionary();

    Iterator<String> iter = ioStemmedTokenDictionary.getIterator();
    int liNonNoiseTokens = ioStemmedTokenDictionary.getNonNoiseTokens();
    while (iter.hasNext())
    {
      String key = iter.next();
      int count1 = ioStemmedTokenDictionary.getEntry(key).getListSize();
      int count2 = loStemmedTokenDictionary.getEntry(key).getListSize();

      if (count1 != 0 && count2 != 0)
        count += count1 + count2;
    }

    return (float) Math.sqrt( ( (float) (count * count) /
                                (double) (liNonNoiseTokens * loStemmedTokenDictionary.getNonNoiseTokens() ))) / 2f;
  }

  public static void main(String[] args)
  {
    try
    {
      NoiseWords.getAllNoiseWords();

      ArrayList<Post> loPostList = new ArrayList<Post>();

      /*loPostList.add(new AbstractPostObject(new File("./test_data/post_cache_11.txt")));
      loPostList.add(new AbstractPostObject(new File("./test_data/post_cache_12.txt")));
      loPostList.add(new AbstractPostObject(new File("./test_data/post_cache_13.txt")));
      loPostList.add(new AbstractPostObject(new File("./test_data/post_cache_14.txt")));
      loPostList.add(new AbstractPostObject(new File("./test_data/post_cache_15.txt")));
      loPostList.add(new AbstractPostObject(new File("./test_data/post_cache_16.txt")));
      loPostList.add(new AbstractPostObject(new File("./test_data/post_cache_17.txt")));
      loPostList.add(new AbstractPostObject(new File("./test_data/post_cache_18.txt")));
      loPostList.add(new AbstractPostObject(new File("./test_data/post_cache_19.txt")));

      loPostList.add(new AbstractPostObject(new File("./test_data/post_cache_21.txt")));
      loPostList.add(new AbstractPostObject(new File("./test_data/post_cache_22.txt")));
      loPostList.add(new AbstractPostObject(new File("./test_data/post_cache_23.txt")));
      loPostList.add(new AbstractPostObject(new File("./test_data/post_cache_24.txt")));*/

      loPostList.add(new PostObject(new File("./test_data/HW8 4Way Set Associative.txt")));
//      loPostList.add(new AbstractPostObject(new File("./test_data/test.txt")));

      float ldCurrent;
      float ldBest = 0;

      int liBestIndexI = 0;
      int liBestIndexJ = 0;

      Post loTempPost;
      for (int i = 0; i < loPostList.size() - 1; i++)
      {
        loTempPost = loPostList.get(i);
        for (int j = i+1; j < loPostList.size(); j++)
        {
          ldCurrent = loTempPost.compareTo(loPostList.get(j));
          if (ldCurrent > ldBest)
          {
            ldBest = ldCurrent;
            liBestIndexI = i;
            liBestIndexJ = j;

            System.out.println(String.format("%f; %d, %d", ldBest, liBestIndexI, liBestIndexJ));
          }
        }
      }

      System.out.println(String.format("%f; %d, %d", ldBest, liBestIndexI, liBestIndexJ));

//      System.out.println(String.format("%f", loPostList.get(3).compareTo(loPostList.get(4))));
//      System.out.println(String.format("%f", loPostList.get(1).compareTo(loPostList.get(2))));
//      System.out.println(String.format("%f", loPostList.get(1).compareTo(loPostList.get(2))));
    }
    catch(FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
