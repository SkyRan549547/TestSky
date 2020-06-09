<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

</body>
<script>
	var http = require('http');
	var serv = http.createServer(function(req, res){
		res.writeHead(200,{'content-Type':'text/html'});
		res.end('<marquee> Smashing Node! <marquee>');
	});
	serv.listen(3000);
</script>
</html>