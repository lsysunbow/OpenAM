//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:33:54 AM PDT 
//


package com.sun.identity.liberty.ws.common.jaxb.xmlenc;


/**
 * Java content class for EncryptedKeyType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/allan/A-SVN/trunk/opensso/products/federation/library/xsd/liberty/xenc-schema.xsd line 101)
 * <p>
 * <pre>
 * &lt;complexType name="EncryptedKeyType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.w3.org/2001/04/xmlenc#}EncryptedType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.w3.org/2001/04/xmlenc#}ReferenceList" minOccurs="0"/>
 *         &lt;element name="CarriedKeyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Recipient" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface EncryptedKeyType
    extends com.sun.identity.liberty.ws.common.jaxb.xmlenc.EncryptedType
{


    /**
     * Gets the value of the referenceList property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlenc.ReferenceListType}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlenc.ReferenceListElement}
     */
    com.sun.identity.liberty.ws.common.jaxb.xmlenc.ReferenceListType getReferenceList();

    /**
     * Sets the value of the referenceList property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlenc.ReferenceListType}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlenc.ReferenceListElement}
     */
    void setReferenceList(com.sun.identity.liberty.ws.common.jaxb.xmlenc.ReferenceListType value);

    /**
     * Gets the value of the carriedKeyName property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getCarriedKeyName();

    /**
     * Sets the value of the carriedKeyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setCarriedKeyName(java.lang.String value);

    /**
     * Gets the value of the recipient property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getRecipient();

    /**
     * Sets the value of the recipient property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setRecipient(java.lang.String value);

}
