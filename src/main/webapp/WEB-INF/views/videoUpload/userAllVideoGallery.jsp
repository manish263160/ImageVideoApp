<%@include file="../fragments/includetags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Image Upload</title>
<link href="${imgvids}/static/lib/css/magnific-popup.css"
	type="text/css" rel="stylesheet" media="screen,projection">
</head>

<body class="${themecolor }">
	<jsp:include page="../fragments/header.jsp" />

	<div class="container">
		<div class="progress" style="display: none;">
			<div class="indeterminate"></div>
		</div>

		<div class="section">
			<p class="caption">All Videos that uploaded by ${user.name }</p>
			<div class="row">
				<!-- <p class="col s3">
					<a class="waves-effect waves-light btn tooltipped modal-trigger"
						href="#modal1" data-position="bottom" data-delay="50"
						data-tooltip="Delete all Videos">Delete All Video</a>
						</p> -->
				<p class="col s3">
					<a href="${imgvids}/user/uploadVideo"
						class="btn-floating btn-large waves-effect waves-light green accent-3 tooltipped"
						data-position="bottom" data-delay="50"
						data-tooltip="Upload a video"> <i class="mdi-content-add ">Upload
							More</i></a>
				</p>
				<!-- <p class="col s3 ">
<input type="date" class="datepicker" placeholder="Datewise search Images" name="searchDate" id="searchDate">
</p>
<button class="btn waves-effect waves-light blue" type="button" onclick="searchImageByDate()">Serach</button> -->
			</div>
			<!-- Modal Structure -->
			<div id="modal1" class="modal card pink lighten-5">
				<div class="modal-content ">
					<h4>Delete Confirmation</h4>
					<p>Your all video from data will be deleted permanently, and
						can't be undone.</p>
					<p>Are you sure you want to delete all video?</p>
				</div>
				<div class="modal-fixed-footer card-action pink lighten-4">
					<a href="#"
						class="waves-effect waves-red btn-flat modal-action modal-close pink-text">Disagree</a>
					<a href="#"
						class="waves-effect waves-green btn-flat modal-action modal-close pink-text"
						onclick="deleteAllImg('All','All')">Agree</a>
				</div>
			</div>



			<div class="divider"></div>
			<c:choose>
				<c:when test="${ not empty categoriesWise || not empty seriesWise}">
					<c:if test="${ not empty categoriesWise}">
						<h4 style="text-align: center;">
							<b>Category Wise Video</b>
						</h4>
						<div class="divider"></div>
						<c:forEach items="${categoriesWise }" var="catewise"
							varStatus="loop">
							<p class="caption">
								<b>${catewise.categoryName}</b>
							</p>
							<div class="masonry-gallery-wrapper">
								<div class="popup-gallery">
									<div class="gallary-sizer"></div>
									<c:forEach items="${catewise.categoryList}" var="images">
										<%--                   <c:if test="${images.newSetDate eq  uniqueDate}"> --%>

										<div class="gallary-item">
											<a href="${images.videoThumbnail }"
												title="${images.description}"
												imageName="${images.videoName}" imgid=${images.id }> 
												<img src="${images.videoThumbnail }" style="padding: 2px;"></a>
										</div>
										<%--                   </c:if> --%>
									</c:forEach>

								</div>
							</div>
							<div class="divider"></div>
						</c:forEach>
					</c:if>

					<c:if test="${ not empty seriesWise}">
						<h4 style="text-align: center;">
							<b>Series Wise Video</b>
						</h4>
						<div class="divider"></div>
						<c:forEach items="${seriesWise }" var="serswise" varStatus="loop">
							<p class="caption">
								<b>${serswise.seriesName}</b>
							</p>
							<div class="masonry-gallery-wrapper">
								<div class="popup-gallery">
									<div class="gallary-sizer"></div>
									<c:forEach items="${serswise.seriesList}" var="images">
										<%--                   <c:if test="${images.newSetDate eq  uniqueDate}"> --%>

										<div class="gallary-item">
											<a href="${images.videoThumbnail }"
												title="${images.description}"
												imageName="${images.videoName}" imgid=${images.id }> <img
												src="${images.videoThumbnail }" style="padding: 2px;"></a>
										</div>
										<%--                   </c:if> --%>
									</c:forEach>

								</div>
							</div>
							<div class="divider"></div>
						</c:forEach>
					</c:if>

				</c:when>
				<c:when test="${ empty categoriesWise &&  empty seriesWise}">
					<div class="row warningmodel">
						<div class="col s8 m6 l6 offset-l4">
							<div class="card">
								<%-- <div class="card-image">
<c:if test="${not empty imagepath }">
<img src="${imagepath }" alt="sample">
</c:if>

</div> --%>
								<div class="card-content">
									<p>There is no Video.Please click upload button to upload
										videos.</p>
								</div>

							</div>
						</div>
					</div>
					<div class="masonry-gallery-wrapper">
						<div class="popup-gallery"></div>
					</div>
				</c:when>
			</c:choose>
		</div>






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
	<script type="text/javascript"
		src="${imgvids}/static/lib/js/masonry.pkgd.min.js"></script>
	<script type="text/javascript"
		src="${imgvids}/static/lib/js/imagesloaded.pkgd.min.js"></script>
	<script type="text/javascript"
		src="${imgvids}/static/lib/js/jquery.magnific-popup.min.js"></script>
	<script type="text/javascript">
            $(document).ready(function () {
                // the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
                $('.tooltipped').tooltip({ delay: 50 });
                //     	$('#modal1').modal();
                $('.datepicker').pickadate({
                    selectMonths: true,
                    selectYears: 15,
                    max: new Date(),
                    min: -7,
                    format: 'yyyy-mm-dd'

                });
            });
            /*
            * Masonry container for Gallery page
            */
            var $containerGallery = $(".masonry-gallery-wrapper");
            $containerGallery.imagesLoaded(function () {
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
                    preload: [0, 1] // Will preload 0 - before current, and 1 after the current image
                },
                image: {

                    verticalFit: true,
                    tError: function (item) {'<a href="%url%">The image</a> could not be loaded.'},
                    cursor: 'mfp-zoom-out-cur',
                    titleSrc: function (item) {
                        return '<div class="row">' +
                            '<a class="btn waves-effect waves-light col  disabled" style="left: 50em;top: -20em;" onclick="deleteAllImg(\'' + item.el.attr('imgid') + '\',\'' + item.el.attr('imageName') + '\')" >Delete</a>' +
                            '</div><div class="row">' +
                            ' <a class="btn waves-effect waves-light col l21 s12" href="${imgvids}/editImageInfo?imageId=' + item.el.attr('imgid') + '&tableName=uploaded_video" type="button" name="action">Edit</a></span>' +
                            '</div>';
                    },
                    zoom: {
                        enabled: true,
                        duration: 300 // don't foget to change the duration also in CSS
                    }
                }
            });

            function deleteAllImg(item, name) {
                console.log("item===" + item + " name==" + name);
                name = name.trim();
                $(".progress").show();
                $.ajax({
                    url: '${imgvids}/deleteImages.json?imageId=' + item + '&imageUrl=' + name + '&tableName=uploaded_video',
                    type: 'GET',
                    success: function (result) {
                        // Do something with the result
                        console.log("result==" + result)
                        /*     		      	  $(".progress").hide();	 */
                        if (result === 'success') {
                            location.reload();
                        }
                    }
                });

            }
            function searchImageByDate() {
                var searchDate = $("#searchDate").val();
                if (searchDate == undefined || searchDate == null || searchDate === '') {
                    alert("Please select a Date");
                    document.getElementById('searchDate').focus();
                } else {
                    $.ajax({
                        url: '${imgvids}/getImageBydate.json?searchDate=' + searchDate,
                        type: 'GET',
                        success: function (result) {
                            // Do something with the result
                            console.log("result==", result)
                            /*     		      	  $(".progress").hide();	 */
                            $(".popup-gallery").html('');
                            $(".warningmodel").html('');
                            if (result.length > 0 && result !== '' && result != undefined) {
                                var nehtml = '<div class="gallary-sizer"></div>';
                                result.forEach(function (images) {
                                    console.log(images);
                                    nehtml += '<div class="gallary-item"><a href="' + images.imageUrl + '" title="' + images.imageDescription + '" imageName="' + images.imageName + '" imgid="' + images.id + '">' +
                                        '<img src="' + images.imageUrl + '"></a></div>'
                                });
                                $(".popup-gallery").append(nehtml);
                            } else {
                                var newHtml = '<div id="card-alert" class="card red">' +
                                    '<div class="card-content white-text">' +
                                    ' <p>No Image Found in This Date.</p>' +
                                    '</div>' +
                                    '</div>';
                                $(".popup-gallery").append(newHtml);
                            }
                        }
                    });
                }
            }

        </script>

</body>


</html>