<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean scope="session" id="cognos"
	class="com.ibm.biospace.cognos.embedded.CognosEmbeddedUtil" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cognos Embedded Dashboard</title>
<script src="https://dde-us-south.analytics.ibm.com/daas/CognosApi.js"></script>


</head>

<body onload="init()">
    ${cognos.tableSchema}
</body>

</html>
