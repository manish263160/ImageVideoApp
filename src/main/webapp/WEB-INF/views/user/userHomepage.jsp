<%@include file="../fragments/includetags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hi ${user.name }</title>
</head>
<body class="${themecolor }">
<jsp:include page="../fragments/header.jsp" />

<div class="container">
<div class="row valign-wrapper">
             <div class="col s12 m8 l9 center">
                <div class="row">
                  <div class="col s12 m5 center">
                  <div class="card yellow darken-4">
                    <div class="card-content white-text ">
                      <span class="card-title ">Upload An Image.</span>
                    </div>
                    <div class="card-action">
                        <a href="${imgvids}/user/uploadImage" class="btn waves-effect waves-light  cyan darken-2">Upload</a>
                      </div>
                  </div>
                  </div>
                   <div class="col s12 m5">
                  <div class="card green accent-4">
                    <div class="card-content white-text">
                      <span class="card-title">Upload A Video.</span>
                    </div>
                    <div class="card-action">
                        <a href="${imgvids}/user/getAllVids" class="btn waves-effect waves-light blue">Upload</a>
                      </div>
                  </div>
                  </div> 
                </div>
              </div>
            </div>
</div>

</body>
<jsp:include page="../includes/include_js.jsp" />
</html>