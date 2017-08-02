<%@include file="../fragments/includetags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Series</title>
<style type="text/css">
html,
body {
    height: 100%;
}
#login-page {
    display: table;
    margin: auto;
}
.divlist {
	overflow-y : scroll ;
	max-height: 400px ; 
}

</style>
</head>
<body class="${themecolor }">
<jsp:include page="../fragments/header.jsp" />

<div class="container" >
	 
<div class="row margin">
<h4 class="label col s6 m4">Existing Series List</h4>
<p><a class="waves-effect waves-light btn modal-trigger  light-blue right" href="#modal1">Add More</a>
                </p>
</div>
<c:choose>
<c:when test="${not empty allcategory}">
<div class="divlist"> 
<ul class="collection" id="listname">
   <c:forEach var="value" items="${allcategory }">
   <li class="collection-item">${value.name }  
   <a href="javascript:void(0);" class="secondary-content right modal-trigger" onclick="editcategory('${value.id}','${value.name}')">Edit</a>
   &nbsp;&nbsp;&nbsp;
   <a href="#"><i class="secondary-content mdi-action-delete left " onclick="deleteCat(${value.id})"></i></a>
   </li>
   </c:forEach>
  </ul>
  </div>
</c:when>
<c:otherwise>
<ul class="collection">
<li class="collection-item">There is no Series present in DB.</li>
</ul>
</c:otherwise>
</c:choose>
 

  </div>

</body>
<div id="editmodel" class="modal">
                  <div class="modal-content">
                  <div id="card-alert" class="card green editpop" style="display: none;">
	                      <div class="card-content white-text">
	                        <p><i class="mdi-alert-error"></i> Series Edited Successfully.</p>
	                      </div>
	                      <button type="button" class="close white-text" data-dismiss="alert" aria-label="Close">
	                        <span aria-hidden="true">×</span>
	                      </button>
	                    </div>
		<form:form  action="#" class="modal-content" 
			id="categoryFormEdit"  >
			<div class="row">
				<div class="input-field col s6">
					<label class="center-align">Edit Series Name</label>
				</div>
				<div class="input-field col s6">
					<input type="text" placeholder="Please write category" name="editedname" 
						id="editedname">
						<input type="hidden" name="editid" id="editid">
				</div>
			</div>
                  <div class="modal-footer">
                    <a onclick="reload();" class="waves-effect waves-red btn-flat modal-action modal-close">Reload List</a>
                    <a  onclick="editCall();" class="waves-effect waves-green btn-flat modal-action">Edit</a>
                  </div>
		</form:form>                  
                  
                  </div>
                  </div>


<div id="modal1" class="modal">
                  <div class="modal-content">
                  <div id="card-alert" class="card green modelnode" style="display: none;">
	                      <div class="card-content white-text">
	                        <p><i class="mdi-alert-error"></i> Series Added Successfully.</p>
	                      </div>
	                      <button type="button" class="close white-text" data-dismiss="alert" aria-label="Close">
	                        <span aria-hidden="true">×</span>
	                      </button>
	                    </div>
		<form:form  action="#" class="modal-content" 
			id="categoryForm"  >
			<div class="row">
				<div class="input-field col s6">
					<label class="center-align">Series Name</label>
				</div>
				<div class="input-field col s6">
					<input type="text" placeholder="Please write Series" name="namecat" 
						id="namecat">
				</div>
			</div>
                  <div class="modal-footer">
                    <a onclick="reload();" class="waves-effect waves-red btn-flat modal-action modal-close">Reload List</a>
                    <a  onclick="insertCall();" class="waves-effect waves-green btn-flat modal-action">Add New</a>
                  </div>
		</form:form>
	</div>
                </div>

	<jsp:include page="../includes/include_js.jsp" />
	<script type="text/javascript">
	
	function insertCall() {
		console.log('----',$("#namecat").val()+'   ',$("#categoryForm").valid());
		if($("#categoryForm").valid()){
		$.post( "${imgvids}/admin/insertCategorySeries/series", { name: $("#namecat").val() })
		  .done(function( data ) {
		    if(data){
		    	$("#categoryForm")[0].reset();
		    	$(".modelnode").show();
		    }
		  });
	}
		}
	
	function editCall(){
		var name=$("#editedname").val();
    	var id=$("#editid").val();
    	if($("#categoryFormEdit").valid()){
    		$.post( "${imgvids}/admin/editCategorySeries/series", { name: name,id : id })
    		  .done(function( data ) {
    		    if(data){
//     		    	$("#categoryFormEdit")[0].reset();
    		    	$(".editpop").show();
    		    }
    		  });
    	}
	}
	
	function reload() {
		 window.location.reload(true);
	}
	
	$("#categoryFormEdit").validate({
    rules: {
     	/* imageLink: {
             required: true,
            
         }, */
         editedname :{
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
	
	 $("#categoryForm").validate({
         rules: {
         	/* imageLink: {
                 required: true,
                
             }, */
             name :{
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
    function deleteCat(id){
    	var ll=confirm("Are you shure to delete this!");
    	if( ll == true){
    	$.post( "${imgvids}/admin/delete/series", { idval: id })
		  .done(function( data ) {
		    if(data){
		    	reload();
		    }
		  });
    }
    }
    
    function editcategory(id,name){
    	console.log(id);
    	$("#categoryFormEdit")[0].reset();
    	$("#editedname").val(name);
    	$("#editid").val(id);
    	$('#editmodel').openModal({
            complete: function() { reload();} // Callback for Modal close
          });
    }
    
    $( document ).ready(function() {
        $('.modal-trigger').leanModal({
        	ready: function() { $("#categoryForm")[0].reset();  },
          complete: function() { reload();} // Callback for Modal close
        });
      });

	</script>
</html>
