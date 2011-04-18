/*
 * Copyright 2010 Antoine Seilles (Natoine)
 *   This file is part of model-htmlDocs.

    model-htmlDocs is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    model-htmlDocs is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with model-htmlDocs.  If not, see <http://www.gnu.org/licenses/>.

 */
package fr.natoine.model_htmlDocs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import fr.natoine.model_annotation.Selection;

@Entity
@Table(name = "SELECTIONHTML")
@XmlRootElement
public class SelectionHTML extends Selection implements HTMLContentable
{
	@Column(name = "XPOINTERBEGIN" , nullable=false)
	private String xpointerBegin;
	
	@Column(name = "XPOINTEREND" , nullable=false)
	private String xpointerEnd;
	
	@Lob
	@Column(name = "HTMLCONTENT" , nullable=false)
	private String HTMLContent;

	/**
	 * Gets the HTML Content of the html Selection
	 * @return
	 */
	public String getHTMLContent() {
		return HTMLContent;
	}
	/**
	 * Sets the HTML Content of the html Selection
	 */
	public void setHTMLContent(String hTMLContent) {
		HTMLContent = hTMLContent;
	}	
	/**
	 * Gets the XPointerBegin of the html Selection
	 * @return
	 */
	public String getXpointerBegin() {
		return xpointerBegin;
	}
	/**
	 * Sets the XPointerBegin of the html Selection
	 * @param xpointerBegin
	 */
	public void setXpointerBegin(String xpointerBegin) {
		this.xpointerBegin = xpointerBegin;
	}
	/**
	 * Gets the XPointerEnd of the html Selection
	 * @return
	 */
	public String getXpointerEnd() {
		return xpointerEnd;
	}
	/**
	 * Sets the XPointerEnd of the html Selection
	 * @param xpointerEnd
	 */
	public void setXpointerEnd(String xpointerEnd) {
		this.xpointerEnd = xpointerEnd;
	}
	
	public String toHTML()
	{
		String _html =
			"<div class=resource_header>" + 
			" <span class=selection_origin>Cette sélection est un fragment de : <a href=" + getSelectionOrigin().getRepresentsResource().getEffectiveURI() + ">" + getSelectionOrigin().getLabel() + " </a></span>" + 
			"</div>";
		_html = _html.concat("<div class=selection_content_header>Contenu de la sélection : </div><div class=selection_content>" + HTMLContent + "</div>");
		return _html;
	}
	
	public String toHTMLMax()
	{
		String _html =
			"<div class=resource_header>" + 
			"<span class=resource_type>" + getClass().getSimpleName() + "</span> : <a href=" + getRepresentsResource().getEffectiveURI() + "?id=" + getId() + ">" + getLabel() + "</a>" + 
			" <span class=selection_origin>Cette sélection est un fragment de : <a href=" + getSelectionOrigin().getRepresentsResource().getEffectiveURI() + ">" + getSelectionOrigin().getLabel() + " </a></span>" + 
			"</div>"
			+ "<div class=creation>Créée le : " + getCreation() + " via : " + getContextCreation() + "</div>" ;
		_html = _html.concat("<div class=selection_content_header>Contenu de la sélection : </div><div class=selection_content>" + HTMLContent + "</div>");
		return _html;
	}
	
	public String getTrueXPointer()
	{
		String _xpointer_begin = xpointerBegin ;
		if(_xpointer_begin.indexOf("(") != -1) _xpointer_begin = _xpointer_begin.substring(_xpointer_begin.indexOf("(")+1 , _xpointer_begin.length()-1);
		String _xpointer_end = xpointerEnd ;
		if(_xpointer_end.indexOf("(") != -1) _xpointer_end = _xpointer_end.substring(_xpointer_end.indexOf("(")+1 , _xpointer_end.length()-1);
		String _true_xpointer = this.getSelectionOrigin().getRepresentsResource().getEffectiveURI() + "#xpointer(" + _xpointer_begin + "/range-to("+ _xpointer_end + "))" ;
		return _true_xpointer ;
	}
	
	public String toRDF(String _url_rdf_resource, String _rdf_toinsert)
	{	
		String _rdf = "<nicetag:PartOfWebRepresentation rdf:about=\"".concat(getTrueXPointer().replaceAll("\"", "\'")).concat("\" >") ;
		if(! _url_rdf_resource.equalsIgnoreCase(getRepresentsResource().getEffectiveURI())) _rdf = _rdf.concat("<rdfs:seeAlso rdf:resource=\"" + getRepresentsResource().getEffectiveURI() + "\" />");
		_rdf = _rdf.concat("<rdfs:seeAlso rdf:resource=\"" + _url_rdf_resource + "?id=" + getId() + "\" />");
		if( this.getLabel()!=null) _rdf = _rdf.concat("<rdfs:label>"+ this.getLabel() +"</rdfs:label>");
		if(HTMLContent != null)_rdf = _rdf.concat("<content:encoded><![CDATA[ " + HTMLContent + " ]]></content:encoded>");
		_rdf = _rdf.concat("<dcterms:created>" + this.getCreation() + "</dcterms:created>");
		if(_rdf_toinsert != null && _rdf_toinsert.length() > 0) _rdf = _rdf.concat(_rdf_toinsert);
		_rdf = _rdf.concat("</nicetag:PartOfWebRepresentation>");
		return _rdf;
	}
	
	public List<String> rdfHeader()
	{
		ArrayList<String> _nms = new ArrayList<String>();
		_nms.add("xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"");
		_nms.add("xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"");
		_nms.add("xmlns:dcterms=\"http://purl.org/dc/terms/\"");
		_nms.add("xmlns:content=\"http://purl.org/rss/1.0/modules/content/\"");
		_nms.add("xmlns:nicetag=\"http://ns.inria.fr/nicetag/2009/09/25/voc\"");//http://ns.inria.fr/nicetag/2009/09/25/voc.rdf
		_nms.add("xmlns:annotea=\"http://www.w3.org/2000/10/annotation-ns\"");
		//_nms.add("xmlns:irw=\"http://ontologydesignpatterns.org/ont/web/irw.owl\"");
		return _nms ;
	}
}