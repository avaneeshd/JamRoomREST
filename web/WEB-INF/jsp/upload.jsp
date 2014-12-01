<%-- 
    Document   : upload
    Created on : Nov 25, 2014, 6:51:09 PM
    Author     : avaneeshdesai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
	<form method="POST" enctype="multipart/form-data"
		action="/JamRoomREST/rest/upload">
		File to upload: <input type="file" name="file"><br /> Name: <input
			type="text" name="name"><br /> <br /> <input type="submit"
			value="Upload"> Press here to upload the file!
	</form>
</body>
</html>