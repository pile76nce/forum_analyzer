package rs.etf.analyzer.post;

import rs.etf.analyzer.parser.ConceptDictionary;
import rs.etf.analyzer.parser.TokenDictionary;
import java.util.ArrayList;
import rs.etf.analyzer.parser.ConfigParser;
import rs.etf.analyzer.parser.tokens.TextTokenizer;
import rs.etf.analyzer.parser.Concept;

/**
 * Klasa <code>PostAnalyzer</code> služi za analizu postova
 *
 * @author Dejan Prodanović
 * @version 1.0
 */
public class PostAnalyzer
{
  /**
   * Recnik sa konceptima
   */
  private ConceptDictionary ioConceptDictionary = null;

  /**
   * Lista u koju se smestaju postovi za analizu
   */
  private ArrayList<Post> ioPostList = new ArrayList<Post>();

  /**
   * Parser za citanje datoteka konfiguracije
   */
  private ConfigParser ioConfigParser = new ConfigParser();

  private TextTokenizer ioTokenizer = null;

  private TokenDictionary ioTokenDictionary = null;

  public PostAnalyzer()
  {
    try
    {
      init();
    }
    catch (Exception ex)
    {
    }
  }

  private void init() throws Exception
  {
//    Parsiranje ontologije i formiranje recnika sa konceptima
    ioConfigParser.parseOntology();
    ioConceptDictionary = ioConfigParser.getConceptDictionary();
  }

  /**
   * Dodavanje posta u listu za analizu
   * @param aoPost Post
   */
  public void addPost(final Post aoPost)
  {
    ioPostList.add(aoPost);
  }

  /**
   * Ova metoda vraca recnik sa konceptima
   * @return ConceptDictionary
   */
  public final ConceptDictionary getConceptDictionary()
  {
    return ioConceptDictionary;
  }

  /**
   * Metoda u kojoj se vrsi analiza postova
   */
  public void analyze()
  {
//    Analizira se svaki post iz liste
    for (int i = 0; i < ioPostList.size(); i++)
      analyzePost(ioPostList.get(i));
  }

  /**
   * Metoda u kojoj se vrsi analiza odredjenog posta
   * @param aoPost Post
   */
  private void analyzePost(final Post aoPost)
  {
    final String lsPost = aoPost.getPost();
//    iz posta se uzimaju svi tokeni i formira recnik tokena
    ioTokenizer.getAllTokens(lsPost);
//    recnik sa tokenima
    ioTokenDictionary = ioTokenizer.getTokenDictionary();

//    lista koncepata koja se uzima iz stabla
    ArrayList<Concept> loConceptHierarchy = ioConfigParser.getConceptHierarchy();

    for (int i = 0; i < loConceptHierarchy.size(); i++)
    {
      Concept c = loConceptHierarchy.get(i);

      if (c != null)
      {
        c.setPost(aoPost);
        c.calculate1();
      }
    }
  }
}
