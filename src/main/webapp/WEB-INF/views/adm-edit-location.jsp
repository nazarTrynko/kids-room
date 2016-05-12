<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>


<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Edit location</title>
	<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">
</head>

<body>
    <a href="adm-add-location"><button name="add-location">Add</button></a>
    <button name="delete-location">Delete</button>


    <form action="adm-edit-location" method="get">
        <div class="rightback">
            <div class="contentback">
                <div class="leftback">
                    <div class="leftsidebar">
                        <fieldset class="possition">
                            <label>
                                <legend>Locations</legend>
                                <select name="locations" required>

                                    <c:forEach var="room" items="${roomList}" >
                                        <option value="${room.name}">${room.name}</option>
                                    </c:forEach>
                                </select>
                            </label>
                        </fieldset>
                    </div>

                    <div class="rightsidebar">

                    </div>
                </div>
            </div>
        </div>
	</form>
</body>
</html>