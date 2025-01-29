<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:srh="main.com.samuel.hellowork.technicalinterview.helpers.XsltExtension">
	<xsl:output method="xml" indent="yes" />
	<!-- Match the root element -->
	<xsl:template match="/">
		<Result>
			<!-- Iterate over each Offer element -->
			<xsl:for-each select="/Root/Offer">
				<TransformedOffer>
					<UpperCase>
						<!-- Call the toUpperCase Java function for the Description value -->
                        <xsl:param name="toUpper" select="Description"/>
						<xsl:value-of select="srh:toUpper($toUpper)"/>
					</UpperCase>
					<Reference>
						<xsl:value-of select="Reference"/>
					</Reference>
					<UTCDate>
						<!-- Call the frenchDateToUTC Java function for xsl:xmlns:xmlns:xmlns:msxsl:msxsl:xsl:xsl:xsl:xsl:the FrenchDate value -->
                        <xsl:param name="frenchToUTCDate" select="FrenchDate"/>
						<xsl:value-of select="srh:frenchDateToUTC($frenchToUTCDate)"/>
					</UTCDate>
				</TransformedOffer>
			</xsl:for-each>
		</Result>
	</xsl:template>
</xsl:stylesheet>