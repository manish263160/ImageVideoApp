<%@include file="../fragments/includetags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Category</title>
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
<h4 class="label col s6 m4">Existing Category List</h4>
<p><a class="waves-effect waves-light btn modal-trigger  light-blue right" href="#modal1">Add More</a>
                </p>
</div>
<c:choose>
<c:when test="${not empty allcategory}">
<div class="divlist"> 
<ul class="collection" id="listname">
<li class="collection-item row">
<strong class="col s2">Category Name</strong>
<strong class="col s4">Category For</strong>
<strong class="col s1 right ">Action</strong>
</li>
   <c:forEach var="value" items="${allcategory }">
   <li class="collection-item row">
   <div class="col s2">
   <a href="#!" ><i class="secondary-content mdi-action-delete left " onclick="deleteCat(${value.id})"></i></a>
   ${value.name }</div>
   <div class="col s4 ">
   <c:choose>
   	<c:when test="${value.catFor eq 1 }">Image</c:when>
   	<c:when test="${value.catFor eq 2 }">Video</c:when>
   </c:choose>
   </div>  
   <a href="javascript:void(0);" class="col s1 secondary-content right modal-trigger" onclick="editcategory('${value.id}','${value.name}','${value.catFor}')">Edit</a>
   &nbsp;&nbsp;&nbsp;
   </li>
   </c:forEach>
  </ul>
  </div>
</c:when>
<c:otherwise>
<ul class="collection">
<li class="collection-item">There is no category present in DB.</li>
</ul>
</c:otherwise>
</c:choose>
 

  </div>

</body>
<div id="editmodel" class="modal">
                  <div class="modal-content">
                  <div id="card-alert" class="card green editpop" style="display: none;">
	                      <div class="card-content white-text">
	                        <p><i class="mdi-alert-error"></i> Category Edited Successfully.</p>
	                      </div>
	                      <button type="button" class="close white-text" data-dismiss="alert" aria-label="Close">
	                        <span aria-hidden="true">×</span>
	                      </button>
	                    </div>
		<form:form  action="#" class="modal-content" 
			id="categoryFormEdit"  >
			<div class="row">
				<div class="input-field col s3">
					<label class="center-align">Edit Category Name</label>
				</div>
				<div class="input-field col s3">
					<input type="text" placeholder="Please write category" name="editedname" 
						id="editedname">
						<input type="hidden" name="editid" id="editid">
				</div>
				
				<div class="input-field col s3">
					<label class="center-align">Edit Category For</label>
				</div>
				<div class="input-field col s3">
					<span  id="editedCatFor"></span>
						
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
	                        <p><i class="mdi-alert-error"></i> Category Added Successfully.</p>
	                      </div>
	                      <button type="button" class="close white-text" data-dismiss="alert" aria-label="Close">
	                        <span aria-hidden="true">×</span>
	                      </button>
	                    </div>
		<form:form  action="#" class="modal-content" 
			id="categoryForm"  >
			<div class="row">
				<div class="input-field col s3">
					<label class="center-align">Category Name</label>
				</div>
				<div class="input-field col s3">
					<input type="text" placeholder="Please write category" name="namecat" 
						id="namecat">
				</div>
				<div class="input-field col s3">
					<label class="center-align">Category For</label>
				</div>
				<div class="input-field col s3">
					<select name="catFor" id="catFor">
						<option value="" disabled selected>Select</option>
						<option value="1">Image</option>
						<option value="2">Video</option>
					</select>
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
		console.log('----',$("#namecat").val()+'   ',$("#categoryForm").valid(),'  ---',$("#catFor").valid());
		if($("#categoryForm").valid()){
		$.post( "${imgvids}/admin/insertCategorySeries/categories", { name: $("#namecat").val(),catFor: $("#catFor").val()})
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
    		$.post( "${imgvids}/admin/editCategorySeries/categories", { name: name,id : id })
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
             namecat :{
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
    	$.post( "${imgvids}/admin/delete/categories", { idval: id })
		  .done(function( data ) {
		    if(data){
		    	reload();
		    }
		  });
    }
    }
    
    function editcategory(id,name,catFor){
    	console.log("------------",id,"  "+catFor);
    	$("#categoryFormEdit")[0].reset();
    	$("#editedname").val(name);
    	$("#editid").val(id);
    	var text= catFor == 1 ? 'Image' :'Video';
    	$("#editedCatFor").text(text);
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
