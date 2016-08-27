<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@page session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Secure Banking System</title>

<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="shortcut icon"
	href="<c:url value="/resources/img/favicon.png" />">
<link rel="apple-touch-icon" sizes="57x57"
	href="<c:url value="/resources/img/apple-touch-icon.png" />">
<link rel="apple-touch-icon" sizes="72x72"
	href="<c:url value="/resources/img/apple-touch-icon-72x72.png" />">
<link rel="apple-touch-icon" sizes="114x114"
	href="<c:url value="/resources/img/apple-touch-icon-114x114.png" />">

<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
<script src="<c:url value="/resources/js/jquery-1.10.2.min.js" />"></script>
</head>
<body>

	<div class="banner">
		<div class="header">
			<div class="header-inner container clear">
				<span class="sr">SBS</span></a> <input
					type="checkbox" id="navigation-toggle-checkbox"
					name="navigation-toggle-checkbox"
					class="navigation-toggle-checkbox none"> <label
					for="navigation-toggle-checkbox" class="navigation-toggle-label"
					onclick> <span class="navigation-toggle-label-inner">
						<span class="sr">Navigation</span>
				</span>
				</label>
				<div class="navigation">
					<ul class="navigation-menu">
						<li class="navigation-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
						<li class="navigation-item"><a href="${pageContext.request.contextPath}/governmentAuthority">Govt Authority</a></li>

						<!--Login link will only be seen if not logged in  -->
						<sec:authorize access="!isAuthenticated()">
							<li class="navigation-item"><a href="${pageContext.request.contextPath}/login">Login</a></li>
						</sec:authorize>
						<sec:authorize access="isAuthenticated()">
							<li class="navigation-item"><a href="${pageContext.request.contextPath}/userHome">User Dashboard</a></li>
						</sec:authorize>
						<sec:authorize access="!isAuthenticated()">
							<li class="navigation-item"><a
								href="${pageContext.request.contextPath}/usersignupOTP">Sign Up</a></li>
						</sec:authorize>
						<li class="navigation-item"><a href="#about">About Us</a></li>

						<!--Logout link will only be seen if logged in  -->
						<sec:authorize access="isAuthenticated()">
							<li class="navigation-item"><a
								href="javascript:formSubmit()"> Logout</a></li>
						</sec:authorize>
					</ul>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="banner-inner">
				<h1 class="banner-lead">
					<span class="banner-lead-1">Welcome to Bank Of Sun Devils</span> 
				</h1>
				<div class="banner-content">
					<p>Growth doesn't always go the way you expect. Our strategy
						employees can help you climb.</p>
				</div>
				<div class="banner-buttons">
					<a href="#services" class="button button-primary">Get started</a> <a
						href="#about" class="button button-secondary">Learn more</a>
				</div>
			</div>
		</div>
	</div>

	<div id="services" class="animate-block content-block services-block">
		<div class="services-block-inner container">
			<div class="content-block-inner text-center">
				<h2>Our Services</h2>
				<div class="col-desktop-10 no-float center-element">
					<p>Our services focus on our clients happiness and best
						experiences.</p>
				</div>
			</div>
			<ul class="services-list clear">
				<li class="service-item">
					<h3
						class="service-item-heading service-icon service-icon-performance">Performance
						Increase</h3>
					<p>Enabling clients to grow revenue, improve margins and
						reposition quickly.</p>
				</li>
				<li class="service-item">
					<h3 class="service-item-heading service-icon service-icon-customer">Secure
						Transactions</h3>
					<p>Provide most advanced and reliable experience to the
						customers.</p>
				</li>
				<li class="service-item">
					<h3 class="service-item-heading service-icon service-icon-it">Innovative
						Technology</h3>
					<p>Realizing the full potential of IT resources, investments
						and assets.</p>
				</li>
				<li class="service-item">
					<h3
						class="service-item-heading service-icon service-icon-transformation">Full
						Transformation</h3>
					<p>A cross-functional effort to alter the financial,
						operational and strategic trajectory of a business.</p>
				</li>
				<li class="service-item">
					<h3 class="service-item-heading service-icon service-icon-strategy">Strategy</h3>
					<p>Tailored solutions that deliver results and achieve
						sustained growth.</p>
				</li>
				<li class="service-item">
					<h3 class="service-item-heading service-icon service-icon-equity">Private
						Equity</h3>
					<p>Advising investors across the entire investment life cycle.
					</p>
				</li>
				<li class="service-item">
					<h3 class="service-item-heading service-icon service-icon-digital">Digital</h3>
					<p>Deliver on core strategy, delight customers and operate
						smarter and faster.</p>
				</li>
				<li class="service-item">
					<h3 class="service-item-heading service-icon service-icon-delivery">Results
						Delivery</h3>
					<p>Predicting, measuring and managing risk associated with
						change management.</p>
				</li>
			</ul>
		</div>
	</div>
	<div id="about" class="content-block about-block">
		<div class="about-block-inner container">
			<div class="animate-block content-block-inner text-center">
				<h2>About Us</h2>
				<div class="col-desktop-10 no-float center-element">
					<p>SBS occupies a rare position within the banking industry. We
						are one of the largest privately owned bank in the United States
						and this combination of size and private ownership provides our
						customers with a special brand of banking.</p>
				</div>
			</div>
			<div class="animate-block about-content clear">
				<div class="what-we-do-block col-tablet-6">
					<h3 class="h3">What We Do</h3>
					<p>When someone asks what we do at SBS, it's tempting to point
						out our four-decade track record.</p>
					<p>SBS offers a full range of commercial, trust, private
						banking and mortgage banking products and services. SBS is a
						strong commercial real estate lender and a major servicer of
						mortgage loans nationally.</p>
					<p>While our portfolio of services competes with those of the
						larger banks, our people and culture truly define the SBS
						difference. SBS provides exceptional financial and deeply
						committed customer service. Our team members are loyal - loyal in
						their character, loyal in their personal commitment to our
						customers and loyal to always doing the right thing. No matter the
						town, no matter the location, no matter the department, SBS
						customers can always count on working with thoughtful,
						intelligent, honest professionals who are true to their financial
						goals.</p>
				</div>
				<div class="our-clients-block col-tablet-6 clear">
					<h3 class="h3">Our Team</h3>
					<ul>
					<li>Ankita Chandak</li>
					<li>Swetha Joshi</li>
					<li>Radhika Kenage</li>
					<li>Deepthi K Pothireddy</li>
					<li>Nishant Kumar</li>
					<li>Sajal Jain </li>
					<li>Akshay Ashwathnarayana</li>
					<li>Ajay Modi</li>
					<li>Rahul Krishna Vasantham</li>
					</ul>
				</div>
			</div>
		</div>
	</div>



	<div id="contact" class="animate-block content-block contact-block">
		<div class="contact-block-inner container">
			<div class="clear">
				<div class="contact-block-content col-tablet-6">
					<h2 class="h2 uppercase">SBS</h2>
					<p>Thank you for your interest in SBS. Please contact us using
						the information below. To locate contacts in the SBS office
						closest to you, visit our office websites. To get the latest
						updates from SBS, subscribe to a newsletter or connect with us on
						social media.</p>
					<ul class="contact-list">
						<li><span class="icon contact-icon contact-icon-location">ASU,
								Tempe, Az</span></li>
						<li><span class="icon contact-icon contact-icon-phone">+1
								480-xxx-xxxx</span></li>
						<li><span class="icon contact-icon contact-icon-email"><a
								href="mailto:bankofsundevils@gmail.com?subject=General%20Enquiry&anp;body=">bankofsundevils@gmail.com</a></span></li>
					</ul>
				</div>
				
			</div>
		</div>
	</div>

	<div class="footer">
		<div class="footer-inner container">
			<div class="clear">
				<div class="footer-column col-tablet-8">
					<p>&copy; Copyright 2015 &mdash; All Rights Reserved</p>
				</div>
				<div class="footer-column col-tablet-4">
					<ul class="footer-social-list icon-list-inline">

						<li class="navigation-item-social"><a
							class="social-icon social-facebook" href="#"><span class="sr">Facebook</span></a></li>
						<li class="navigation-item-social"><a
							class="social-icon social-twitter" href="#"><span class="sr">Twitter</span></a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	
	<!--Links seen only by admin  -->


	<c:url value="/j_spring_security_logout" var="logoutUrl" />
	
	<!-- csrt for log out-->
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>


	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
		
		$( ".contact-form" ).submit(function( event ) {
			if ($("#name").val().length > 0 && $("#email").val().length > 0 && $("#subject").val().length > 0 && $("#message").val().length > 0) {
    			alert("Thank you for contacting us. We have received your request");	
			} else{
			   alert("Fill up all necessary fields");
			}
  			event.preventDefault();
		});
	</script>
	<script src="<c:url value="/resources/js/vendor/wow.js" />"></script>
	<script src="<c:url value="/resources/js/default.js" />"></script>

</body>
</html>