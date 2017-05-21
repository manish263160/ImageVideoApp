<%@include file="../fragments/includetags.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="msapplication-tap-highlight" content="no">
    <title>User Registration Page</title>
<jsp:include page="../includes/include_css.jsp"></jsp:include>
   <link href="${imgvids}/static/lib/css/page-center.css" type="text/css" rel="stylesheet" media="screen,projection">
</head>

<%-- <jsp:include page="../fragments/header.jsp" /> --%>
<body class="${themecolor }">
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
<div  id="registration-form">
  <div id="login-page" class="row">
      <form:form action="${imgvids}/user/insertUser" class="login-form col s12 z-depth-4 card-panel" id="userRegForm" method="post" modelAttribute="user">
        <div class="row">
          <div class="input-field col s12 center">
            <h4>Register</h4>
            <p class="center">Join us now !</p>
          </div>
        </div>
        <div class="row margin">
          <div class="input-field col s12">
            <i class="mdi-social-person-outline prefix"></i>
            <input id="name" type="text" name="name">
            <label for="name" class="center-align">Name</label>
          </div>
        </div>
        
        <div class="row margin">
          <div class="input-field col s12">
            <i class="mdi-communication-call prefix"></i>
            <input id="mobileNo" type="text" name="mobileNo">
            <label for="mobileNo" class="center-align">Mobile</label>
          </div>
        </div>
        
        <div class="row margin">
          <div class="input-field col s12">
            <i class="mdi-communication-email prefix"></i>
            <input id="email" type="email" name="email">
            <label for="email" class="center-align">Email</label>
          </div>
        </div>
        <div class="row margin">
          <div class="input-field col s12">
            <i class="mdi-action-lock-outline prefix"></i>
            <input id="password" type="password" name="password">
            <label for="password">Password</label>
          </div>
        </div>
        <div class="row margin">
          <div class="input-field col s12">
            <i class="mdi-action-lock-outline prefix"></i>
            <input id="cpassword" type="password" name="cpassword">
            <label for="cpassword">Password again</label>
          </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
            <button type="submit" class="btn waves-effect waves-light col s12">Register Now</button>
          </div>
          <div class="input-field col s12">
            <p class="margin center medium-small sign-up">Already have an account? <a href="${imgvids}/login">Login</a></p>
          </div>
        </div>
      </form:form>
  </div>

</div>
 <jsp:include page="../includes/include_js.jsp" />
 <script type="text/javascript" src="${imgvids}/static/lib/js/plugins.min.js"></script>
<script type="text/javascript">
$("#userRegForm").validate({
    rules: {
        email: {
            required: true,
            email:true
        },
        password: {
			required: true,
			minlength: 5
		},
		cpassword: {
			required: true,
			minlength: 5,
			equalTo: "#password"
		},
		name: {
            required: true,
        },
        mobileNo: {
			required: true,
			maxlength: 12
        }
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
</body>

</html>