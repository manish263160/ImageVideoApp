	<%@include file="../fragments/includetags.jsp"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Image Upload</title>
	<jsp:include page="../fragments/header.jsp" />
	<link href="${imgvids}/static/lib/css/dropify.min.css" type="text/css" rel="stylesheet" media="screen,projection">
		<style type="text/css">
		#input_file-error {
			color: red !important;
			padding-top: 5% !important;
		}
		</style>
</head>
	<body class="${themecolor }">
	<c:choose>
	<c:when test="${!isEdited }">
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
	
	<form action="${imgvids}/editImageUpload" method="post" modelAttribute="uploadedVideo" enctype="multipart/form-data" id="formupload">
	               <div class="divider"></div>
	               <input type="hidden" name="tableName" value="uploaded_video">
	            <div class="row section">
	              <div class="col s12 m4 l3">
	                <p>Previous Image Is Below and Select An Image to Edit (Max 2MB)</p>
	                  <img alt="" src="${imageInfo.videoThumbnail }" data-height="50" width="50"/>
	              </div>
	              <div class="col s8 m4 l7">
	                  <input type="file" id="input_file" name="file" class="dropify" data-height="150"  data-max-file-size="2M" />
	                  <input type="hidden" name="id" value="${imageInfo.id }">
	              </div>
	            </div>
	            <div class="row section">
	             <div class="col s12 m3 l3">
	                <p>Video Link</p>
	              </div>
	              
	              <div class="input-field col s12 m3 l3">
                          <input id="videoLink" type="text" class="validate" autocomplete="off" name="videoLink" value="${imageInfo.videoLink}">
                          <label for="videoLink" class="">Video Link</label>
                        </div>
                  
                  <div class="col s12 m3 l3">
	                <p>Time Length</p>
	              </div>
                  <div class="input-field col s12 m3 l3">
                          <input id="timeLength" type="text" class="validate" autocomplete="off" name="timeLength" value="${imageInfo.timeLength }">
                          <label for="timeLength" class="">Time Length</label>
                        </div>
                     </div>   
                        <div class="row section">
                  <%-- <div class="col s12 m3 l3">
	                <p>Category</p>
	              </div>
                  <div class="input-field col s12 m3 l3">
                    <select name="categoryId" id="categoryId" class="validate" required >
					<option value=""  selected>Select Category</option>
                    <c:forEach items="${categorylist }" var="cat">
						<option value="${cat.id }" ${ imageInfo.categoryId eq cat.id ? 'selected' : ''}>${cat.name }</option>
                    </c:forEach>
					</select>
                        </div> --%>
                        
                  <div class="col s12 m3 l3">
	                <p>Series</p>
	              </div>
                  <div class="input-field col s12 m3 l3">
					<select name="seriesId" id="seriesId" class="validate" >
						<option value=""  selected>Select Series</option>
						<c:forEach items="${serieslist }" var="sers">
							<option value="${sers.id }" ${ imageInfo.seriesId eq sers.id ? 'selected' : ''}>${sers.name }</option>
						</c:forEach>
					</select>
				</div>   
				   
	            </div>
	            
	            <div class="row section">
	            <div class="col s12 m4 l3">
	                <p>Image Title</p>
	              </div>
	              
	              <div class="input-field col s12 m4 l3">
                          <input id="title" type="text" maxlength="200" class="validate" autocomplete="off" name="title" value="${imageInfo.title }">
                          <label for="title" class="">Image Title</label>
                        </div>
	             <div class="col s12 m4 l3">
	                <p>Description</p>
	              </div>
	              
	              <div class="input-field col s12 m4 l3">
                          <textarea id="imgDesc" maxlength="200" type="text" class="materialize-textarea"  autocomplete="off" name="description" >${imageInfo.description}</textarea>
                          <label for="email" class="">Image Description</label>
                        </div>
	            </div>
	      <div class="row section">
	      <div class="col s12 m8 l9  center" style="padding-right: 193px;" ><button class="btn btn-large waves-effect waves-light red darken-4" type="submit"> Edit</button></div>
	      </div>
	      </form>
	      </div>
	</c:when>
	<c:otherwise>
	<div class="container">
		<div class="row">
			<div class="col s8 m6 l6 offset-l4">
				<div class="card">
					<%-- <div class="card-image">
						<c:if test="${not empty imagepath }">
							<img src="${imagepath }" alt="sample">
						</c:if>

					</div> --%> 
					<div class="card-content">
						<p>Edit successfully.</p>
					</div>
					<div class="card-action">
						<a href="${imgvids}/user/getAllVids" class="btn waves-effect waves-red light-blue darken-4">OK</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	</c:otherwise>
	</c:choose>
	
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
	               /*  file :{
	                	required: true,
	                }, */
	            	
	                videoLink :{
	                	required: true,
	                },
	                /* timeLength :{
	                	required: true,
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
	                /* description :{
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
	         });	        
	       
	    </script>

	</body>
	</html>