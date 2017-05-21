<jsp:include page="../includes/include_css.jsp"></jsp:include>
<!--Mobile Collapse Button-->
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<security:authorize access="isAuthenticated()">
	<security:authentication property="principal.email"
		var="loggedInUser" />
	<security:authentication property="principal.name"
		var="loggedInUserName" />
	
	<input type="hidden" id="userName" name="userName"
		value="${loggedInUser}" />
	</security:authorize>

<header id="header" class="page-topbar">
<div class="navbar-fixed">
      <nav class="navbar-color">
    <div class="nav-wrapper">
      <h1 class="logo-wrapper"><a href="#!" class="brand-logo darken-1"><img alt="abatar" src="${imgvids}/static/images/materialize-logo.png"> </a></h1>
      <a href="#" data-activates="mobile-demo" class="button-collapse"><i class="mdi-navigation-menu"></i></a>
       <ul class="right hide-on-med-and-down">
       <security:authorize access="hasRole('ROLE_USER')">
       <li><a href="${imgvids}/user/homepage">Home</a></li>
        <li><a href="${imgvids}/user/image/getAllFile">All Images</a></li>
<%--         <li><a href="${imgvids}/user/video/getAllFile">All Videos</a></li> --%>
        </security:authorize>
       
       <security:authorize access="isAuthenticated()">
        <li><a href="${imgvids}/logout">Logout</a></li>
        </security:authorize>
        
        
        <!-- <li><a href="badges.html">Components</a></li>
        <li><a href="collapsible.html">Javascript</a></li>
        <li><a href="mobile.html">Mobile</a></li> -->
      </ul>
      <ul class="side-nav" id="mobile-demo">
      <security:authorize access="isAuthenticated()">
        <li><a href="${imgvids}/logout">Logout</a></li>
        </security:authorize>
       <!--  <li><a href="badges.html">Components</a></li>
        <li><a href="collapsible.html">Javascript</a></li>
        <li><a href="mobile.html">Mobile</a></li> -->
      </ul>
    </div>
  </nav>
  </div>
       </header>
<%-- <div class="row">
                            <div class="col s12 m12 l12">
                                <ol class="breadcrumbs">
			                      <c:forEach var="entry" items="${sessionScope.currentBreadCrumb}">
									<c:when test="${entry.currentPage == true}">
									  <li class="active"> ${entry.label}</li>
									   </c:when>
										<c:otherwise>
											<li><a href="${entry.url}">${entry.label}></a></li>
										</c:otherwise>
											
                                    </c:forEach>
                                </ol>
                            </div>
                        </div> --%>
