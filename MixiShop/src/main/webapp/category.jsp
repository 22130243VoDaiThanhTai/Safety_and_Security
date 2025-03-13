<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:if test="${not empty sessionScope.notification}">
    <div id="notification" class="notification ${sessionScope.notificationType}">
        <span>${sessionScope.notification}</span>
    </div>
    <c:remove var="notification"/>
    <c:remove var="notificationType"/>
</c:if>

<div class="container">
	<div class="row">
		<div class="col-12 text-center mb-4">
			<c:choose>
				<c:when test="${not empty listProductByCategory}">
					<h2>Sản phẩm trong danh mục</h2>
				</c:when>
				<c:otherwise>
					<h2>Không có sản phẩm nào trong danh mục.</h2>
					<img src="<c:url value='/images/cart_empty_background.webp'/>"
						class="img-fluid" />
				</c:otherwise>
			</c:choose>
		</div>

		<c:forEach var="product" items="${listProductByCategory}">
			<div class="col-lg-3 col-sm-6 d-flex justify-content-center mb-4">
				<div class="card card-product" style="width: 16.2rem;">
					<img src="<c:url value='/images/${product.image}'/>"
						class="card-img-top" alt="${product.name}" height="260">
					<div class="card-body">
						<h6>
							<strong>${product.name}</strong>
						</h6>
						<hr>
						<form action="category" method="post" onsubmit="return checkProductQuantity(${product.quantity}, '${product.name}')">
						    <input type="hidden" name="action" value="add" /> 
						    <input type="hidden" name="productCode" value="${product.id}" />
						    <c:choose>
						        <c:when test="${empty sessionScope.account}">
						            <a href="login" onclick="alert('Bạn chưa đăng nhập tài khoản! \nVui lòng đăng nhập trước khi mua sắm!');">
						                <button type="button" class="btn btn-outline-success btn-radius btn-scale">Thêm vào giỏ hàng</button>
						            </a>
						        </c:when>
						        <c:otherwise>
						            <button type="submit" class="btn btn-outline-success btn-radius btn-scale">Thêm vào giỏ hàng</button>
						        </c:otherwise>
						    </c:choose>
						    <a class="btn btn-outline-info btn-radius btn-scale" href="detail?id=${product.id}">Xem</a>
						</form>



						<h5 class="text-center">
							<strong> <fmt:formatNumber value="${product.price}"
									type="number" groupingUsed="true" maxFractionDigits="0" /> VNĐ
							</strong>
						</h5>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>