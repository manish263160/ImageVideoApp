<%@include file="../fragments/includetags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Image Upload</title>
<jsp:include page="../fragments/header.jsp" />
 <link href="${imgvids}/static/lib/css/dropify.min.css" type="text/css" rel="stylesheet" media="screen,projection">
</head>
<body class="${themecolor }">

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

<form action="${imgvids}/uploadVideo?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data" >
 <div class="divider"></div>
            <div class="row section">
              <div class="col s12 m4 l3">
                <p>Select Video (Max 20MB)</p>
              </div>
              <div class="col s12 m8 l9">
                  <input type="file" id="input_file" name="file" class="dropify" data-height="400" data-max-file-size="20M" />
              </div>
                       
            </div>
      <div class="row section">
      <div class="col s12 m8 l9  center"><button class="btn btn-large waves-effect waves-white" type="submit"> Submit</button></div>
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
        
        
       
    </script>
</body>
</html>