package rs.etf.analyzer.post;

import rs.etf.analyzer.parser.TokenDictionary;
import rs.etf.analyzer.parser.ConceptDictionary;
import rs.etf.analyzer.parser.StemmedTokenDictionary;
import rs.etf.analyzer.parser.Concept;

/**
 * Interfejs <code>Post</code> služi za rad sa postovima. Sadrži metodu za pore\u0111enje sa
 * drugim postovima.
 *
 * @author Dejan Prodanovi\u0107
 * @version 1.0
 */
public interface Post
{
  /**
   * Sadrzaj posta
   * @return Sadrzaj posta
   */
  public String getPost();

  /**
   * Vrsta posta
   * @return PostType
   */
  public PostType getPostType();

  /**
   * Recnik sa tokenima
   * @return Recnik sa tokenima procitanih iz posta
   */
  public TokenDictionary getTokenDictionary();

  public StemmedTokenDictionary getStemmedTokenDictionary();

  /**
   * Recnik sa konceptima
   * @return ConceptDictionary
   */
  public ConceptDictionary getConceptDictionary();

  /**
   * Izmena odgovarajuceg koncepta u recniku sa konceptima
   * @param aoConcept Concept
   */
  public void updateConcept(Concept aoConcept);

  /**
   * Poredjenje posmatranog posta sa odredjenim postom
   * @param aoPost Post
   * @return float
   */
  public float compareTo(final Post aoPost);
}
