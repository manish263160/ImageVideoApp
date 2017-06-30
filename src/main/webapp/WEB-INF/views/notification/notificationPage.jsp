<%@include file="../fragments/includetags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Notification page</title>
<jsp:include page="../fragments/header.jsp" />
<style type="text/css">
html,
body {
    height: 100%;
}
#login-page {
    display: table;
    margin: auto;
}

</style>
</head>
<body class="${themecolor }">

 <jsp:include page="../includes/include_js.jsp" />
 <div class="container" >
  <div id="login-page" class="row">
  <c:if test="${not empty isSendNotification && isSendNotification}">
 <div id="card-alert" class="card green">
                      <div class="card-content white-text">
                        <p><i class="mdi-alert-error"></i> Notification ahs been sent.</p>
                      </div>
                      <button type="button" class="close white-text" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">×</span>
                      </button>
                    </div>
 </c:if>      
      <form:form action="${imgvids}/appNotification/pushNotification" class="login-form col s12 z-depth-4 card-panel" id="notificationForm" method="post" modelAttribute="notificationForm">
        <div class="row">
          <div class="input-field col s12 center">
            <h4>Notification Settings</h4>
            <p class="center">Set a mobile notification!</p>
          </div>
        </div>
        <div class="row margin">
          <div class="input-field col s12">
            <input id="title" type="text" name="title">
            <label for="title" class="center-align">Title</label>
          </div>
        </div>
        
        <div class="row margin">
         <div class="input-field col s4">
            <label  class="center-align">Scheduling</label>
          </div>
          <div class="input-field col s8">
            <select name="schedulingType" onchange="toggleDate(this)">
						<option value="" disabled selected> schedule time</option>
						<option value="1">Send now</option>
						<option value="2">send later</option>
					</select>
          </div>
        </div>
        
        <div class="row margin" style="display: none;" id="isShowChooseDate">
         <div class="input-field col s4">
            <label  class="center-align">&nbsp;</label>
          </div>
          <div class="input-field col s8">
            <input type="text" class="datepickerTest" placeholder="Please choose date" name="scheduleTime" id="scheduleTime">
          </div>
        </div>
        <div class="row margin">
          <div class="input-field col s12">
            <textarea id="notificationDesc"  class="materialize-textarea"  autocomplete="off" name="description" ></textarea>
            <label for="notificationDesc" class="">	Description</label>
          </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
            <button type="submit" class="btn waves-effect waves-light col s12">Send</button>
          </div>
          
        </div>
      </form:form>
  </div>

</div>
 <script type="text/javascript">
 $(document).ready(function(){
	 
	 $('.datepickerTest').pickadate({
 	    min: new Date(),
 	    selectMonths: true, 
 	    selectYears: 15,
 		format : 'yyyy-mm-dd'

 	  });
 });
 function toggleDate(e){
	 console.log($(e).val());
	 $(e).val() == 2 ? $("#isShowChooseDate").show() : $("#isShowChooseDate").hide();
 }
 
 $("#notificationForm").validate({
	    rules: {
	    	title: {
	            required: true,
	        },
	        schedulingType: {
	            required: true,
	        },
	        scheduleTime: {
				required: true,
				
	        },
	        description: {
				required: true,
				
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