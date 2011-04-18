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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import fr.natoine.model_resource.URI;

@Entity
@Table(name = "WEBPAGE")
@XmlRootElement
public class WebPage extends DocumentHTML 
{
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = URI.class)
	@JoinColumn(name = "PRINCIPALURLURI_ID")
	private URI principalURL;

	/**
	 * Gets the principal URL to access the WebPage
	 * A WebPage may have many different URL to be accessed.
	 * It may be important to define the principal one
	 * @return
	 */
	public URI getPrincipalURL() {
		return principalURL;
	}
	/**
	 * Sets the principal URL to access the WebPage
	 * A WebPage may have many different URL to be accessed.
	 * It may be important to define the principal one
	 * @param principalURL
	 */
	public void setPrincipalURL(URI principalURL) {
		this.principalURL = principalURL;
	}
	
	/**
	 * Gets a html representation of the WebPage
	 * @return a String that is a html representation of the WebPage
	 */
	public String toHTML()
	{
		String _html =
			"<div class=resource_header>" + getClass().getSimpleName() + " : <a href=" + getRepresentsResource().getEffectiveURI() + ">" + getLabel() + "</a></div>";
		return _html;
	}
	
	/**
	 * Gets a full html representation of the WebPage
	 * @return a String that is a html representation of the WebPage
	 */
	public String toHTMLMax()
	{
		String _html =
			"<div class=resource_header>" + getClass().getSimpleName() + " : <a href=" + getRepresentsResource().getEffectiveURI() + ">" + getLabel() + "</a></div>"
			+ "<div class=creation>Créée le : " + getCreation() + " via : " + getContextCreation() + "</div>" ;
		return _html;
	}
	
	public String toRDF(String _url_rdf_resource, String _rdf_toinsert)
	{
		String _rdf = "<foaf:Document rdf:about=\""+ _url_rdf_resource + "?id=" + getId() +"\" >" ;
		if(! _url_rdf_resource.equalsIgnoreCase(getAccess().getEffectiveURI()))
			_rdf = _rdf.concat("<rdfs:seeAlso rdf:resource=\"" + getAccess().getEffectiveURI() + "\" />");
		if(! getPrincipalURL().getEffectiveURI().equalsIgnoreCase(getAccess().getEffectiveURI()))
			_rdf = _rdf.concat("<rdfs:seeAlso rdf:resource=\"" + getPrincipalURL().getEffectiveURI() + "\" />");
		_rdf = _rdf.concat("<rdfs:label>"+ this.getLabel() +"</rdfs:label>");
		_rdf = _rdf.concat("<dcterms:created>" + this.getCreation() + "</dcterms:created>");
		_rdf = _rdf.concat("<dcterms:title>" + this.getLabel() + "</dcterms:title>");
		if(getHTMLContent() != null)_rdf = _rdf.concat("<content:encoded><![CDATA[" + getHTMLContent() + "]]></content:encoded>");
		if(_rdf_toinsert != null && _rdf_toinsert.length() > 0) _rdf = _rdf.concat(_rdf_toinsert);
		_rdf = _rdf.concat("</foaf:Document>");
		return _rdf;
	}
}
