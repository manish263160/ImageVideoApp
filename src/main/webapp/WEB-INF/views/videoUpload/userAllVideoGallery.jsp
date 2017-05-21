<%@include file="../fragments/includetags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Image Upload</title>
<jsp:include page="../fragments/header.jsp" />
<link href="${imgvids}/static/lib/css/magnific-popup.css"
	type="text/css" rel="stylesheet" media="screen,projection">

</head>
<body class="${themecolor }">

	<div class="container">
	<div class="row">
	<c:forEach items="${allfileList }" var="video">
	<div class="col s12 m8 l4">
                      <div class="card-panel">
                        <video width="100%" controls="">
                          <source src="${video}" type="video/mp4">
                        </video>
                      </div>
                    </div>
	</c:forEach>
	<div class="col s12 m8 l4">
                      <div class="card-panel">
                        <video width="100%" controls="">
                          <source src="http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4" type="video/mp4">
                        </video>
                      </div>
                    </div>
	</div>
	</div>
</body>
</html>