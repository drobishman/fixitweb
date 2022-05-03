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

<body>
	<div class="generic-container">
		<%@include file="authheader.jsp"%>
		<div class="panel panel-default">

			<!-- USER -->
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
							<th width="100"></th>
							<th width="100"></th>
							<td><a
								href="<c:url value='/edit-user-${loggedinuser.ssoId}' />"
								class="btn btn-success custom-width">edit</a></td>
						</tr>
				</table>

				<div class="panel-heading">
					<span class="lead">User cars </span>
				</div>

				<table class="table table-hover">
					<thead>
						<tr>
							<th>Registration number</th>
							<th>Chasis number</th>
							<th>Brand</th>
							<th>Model</th>
							<th width="100"></th>
							<th width="100"></th>
							<td><a href="<c:url value='/newcar' />"
								class="btn btn-default">Add car</a></td>
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
								<td><a href="<c:url value='/edit-car-${car.id}' />"
									class="btn btn-success custom-width">edit</a></td>
								<td><a href="<c:url value='/delete-car-${car.id}' />"
									class="btn btn-danger custom-width">delete</a></td>
							</tr>
							<tr>
								<th>Code</th>
								<th>Description</th>
								<th width="100"></th>
								<th width="100"></th>
							</tr>

							<c:forEach items="${car.troubleCodes}" var="troubleCode">
								<tr>
									<td>${troubleCode.number}</td>
									<td>${troubleCode.faultLocation}</td>
									<td>${carTroubleCodes.job}</td>
									<th width="100"></th>
									<th width="100"></th>
								</tr>
							</c:forEach>
							<th>Jobs done</th>
							<th>Related Trouble Code</th>
							<c:forEach items="${car.carTroubleCodes}" var="carTroubleCode">
								<tr>
									<td>${carTroubleCode.job}</td>
									<td>${carTroubleCode.troubleCode.number}</td>
									<th width="100"></th>
									<th width="100"></th>
								</tr>
							</c:forEach>
						</c:forEach>
					</tbody>
				</table>
			</sec:authorize>

			<!-- ADMIN -->
			<sec:authorize access="hasRole('ADMIN')">
				<div class="panel-heading">
					<span class="lead">List of Users </span>
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
						<c:forEach items="${users}" var="user">
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
						</c:forEach>
				</table>

				<%--  Uncomment this to view cars releated to each user
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
					</table> --%>

			</sec:authorize>

			<sec:authorize access="hasRole('ADMIN')">
				<div class="well">
					<a href="<c:url value='/newuser' />" class="btn btn-default">Add
						New User</a>
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
									<td><a href="<c:url value='/edit-car-${car.id}' />"
										class="btn btn-success custom-width">edit</a></td>
									<td><a href="<c:url value='/delete-car-${car.id}' />"
										class="btn btn-danger custom-width">delete</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</sec:authorize>
			</div>

			<sec:authorize access="hasRole('ADMIN')">
				<div class="well">
					<a href="<c:url value='/newcar' />" class="btn btn-default">Add
						car</a>
					</td>
				</div>
			</sec:authorize>

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
									<td><a
										href="<c:url value='/edit-trouble_code-${troubleCode.id}' />"
										class="btn btn-success custom-width">edit</a></td>
									<td><a
										href="<c:url value='/delete-trouble_code-${troubleCode.id}' />"
										class="btn btn-danger custom-width">delete</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</sec:authorize>
			</div>

			<sec:authorize access="hasRole('ADMIN')">
				<div class="well">
					<a href="<c:url value='/newTroubleCode' />" class="btn btn-default">Add
						New TroubleCode</a>
				</div>
			</sec:authorize>

			<sec:authorize access="hasRole('DBA')">
				<div class="panel-heading">
					<span class="lead">List of detected trouble codes </span>
				</div>
				<table class="table table-hover">
					<tbody>
					<thead>
						<tr>
							<th>Job</th>
							<th>Trouble Code</th>
							<th>Car</th>
							<th>Brand</th>
							<th>Model</th>
						</tr>
					</thead>
					<c:forEach items="${cars}" var="car">
						<c:forEach items="${car.carTroubleCodes}" var="carTroubleCode">
							<tr>
								<td>${carTroubleCode.job}</td>
								<td>${carTroubleCode.troubleCode.number}</td>
								<td>${carTroubleCode.car.registrationNumber}</td>
								<td>${carTroubleCode.car.brand}</td>
								<td>${carTroubleCode.car.model}</td>
								<th width="100"></th>
								<th width="100"></th>
								<td><a
									href="<c:url value='/edit-car_trouble_code-${carTroubleCode.id}' />"
									class="btn btn-success custom-width">edit</a></td>
							</tr>
						</c:forEach>

					</c:forEach>
					</tbody>
				</table>
			</sec:authorize>

		</div>
	</div>
</body>
</html>
