<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" omit-xml-declaration="no" />
<xsl:template match="/template">

  <!-- root variables -->
  <xsl:variable name="template_key" select="@key"/>
  <xsl:variable name="keyword" select="@keyword"/>
  <xsl:variable name="description" select="@description"/>
  <xsl:variable name="image_root" select="concat(@image-root,'/')"/>

  <!-- otput "TEXTAREA" -->
  <textarea>

    <!-- set attribute "value" -->
    <xsl:attribute name="value">
      <xsl:apply-templates select="property[@name='text']"/>
    </xsl:attribute>

    <!-- set attribute "maxlength" -->
    <xsl:attribute name="maxlength">
      <xsl:apply-templates select="property[@name='maxLength']"/>
    </xsl:attribute>

    <!-- set attribute "readonly" -->
    <xsl:if test="property[@name='readOnly']/@value='true'">
      <xsl:attribute name="readonly">true</xsl:attribute>
    </xsl:if>

    <!-- set attribute "size" -->
    <xsl:attribute name="size">
      <xsl:apply-templates select="property[@name='size']"/>
    </xsl:attribute>

    <!-- set attribute "css" -->
    <xsl:if test="property[@name='cssClass']/@value!=''">
      <xsl:attribute name="class">
        <xsl:apply-templates select="property[@name='cssClass']"/>
      </xsl:attribute>
    </xsl:if>

    <!-- set attribute "style" -->
    <xsl:attribute name="style">

      <!-- set attribute "ime-mode" -->
      ime-mode:active;

      <!-- set attribute "text-align" -->
      text-align:left;

      <!-- set attribute "readonly's color" -->
      <xsl:if test="property[@name='readOnly']/@value='true'">
        color:#000000
      </xsl:if>

    </xsl:attribute>

  </textarea>

</xsl:template>

<xsl:template match="property">
  <xsl:value-of select="@value"/>
</xsl:template>

</xsl:stylesheet>
