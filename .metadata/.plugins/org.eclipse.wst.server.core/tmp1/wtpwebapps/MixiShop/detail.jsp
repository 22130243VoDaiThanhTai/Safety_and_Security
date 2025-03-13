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

<div class="container mt-5 mb-5">
	<div class="">
		<div class="row g-0">
			<div class="col-md-4 border-end">
				<div class="d-flex flex-column justify-content-center">
					<div class="main_image">
						<img src="<c:url value='/images/${productDetail.image}'/>"
							id="main_product_image" style="width: 91%;">
					</div>
				</div>
			</div>
			<div class="col-md-8">
				<div class="p-4 right-side">
					<div class="d-flex justify-content-between align-items-center">
						<h2>${productDetail.name}</h2>
						<span class="heart"><i class='bx bx-heart'></i></span>
					</div>
					<h5>
						<fmt:formatNumber value="${productDetail.price}" type="number"
							groupingUsed="true" />
						VNĐ
					</h5>

					<div class="ratings d-flex flex-row align-items-center">
						<span>(Tiết kiệm)</span>
					</div>

					<div class="mt-5">
						<span class="fw-bold text-primary">KHUYẾN MÃI - ƯU ĐÃI</span>
						<div class="colors">
							<ul id="marker">
								<li>Đồng giá ship toàn quốc 30k</li>
								<li>Hỗ trợ trả lời thắc mắc qua fanpage chính thức</li>
								<li>Khuyến mãi trực tiếp trên giá sản phẩm</li>
								<li>Đổi trả nếu sản phẩm lỗi bất kì</li>
							</ul>
						</div>
					</div>

					<hr>
					<div class="buttons d-flex flex-row mt-5 gap-3">
						<form action="detail" method="post" onsubmit="return checkProductQuantity(${product.quantity}, '${product.name}')">
							<input type="hidden" name="action" value="add" /> <input
								type="hidden" name="productCode" value="${productDetail.id}" />
							<c:choose>
								<c:when test="${empty sessionScope.account}">
									<!-- Nếu chưa đăng nhập, hiển thị thông báo và chuyển hướng đến trang đăng nhập -->
									<a href="login"
										onclick="alert('Bạn chưa đăng nhập tài khoản! \nVui lòng đăng nhập trước khi mua sắm!');">
										<button type="button" class="btn btn-outline-success btn-scale btn-radius"><i class="fa-solid fa-cart-shopping"></i> Thêm
											vào giỏ hàng</button>
									</a>
								</c:when>
								<c:otherwise>
									<!-- Nếu đã đăng nhập, thêm vào giỏ hàng -->
									<button type="submit" class="btn btn-outline-success btn-scale btn-radius" style="padding: 8px 50px"><i class="fa-solid fa-cart-shopping"></i> Thêm
										vào giỏ hàng</button>
								</c:otherwise>
							</c:choose>

						</form>
					</div>
					
					<br>
					<div>
						<h6>Hotline: 0822221992 (7:30 - 22:00)</h6>
					</div>
					
					<ul class="product-policises list-unstyled py-sm-3 px-sm-3 m-0">
						<li class="media" style="display: flex;">
							<div class="mr-2">
								<img class="img-fluid " width="24" height="24" src="//theme.hstatic.net/200000881795/1001243022/14/policy_product_image_1.png?v=152" alt="Giao hàng toàn quốc">
							</div>
							<div class="media-body" style="margin-left: 2%"> 
								Giao hàng toàn quốc
							</div>
						</li>
							<li class="media" style="display: flex;">
								<div class="mr-2">
									<img class="img-fluid " width="24" height="24" src="//theme.hstatic.net/200000881795/1001243022/14/policy_product_image_2.png?v=152" alt="Ưu đãi hấp dẫn">
								</div>
								<div class="media-body" style="margin-left: 2%"> 
									Ưu đãi hấp dẫn
								</div>
							</li>
							<li class="media" style="display: flex;">
								<div class="mr-2">
									<img class="img-fluid " width="24" height="24" src="//theme.hstatic.net/200000881795/1001243022/14/policy_product_image_3.png?v=152" alt="Sản phẩm chính hãng">
								</div>
								<div class="media-body" style="margin-left: 2%"> 
									Sản phẩm chính hãng
								</div>
							</li>
							</ul>
				</div>
			</div>
		</div>
	</div>
</div>