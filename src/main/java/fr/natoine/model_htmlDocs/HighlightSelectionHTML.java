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

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HighlightSelectionHTML implements Serializable
{
	private String id;
	private SelectionHTML selection ;
	private String style; // pour balancer un style direct dans le html
	private String info ; // pour infobulle au survol, title
	private String html_classe ; // pour ajouter un attribut class=[html_classe]
	
	public String getHtmlClasse() {
		return html_classe;
	}
	public void setHtmlClasse(String _html_classe) {
		html_classe = _html_classe;
	}
	/**
	 * Gets the effective Selection
	 * @return
	 */
	public SelectionHTML getSelection() {
		return selection;
	}
	/**
	 * Sets the effective Selection
	 * @param selection
	 */
	public void setSelection(SelectionHTML selection) {
		this.selection = selection;
	}
	/**
	 * Gets the htmlstyle to apply to highlight
	 * @return
	 */
	public String getStyle() {
		return style;
	}
	/**
	 * Sets the htmlstyle to apply to highlight
	 * @param style
	 */
	public void setStyle(String style) {
		this.style = style;
	}
	/**
	 * Sets the String to show when rolling over the highlighted selection
	 * @param info
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	/**
	 * Gets the String to show when rolling over the highlighted selection
	 * @return
	 */
	public String getInfo() {
		return info;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
}
