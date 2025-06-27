<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="container">
    <div class="row">
        <div class="col-lg-8">
            <div class="box-element">
                <a class="btn btn-outline-dark btn-radius btn-scale" href="cart">&#x2190; Trở về giỏ hàng</a>
                <hr>
                <h3>Tóm tắt giỏ hàng</h3>
                <hr>
                <h5>Số lượng sản phẩm: ${cart.getNumberOfItems()}</h5>
                <h5>Tổng tiền: <strong><fmt:formatNumber value="${cart.getTotal()}" pattern="#,###"/> VNĐ</strong></h5>
                <hr>

                    <div class="cart-row d-flex justify-content-between">
                        <div style="flex:1"><strong>ID</strong></div>
                        <div style="flex:2"><strong>Hình ảnh</strong></div>
                        <div style="flex:3"><strong>Tên sản phẩm</strong></div>
                        <div style="flex:1"><strong>Số lượng</strong></div>
                        <div style="flex:1"><strong>Tổng Giá</strong></div>
                    </div>

                <c:forEach var="item" items="${cart.items}">
                    <div class="cart-row">
                        <div style="flex:3"><strong>${item.product.id}</strong></div>
                        <div style="flex:2"><img class="row-image" src="<c:url value='/images/${item.product.image}'/>" alt="${item.product.name}"></div>
                        <div style="flex:3"><strong>${item.product.name}</strong></div>

                        <div style="flex:1">
                            <strong class="quantity">x${item.quantity}</strong>
                        </div>
                        <div style="flex:1"><strong><fmt:formatNumber value="${item.getTotal()}" pattern="#,###"/> VNĐ</strong></div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <div class="col-lg-4">
            <div class="box-element" id="form-wrapper">
                <form id="form" action="checkout" method="post">
                    <div id="user-info">
                        <div class="form-field">
                            <label class="form-label" for="form3Example1c">Họ tên:</label> <br>
                            <input id="form3Example1c" required class="form-control" type="text" name="name" value="${user.username}" placeholder="${user.username}">
                        </div>
                        <div class="form-field">
                            <label class="form-label" for="form3Example1c">Email:</label> <br>
                            <input  required class="form-control" type="email" name="email" value="${user.email}" placeholder="${user.email}">
                        </div>
                    </div>

                    <div id="shipping-info">
                        <hr>
                        <p>Thông tin giao hàng:</p>
                        <hr>
                        <div class="form-field">
                            <label class="form-label" for="form3Example1c">Địa chỉ:</label> <br>
                            <input class="form-control" type="text" name="address" placeholder="Địa chỉ cụ thể ..." required="required">
                        </div>

                        <div class="form-field">
                            <label class="form-label" for="form3Example1c">Số điện thoại:</label> <br>
                            <input class="form-control" type="text" name="phone" value="${user.phone}" placeholder="${user.phone}">
                        </div>
                        <div class="mb-3">
                            <label for="privateKey" class="form-label">Nhập khóa kí đơn hàng:</label>
                            <input name="privateKey" id="privateKey" class="form-control" rows="4" placeholder="Dán khóa riêng vào đây..."></input>
                            <button type="button" class="btn btn-sm btn-primary mt-2" onclick="validatePrivateKey()">Xác nhận khóa</button>
                            <p id="keyStatus" class="mt-1"></p>
                        </div>

                    </div>

                    <hr>
                    <c:forEach var="item" items="${cart.items}">
                        <input type="hidden" name="productId" value="${item.product.id}" />
                        <input type="hidden" name="quantity" value="${item.quantity}" />
                        <input type="hidden" name="price" value="${item.price}" />
                    </c:forEach>
                    <button id="form-button" class="btn btn-success btn-radius" style="width: 100%" type="submit">
                        <i class="fa-solid fa-truck-fast"></i> Đặt Hàng</button>
                    <br>

                </form>
            </div>

            <br>
        </div>
    </div>

</div>
<script>
        let isKeyValid = false; // ✅ Biến toàn cục để kiểm tra


        function validatePrivateKey() {
            console.log(isKeyValid)
            const key = document.getElementById("privateKey").value.trim();
            const status = document.getElementById("keyStatus");
            console.log(key)
            console.log(status)

            fetch("/MixiShop/validateKey", {
            method: "POST",
            headers: {
            "Content-Type": "application/json"
        },
            body: JSON.stringify({privateKey: key})
        })
            .then(res => res.json())
            .then(result => {
                console.log(result.valid + "ét ét")
                if (result.valid) {
                    status.innerText = "✅ Khóa hợp lệ.";
                    status.style.color = "green";
                    isKeyValid = true;
                } else {
                    status.innerText = "❌ Khóa không hợp lệ.";
                    status.style.color = "red";
                    isKeyValid = false;
                }
        })
            .catch(error => {
            console.error("Lỗi xác minh khóa:", error);
            status.innerText = "❌ Lỗi xác minh khóa.";
            status.style.color = "red";
            isKeyValid = false;
        });

            // ❌ Tránh reload form nếu chỉ nhấn nút "Xác nhận khóa"
            return false;
        }

            // ✅ Bắt sự kiện submit form
            document.addEventListener("DOMContentLoaded", function () {
            const form = document.getElementById("form");
            form.addEventListener("submit", function (e) {
            if (!isKeyValid) {
            e.preventDefault(); // ❌ Ngăn submit
            alert("Vui lòng xác nhận khóa hợp lệ trước khi đặt hàng!");
            }
        });
    });

</script>
