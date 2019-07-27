<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>展示投票</title>

		<script src="static/js/jquery-3.3.1.js"></script>
		<link href="static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
		<script src="static/bootstrap-3.3.7-dist/js/bootstrap.js"></script>

		<style type="text/css">
		.progress-bar-info {
			background-color: #3762BC
		}
		</style>
	</head>

	<body>
		<div class="container" style="border: 1px solid silver;border-radius:4px;">
			<div class="row" style="text-shadow: 0 1px #FFFFFF;border-bottom: 1px solid silver;">
				<div class="col-md-12" style="text-align: center;">
					<h2>投票详情</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-md-8" style="float: left">
					<h3>${vote.title }</h3><br />
					<c:forEach items="${vote.optionInfoVoList }" var="item">
						<c:choose>
							<c:when test="${vote.totalVote!=0}">
								${item.content } <span>(<fmt:formatNumber type="number" value="${item.count/vote.totalVote*100 }" pattern="0.00" maxFractionDigits="2"/>%, ${item.count}票)</span><br /><br />
								<div class="progress">
									<div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="${item.count/vote.totalVote*100 }" aria-valuemin="0" aria-valuemax="100" style="width: ${item.count/vote.totalVote*100 }%;">
										<fmt:formatNumber type="number" value="${item.count/vote.totalVote*100 }" pattern="0.00" maxFractionDigits="2"/>%
									</div>
								</div>
							</c:when>
							<c:otherwise>
								${item.content } <span>(<fmt:formatNumber type="number" value="0" pattern="0.00" maxFractionDigits="2"/>%, ${item.count}票)</span><br /><br />
								<div class="progress">
									<div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">
										<fmt:formatNumber type="number" value="0" pattern="0.00" maxFractionDigits="2"/>%
									</div>
								</div>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</div>
				<div class="col-md-4" style="margin-top: 15px;float: right;">
					<table class="table table-bordered">
						<th colspan="2"><strong>概况</strong></th>
						<tr>
							<td>历史投票人次：</td>
							<td>${vote.totalVote }</td>
						</tr>
						<tr>
							<td>投票创建时间：</td>
							<td>${vote.startTime}</td>
						</tr>
						<tr>
							<td>投票截至时间：</td>
							<td>${vote.endTime}</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</body>
</html>