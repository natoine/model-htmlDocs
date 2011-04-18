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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import fr.natoine.model_document.Document;

@Entity
@Table(name = "DOCUMENTHTML")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@XmlRootElement
public class DocumentHTML extends Document implements HTMLContentable
{
	@Lob
	@Column(name = "HTMLCONTENT")
	private String HTMLContent;

	/**
	 * Gets the HTML Content of the HTML Document
	 * @return
	 */
	public String getHTMLContent() {
		return HTMLContent;
	}
	/**
	 * Sets the HTML Content of the HTML Document
	 * @param hTMLContent
	 */
	public void setHTMLContent(String hTMLContent) {
		HTMLContent = hTMLContent;
	}
	
	public String toRDF(String _url_rdf_resource, String _rdf_toinsert)
	{
		String _rdf = "<foaf:Document rdf:about=\""+ _url_rdf_resource + "?id=" + getId() +"\" >" ;
		if(! _url_rdf_resource.equalsIgnoreCase(getAccess().getEffectiveURI()))
		{
			_rdf = _rdf.concat("<rdfs:seeAlso rdf:resource=\"" + getAccess().getEffectiveURI() + "\" />");
		}
		_rdf = _rdf.concat("<rdfs:label>"+ this.getLabel() +"</rdfs:label>");
		_rdf = _rdf.concat("<dcterms:created>" + this.getCreation() + "</dcterms:created>");
		_rdf = _rdf.concat("<dcterms:title>" + this.getLabel() + "</dcterms:title>");
		if(HTMLContent != null)_rdf = _rdf.concat("<content:encoded><![CDATA[" + HTMLContent + "]]></content:encoded>");
		if(_rdf_toinsert != null && _rdf_toinsert.length() > 0) _rdf = _rdf.concat(_rdf_toinsert);
		_rdf = _rdf.concat("</foaf:Document>");
		return _rdf;
	}
	
	public List<String> rdfHeader()
	{
		ArrayList<String> _nms = new ArrayList<String>();
		_nms.add("xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"");
		_nms.add("xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"");
		_nms.add("xmlns:dcterms=\"http://purl.org/dc/terms/\"");
		_nms.add("xmlns:foaf=\"http://xmlns.com/foaf/0.1/\"");
		_nms.add("xmlns:content=\"http://purl.org/rss/1.0/modules/content/\"");
		_nms.add("xmlns:annotea=\"http://www.w3.org/2000/10/annotation-ns\"");
		return _nms ;
	}
}
