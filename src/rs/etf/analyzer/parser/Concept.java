package rs.etf.analyzer.parser;

import java.util.ArrayList;
import com.hp.hpl.jena.ontology.OntClass;
import rs.etf.analyzer.post.Post;
import java.util.Collections;
import rs.etf.analyzer.util.LogSigActivationFunction;
import java.util.StringTokenizer;

/**
 * Klasa <code>Concept</code> koja reprezentuje koncept iz ontologije</p>
 *
 * @author Dejan Prodanovi\u0107
 * @version 1.0
 */
public class Concept implements Comparable
{
  private Post ioPost;
  private OntClass ioOntClass;

  private String isLabel;

  private int iiConceptWeight;

  private final int[] weights = {15, 20, 30};

  private final float K1 = (float)0.2;

  /**
   * Maksimalna parcijalna ocena na osnovu relevantnosti koncepta
   */
  private final float K2 = (float)0.5;

  /**
   * Maksimalna parcijalna ocena na osnovu zastupljenosti koncepta u postu
   */
  private final float K3 = (float)0.3;

  private int iiConceptCount;
  private int iiCountAllTokens;

  /**
   * broj podkoncepata
   */
  private int iiNumOfSubConcepts;

  /**
   * broj ocenjenih podkoncepata
   */
  private int iiNumOfScoringSubConcepts;

  /**
   * da li je koncept ocenjen
   */
  private boolean ibHasScore = false;

  /**
   * parcijalna ocena koncepta
   */
  private int iiScore;

  /**
   * parcijalna ocena koncepta
   */
  private double idScore;

  /**
   * ukupna ocena koncepta
   */
  private float ifTotalScore;

  private boolean ibLeaf;

  /**
   * lista potomaka
   */
  private ArrayList<Concept> ioChildrenList = new ArrayList<Concept>();

  /**
   * lista naziva
   */
  private ArrayList<Label> ioLabelList = new ArrayList<Label>();

  private LogSigActivationFunction ioActivationFunction = new LogSigActivationFunction();

  public Concept(Concept aoConcept)
  {
    this(aoConcept.getOntClass());
  }

  public Concept(OntClass aoOntClass)
  {
    ioOntClass = aoOntClass;

    isLabel = ioOntClass.getLabel(null);

    if (isLabel != null)
    {
      StringTokenizer loStringTokenizer = new StringTokenizer(isLabel, ";");

      while (loStringTokenizer.hasMoreTokens() == true)
      {
        String lsLabel = loStringTokenizer.nextToken();

//        odredjivanje duzine koncepta
        int liConceptLength = isLabel.split("\\;").length;

//        postavljanje pocetnih koeficijenata u zavisnosti od duzine koncepta
        if (liConceptLength == 1)
          iiConceptWeight = weights[0];
        else
        if (liConceptLength == 2)
          iiConceptWeight = weights[1];
        else
        if (liConceptLength >= 3)
          iiConceptWeight = weights[2];

//        dodavanje naziva koncepta u listu
        ioLabelList.add(new Label(lsLabel, iiConceptWeight));
      }
    }
  }

  public Concept(OntClass aoOntClass, ArrayList<Concept> a)
  {
    ioOntClass = aoOntClass;

    isLabel = ioOntClass.getLabel(null);

    ioChildrenList.addAll(a);

//    if (hasChildren()) calculate();
  }

  public Concept(OntClass aoOntClass, Post aoPost)
  {
    ioOntClass = aoOntClass;

    isLabel = ioOntClass.getLabel(null);
  }

  public void setLabel(String asLabel)
  {
    isLabel = asLabel;
  }

  public ArrayList<Label> getLabelList()
  {
    return ioLabelList;
  }

  public void setPost(Post aoPost)
  {
    ioPost = aoPost;

    Label loLabel = null;

    try
    {
      for (int i = 0; i < ioLabelList.size(); i++)
      {
        loLabel = ioLabelList.get(i);
        String lsLabel = loLabel.getName();

//        da li se neki od naziva koncepta pojavljuje u postu
        if (resolve(lsLabel) == true)
          break;
      }
    }
    catch (Exception e)
    {
      System.out.println(isLabel);
    }
  }

  /**
   * Metoda koja utvrdjuje da li se koncept nalazi u postu
   * @param asLabel String
   * @return boolean
   */
  public boolean resolve(String asLabel)
  {
    if (asLabel == null)
      return false;

    StemmedTokenDictionary loStemmedTokenDictionary = ioPost.getStemmedTokenDictionary();
    ConceptDictionaryEntry loCDE = ioPost.getConceptDictionary().getEntry(asLabel);

    StemmedTokenDictionaryEntry loSTDE = loStemmedTokenDictionary.getEntry(asLabel);

    ArrayList<ConceptDictionaryEntry> loFoundPositionList = new ArrayList<ConceptDictionaryEntry> ();
    String[] loStemmedConcept = null;
    try
    {
      loStemmedConcept = loCDE.getStemmedConceptArray();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    ArrayList<Position> loPositionList = new ArrayList<Position> ();
    ArrayList<Position> p = null;

    for (int i = 0; i < loStemmedConcept.length; i++)
    {
      loSTDE = loStemmedTokenDictionary.getEntry(loStemmedConcept[i]);

      if (loSTDE != null)
      {
        p = loSTDE.getPositionList();
        loPositionList.addAll(p);
      }
    }

    Collections.sort(loPositionList);

    int liPreviousTokenId;
    int liCurrentTokenId;

    String lsPreviousToken;
    String lsCurrentToken;

    for (int i = 0; i <= loPositionList.size() - loStemmedConcept.length; i++)
    {
      boolean lbContinue = true;
      liPreviousTokenId = loPositionList.get(i).getTokenId();
      lsPreviousToken = loPositionList.get(i).getToken();

      for (int j = 1; j < loStemmedConcept.length; j++)
      {
        liCurrentTokenId = loPositionList.get(i + j).getTokenId();
        lsCurrentToken = loPositionList.get(i + j).getToken();

        if ( (liCurrentTokenId - liPreviousTokenId == 1) &&
            (lsCurrentToken.equals(lsPreviousToken) == false))
        {
          liPreviousTokenId = liCurrentTokenId;
          lsPreviousToken = lsCurrentToken;
        }
        else
        {
          lbContinue = false;
          break;
        }
      }

      if (lbContinue)
      {
        loCDE.addPosition(loPositionList.get(i));
        loFoundPositionList.add(loCDE);
      }
    }

    if (loCDE != null)
      iiConceptCount = loCDE.get().size();

    iiCountAllTokens = loStemmedTokenDictionary.getNonNoiseTokens();

    return (iiConceptCount > 0) ? true : false;
  }

  public OntClass getOntClass()
  {
    return ioOntClass;
  }

  public void add(Concept a)
  {
    ioChildrenList.add(a);
  }

  public Concept remove(int i)
  {
    return ioChildrenList.remove(i);
  }

  public Concept find(Concept c)
  {
    int i = ioChildrenList.lastIndexOf(c);

    return (i > -1) ? ioChildrenList.get(i) : null;
  }

  public void addChildren(ArrayList<Concept> a)
  {
    ioChildrenList.addAll(a);
  }

  public int getNumOfSubConcepts()
  {
    return iiNumOfSubConcepts;
  }

  public int getNumOfScoringSubConcepts()
  {
    return iiNumOfScoringSubConcepts;
  }

  public int calculate()
  {
    int liChildCalc = 10000;

    iiScore = (iiConceptCount > 0) ? iiConceptWeight : 0;
    iiNumOfScoringSubConcepts += (iiScore > 0) ? 1 : 0;
    iiNumOfSubConcepts += 1;

    if (hasChildren())
    {
      float t = 0;

      for (int i = 0; i < ioChildrenList.size(); i++)
      {
        Concept c = ioChildrenList.get(i);
        c.setPost(ioPost);

        liChildCalc = liChildCalc*(100 - c.calculate()) / 100;

        iiNumOfSubConcepts += c.getNumOfSubConcepts();
        iiNumOfScoringSubConcepts += c.getNumOfScoringSubConcepts();
      }

      liChildCalc = liChildCalc*(100 - iiScore) / 100;

      iiScore = (100 * (10000 - liChildCalc) + 5000) / 10000;
    }

    ibHasScore = true;

    ioPost.updateConcept(this);

    return iiScore;
  }

  /**
   * Ova metoda se poziva iz koncepta roditelja. Izracunavanje ocene koncepta za podstablo
   * @return Ocena koncepta
   */
  public double calculate1()
  {
//    parcijalna ocena na osnovu broja pojavljivanja
    idScore = (double)((iiConceptCount > 0) ? iiConceptWeight : 0);

//    povecanje broja podkoncepata sa ocenom vecom od 0 (racuna se i sam koncept)
    iiNumOfScoringSubConcepts += (idScore > 0) ? 1 : 0;

//    povecanje broja podkoncepata (racuna se i sam koncept)
    iiNumOfSubConcepts += 1;

//    suma ocena od potomaka
    double ldChildScore = 0;

//    ako koncept ima potomke
    if (hasChildren())
    {
      for (int i = 0; i < ioChildrenList.size(); i++)
      {
//        uzima se koncept iz liste
        Concept c = ioChildrenList.get(i);
//        konceptu se pridruzuje post koji se analizira
        c.setPost(ioPost);
//        povecava se suma ocena potomaka
        ldChildScore += c.calculate1();//ioActivationFunction.activation(c.calculate1());
//        idScore += ioActivationFunction.activation(c.calculate1());

//        povecava se broj podkoncepata
        iiNumOfSubConcepts += c.getNumOfSubConcepts();
//        povecava se broj koncepata sa ocenom vecom od 0
        iiNumOfScoringSubConcepts += c.getNumOfScoringSubConcepts();
      }

//      racunanje ocene na osnovu kolicnika sume ocena podkoncepata i ukupnog broja podkoncepata
      idScore += ldChildScore / ioChildrenList.size();
    }

//    koncept je ocenjen
    ibHasScore = true;

//    izmena koncepta za post
    ioPost.updateConcept(this);

    return idScore;
  }

  /**
   * Metoda kojom se odredjuje parcijalna ocena na osnovu relevantnosti koncepta na skali od 0 do 1 (0 - 100%)
   * @return float Parcijalna ocena na osnovu relevantnosti koncepta na skali od 0 do 1 (0 - 100%)
   */
  public float conceptRelevance()
  {
    try
    {
      float f = (float) ( iiNumOfScoringSubConcepts * 100 / iiNumOfSubConcepts);

      return f;
    }
    catch (Exception e)
    {
      return (float)0;
    }
  }

  /**
   * Metoda kojom se odredjuje parcijalna ocena na osnovu zastupljenosti koncepta u postu na skali od 0 do 1 (0 - 100%)
   * @return Parcijalna ocena na osnovu zastupljenosti koncepta na skali od 0 do 1 (0 - 100%)
   */
  public float documentRelevance()
  {
    try
    {
      return (float)java.lang.StrictMath.min(100, iiConceptCount * 100.0 / iiCountAllTokens);
    }
    catch (Exception e)
    {
      return 0;
    }
  }

  /**
   *
   * @return Da li je koncept ocenjen
   */
  public boolean hasScore()
  {
    return ibHasScore;
  }

  public float getScore()
  {
//    if (ibHasScore == false)
//      return calculate();

    return iiScore;
  }

  /**
   * Izracunavanje ukupne ocene koncepta
   * @return Ukupna ocena koncepta
   */
  public float getTotalScore()
  {
    ifTotalScore = (float)(0.2 * idScore + 0.5 * conceptRelevance() + 0.3 * documentRelevance());

    return ifTotalScore;
  }

  /**
   * Da li koncept ima potomke
   * @return boolean
   */
  public boolean hasChildren()
  {
    return (ioChildrenList.size() > 0);
  }

  /**
   * Metoda koja vraca naziv koncepta
   * @return String
   */
  public String getConcept()
  {
    return isLabel;
  }

  /**
   * Metoda koja vraca listu sa potomcima
   * @return ArrayList
   */
  public ArrayList<Concept> getChildren()
  {
    return ioChildrenList;
  }

  /**
   * Poredjenje koncepta sa
   * @param o Object
   * @return 1 ako je ukupna ocena veca od
   *        -1 ako je ukupna ocena manja od
   *         0 ako su ukupne ocene jednake
   */
  public int compareTo(Object o)
  {
    try
    {
      Concept c = (Concept)o;
      if (getTotalScore() > c.getTotalScore())
        return 1;
      else
        if (getTotalScore() < c.getTotalScore())
          return -1;

      return 0;
    }
    catch (ClassCastException cce)
    {
      return 0;
    }
  }

  public String toString()
  {
    OntClass loSuperClass = ioOntClass.getSuperClass();
    String lsSuperClass = (loSuperClass != null) ? loSuperClass.getLocalName() : "";

    return String.format("class: %s, label: %s, total score: %f, score: %f, cr: %f, dr: %f", ioOntClass.getLocalName(), isLabel, getTotalScore(), idScore, conceptRelevance(), iiConceptCount*1.0);
  }
}
