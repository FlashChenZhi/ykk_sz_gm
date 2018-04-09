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
  <xsl:variable name="layout_window" select="@layout-window"/>

  <!-- default value -->
  <xsl:variable name="def_val">PullDown</xsl:variable>

  <!-- otput "SELECT" tag -->
  <select>

    <xsl:choose>
      <!-- Layout window Mode -->
      <xsl:when test="$layout_window=''">
        <!-- set attribute "disabled" -->
        <xsl:if test="property[@name='enabled']/@value!='true'">
          <xsl:attribute name="disabled">true</xsl:attribute>
        </xsl:if>
      </xsl:when>
      <!-- Preview window Mode -->
      <xsl:otherwise>
        <!-- set attribute "disabled" -->
        <xsl:attribute name="disabled">true</xsl:attribute>
      </xsl:otherwise>
    </xsl:choose>

    <!-- set attribute "css" -->
    <xsl:if test="property[@name='cssClass']/@value!=''">
      <xsl:attribute name="class">
        <xsl:apply-templates select="property[@name='cssClass']"/>
      </xsl:attribute>
    </xsl:if>

    <!-- when PullDownItem does not exist -->
    <xsl:if test="count(list-items[@type='PullDownItems']/list-item[@type='PullDownItem'])=0">

      <!-- output "OPTION" tags -->
      <option>

        <!-- set attribute "value" -->
        <xsl:attribute name="value">
          <xsl:value-of select="$def_val"/>
        </xsl:attribute>

        <!-- set "text" -->
        <xsl:value-of select="$def_val"/>

      </option>

    </xsl:if>

    <xsl:for-each select="list-items[@type='PullDownItems']/list-item[@type='PullDownItem']">

      <!-- output "OPTION" tag -->
      <option>

        <!-- set attribute "value" -->
        <xsl:attribute name="value">
          <xsl:apply-templates select="property[@name='value']"/>
        </xsl:attribute>

        <!-- set attribute "selected" -->
        <xsl:if test="property[@name='selected']/@value='true'">
          <xsl:attribute name="selected">true</xsl:attribute>
        </xsl:if>

        <!-- set "text" -->
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
            <xsl:apply-templates select="property[@name='value']"/>
          </xsl:otherwise>
        </xsl:choose>

      </option>

    </xsl:for-each>

  </select>

</xsl:template>

<xsl:template match="property">
  <xsl:value-of select="@value"/>
</xsl:template>

</xsl:stylesheet>