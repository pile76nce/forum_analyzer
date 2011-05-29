// Package
///////////////
package rs.etf.analyzer.ontology;


// Imports
///////////////
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.util.iterator.Filter;

import java.io.PrintStream;
import java.util.*;
import rs.etf.analyzer.parser.Concept;
import rs.etf.analyzer.parser.ConfigParser;


/**
 * <p>
 * Simple demonstration program to show how to list a hierarchy of classes. This
 * is not a complete solution to the problem (sub-classes of restrictions, for example,
 * are not shown).  It is inteded only to be illustrative of the general approach.
 * </p>
 *
 * @author Ian Dickinson, HP Labs
 *         (<a  href="mailto:Ian.Dickinson@hp.com" >email</a>)
 * @version CVS $Id: ClassHierarchy.java,v 1.1 2005/10/06 17:49:07 andy_seaborne Exp $
 */
public class ClassHierarchy {
    // Constants
    //////////////////////////////////

    // Static variables
    //////////////////////////////////

    // Instance variables
    //////////////////////////////////

    protected OntModel m_model;
    private Map m_anonIDs = new HashMap();
    private int m_anonCount = 0;

    private ConfigParser ioConfigParser;



    // Constructors
    public ClassHierarchy(ConfigParser aoConfigParser)
    {
      ioConfigParser = aoConfigParser;
    }
    //////////////////////////////////

    // External signature methods
    //////////////////////////////////

    /** Show the sub-class hierarchy encoded by the given model */
    public void showHierarchy( PrintStream out, OntModel m ) {
        // create an iterator over the root classes that are not anonymous class expressions
        Iterator i = m.listHierarchyRootClasses()
                      .filterDrop( new Filter() {
                                    public boolean accept( Object o ) {
                                        return ((Resource) o).isAnon();
                                    }} );

        while (i.hasNext())
        {
//            showClass( out, (OntClass) i.next(), new ArrayList(), 0 );

            createTree((OntClass) i.next(), 0);
        }
    }

    public Stack<Concept> createClassHierarchy(final String asFileName)
    {
      String lsFileURL = String.format("file:%s", asFileName);

      Stack<Concept> loClassHierarchy = new Stack<Concept>();

      OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );
      m.read(lsFileURL);

      Iterator i = m.listHierarchyRootClasses()
                      .filterDrop( new Filter() {
                                    public boolean accept( Object o ) {
                                        return ((Resource) o).isAnon();
                                    }} );

      while (i.hasNext())
        loClassHierarchy.addAll(createTree((OntClass)i.next(), 0));

      return loClassHierarchy;
    }

    public ArrayList<Concept> createConceptHierarchy(final String asFileName)
    {
      String lsFileURL = String.format("file:%s", asFileName);

      ArrayList<Concept> loConceptHierarchy = new ArrayList<Concept>();

      OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );
      m.read(lsFileURL);

      Iterator i = m.listClasses();/*m.listHierarchyRootClasses()
                      .filterDrop( new Filter() {
                                    public boolean accept( Object o ) {
                                        return ((Resource) o).isAnon();
                                    }} );*/

      while (i.hasNext())
        loConceptHierarchy.add(parse((OntClass)i.next()));

      return loConceptHierarchy;
    }

    // Internal implementation methods
    //////////////////////////////////

    /** Present a class, then recurse down to the sub-classes.
     *  Use occurs check to prevent getting stuck in a loop
     */
    public void showClass( PrintStream out, OntClass cls, List occurs, int depth ) {
        renderClassDescription( out, cls, depth );
        out.println();

        // recurse to the next level down
        if (cls.canAs( OntClass.class )  &&  !occurs.contains( cls )) {
            for (Iterator i = cls.listSubClasses( true );  i.hasNext(); ) {
                OntClass sub = (OntClass) i.next();

                // we push this expression on the occurs list before we recurse
                occurs.add( cls );
                showClass( out, sub, occurs, depth + 1 );
                occurs.remove( cls );
            }
        }
    }


    /**
     * <p>Render a description of the given class to the given output stream.</p>
     * @param out A print stream to write to
     * @param c The class to render
     */
    public void renderClassDescription( PrintStream out, OntClass c, int depth ) {
        indent( out, depth );

        if (c.isRestriction()) {
            renderRestriction( out, (Restriction) c.as( Restriction.class ) );
        }
        else {
            if (!c.isAnon()) {
                out.print( "Class " );
                renderURI( out, c.getModel(), c.getURI() );
//                System.out.print("added: " + c.getLabel(null));
                out.print( ' ' );
            }
            else {
                renderAnonymous( out, c, "class" );
            }
        }
    }

    /**
     * <p>Handle the case of rendering a restriction.</p>
     * @param out The print stream to write to
     * @param r The restriction to render
     */
    protected void renderRestriction( PrintStream out, Restriction r ) {
        if (!r.isAnon()) {
            out.print( "Restriction " );
            renderURI( out, r.getModel(), r.getURI() );
        }
        else {
            renderAnonymous( out, r, "restriction" );
        }

        out.print( " on property " );
        renderURI( out, r.getModel(), r.getOnProperty().getURI() );
    }

    /** Render a URI */
    protected void renderURI( PrintStream out, PrefixMapping prefixes, String uri ) {
        out.print( prefixes.shortForm( uri ) );
    }

    /** Render an anonymous class or restriction */
    protected void renderAnonymous( PrintStream out, Resource anon, String name ) {
        String anonID = (String) m_anonIDs.get( anon.getId() );
        if (anonID == null) {
            anonID = "a-" + m_anonCount++;
            m_anonIDs.put( anon.getId(), anonID );
        }

        out.print( "Anonymous ");
        out.print( name );
        out.print( " with ID " );
        out.print( anonID );
    }

    /** Generate the indentation */
    protected void indent( PrintStream out, int depth ) {
        for (int i = 0;  i < depth; i++) {
            out.print( "  " );
        }
    }

    public Stack<Concept> createTree(OntClass cls, int depth)
    {
      Stack<OntClass> occurs = new Stack<OntClass>();
      Stack<OntClass> found = new Stack<OntClass>();

      Stack<Concept> loFoundConcepts = new Stack<Concept>();

      /*while (true)
      {
        if (cls.canAs( OntClass.class )  &&  !occurs.contains( cls ))
        {
          // we push this expression on the occurs list before we recurse
          occurs.add(cls);
          for (Iterator i = cls.listSubClasses(true); i.hasNext(); )
          {
            OntClass sub = (OntClass) i.next();
            occurs.add(sub);
          }
          cls = (OntClass)occurs.remove(0);
        }

        if (occurs.isEmpty())
          break;
      }*/

      /*if (cls.canAs( OntClass.class ))
      {
        occurs.add(cls);
        found.add(cls);
      }*/

//      System.out.println(cls.getLabel(null));

      occurs.push(cls);

      while (true)
      {
//        cls = occurs.remove(0);
        if (occurs.isEmpty())
          break;

        cls = occurs.pop();

        Concept c = new Concept(cls);

        Iterator loSubIterator = cls.listSubClasses(true);
        ArrayList<Concept> loSub = new ArrayList<Concept>();

        if (loSubIterator.hasNext() == true)
        {
          while (loSubIterator.hasNext() == true)
          {
            OntClass sub = (OntClass) loSubIterator.next();

            if (sub.canAs(OntClass.class))
            {
//              System.out.println(sub.getLabel(null));
              occurs.push(sub);
              Concept subc = new Concept(sub);
              loSub.add(subc);
            }
          }
          c.addChildren(loSub);
          loFoundConcepts.push(c);

          found.push(cls);
        }
        else
        {
//          c.addChildren(loSub);
          loFoundConcepts.push(c);

//          evaluate
          found.push(cls);
        }

        /*if (occurs.size() > 0)
          cls = occurs.pop();
        else
          break;*/
      }

//      found.add(cls);

      /*while (!found.isEmpty())
      {
        cls = found.pop();
        System.out.println(String.format("label: %s", cls.getLabel(null)));
      }*/

      return loFoundConcepts;
    }

    public Concept parse(OntClass cls)
    {
      Concept loRetConcept = new Concept(cls);
      Concept loCurrConcept = null;
      ArrayList<Concept> loRetConceptList = new ArrayList<Concept>();
      ArrayList<Concept> loSubConceptList = new ArrayList<Concept>();
      ArrayList<OntClass> occurs = new ArrayList<OntClass>();

      if (cls.getLocalName() == null)
        return null;

//      loSubConceptList.add(new Concept(cls));
      Concept loConcept = new Concept(cls);
      loSubConceptList.add(loConcept);

      ioConfigParser.addConcept(loConcept);

      Concept loSubConcept = null;

      while (!loSubConceptList.isEmpty())
      {
        loSubConcept = loSubConceptList.remove(0);

        Iterator loSubIterator = loSubConcept.getOntClass().listSubClasses(true);
        while (loSubIterator.hasNext())
        {
          OntClass sub = (OntClass) loSubIterator.next();

            if (sub.canAs(OntClass.class) && (sub.getLocalName() != null))
            {
              Concept subc = new Concept(sub);
              if (sub.hasSubClass())
                loSubConceptList.add(subc);

              Concept c = loConcept.find(loSubConcept);

              ioConfigParser.addConcept(subc);

              if (c == null)
                loConcept.add(subc);
              else
                loConcept.find(loSubConcept).add(subc);
            }
        }
      }

      return loConcept;
    }
}

