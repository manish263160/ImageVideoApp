<%@include file="../fragments/includetags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Image Upload</title>
<jsp:include page="../fragments/header.jsp" />
 <link href="${imgvids}/static/lib/css/magnific-popup.css" type="text/css" rel="stylesheet" media="screen,projection">

</head>
<body class="${themecolor }">

	<div class="container">
	<div class="progress" style="display: none;">
      <div class="indeterminate"></div>
  </div>
<c:choose>
<c:when test="${ not empty allfileList}">

	<div class="section">
            <p class="caption">All Images that uploaded by ${user.name }</p>
			<p><a class="waves-effect waves-light btn" href="#modal1">Delete All</a>
                </p>
                              <!-- Modal Structure -->
                <div id="modal1" class="modal card pink lighten-5">
                  <div class="modal-content ">
                  <h4>Delete Confirmation</h4>
                   <p>All images will be deleted permanently, and can't be undone. </p>
                    <p>Are you sure you want to delete all images?</p>
                  </div>
                  <div class="modal-fixed-footer card-action pink lighten-4">
                    <a href="#" class="waves-effect waves-red btn-flat modal-action modal-close pink-text">Disagree</a>
                    <a href="#" class="waves-effect waves-green btn-flat modal-action modal-close pink-text" onclick="deleteAllImg('All')">Agree</a>
                  </div>
                </div>
                
  
  
			<div class="divider"></div>

              <div class="masonry-gallery-wrapper">                
                <div class="popup-gallery">
                  <div class="gallary-sizer"></div>
                  <c:forEach items="${allfileList}" var="images">
                  <div class="gallary-item"><a href="${images.imageUrl }" title="${images.imageDescription}" id=${images.id }>
                  <img src="${images.imageUrl }"></a></div>
                  
                  </c:forEach>
                  <!-- <div class="gallary-item"><a href="http://demo.geekslabs.com/materialize/v3.1/images/gallary/2.jpg" title="Winter Dance"><img src="http://demo.geekslabs.com/materialize/v3.1/images/gallary/2.jpg"></a></div>
                  <div class="gallary-item"><a href="http://demo.geekslabs.com/materialize/v3.1/images/gallary/3.jpg" title="The Uninvited Guest"><img src="http://demo.geekslabs.com/materialize/v3.1/images/gallary/3.jpg"></a></div>
                  <div class="gallary-item"><a href="http://demo.geekslabs.com/materialize/v3.1/images/gallary/4.jpg" title="Oh no, not again!"><img src="http://demo.geekslabs.com/materialize/v3.1/images/gallary/4.jpg"></a></div>
                  <div class="gallary-item"><a href="http://demo.geekslabs.com/materialize/v3.1/images/gallary/5.jpg" title="Swan Lake"><img src="http://demo.geekslabs.com/materialize/v3.1/images/gallary/5.jpg"></a></div>
                  <div class="gallary-item"><a href="http://demo.geekslabs.com/materialize/v3.1/images/gallary/6.jpg" title="The Shake"><img src="http://demo.geekslabs.com/materialize/v3.1/images/gallary/6.jpg"></a></div>
                  <div class="gallary-item"><a href="http://demo.geekslabs.com/materialize/v3.1/images/gallary/7.jpg" title="Who's that, mommy?"><img src="http://demo.geekslabs.com/materialize/v3.1/images/gallary/7.jpg"></a></div>
                  <div class="gallary-item"><a href="http://demo.geekslabs.com/materialize/v3.1/images/gallary/8.jpg" title="Who's that, mommy?"><img src="http://demo.geekslabs.com/materialize/v3.1/images/gallary/8.jpg"></a></div> -->
                </div>
              </div>
          </div>
	
	
	</c:when>
	<c:otherwise>
		<div class="row">
			<div class="col s8 m6 l6 offset-l4">
				<div class="card">
					<%-- <div class="card-image">
						<c:if test="${not empty imagepath }">
							<img src="${imagepath }" alt="sample">
						</c:if>

					</div> --%> 
					<div class="card-content">
						<p>There is no  Images.Please click upload button to upload image.</p>
					</div>
					<div class="card-action">
						<a href="${imgvids}/user/uploadImage" class="btn waves-effect waves-red light-blue darken-4">Upload Image</a>
					</div>
				</div>
			</div>
		</div>
	</c:otherwise>
	
</c:choose>

<!-- <div class="preloader-wrapper big active">
      <div class="spinner-layer spinner-blue">
        <div class="circle-clipper left">
          <div class="circle"></div>
        </div><div class="gap-patch">
          <div class="circle"></div>
        </div><div class="circle-clipper right">
          <div class="circle"></div>
        </div>
      </div>
</div> -->	
</div>
	<jsp:include page="../includes/include_js.jsp" />
	<script type="text/javascript" src="${imgvids}/static/lib/js/masonry.pkgd.min.js"></script>
	<script type="text/javascript" src="${imgvids}/static/lib/js/imagesloaded.pkgd.min.js"></script>
	<script type="text/javascript" src="${imgvids}/static/lib/js/jquery.magnific-popup.min.js"></script>
	<script type="text/javascript">
	
    $(document).ready(function(){
	    // the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
    	$('#modal1').modal();
	  });
      /*
       * Masonry container for Gallery page
       */
      var $containerGallery = $(".masonry-gallery-wrapper");
      $containerGallery.imagesLoaded(function() {
        $containerGallery.masonry({
            itemSelector: '.gallary-item img',
           columnWidth: '.gallary-sizer',
           isFitWidth: true
        });
      });

      //popup-gallery
      $('.popup-gallery').magnificPopup({
        delegate: 'a',
        type: 'image',
        closeOnContentClick: true,    
        fixedContentPos: true,
        tLoading: 'Loading image #%curr%...',
        mainClass: 'mfp-img-mobile mfp-no-margins mfp-with-zoom',
        gallery: {
          enabled: true,
          navigateByImgClick: true,
          preload: [0,1] // Will preload 0 - before current, and 1 after the current image
        },
        image: {
          verticalFit: true,
          tError: '<a href="%url%">The image #%curr%</a> could not be loaded.',
          titleSrc: function(item) {
            return '<span class="row"><a class="btn waves-effect waves-light col l6" onclick="deleteAllImg('+item.el.attr('id')+')" >Delete</a> <a class="btn waves-effect waves-light col l6" href="${imgvids}/editImageInfo?imageId='+item.el.attr('id')+'" type="button" name="action">Edit</a></span>';
		          },
        zoom: {
          enabled: true,
          duration: 300 // don't foget to change the duration also in CSS
        }
        }
      });
      
      function deleteAllImg(item){
    	  console.log("item==="+item);
    	  $(".progress").show();
    	  $.ajax({
    		    url: '${imgvids}/deleteImages.json?imageId='+item,
    		    type: 'GET',
    		    success: function(result) {
    		        // Do something with the result
    		        console.log("result=="+result)
/*     		      	  $(".progress").hide();	 */
    		        if(result === 'success'){
    		        	location.reload();
    		        }
    		    }
    		});
    	  
      }
      
  
    </script>
	
</body>



</html>
