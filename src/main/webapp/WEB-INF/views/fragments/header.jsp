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
       <%-- <ul class="right hide-on-med-and-down">
       <security:authorize access="hasAnyRole('ROLE_USER', 'ROLE_ADMIN')">
       <li><a href="${imgvids}/user/homepage">Home</a></li>
        <li><a href="${imgvids}/user/image/getAllFile">All Images</a></li>
        <li><a href="${imgvids}/user/video/getAllFile">All Videos</a></li>
        </security:authorize>
       <security:authorize access="hasAnyRole('ROLE_USER', 'ROLE_ADMIN')">
       <li><a href="${imgvids}/appNotification/notificationPage">App Notification</a></li>
       </security:authorize>
       <security:authorize access="isAuthenticated()">
        <li><a href="${imgvids}/logout">Logout</a></li>
        </security:authorize>
        
        
        <!-- <li><a href="badges.html">Components</a></li>
        <li><a href="collapsible.html">Javascript</a></li>
        <li><a href="mobile.html">Mobile</a></li> -->
      </ul> --%>
      <ul class="side-nav" id="mobile-demo">
      <security:authorize access="hasAnyRole('ROLE_USER', 'ROLE_ADMIN')">
       <li><a href="${imgvids}/user/homepage">Home</a></li>
        <li><a href="${imgvids}/user/image/getAllFile">All Images</a></li>
        <li><a href="${imgvids}/user/video/getAllFile">All Videos</a></li>
        </security:authorize>
       <security:authorize access="hasAnyRole('ROLE_USER', 'ROLE_ADMIN')">
       <li><a href="${imgvids}/appNotification/notificationPage">App Notification</a></li>
       </security:authorize>
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
<div id="main">
        <!-- START WRAPPER -->
        <div class="wrapper">

            <!-- START LEFT SIDEBAR NAV-->
            <aside id="left-sidebar-nav">
                <ul id="slide-out" class="side-nav fixed leftside-navigation ps-container ps-active-y" style="width: 240px; height: 100%">
                <li class="user-details cyan darken-2">
                <div class="row">
                   <!--  <div class="col col s4 m4 l4">
                        <img src="images/avatar.jpg" alt="" class="circle responsive-img valign profile-image">
                    </div> -->
                    <div class="col col s8 m8 l8">
                        
                        <a class="btn-flat dropdown-button waves-effect waves-light white-text profile-btn" href="#" data-activates="profile-dropdown">${user.name }<i class="mdi-navigation-arrow-drop-down right"></i></a><ul id="profile-dropdown" class="dropdown-content">
                            <li class="divider"></li>
                            <security:authorize access="isAuthenticated()">
                            <li><a href="${imgvids}/logout"><i class="mdi-hardware-keyboard-tab"></i> Logout</a>
                            </li>
                            </security:authorize>
                        </ul>
                        <p class="user-roal">Administrator</p>
                    </div>
                </div>
                </li>
                    <security:authorize access="hasAnyRole('ROLE_USER', 'ROLE_ADMIN')">
                <li class="bold active"><a href="${imgvids}/user/homepage" class="waves-effect waves-cyan"><i class="mdi-action-home"></i> Home</a>
                </li>
                <li class="no-padding">
                    <ul class="collapsible collapsible-accordion">
                        <li class="bold"><a class="collapsible-header waves-effect waves-cyan"><i class="mdi-action-account-box"></i> Admin Control</a>
                            <div class="collapsible-body" style="">
                                <ul>
                                    <li><a href="${imgvids}/admin/addCategory">Add Category</a>
                                    </li>
                                    <li><a href="${imgvids}/admin/addSeries">Add Series</a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                    </ul>
                </li>
                <li class="bold"><a href="${imgvids}/user/image/getAllFile" class="waves-effect waves-cyan"><i class="mdi-image-collections"></i> All Images <span class="new badge">4</span></a>
                </li>
                <li class="bold"><a href="${imgvids}/user/video/getAllFile" class="waves-effect waves-cyan"><i class="mdi-av-video-collection"></i> All Videos</a>
                </li>
                
                <li class="li-hover"><div class="divider"></div></li>
                
                <li><a href="${imgvids}/appNotification/notificationPage"><i class="mdi-action-verified-user"></i> App Notifications </a>
                </li>
                        </security:authorize>
                
            </ul>
            </aside>
            <!-- END LEFT SIDEBAR NAV-->

        </div>
        <!-- END WRAPPER -->

    </div>
