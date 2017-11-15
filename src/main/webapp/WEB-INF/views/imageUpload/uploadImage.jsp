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
	                        <p><i class="mdi-alert-error"></i> This Image Link already exist.</p>
	                      </div>
	                      <button type="button" class="close white-text" data-dismiss="alert" aria-label="Close">
	                        <span aria-hidden="true">×</span>
	                      </button>
	                    </div>
	<form action="${imgvids}/uploadImage?${_csrf.parameterName}=${_csrf.token}" method="post"
	modelAttribute="uploadedImage" enctype="multipart/form-data" id="formupload">
	 <div class="divider"></div>
	            <div class="row section">
	              <div class="col s12 m4 l3">
	                <p>Select An Image (Max 2MB)</p>
	              </div>
	              <div class="col s8 m4 l7">
	                  <input type="file" id="input_file" name="file" class="dropify" data-height="150"  data-max-file-size="2M" />
	              </div>
	                       
	            </div>
	            <div class="row section">
	             <div class="col s12 m4 l3">
	                <p>Image Link</p>
	              </div>
	              
	              <div class="input-field col s8 m4 l4">
                          <input id="imgLink" type="text" class="validate" onchange="checkImageLink(this)" autocomplete="off" name="imageLink" maxlength="250">
                          <label for="email" class="">Image Link</label>
                        </div>
                        
                        <div class=" input-field col s8 m4 l4">
                      <select name="linkType">
						<option value="" disabled selected> Image Link Type</option>
						<option value="1">Image</option>
						<option value="2">Video</option>
					</select>
				</div>
	            </div>
	            
	             <div class="row section">
                  <div class="col s12 m2 l3">
	                <p>Category <span style="font-size: 10px">(Please use CTRL+click for multiselect)</span> </p>
	              </div>
                  <div class="input-field col s12 m3 l3">
                    <select name="categoryId" id="categoryId" class="validate browser-default" required multiple onchange="changeSelect()">
					<option disabled="disabled" selected>Select Category</option>
                    <c:forEach items="${categorylist }" var="cat">
						<option value="${cat.id }">${cat.name }</option>
                    </c:forEach>
					</select>
                        </div>
                  </div>  
	            
	            <div class="row section">
	             <div class="col s12 m4 l3">
	                <p>Description</p>
	              </div>
	              
	              <div class="input-field col s8 m4 l7">
                          <textarea id="imgDesc" type="text" class="materialize-textarea" maxlength="250" autocomplete="off" name="imageDescription" ></textarea>
                          <label for="email" class="">Image Description</label>
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
	                type :{
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
	       
	        function checkImageLink(text){
	        	var textValue=text.value;
	        	$.post("${imgvids}/checkVideoLink", {urllink: textValue , from :'image'}, function(result){
	            	console.log(result)
	        		if(result){
	        			$("#card-alert").removeClass("hide");
	        			$("#imgLink").val("");
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