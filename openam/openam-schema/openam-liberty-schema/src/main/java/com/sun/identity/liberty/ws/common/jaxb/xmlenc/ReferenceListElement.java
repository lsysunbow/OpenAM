//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:33:54 AM PDT 
//


package com.sun.identity.liberty.ws.common.jaxb.xmlenc;


/**
 * Java content class for ReferenceList element declaration.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/allan/A-SVN/trunk/opensso/products/federation/library/xsd/liberty/xenc-schema.xsd line 128)
 * <p>
 * <pre>
 * &lt;element name="ReferenceList">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element name="DataReference" type="{http://www.w3.org/2001/04/xmlenc#}ReferenceType"/>
 *           &lt;element name="KeyReference" type="{http://www.w3.org/2001/04/xmlenc#}ReferenceType"/>
 *         &lt;/choice>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 */
public interface ReferenceListElement
    extends javax.xml.bind.Element, com.sun.identity.liberty.ws.common.jaxb.xmlenc.ReferenceListType
{


}
