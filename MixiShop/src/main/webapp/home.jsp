<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- SLIDER -->
<div id="carouselExampleIndicators" class="carousel slide"
	data-bs-ride="carousel">
	<div class="carousel-indicators">
		<button type="button" data-bs-target="#carouselExampleIndicators"
			data-bs-slide-to="0" class="active" aria-current="true"
			aria-label="Slide 1"></button>
		<button type="button" data-bs-target="#carouselExampleIndicators"
			data-bs-slide-to="1" aria-label="Slide 2"></button>
	</div>
	<div class="carousel-inner">
		<div class="carousel-item active">
			<img src="<c:url value='/images/banner/slide1.webp'/>"
				class="d-block w-100" alt="slide1">
		</div>
		<div class="carousel-item">
			<img src="<c:url value='/images/banner/slide2.webp'/>"
				class="d-block w-100" alt="slide2">
		</div>
	</div>
	<button class="carousel-control-prev" type="button"
		data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
		<span class="carousel-control-prev-icon" aria-hidden="true"></span> <span
			class="visually-hidden">Previous</span>
	</button>
	<button class="carousel-control-next" type="button"
		data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
		<span class="carousel-control-next-icon" aria-hidden="true"></span> <span
			class="visually-hidden">Next</span>
	</button>
</div>
<br>
<br>

<!-- POLICY -->
<div class="container">
	<div class="row policies-body justify-content-center">
		<div class="col-lg-3 col-sm-6 d-flex justify-content-center mb-4">
			<div class="policies-item text-center">
				<div class="policies-image mb-2">
					<img
						src="//theme.hstatic.net/200000881795/1001243022/14/policies_icon_1.png?v=137"
						alt="policies_icon_1.png" width="40" height="40" class="img-fluid">
				</div>
				<div class="policies-info">
					<h4 class="policies-title">Vận chuyển toàn quốc</h4>
					<div class="policies-desc">Vận chuyển nhanh chóng</div>
				</div>
			</div>
		</div>
		<div class="col-lg-3 col-sm-6 d-flex justify-content-center mb-4">
			<div class="policies-item text-center">
				<div class="policies-image mb-2">
					<img
						src="//theme.hstatic.net/200000881795/1001243022/14/policies_icon_2.png?v=137"
						alt="policies_icon_2.png" width="40" height="40" class="img-fluid">
				</div>
				<div class="policies-info">
					<h4 class="policies-title">Ưu đãi hấp dẫn</h4>
					<div class="policies-desc">Nhiều ưu đãi khuyến mãi hot</div>
				</div>
			</div>
		</div>
		<div class="col-lg-3 col-sm-6 d-flex justify-content-center mb-4">
			<div class="policies-item text-center">
				<div class="policies-image mb-2">
					<img
						src="//theme.hstatic.net/200000881795/1001243022/14/policies_icon_3.png?v=137"
						alt="policies_icon_3.png" width="40" height="40" class="img-fluid">
				</div>
				<div class="policies-info">
					<h4 class="policies-title">Bảo đảm chất lượng</h4>
					<div class="policies-desc">Sản phẩm đã dược kiểm định</div>
				</div>
			</div>
		</div>
		<div class="col-lg-3 col-sm-6 d-flex justify-content-center mb-4">
			<div class="policies-item text-center">
				<div class="policies-image mb-2">
					<img
						src="//theme.hstatic.net/200000881795/1001243022/14/policies_icon_4.png?v=137"
						alt="policies_icon_4.png" width="40" height="40" class="img-fluid">
				</div>
				<div class="policies-info">
					<h4 class="policies-title">Hotline: 0822221992</h4>
					<div class="policies-desc">Vui lòng gọi hotline để hỗ trợ</div>
				</div>
			</div>
		</div>
	</div>
</div>

<br>
<br>

<!-- CONTENT -->

<c:if test="${not empty sessionScope.notification}">
    <div id="notification" class="notification ${sessionScope.notificationType}">
        <span>${sessionScope.notification}</span>
    </div>
    <c:remove var="notification"/>
    <c:remove var="notificationType"/>
</c:if>

<div class="container">
    <div class="d-flex align-items-center justify-content-center mb-4">
        <h1 class="mr-2">SẢN PHẨM ĐANG BÁN</h1>
        <img src="<c:url value='/images/flashsale-hot.png'/>"
             alt="flashsale-hot" height="50" width="50">
    </div>

    <c:choose>
        <c:when test="${not empty listProduct}">
            <div class="row justify-content-start">
                <c:forEach var="product" items="${listProduct}">
                    <div class="col-lg-3 col-sm-6 d-flex justify-content-center mb-4">
                        <div class="card card-product" style="width: 16.2rem;">
                            <img src="<c:url value='/images/${product.image}'/>"
                                 class="card-img-top" alt="${product.name}" height="260">
                            <div class="card-body">
                                <h6>
                                    <strong>${product.name}</strong>
                                </h6>
                                <hr>
                                <form action="home" method="post" onsubmit="return checkProductQuantity(${product.quantity}, '${product.name}')">
                                    <input type="hidden" name="action" value="add" /> 
                                    <input type="hidden" name="productCode" value="${product.id}" />

                                    <c:choose>
                                        <c:when test="${empty sessionScope.account}">
                                            <!-- Nếu chưa đăng nhập, hiển thị thông báo và chuyển hướng đến trang đăng nhập -->
                                            <a href="login"
                                               onclick="alert('Bạn chưa đăng nhập tài khoản! \nVui lòng đăng nhập trước khi mua sắm!');">
                                                <button type="button" class="btn btn-outline-success btn-radius btn-scale">Thêm
                                                    vào giỏ hàng</button>
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <!-- Nếu đã đăng nhập, thêm vào giỏ hàng -->
                                            <button type="submit" class="btn btn-outline-success btn-radius btn-scale">Thêm
                                                vào giỏ hàng</button>
                                        </c:otherwise>
                                    </c:choose>

                                    <a class="btn btn-outline-info btn-radius btn-scale" href="detail?id=${product.id}">Xem</a>

                                </form>

                                <h5 class="text-center">
                                    <strong> <fmt:formatNumber value="${product.price}"
                                            type="number" groupingUsed="true" maxFractionDigits="0" />
                                        VNĐ
                                    </strong>
                                </h5>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <div class="text-center">
                <h2>Chưa có sản phẩm nào được bán :(</h2>
                <img src="<c:url value='/images/cart_empty_background.webp'/>"
                     class="img-fluid" alt="Không có sản phẩm">
            </div>
        </c:otherwise>
    </c:choose>
</div>

<br>
<br>

<!-- CONTAINER-CART1 -->
<a href="home">
	<div class="container card border-0">
		<div class="imgtext_body d-flex">
			<a class="imgtext_img" href=""> <picture>
				<source media="(max-width: 991px)"
					srcset="//theme.hstatic.net/200000881795/1001243022/14/imgtext_1_img_large.jpg?v=137">
				<img class="img-fluid mx-auto"
					src="<c:url value='/images/banner/slide2.webp'/>" width="100%"
					height="540" alt="imgtext_img"> </picture>
			</a>
		</div>
	</div>
</a>
<br>
<br>

<!-- CUSTOMER -->
<div class="container">
	<h2 class="text-center mb-4">Khách hàng đã nói gì</h2>

	<div class="row d-flex justify-content-between">

		<div class="card col-lg-4 col-sm-6 mx-auto" style="width: 20rem;">
			<img
				src="https://theme.hstatic.net/200000881795/1001243022/14/cus_review_avatar_1_compact.jpg?v=137"
				class="card-img-top" alt="Trần Linh">
			<h4 class="text-center">Trần Linh</h4>
			<div class="card-body">
				<p class="card-text">Sản phẩm chất lượng, chất vải mềm mát mặc
					vào rất thoải mái và dễ hoạt động, giá cả hợp lý, giao hàng nhanh</p>
			</div>
		</div>

		<div class="card col-lg-4 col-sm-6 mx-auto" style="width: 20rem;">
			<img
				src="https://theme.hstatic.net/200000881795/1001243022/14/cus_review_avatar_2_compact.jpg?v=137"
				class="card-img-top" alt="Nguyên An">
			<h4 class="text-center">Nguyên An</h4>
			<div class="card-body">
				<p class="card-text">Sản phẩm đẹp, chất liệu tốt, mặc đúng size
					chuẩn, nhân viên phục vụ rất tận tình và chu đáo. 10 điểm!</p>
			</div>
		</div>

		<div class="card col-lg-4 col-sm-6 mx-auto" style="width: 20rem;">
			<img
				src="https://theme.hstatic.net/200000881795/1001243022/14/cus_review_avatar_3_compact.jpg?v=137"
				class="card-img-top" alt="Tuấn Trần">
			<h4 class="text-center">Tuấn Trần</h4>
			<div class="card-body">
				<p class="card-text">Giao hàng nhanh, anh shipper thân thiện.
					Sản phẩm đẹp, đã mua nhiều lần và sẽ tiếp tục ủng hộ shop</p>
			</div>
		</div>
	</div>
</div>
<br>
<br>

<!-- CONTAINER-CART2 -->
<a href="home">
	<div class="container card border-0">
		<div class="imgtext_body d-flex">
			<a class="imgtext_img" href=""> <picture>
				<source media="(max-width: 991px)"
					srcset="//theme.hstatic.net/200000881795/1001243022/14/imgtext_1_img_large.jpg?v=137">
				<img class="img-fluid mx-auto"
					src="<c:url value='/images/banner/slide3.webp'/>" width="100%"
					height="540" alt="imgtext_img"> </picture>
			</a>
		</div>
	</div>
</a>
<br>
<br>

<!-- INSTAGRAM -->
<div class="container">
	<h2 class="text-center mb-4">@ Follow Instagram</h2>

	<div class="row justify-content-center">
		<div class="col-lg-3 col-sm-6 mb-4 d-flex justify-content-center">
			<!-- NOTE images -->
			<a href="https://www.instagram.com/p/CMM6ikxDM-r/">
				<div class="card" style="width: 16rem;">
					<img
						src="https://theme.hstatic.net/200000881795/1001243022/14/insta_1_img.jpg?v=137"
						class="card-img-top" alt="" height="250">
				</div>
			</a>
		</div>
		<div class="col-lg-3 col-sm-6 mb-4 d-flex justify-content-center">
			<!-- NOTE images -->
			<a href="https://www.instagram.com/p/CMM6ikxDM-r/">
				<div class="card" style="width: 16rem;">
					<img
						src="https://theme.hstatic.net/200000881795/1001243022/14/insta_2_img.jpg?v=137"
						class="card-img-top" alt="" height="250">
				</div>
			</a>
		</div>
		<div class="col-lg-3 col-sm-6 mb-4 d-flex justify-content-center">
			<!-- NOTE images -->
			<a href="https://www.instagram.com/p/CMM6ikxDM-r/">
				<div class="card" style="width: 16rem;">
					<img
						src="https://theme.hstatic.net/200000881795/1001243022/14/insta_4_img.jpg?v=137"
						class="card-img-top" alt="" height="250">
				</div>
			</a>
		</div>
		<div class="col-lg-3 col-sm-6 mb-4 d-flex justify-content-center">
			<!-- NOTE images -->
			<a href="https://www.instagram.com/p/CMM6ikxDM-r/">
				<div class="card" style="width: 16rem;">
					<img
						src="https://theme.hstatic.net/200000881795/1001243022/14/insta_5_img.jpg?v=137"
						class="card-img-top" alt="" height="250">
				</div>
			</a>
		</div>
	</div>
</div>