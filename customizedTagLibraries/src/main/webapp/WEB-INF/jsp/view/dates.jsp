<%--@elvariable id="date" type="java.util.Date"--%>
<%--@elvariable id="calendar" type="java.util.Calendar"--%>
<%--@elvariable id="instant" type="java.time.Instant"--%>
<template:main htmlTitle="Displaying Dates Properly">
	<b><fmt:message key="dates.date" />:</b>
		<customDateFormat:formatDate value="${date}" type="both" dateStyle="full" timeStyle="full" /><br />
	<b><fmt:message key="dates.date.separator" />:</b>
		<customDateFormat:formatDate value="${date}" type="both" dateStyle="full" timeStyle="full" timeFirst="true" separateDateTimeWith=" on " /><br />
	<b><fmt:message key="dates.calendar" />:</b>
		<customDateFormat:formatDate value="${calendar}" type="both" dateStyle="full" timeStyle="full" /><br />
	<b><fmt:message key="dates.instant" />:</b>
		<customDateFormat:formatDate value="${instant}" type="both" dateStyle="full" timeStyle="full" /><br />
	<b><fmt:message key="dates.instant.separator" />:</b>
		<customDateFormat:formatDate value="${instant}" type="both" dateStyle="full" timeStyle="full" timeFirst="true" separateDateTimeWith=" on " /><br />
</template:main>