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
  <div class="container1">
<div class="progress" style="display: none;">
      <div class="indeterminate"></div>
  </div>
  <div id="login-page" class="row">
    <div class="col s12 z-depth-4 card-panel">
    <c:url var="loginUrl" value="/login"/>
      <form action="${loginUrl}" method="post" class="login-form" id="formValidate">
        <div class="row">
          <div class="input-field col s12 center">
            <img src="static/images/logo-512.png" alt="" class="valign profile-image-login">
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
           <div class="input-field col s6 m6 l6">
              <p class="margin right-align medium-small"><a class="tooltipped modal-trigger" href="#modal1" data-position="bottom" data-delay="50" data-tooltip="Forgot Password">Forgot password ?</a></p>
          </div>           
        </div>

      </form>
    </div>
  </div>
  
  <div id="modal1" class="modal modal-fixed-footer  lighten-3">
		<form name="fogotpassform" id="fogotpassform" action="#">
			<div class="modal-content ">
				<div id="alertmsg"></div>
				<h4>Forgot Password</h4>
				<p>Please enter your registered email Id.</p>
				<div class="input-field col s12">
					<input id="forgotemail" name="forgotemail" type="text"> <label 
						for="username" class="center-align">Email</label>
				</div>
				<!-- <div class="input-field col s12">
					<input id="newpassword" name="newpassword" type="password"> <label
						for="username" class="center-align">New Password</label>
				</div>
				
				<div class="input-field col s12">
					<input id="newpasswordcopy" name="newpasswordcopy" type="text"> <label
						for="username" class="center-align">Confirm Password</label>
				</div> -->
			</div>
			<div class="modal-footer card-action pink lighten-4">
				<input  type="button"  class="waves-effect waves-green btn-flat modal-action  pink-text"
					 onclick="forgotpassword()" value="Submit "/> 
			<a href="#" class="waves-effect waves-red btn-flat modal-action modal-close pink-text">Close</a>
			</div>
		</form>
	</div>
</div>
	<jsp:include page="includes/include_js.jsp" />
<%-- <script type="text/javascript" src="${imgvids}/static/lib/js/plugins.min.js"></script> --%>
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
    
    $("#fogotpassform").validate({
        rules: {
        	forgotemail: {
                required: true,
                email:true
               
            },
            /* newpassword: {
                required: true,
               
            },
            newpasswordcopy :{
            	required: true,
            	equalTo: "#newpassword"
            } */
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
    
    $(document).ready(function(){
	    // the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
    	$('.tooltipped').tooltip({delay: 50});
//     	$('.modal').modal();
    	
    	$("#alertmsg").html("");	

	  });
    function forgotpassword(){
    	if($('#fogotpassform').valid()){
    	var email=$("#forgotemail").val();
//     	var newpassword=$("#newpassword").val()
//     	 var json = { "email" : email, "newpassword" : newpassword};
    	if(email !== '' ){
    		 $(".progress").show();
    		  $.ajax({
      		    url: '${imgvids}/forgotpassword.json?email='+email,
      		    type: 'GET',
	      		 
      		    success: function(result) {
      		        if(result === 'NOT_FOUND'){
					var alertmsg='<div id="card-alert" class="card red">'+
	                '      <div class="card-content white-text">'+
                   ' <p>DANGER : This Email does not exist.</p>'+
                  '</div>'+
                  /* '<button type="button" class="close white-text" data-dismiss="alert" aria-label="Close">'+
                  '  <span aria-hidden="true">×</span>'+
                 ' </button>'+ */
                '</div>';
                $("#alertmsg").html("");
                    $("#alertmsg").html(alertmsg);
      		        }
      		        if(result === 'success'){
      		        	var alertmsg='<div id="card-alert" class="card green">'+
    	                '      <div class="card-content white-text">'+
                       ' <p>SUCCESS : Please check your email for generate new password.</p>'+
                      '</div>'+
                     /*  '<button type="button" class="close white-text" data-dismiss="alert" aria-label="Close">'+
                      '  <span aria-hidden="true">×</span>'+
                     ' </button>'+ */
                    '</div>';
                    $("#alertmsg").html("");	
                        $("#alertmsg").html(alertmsg);	
      		        }
      		      if(result === 'fail'){
      		    	var alertmsg='<div id="card-alert" class="card red">'+
	                '      <div class="card-content white-text">'+
                   ' <p>DANGER : Something went wrong.</p>'+
                  '</div>'+
                  /* '<button type="button" class="close white-text" data-dismiss="alert" aria-label="Close">'+
                  '  <span aria-hidden="true">×</span>'+
                 ' </button>'+ */
                '</div>';
                $("#alertmsg").html("");
                    $("#alertmsg").html(alertmsg);
      		      }
      		      $("#fogotpassform")[0].reset();
       		      	  $(".progress").hide();	 
      		        
      		    }
      		});
    	}
    	}
    }
    </script>
</html>