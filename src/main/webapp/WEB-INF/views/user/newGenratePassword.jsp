<%@include file="../fragments/includetags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="msapplication-tap-highlight" content="no">
    <title>Set New Password</title>
<jsp:include page="../includes/include_css.jsp"></jsp:include>
   <link href="${imgvids}/static/lib/css/page-center.css" type="text/css" rel="stylesheet" media="screen,projection">
</head>
<body class="${themecolor }">
			<div id="alertmsg"></div>
<div class="container">
<div class="progress" style="display: none;">
      <div class="indeterminate"></div>
  </div>
<div id="newgenpass" class="row">
		<form name="newPassGenForm" id="newPassGenForm" action="#" class="login-form col s12 z-depth-4 card-panel">
				<h4>New Password</h4>
				<p>Please enter your new password</p>
				
				 <div class="input-field col s12">
					<input id="newpassword" name="newpassword" type="password"> <label
						for="username" class="center-align">New Password</label>
				</div>
				
				<div class="input-field col s12">
					<input id="newpasswordcopy" name="newpasswordcopy" type="text"> <label
						for="username" class="center-align">Confirm Password</label>
				</div> 
          <input name="userId" value="${userId }" id="userId" type="hidden">
			<div class="row">
          <div class="input-field col s12">
				<button  type="button"  class="waves-effect waves-light btn-large"
					 onclick="newGenPassword()" >Submit</button> 
			</div>
		</div>
		</form>
		</div>
		</div>
		 <jsp:include page="../includes/include_js.jsp" />
		 <script type="text/javascript">
		    $("#newPassGenForm").validate({
		        rules: {
		        	
		             newpassword: {
		                required: true,
		               
		            },
		            newpasswordcopy :{
		            	required: true,
		            	equalTo: "#newpassword"
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
		    
		    function newGenPassword(){
		    	if($('#newPassGenForm').valid()){
		     	var newpassword=$("#newpassword").val();
		     	var userId=$("#userId").val();
		    	 console.log("newpassword===="+newpassword);
		    	if(newpassword !== '' ){
		    		 $(".progress").show();
		    		  $.ajax({
		      		    url: '${imgvids}/user/newGenPassword/'+userId+'?newpassword='+newpassword,
		      		    type: 'GET',
			      		 
		      		    success: function(result) {
		      		        console.log("result=="+result)
		      		        
		      		        if(result === true){
		      		        	var alertmsg='<div id="card-alert" class="card green">'+
		    	                '      <div class="card-content white-text">'+
		                       ' <p>SUCCESS : Password changes successful.</p>'+
		                       '<div class="card-action">'+
								'<a href="${imgvids}/login" class="btn waves-effect waves-red light-blue darken-4">Ok</a>'+
							'</div>'+
		                      '</div>'+
		                     /*  '<button type="button" class="close white-text" data-dismiss="alert" aria-label="Close">'+
		                      '  <span aria-hidden="true">×</span>'+
		                     ' </button>'+ */
		                    '</div>';
		                    $("#alertmsg").html("");	
		                        $("#alertmsg").html(alertmsg);	
		                        $("#newPassGenForm").hide();
		      		        }
		      		      if(result === false){
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
		      		      
		       		      	  $(".progress").hide();	 
		      		        
		      		    }
		      		});
		    	}
		    	}
		    
		    }
		 </script>
		  
</body>
</html>