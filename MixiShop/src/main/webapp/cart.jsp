<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        <div class="col-lg-12">
            <c:if test="${empty cart.items}">
                <div class="text-center">
                    <h1>Chưa có sản phẩm trong giỏ hàng</h1>
                    <img src="<c:url value='/images/cart_empty_background.webp'/>" class="img-fluid" alt="Không có sản phẩm"><br>
                    <a class="btn btn-outline-dark btn-radius btn-scale" href="home">&#x2190; Tiếp tục mua hàng</a>
                </div>
            </c:if>

            <c:if test="${not empty cart.items}">
                <div class="box-element">
                    <a class="btn btn-outline-dark btn-radius btn-scale" href="home">&#x2190; Tiếp tục mua hàng</a>
                    <br><br>
                    <table class="table">
                        <tr>
                            <th><h5>Số lượng sản phẩm: <strong>${cart.getNumberOfItems()}</strong></h5></th>
                            <th><h5>Tổng tiền: <strong><fmt:formatNumber value="${cart.getTotal()}" pattern="#,###"/> VNĐ</strong></h5></th>
                            <th><a style="float:right; margin: 5px; padding: 6px 40px" class="btn btn-success btn-radius btn-scale" href="checkout"><strong><i class="fa-brands fa-shopify"></i> Mua hàng</strong></a></th>
                        </tr>
                    </table>
                </div>

                <br>
                <div class="box-element">
                    <div class="cart-row d-flex justify-content-between">
                        <div style="flex:1;display: none"><strong>ID</strong></div>
                        <div style="flex:1"><strong>Hình ảnh</strong></div>
                        <div style="flex:2"><strong>Tên Sản phẩm</strong></div>
                        <div style="flex:1"><strong>Giá</strong></div>
                        <div style="flex:1"><strong>Số lượng</strong></div>
                        <div style="flex:1"><strong>Tổng</strong></div>
                        <div style="flex:1"><strong></strong></div>
                    </div>

                    <c:forEach var="item" items="${cart.items}">
                        <div class="cart-row d-flex justify-content-between">
                            <div style="flex:1;display: none"><strong>${item.product.id}</strong></div>
                            <div style="flex:1"><img class="row-image" src="<c:url value='/images/${item.product.image}'/>" alt="${item.product.name}"></div>
                            <div style="flex:2"><strong>${item.product.name}</strong></div>
                            <div style="flex:1"><strong><fmt:formatNumber value="${item.product.price}" pattern="#,###"/> VNĐ</strong></div>
                            <div style="flex:1">
                                <strong class="quantity">x${item.quantity}</strong>
                            </div>
                            <div style="flex:1"><strong><fmt:formatNumber value="${item.getTotal()}" pattern="#,###"/> VNĐ</strong></div>
                            <div style="flex:1">
                                <form action="cart" method="post" onsubmit="return checkProductQuantity(${product.quantity}, '${product.name}')">
                                    <input type="hidden" name="action" value="deleted" />
                                    <input type="hidden" name="productCode" value="${item.product.id}" />
                                    <button type="submit" class="btn btn-outline-danger btn-radius btn-scale"><i class="fa-solid fa-trash"></i> Xóa sản phẩm</button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
        </div>
    </div>
</div>

