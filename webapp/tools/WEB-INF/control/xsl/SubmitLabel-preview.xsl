<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" omit-xml-declaration="no" />
<xsl:template match="/template">

  <!-- root variables -->
  <xsl:variable name="template_key" select="@key"/>
  <xsl:variable name="keyword" select="@keyword"/>
  <xsl:variable name="description" select="@description"/>
  <xsl:variable name="image_root" select="concat(@image-root,'/')"/>

  <!-- write submitlabel -->
  <xsl:call-template name="writesubmitlabel" />

</xsl:template>

<xsl:template match="property">
  <xsl:value-of select="@value"/>
</xsl:template>

<!-- write pulldown -->
<xsl:template name="writesubmitlabel">

  <xsl:param name="cssClass" />

  <!-- otput "A" -->
  <a href = "#" onclick="return false;">

    <!-- set attribute "class" -->
    <xsl:if test="property[@name='cssClass']/@value!=''">
      <xsl:attribute name="class">
        <xsl:apply-templates select="property[@name='cssClass']"/>
      </xsl:attribute>
    </xsl:if>


    <!-- set attribute "text" -->
    <xsl:choose>

      <xsl:when test="property[@name='text']/@value!=''">
        <xsl:apply-templates select="property[@name='text']"/>
      </xsl:when>

      <xsl:when test="property[@name='resourceKey']/@value!=''">
        <xsl:text disable-output-escaping="yes"><![CDATA[&DISP_RESOURCE]]>{</xsl:text>
        <xsl:apply-templates select="property[@name='resourceKey']"/>
        <xsl:text>}</xsl:text>
      </xsl:when>

      <xsl:otherwise>
        <xsl:text>SubmitLabel</xsl:text>
      </xsl:otherwise>

    </xsl:choose>

  </a>

</xsl:template>

</xsl:stylesheet>