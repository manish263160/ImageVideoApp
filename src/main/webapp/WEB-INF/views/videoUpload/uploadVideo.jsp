	<%@include file="../fragments/includetags.jsp"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Image Upload</title>
	 <link href="${imgvids}/static/lib/css/dropify.min.css" type="text/css" rel="stylesheet" media="screen,projection">
	 <style type="text/css">
#input_file-error{
	color: red !important;
	padding-top:  5% !important;
}
 #categoryId{
 
 height: 100px;
 }
</style>
	 
	</head>
	<body class="${themecolor }">
	<jsp:include page="../fragments/header.jsp" />
	
	<div class="container">
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
	<div id="card-alert" class="card red hide">
	                      <div class="card-content white-text">
	                        <p><i class="mdi-alert-error"></i> This video url already exist.</p>
	                      </div>
	                      <button type="button" class="close white-text" data-dismiss="alert" aria-label="Close">
	                        <span aria-hidden="true">×</span>
	                      </button>
	                    </div>
	<form action="${imgvids}/insertVideo" method="post"
	modelAttribute="UploadedVideo" enctype="multipart/form-data" id="formupload">
	 <div class="divider"></div>
	            <div class="row section">
	              <div class="col s12 m4 l3">
	                <p>Select An Image for Thumbnail (Max 2MB)</p>
	              </div>
	              <div class="col s8 m4 l7">
	                  <input type="file" id="input_file" name="file" class="dropify" data-height="150"  data-max-file-size="2M" />
	              </div>
	                       
	            </div>
	            <div class="row section">
	             <div class="col s12 m3 l3">
	                <p>Video Link</p>
	              </div>
	              
	              <div class="input-field col s12 m3 l3">
                          <input id="videoLink" type="text" class="validate" onchange="checkVideoLink(this)" autocomplete="off" name="videoLink" maxlength="250">
                          <label for="videoLink" class="">Video Link</label>
                        </div>
                  
                  <div class="col s12 m3 l3">
	                <p>Time Length</p>
	              </div>
                  <div class="input-field col s12 m3 l3">
                          <input id="timeLength" type="text" class="validate" autocomplete="off" name="timeLength">
                          <label for="timeLength" class="">Time Length</label>
                        </div>
                        </div>
                 <div class="row section">
                  <div class="col s12 m2 l3">
	                <p>Category <span style="font-size: 10px">(Please use CTRL+click for multiselect)</span></p>
	              </div>
                  <div class="input-field col s12 m3 l3">
                    <select name="categoryId" id="categoryId" class="validate browser-default" required multiple="multiple" onchange="changeSelect()">
					<option disabled="disabled" selected>Select Category</option>
                    <c:forEach items="${categorylist }" var="cat">
						<option value="${cat.id }">${cat.name }</option>
                    </c:forEach>
					</select>
                        </div>
                        
                  <div class="col s12 m3 l3">
	                <p>Series</p>
	              </div>
                  <div class="input-field col s12 m3 l3">
					<select name="seriesId" id="seriesId" class="validate" >
						<option value="" selected>Select Series</option>
						<c:forEach items="${serieslist }" var="sers">
							<option value="${sers.id }">${sers.name }</option>
						</c:forEach>
					</select>
				</div>  
				</div>
	            
	            <div class="row section">
	             <div class="col s12 m4 l3">
	                <p>Image Title</p>
	              </div>
	              
	              <div class="input-field col s8 m4 l3">
                          <input id="title" type="text" class="validate" autocomplete="off" maxlength="250" name="title">
                          <label for="title" class="">Image Title</label>
                        </div>
	             <div class="col s12 m4 l3">
	                <p>Description</p>
	              </div>
	              
	              <div class="input-field col s8 m4 l3">
                          <textarea id="description" type="text" class="materialize-textarea" maxlength="250" autocomplete="off" name="description" ></textarea>
                          <label for="email" class="">Description</label>
                        </div>
	            </div>
	      <div class="row section">
	      <div class="col s12 m8 l9  center" style="padding-right: 193px;" ><button class="btn btn-large waves-effect waves-light red darken-4" type="submit"> Submit</button></div>
	      </div>
	</form>            
	   
	</div>
	
	 <jsp:include page="../includes/include_js.jsp" />
	 <script type="text/javascript" src="${imgvids}/static/lib/js/dropify.min.js"></script>
	 <script type="text/javascript">
	        $(document).ready(function(){
	        	/* $("select[required]").css({
	        	    display: "block", 
	        	    position: 'absolute',
	        	    visibility: 'hidden'
	        	  }) */
	            // Basic
	            $('.dropify').dropify();
	
	            // Translated
	            $('.dropify-fr').dropify({
	                messages: {
	                    default: 'Glissez-déposez un fichier ici ou cliquez',
	                    replace: 'Glissez-déposez un fichier ou cliquez pour remplacer',
	                    remove:  'Supprimer',
	                    error:   'Désolé, le fichier trop volumineux'
	                }
	            });
	
	            // Used events
	            var drEvent = $('.dropify-event').dropify();
	
	            drEvent.on('dropify.beforeClear', function(event, element){
	                return confirm("Do you really want to delete \"" + element.filename + "\" ?");
	            });
	
	            drEvent.on('dropify.afterClear', function(event, element){
	                alert('File deleted');
	            });
	        });
	        
	        $("#formupload").validate({
	            rules: {
	            	/* imageLink: {
	                    required: true,
	                   
	                }, */
	                file :{
	                	required: true,
	                },
	                videoLink :{
	                	required: true,
	                },
	                /* timeLength :{
	                	required: true,
	                }, */
	               /*  seriesId :{
	                	required: function(){
	                		if($("#seriesId").val() !=='' || $("categoryId").val() !==''){
	                			return true;
	                		}else {
	                			return false;
	                		}
	                	},
	                }, */
	                categoryId :{
	                	required: function(){
	                		if(($("#seriesId").val() !=='' && $("#seriesId").val() != null)  || ($("#categoryId").val() !=='' && $("#categoryId").val() !=null)){
	                			return false;
	                		}else {
	                			return true;
	                		}
	                	},
	                },
	                title :{
	                	required: true,
	                },
	               /*  description :{
	                	required: true,
	                }, */
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
	            /* errorPlacement: function(error, element) {
	                error.appendTo( element.parent() );
	            } */
	         });	        
	       
	        function checkVideoLink(text){
	        	var textValue=text.value;
	        	$.post("${imgvids}/checkVideoLink", {urllink: textValue, from :'video'}, function(result){
	            console.log(result)
	        		if(result){
	        			$("#card-alert").removeClass("hide");
	        			$("#videoLink").val("");
	        		}else{
	        			$("#card-alert").addClass("hide");
	        		}
	            });
	        	
	        }
	        
	        function changeSelect(){
	        	console.log("------",$("#categoryId").val());
	        }
	    </script>

	</body>
	</html>