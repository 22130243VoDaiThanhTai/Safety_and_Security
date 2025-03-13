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

<div class="row" style="width: 100%">
    <div class="col-lg-12 mx-auto">
        <c:if test="${empty orders}">
            <div class="text-center">
                <h1>Chưa có đơn hàng nào :(</h1>
                <img src="<c:url value='/images/cart_empty_background.webp'/>" class="img-fluid" alt="Không có sản phẩm"><br>
                <a class="btn btn-outline-dark btn-radius btn-scale" href="home">&#x2190; Tiếp tục mua hàng</a>
            </div>
        </c:if>
        
        <c:if test="${not empty orders}">
            <h1 class="text-center">Đơn hàng của bạn</h1><br><br>
            <div class="text-left">
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">Mã đơn hàng</th>
                            <th scope="col">Sản phẩm</th>
                            <th scope="col">Địa chỉ</th>
                            <th scope="col">Số điện thoại</th>
                            <th scope="col">Ngày mua</th>
                            <th scope="col">Tổng giá</th>
                            <th scope="col">Trạng thái</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order" items="${orders}">
                            <tr>
                                <td><strong>#${order.orderId}</strong></td>
                                <td>
                                    <c:forEach var="item" items="${order.items}">
                                        <div style="display: flex; margin: 2% 0">
                                            <div>
                                                <img src="<c:url value='/images/${item.product.image}'/>" alt="${item.product.name}" height="80" width="80">
                                            </div>
                                            <div style="margin-left: 3%">
                                                <strong>${item.product.name}</strong><br>
                                                <span>Số lượng: x<span>${item.quantity}</span> </span><br>
                                                <span>Giá: <span><fmt:formatNumber value="${item.product.price}"
                                                        type="number" groupingUsed="true" maxFractionDigits="0" />
                                                    VNĐ</span> </span>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </td>
                                <td>${order.address}</td>
                                <td>${order.phoneNumber}</td>
                                <td>${order.getFormattedOrderDate()}</td>
                                <td><fmt:formatNumber value="${order.total}" type="number" groupingUsed="true" maxFractionDigits="0" /> VNĐ</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${order.status == 'Đang lên đơn hàng'}">
                                            <strong class="text-info">${order.status} <i class="fa-solid fa-spinner spinning"></i></strong>
                                            <br>
                                            <form action="orders" method="post" onsubmit="return checkProductQuantity(${product.quantity}, '${product.name}')">
                                                <input type="hidden" name="action" value="refund" />
                                    			<input type="hidden" name="orderCode" value="${order.orderId}" />
                                            	<button type="submit" class="btn btn-outline-danger btn-radius" style="margin-top: 5%"><i class="fa-solid fa-trash"></i> Hủy đơn hàng</button>
                                            </form>
                                            
                                        </c:when>
                                        <c:when test="${order.status == 'Đang vận chuyển'}">
                                            <strong class="text-warning">${order.status} <i class="fa-solid fa-truck-fast moving-truck"></i></strong>
                                        </c:when>
                                        <c:when test="${order.status == 'Vui lòng nhận hàng'}">
                                            <strong class="text-primary">${order.status} <i class="fa fa-shopping-cart falling"></i></strong>
                                            <br>
                                            <form action="orders" method="post" onsubmit="return checkProductQuantity(${product.quantity}, '${product.name}')">
                                                <input type="hidden" name="action" value="confirm"/>
                                    			<input type="hidden" name="orderCode" value="${order.orderId}" />
                                            	<button class="btn btn-outline-success btn-radius" style="margin-top: 5%"><i class="fa-regular fa-circle-check"></i> Xác nhận</button>
                                            </form>                                            
                                        </c:when> 
                                        <c:when test="${order.status == 'Đã nhận hàng'}">
                                            <strong class="text-success">${order.status} <i class="fa-solid fa-face-smile shaking"></i></strong>
                                        </c:when>   
                                        <c:when test="${order.status == 'Hủy đơn hàng'}">
                                            <strong class="text-danger">${order.status} <i class="fa-solid fa-circle-xmark"></i></strong>
                                        </c:when>                                                                             
                                        <c:when test="${order.status == 'Lỗi đặt hàng'}">
                                            <strong class="text-danger">${order.status} <i class="fa-solid fa-circle-exclamation"></i></strong>
                                        </c:when>
                                        <c:otherwise>
                                            <span>${order.status}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>
</div>

