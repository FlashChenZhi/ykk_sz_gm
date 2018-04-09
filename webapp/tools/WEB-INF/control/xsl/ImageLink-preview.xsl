<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" omit-xml-declaration="no" />
<xsl:template match="/template">

  <!-- root variables -->
  <xsl:variable name="template_key" select="@key"/>
  <xsl:variable name="keyword" select="@keyword"/>
  <xsl:variable name="description" select="@description"/>
  <xsl:variable name="image_root" select="concat(@image-root,'/')"/>
  <xsl:variable name="context_path" select="@context-path"/>

  <!-- otput "A" -->
  <a href = "#" onclick="return false;">

    <!-- set attribute "css" -->
    <xsl:if test="property[@name='cssClass']/@value!=''">
      <xsl:attribute name="class">
        <xsl:apply-templates select="property[@name='cssClass']"/>
      </xsl:attribute>
    </xsl:if>

    <!-- otput "IMG" -->
    <img>

      <!-- set attribute "src" -->
      <xsl:attribute name="src">
        <xsl:choose>
          <xsl:when test="property[@name='src']/@value!=''">
            <xsl:value-of select="$context_path"/>
            <xsl:apply-templates select="property[@name='src']"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="$image_root"/>
            <xsl:text>imglnk.gif</xsl:text>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:attribute>

      <!-- set attribute "border" -->
      <xsl:if test="property[@name='border']/@value!=''">
        <xsl:attribute name="border">
          <xsl:apply-templates select="property[@name='border']"/>
        </xsl:attribute>
      </xsl:if>

    </img>

    </a>

</xsl:template>

<xsl:template match="property">
  <xsl:value-of select="@value"/>
</xsl:template>

</xsl:stylesheet>