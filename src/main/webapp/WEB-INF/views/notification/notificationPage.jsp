<%@include file="../fragments/includetags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Notification page</title>
<jsp:include page="../fragments/header.jsp" />
<link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="${imgvids}/static/lib/css/material-datetime-picker.css" type="text/css" rel="stylesheet" >
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

 
 <div class="container" >
  <div id="login-page" class="row">
  <c:if test="${not empty isSendNotification && isSendNotification}">
 <div id="card-alert" class="card green">
                      <div class="card-content white-text">
                        <p><i class="mdi-alert-error"></i> Notification has been sent.</p>
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
            <label for="title" class="center-align">Type</label>
            </div>
          <div class="input-field col s8">
            <select name="type" id="type" required>
						<option value="Image" selected="selected">Image</option>
						<option value="Video">Video</option>
					</select>
          </div>
        </div>
        <div class="row margin">
         <div class="input-field col s4">
            <label  class="center-align">Scheduling</label>
          </div>
          <div class="input-field col s8">
            <select name="schedulingType" id="schedulingType" onchange="toggleDate(this)">
						<option value="1" selected="selected">Send now</option>
						<option value="2">send later</option>
					</select>
          </div>
        </div>
        
        <div class="row margin" style="display: none;" id="isShowChooseDate">
         <div class="input-field col s4">
            <label  class="center-align">&nbsp;</label>
          </div>
          <div class="input-field col s8">
            <input type="text" class="c-datepicker-btn" placeholder="Please choose date" name="scheduleTime" id="scheduleTime">
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
 <jsp:include page="../includes/include_js.jsp" />
  <script src="${imgvids}/static/lib/js/polyfill.js"></script>
  <script src="${imgvids}/static/lib/js/moment.js"></script>
  <script src="${imgvids}/static/lib/js/rome.standalone.js"></script>
  <script src="${imgvids}/static/lib/js/material-datetime-picker.js" charset="utf-8"></script>
 <script type="text/javascript">
 $(document).ready(function(){
	 
	 /* $('.datepickerTest').pickadate({
 	    min: new Date(),
 	    selectMonths: true, 
 	    selectYears: 15,
 		format : 'yyyy-mm-dd'

 	  }); */
 	  
	 var picker = new MaterialDatetimePicker({min: '2017-07-06',})
	   .on('submit', function(d) {
// 		   console.log('asasas==',d.format("DD-MM-YYYY HH:mm:ss"));
		   scheduleTime.value = d.format("YYYY-MM-DD HH:mm:ss");
	   });
	 var el = document.querySelector('.c-datepicker-btn');
	 el.addEventListener('click', function() {
	   picker.open();
	 }, false);

 });
 


 
 function toggleDate(e){
	 console.log($(e).val());
	 $(e).val() == 2 ? $("#isShowChooseDate").show() : $("#isShowChooseDate").hide();
 }
 
 $("#notificationForm").validate({
	    rules: {
	    	
	        schedulingType: {
	            required: true,
	            valueNotEquals: ""
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