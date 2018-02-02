<%--@elvariable id="shortText" type="java.lang.String"--%>
<%--@elvariable id="longText" type="java.lang.String"--%>
<template:main htmlTitle="Abbreviating Text">
	<b>Short text:</b> ${customDateFormat:abbreviateString(shortText, 32)}<br />
	<b>Long text:</b> ${customDateFormat:abbreviateString(longText, 32)}<br />
</template:main>