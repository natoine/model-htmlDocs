package fr.natoine.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import fr.natoine.model_htmlDocs.DocumentHTML;
import fr.natoine.model_htmlDocs.SelectionHTML;
import fr.natoine.model_htmlDocs.WebPage;
import fr.natoine.model_resource.URI;
import fr.natoine.model_resource.UriStatus;

import junit.framework.TestCase;

public class HtmlDocsTest  extends TestCase
{
	public HtmlDocsTest(String name) 
	{
	    super(name);
	}
	
	public void testCreate()
	{
		DocumentHTML _testDocument = new DocumentHTML();
		_testDocument.setContextCreation("DocumentHTML Test");
		_testDocument.setCreation(new Date());
		_testDocument.setLabel("test document HTML");
		URI _representsResource = new URI();
		_representsResource.setEffectiveURI("http://www.testURI.fr");
		_testDocument.setRepresentsResource(_representsResource);
		_testDocument.setAccess(_representsResource); // same URI to access and Represent the Document.
		_testDocument.setHTMLContent("<div></div>");
		
		Collection<URI> uris = new ArrayList<URI>() ;
		URI _testURI1 = new URI();
		_testURI1.setEffectiveURI("http://www.testURI1.fr");
		uris.add(_testURI1);
		URI _testURI2 = new URI();
		_testURI2.setEffectiveURI("http://www.testURI2.fr");
		uris.add(_testURI2);
		_testDocument.setUris(uris);
		
		Collection<UriStatus> _status = new ArrayList<UriStatus>() ;
		UriStatus _status1 = new UriStatus();
		_status1.setLabel("test père status");
		_status.add(_status1);
		UriStatus _status2 = new UriStatus();
		_status2.setLabel("test fils status");
		_status2.setFather(_status1);
		_status.add(_status2);
		_testDocument.setUrisStatus(_status);
		
		WebPage _testWP = new WebPage();
		_testWP.setContextCreation("DocumentHTML Test");
		_testWP.setCreation(new Date());
		_testWP.setLabel("test WebPage");
		URI _representsWP = new URI();
		_representsWP.setEffectiveURI("http://www.testURI.fr/WP");
		_testWP.setRepresentsResource(_representsWP);
		_testWP.setAccess(_representsWP); // same URI to access and Represent the Document.
		_testWP.setHTMLContent("<div></div>");
		_testWP.setPrincipalURL(_representsWP); // principal URL is the same than access in this test
		
		Collection<URI> urisWP = new ArrayList<URI>() ;
		URI _testURI1WP = new URI();
		_testURI1WP.setEffectiveURI("http://www.testURI1.fr/WP");
		urisWP.add(_testURI1WP);
		URI _testURI2WP = new URI();
		_testURI2WP.setEffectiveURI("http://www.testURI2.fr/WP");
		urisWP.add(_testURI2WP);
		_testWP.setUris(urisWP);
		
		Collection<UriStatus> _statusWP = new ArrayList<UriStatus>() ;
		UriStatus _status1WP = new UriStatus();
		_status1WP.setLabel("test père status WP");
		_statusWP.add(_status1WP);
		UriStatus _status2WP = new UriStatus();
		_status2WP.setLabel("test fils status WP");
		_status2WP.setFather(_status1WP);
		_statusWP.add(_status2WP);
		_testWP.setUrisStatus(_statusWP);
		
		SelectionHTML _testSelection = new SelectionHTML();
		_testSelection.setContextCreation("DocumentHTML Test");
		_testSelection.setCreation(new Date());
		_testSelection.setLabel("test Selection HTML");
		URI _representsResourceSelection = new URI();
		_representsResourceSelection.setEffectiveURI("http://www.testURISelectionHTML.fr");
		_testSelection.setRepresentsResource(_representsResourceSelection);
		//_testSelection.setAccess(_representsResource); // same URI to access and Represent the Document.
		_testSelection.setHTMLContent("copie de texte");
		_testSelection.setXpointerBegin("xpointerBegin");
		_testSelection.setXpointerEnd("xpointerEnd");
		_testSelection.setSelectionOrigin(_testDocument);
		
		Collection<URI> uris2 = new ArrayList<URI>() ;
		URI _testURI12 = new URI();
		_testURI12.setEffectiveURI("http://www.testURI1.fr/selection");
		uris2.add(_testURI12);
		URI _testURI22 = new URI();
		_testURI22.setEffectiveURI("http://www.testURI2.fr/selection");
		uris2.add(_testURI22);
		_testSelection.setUris(uris2);
		
		Collection<UriStatus> _status_2 = new ArrayList<UriStatus>() ;
		UriStatus _status12 = new UriStatus();
		_status12.setLabel("test père status selection");
		_status_2.add(_status12);
		UriStatus _status22 = new UriStatus();
		_status22.setLabel("test fils status selection");
		_status22.setFather(_status12);
		_status_2.add(_status22);
		_testSelection.setUrisStatus(_status_2);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("htmlDocs");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try{
        	tx.begin();
        	em.persist(_testDocument);
        	tx.commit();
        }
        catch(Exception e)
        {
        	 System.out.println( "[HTMLDocsTest] unable to persist DocumentHTML" );
        	 System.out.println(e.getStackTrace());
        }
        try{
        	tx.begin();
        	em.persist(_testSelection);
        	tx.commit();
        }
        catch(Exception e)
        {
        	 System.out.println( "[HTMLDocsTest] unable to persist SelectionHTML" );
        	 System.out.println(e.getStackTrace());
        }
        try{
        	tx.begin();
        	em.persist(_testWP);
        	tx.commit();
        }
        catch(Exception e)
        {
        	 System.out.println( "[HTMLDocsTest] unable to persist WebPage" );
        	 System.out.println(e.getStackTrace());
        }
        em.close();
	}
	
	public void testRetrieve()
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("htmlDocs");
        EntityManager newEm = emf.createEntityManager();
        EntityTransaction newTx = newEm.getTransaction();
        newTx.begin();
        List documents = newEm.createQuery("from DocumentHTML").getResultList();
        System.out.println( documents.size() + " documentHTML(s) found" );
        DocumentHTML loadedDocument ;
        for (Object u : documents) 
        {
        	loadedDocument = (DocumentHTML) u;
            System.out.println("[HTMLDocsTest] DocumentHTML id : " + loadedDocument.getId()  
            		+ " contextCreation : " + loadedDocument.getContextCreation()
            		+ " label : " + loadedDocument.getLabel()
            		+ " date : " + loadedDocument.getCreation()
            		+ " htmlcontent : " + loadedDocument.getHTMLContent()
            		);
        }
        newTx.commit();
        newEm.close();
        // Shutting down the application
        emf.close();
	}
}
