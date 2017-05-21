<%@include file="fragments/includetags.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>  
    <title>Login Page</title>

<jsp:include page="includes/include_css.jsp"></jsp:include>
    <link href="${imgvids}/static/lib/css/page-center.css" type="text/css" rel="stylesheet" media="screen,projection">
</head>

<body class="${themecolor }">
  <!-- Start Page Loading -->
 <!--  <div id="loader-wrapper">
      <div id="loader"></div>        
      <div class="loader-section section-left"></div>
      <div class="loader-section section-right"></div>
  </div> -->
  <!-- End Page Loading -->


<c:if test="${not empty error}">
 <div id="card-alert" class="card red">
                      <div class="card-content white-text">
                        <p><i class="mdi-alert-error"></i> ${error}</p>
                      </div>
                      <button type="button" class="close white-text" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">×</span>
                      </button>
                    </div>
 </c:if>   

  <div id="login-page" class="row">
    <div class="col s12 z-depth-4 card-panel">
    <c:url var="loginUrl" value="/login"/>
      <form action="${loginUrl}" method="post" class="login-form" id="formValidate">
        <div class="row">
          <div class="input-field col s12 center">
            <img src="static/images/login-logo.png" alt="" class="valign profile-image-login">
            <p class="center login-form-text"> Login Page</p>
          </div>
        </div>
        <div class="row margin">
          <div class="input-field col s12">
            <i class="mdi-social-person-outline prefix"></i>
            <input id="username" name="username" type="text">
            <label for="username" class="center-align">Username</label>
          </div>
        </div>
        <div class="row margin">
          <div class="input-field col s12">
            <i class="mdi-action-lock-outline prefix"></i>
            <input id="password" type="password" name="password">
            <label for="password">Password</label>
          </div>
        </div>
        <div class="row">          
          <div class="input-field col s12 m12 l12  login-text">
              <input type="checkbox" id="remember-me" />
              <label for="remember-me">Remember me</label>
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
          </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
            <button type="submit" class="btn waves-effect waves-light col s12">Login</button>
          </div>
        </div>
        <div class="row">
         <%--  <div class="input-field col s6 m6 l6">
            <p class="margin medium-small"><a href="${imgvids}/user/userregistration">Register Now!</a></p>
          </div> --%>
         <!--  <div class="input-field col s6 m6 l6">
              <p class="margin right-align medium-small"><a href="forgotpassword.htm">Forgot password ?</a></p>
          </div>     -->      
        </div>

      </form>
    </div>
  </div>
<jsp:include page="includes/include_js.jsp" />
<script type="text/javascript" src="${imgvids}/static/lib/js/plugins.min.js"></script>
</body>
<script type="text/javascript">
    $("#formValidate").validate({
        rules: {
        	username: {
                required: true,
               
            },
            password: {
                required: true,
               
            },
       },
        //For custom messages
        messages: {

        },
        errorElement : 'div',
        errorPlacement: function(error, element) {
          var placement = $(element).data('error');
          if (placement) {
            $(placement).append(error)
          } else {
            error.insertAfter(element);
          }
        }
     });
    </script>
</html>