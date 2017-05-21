<%@include file="../fragments/includetags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Partner With Us</title>
 <link href="${imgvids}/static/lib/css/page-center.css" type="text/css" rel="stylesheet" media="screen,projection">
<style type="text/css">
#login-page{
height: 100%;
    
}
</style>
  
</head>
<body class="${themecolor }">
<div class="container">
<div id="login-page" class="valign-wrapper row">
  <div class="col card hoverable s12  m6 pull-m3 l6 pull-l3">
    <div class="card-content">
      	<form:form id="restorent_register" class="login-form col s12" action="${imgvids}/restorent/restoRegistartion" method="post" modelAttribute="user" >
        <div class="row">
          <div class="input-field col s12 center">
            <h4>Add your Quality With Us</h4>
            <hr style="border-top: dotted 1px;" />
            <p class="center">Join us now !</p>
          </div>
        </div>
        <div class="row margin">
          <div class="input-field col m6 s12 l6">
            <i class="mdi-communication-email prefix"></i>
            <input id="email" type="text" name="email">
            <label for="email" class="center-align">Your Email</label>
          </div>
        
          <div class="input-field col m6 s12 l6">
            <i class="mdi-communication-call prefix"></i>
            <input id="mobileNo" type="text" name="mobileNo">
            <label for="mobileNo" class="center-align">Mobile</label>
          </div>
          </div>
          <div class="row">
          <div class="input-field col m6 s12 l6">
            <i class="mdi-maps-local-restaurant prefix"></i>
            <input id="shopName" type="text" name="shopName">
            <label for="shopName"> Restaurant Name</label>
          </div>
          
       		<div class="input-field col m6 s12 l6">
            	<select id="shopType" name="shopType">
                    <option value="" disabled selected>Restaurant Type</option>
                    <c:forEach var="shoptypeMap" items="${shoptypeMap}">
                    <option value="${shoptypeMap.key}">${shoptypeMap.value}</option>
					</c:forEach>
                    
                  </select>
          </div> 
          </div>
         
         <div class="row">
          <div class="input-field col m6 s12 l6">
            <i class="mdi-action-lock-outline prefix"></i>
            <input id="password" type="password" name="password">
            <label for="password"> Password</label>
        </div>

		<div class="input-field col m6 s12 l6">
				<i class="mdi-action-lock-outline prefix"></i> 
				<input id="cpassword" type="password" name="cpassword">
					 <label for="cpassword">Confirm Password</label>
		</div>
	</div>
         
         <div class="row">
          <div class="input-field col m12 s12 l12">
            <i class="mdi-editor-mode-edit prefix"></i>
            <input id="shopAddress" type="text" name="shopAddress">
            <label for="shopAddress"> Restaurant Address</label>
          </div>
         </div>
        
        <div class="row">
        <div class="input-field col s12 m12 l12">
                     <button class="btn cyan waves-effect waves-light right" type="submit" name="action">Submit
                            <i class="mdi-content-send right"></i>
                          </button>
                          </div>
                    </div>
      </form:form>
    </div>
    </div>
    </div>
   </div>
<jsp:include page="../includes/include_js.jsp" />
<script type="text/javascript">
$("#restorent_register").validate({
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
		shopName: {
            required: true,
        },
        mobileNo: {
			required: true,
			maxlength: 12
        },
        shopAddress :{
        	required: true,
			maxlength: 12
        },
    },
    //For custom messages
    messages: {
    	shopName:{
            required: "Enter your shop name"
        }
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