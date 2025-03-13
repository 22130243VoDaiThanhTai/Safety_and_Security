<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="row" style="width: 100%">
    <div class="col-lg-4">
        <div class="profile text-center">
            <div class="avatar mb-3"
                style="position: relative; width: 200px; height: 200px; margin: 0 auto; border-radius: 50%; overflow: hidden; background: #f0f0f0;">
                <!-- Hiển thị ảnh đại diện, nếu không có ảnh sẽ sử dụng ảnh mặc định -->
                <img id="avatarImg" src="<c:url value='/images/noAvatar.png'/>"
                    alt="Avatar"
                    style="width: 100%; height: 100%; object-fit: cover; border: 1.4px solid black;">
            </div>

            <div class="info">
                <!-- Hiển thị tên người dùng (username) -->
                <p class="username fs-4 fw-bold">${user.username}</p>  
                
                <!-- Hiển thị địa chỉ -->
                <p class="address text-muted">Địa chỉ: ${user.address}</p>
                
                <!-- Hiển thị email -->
                <p class="email text-dark">Email: ${user.email}</p> 
            </div>
        </div>
    </div>

    	<div class="col-lg-8">
		<div class="box-element">
			<a class="btn btn-outline-dark" href="cart">&#x2190;
				Trở về giỏ hàng</a>
			<hr>
			<h3>Tóm tắt đơn hàng</h3>
			<hr>
				<c:forEach var="item" items="${cart.items}">
                    <div class="cart-row">
                        <div style="flex:2"><img class="row-image" src="<c:url value='/images/${item.product.image}'/>" alt="${item.product.name}"></div>
                        <div style="flex:2"><p>${item.product.name}</p></div>
                        <div style="flex:1"><p><fmt:formatNumber value="${item.product.price}" pattern="#,###"/> VNĐ</p></div>
                        <div style="flex:1">
                            <p class="quantity">x${item.quantity}</p>
                        </div>
                        <div style="flex:1"><p><fmt:formatNumber value="${item.getTotal()}" pattern="#,###"/> VNĐ</p></div>
                    </div>
                </c:forEach>

			<h5>Số lượng sản phẩm: ${cart.getNumberOfItems()}</h5>
			<h5>Tổng tiền: <strong><fmt:formatNumber value="${cart.getTotal()}" pattern="#,###"/> VNĐ</strong></h5>
		</div>
	</div>
</div>
