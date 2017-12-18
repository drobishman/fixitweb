<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Users List</title>
<link href="<c:url value='/static/css/bootstrap.css' />"
	rel="stylesheet"></link>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body style="overflow: auto;">
	<div class="generic-container">
		<%@include file="authheader.jsp"%>
		<div class="panel panel-default">
			
			<!-- Logged User -->
			<sec:authorize access="hasRole('USER')">
				<div class="panel-heading">
				<span class="lead">User details and cars </span>
				</div> 
				<table class="table table-hover">
						<thead>
							<tr>
								<th>Firstname</th>
								<th>Lastname</th>
								<th>Email</th>
								<th>SSO ID</th>
								<th width="100"></th>
								<th width="100"></th>

							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${loggedinuser.firstName}</td>
								<td>${loggedinuser.lastName}</td>
								<td>${loggedinuser.email}</td>
								<td>${loggedinuser.ssoId}</td>
								<td><a href="<c:url value='/edit-user-${user.ssoId}' />"
									class="btn btn-success custom-width">edit</a></td>
							</tr>
						<table class="table table-hover">
							<thead>
								<tr>
									<th>Registration number</th>
									<th>Chasis number</th>
									<th>Brand</th>
									<th>Model</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${loggedinuser.userCars}" var="car">
									<tr>
										<td>${car.registrationNumber}</td>
										<td>${car.chasisNumber}</td>
										<td>${car.brand}</td>
										<td>${car.model}</td>
										<th width="100"></th>
										<th width="100"></th>
									</tr>
								</c:forEach>
							</tbody>
						</table>
			</sec:authorize>
			
			<!-- Users -->
			<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
			<div class="panel-heading">
				<span class="lead">List of Users </span>
			</div>
				<c:forEach items="${users}" var="user">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>Firstname</th>
								<th>Lastname</th>
								<th>Email</th>
								<th>SSO ID</th>
								<th width="100"></th>
								<th width="100"></th>

							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${user.firstName}</td>
								<td>${user.lastName}</td>
								<td>${user.email}</td>
								<td>${user.ssoId}</td>
								<td><a href="<c:url value='/edit-user-${user.ssoId}' />"
									class="btn btn-success custom-width">edit</a></td>
								<td><a href="<c:url value='/delete-user-${user.ssoId}' />"
									class="btn btn-danger custom-width">delete</a></td>
							</tr>
						<table class="table table-hover">
							<thead>
								<tr>
									<th>Registration number</th>
									<th>Chasis number</th>
									<th>Brand</th>
									<th>Model</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${user.userCars}" var="car">
									<tr>
										<td>${car.registrationNumber}</td>
										<td>${car.chasisNumber}</td>
										<td>${car.brand}</td>
										<td>${car.model}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:forEach>
					
				</tbody>
			</table>
		</div>
		</sec:authorize>
		
		<sec:authorize access="hasRole('ADMIN')or hasRole('DBA')">
			<div class="well">
				<a href="<c:url value='/newuser' />">Add New User</a>
			</div>
		</sec:authorize>

		<div class="panel panel-default">
			<!-- Cars -->
			<sec:authorize access="hasRole('ADMIN')">
				<div class="panel-heading">
					<span class="lead">List of Cars </span>
				</div>
				<table class="table table-hover">
					<thead>
						<tr>
							<th>Registration number</th>
							<th>Chasis number</th>
							<th>Brand</th>
							<th>Model</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${cars}" var="car">
							<tr>
								<td>${car.registrationNumber}</td>
								<td>${car.chasisNumber}</td>
								<td>${car.brand}</td>
								<td>${car.model}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</sec:authorize>
		</div>

		<div class="panel panel-default">
			<!-- Trouble Codes -->
			<sec:authorize access="hasRole('ADMIN')">
				<div class="panel-heading">
					<span class="lead">List of Trouble Codes </span>
				</div>
				<table class="table table-hover">
					<thead>
						<tr>
							<th>Number</th>
							<th>Fault Location</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${troubleCodes}" var="troubleCode">
							<tr>
								<td>${troubleCode.number}</td>
								<td>${troubleCode.faultLocation}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</sec:authorize>
		</div>

		<sec:authorize access="hasRole('ADMIN')">
			<div class="well">
				<a href="<c:url value='/newTroubleCode' />">Add New TroubleCode</a>
			</div>
		</sec:authorize>
	</div>
</body>
</html>
