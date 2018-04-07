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

  <!-- otput "TABLE" -->
  <table border="0" cellspacing="0" cellpadding="0">

    <!-- otput "Spacer TR" -->
    <tr>
      <td colspan="13" bgcolor="#413a8a">

        <!-- otput "IMG" -->
        <img width="2" height="2" border="0">

          <!-- set attribute "src" -->
          <xsl:attribute name="src">
            <xsl:value-of select="$image_root"/>
            <xsl:text>Spacer.gif</xsl:text>
          </xsl:attribute>

        </img>
      </td>
    </tr>

    <!-- otput "TR" -->
    <tr>

      <!-- otput "Spacer TD" -->
      <td bgcolor="#413a8a">

        <!-- otput "IMG" -->
        <img width="2" height="1" border="0">

          <!-- set attribute "src" -->
          <xsl:attribute name="src">
            <xsl:value-of select="$image_root"/>
            <xsl:text>Spacer.gif</xsl:text>
          </xsl:attribute>

        </img>
      </td>

      <!-- otput "First TD" -->
      <td>

        <!-- otput "IMG" -->
        <img>

          <!-- set attribute "src" -->
          <xsl:attribute name="src">
            <xsl:value-of select="$image_root"/>
 
            <xsl:choose>

              <xsl:when test="property[@name='beforePage']/@value='true'">
                <xsl:text>tbtn_ff_rglr.gif</xsl:text>
              </xsl:when>

              <xsl:otherwise>
                <xsl:text>tbtn_ff_off.gif</xsl:text>
              </xsl:otherwise>

            </xsl:choose>
          </xsl:attribute>

        </img>

      </td>

      <!-- otput "Spacer TD" -->
      <td bgcolor="#413a8a">

        <!-- otput "IMG" -->
        <img width="2" height="1" border="0">

          <!-- set attribute "src" -->
          <xsl:attribute name="src">
            <xsl:value-of select="$image_root"/>
            <xsl:text>Spacer.gif</xsl:text>
          </xsl:attribute>

        </img>
      </td>

      <!-- otput "Prev TD" -->
      <td>

        <!-- otput "IMG" -->
        <img>

          <!-- set attribute "src" -->
          <xsl:attribute name="src">
            <xsl:value-of select="$image_root"/>
 
            <xsl:choose>

              <xsl:when test="property[@name='beforePage']/@value='true'">
                <xsl:text>tbtn_prev_rglr.gif</xsl:text>
              </xsl:when>

              <xsl:otherwise>
                <xsl:text>tbtn_prev_off.gif</xsl:text>
              </xsl:otherwise>

            </xsl:choose>
          </xsl:attribute>

        </img>

      </td>

      <!-- otput "Spacer TD" -->
      <td bgcolor="#413a8a">

        <!-- otput "IMG" -->
        <img width="2" height="1" border="0">

          <!-- set attribute "src" -->
          <xsl:attribute name="src">
            <xsl:value-of select="$image_root"/>
            <xsl:text>Spacer.gif</xsl:text>
          </xsl:attribute>

        </img>
      </td>

      <!-- otput "Result TD" -->
      <td bgcolor = "white">
        <span>
          <xsl:attribute name="style">
            <xsl:text>white-space : nowrap;</xsl:text>
          </xsl:attribute>
           <xsl:text>1-1/</xsl:text>
           <xsl:apply-templates select="property[@name='max']"/>
         </span>
      </td>

      <!-- otput "Spacer TD" -->
      <td bgcolor="#413a8a">

        <!-- otput "IMG" -->
        <img width="2" height="1" border="0">

          <!-- set attribute "src" -->
          <xsl:attribute name="src">
            <xsl:value-of select="$image_root"/>
            <xsl:text>Spacer.gif</xsl:text>
          </xsl:attribute>

        </img>
      </td>

      <!-- otput "Next TD" -->
      <td>

        <!-- otput "IMG" -->
        <img>

          <!-- set attribute "src" -->
          <xsl:attribute name="src">
            <xsl:value-of select="$image_root"/>

            <xsl:choose>

              <xsl:when test="property[@name='nextPage']/@value='true'">
                <xsl:text>tbtn_nxt_rglr.gif</xsl:text>
              </xsl:when>

              <xsl:otherwise>
                <xsl:text>tbtn_nxt_off.gif</xsl:text>
              </xsl:otherwise>

            </xsl:choose>
          </xsl:attribute>

         </img>

      </td>

      <!-- otput "Spacer TD" -->
      <td bgcolor="#413a8a">

        <!-- otput "IMG" -->
        <img width="2" height="1" border="0">

          <!-- set attribute "src" -->
          <xsl:attribute name="src">
            <xsl:value-of select="$image_root"/>
            <xsl:text>Spacer.gif</xsl:text>
          </xsl:attribute>

        </img>
      </td>

      <!-- otput "Last TD" -->
     <td>

        <!-- otput "IMG" -->
        <img>

          <!-- set attribute "src" -->
          <xsl:attribute name="src">
            <xsl:value-of select="$image_root"/>

            <xsl:choose>

              <xsl:when test="property[@name='nextPage']/@value='true'">
                <xsl:text>tbtn_last_rglr.gif</xsl:text>
              </xsl:when>

              <xsl:otherwise>
                <xsl:text>tbtn_last_off.gif</xsl:text>
              </xsl:otherwise>

            </xsl:choose>
          </xsl:attribute>

        </img>

    </td>

      <!-- otput "Spacer TD" -->
      <td bgcolor="#413a8a">

        <!-- otput "IMG" -->
        <img width="2" height="1" border="0">

          <!-- set attribute "src" -->
          <xsl:attribute name="src">
            <xsl:value-of select="$image_root"/>
            <xsl:text>Spacer.gif</xsl:text>
          </xsl:attribute>

        </img>
      </td>

    </tr>

    <!-- otput "Spacer TR" -->
    <tr>
      <td colspan="13" bgcolor="#413a8a">

        <!-- otput "IMG" -->
        <img width="2" height="2" border="0">

          <!-- set attribute "src" -->
          <xsl:attribute name="src">
            <xsl:value-of select="$image_root"/>
            <xsl:text>Spacer.gif</xsl:text>
          </xsl:attribute>

        </img>
      </td>
    </tr>

  </table>

</xsl:template>

<xsl:template match="property">
  <xsl:value-of select="@value"/>
</xsl:template>

</xsl:stylesheet>