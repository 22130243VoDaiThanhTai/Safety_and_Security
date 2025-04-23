<%--
  Created by IntelliJ IDEA.
  User: THAI
  Date: 4/23/2025
  Time: 11:45 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- Owl Carousel CSS -->
<link rel="stylesheet" href="css/owl.carousel.min.css">


<!-- Owl Carousel JS -->
<script src="js/owl.carousel.min.js"></script>
<style>
    .container-margin{
        margin: 0 5%;
    }

    .list-unstyled {
        padding-left: 0;
    }

    .list-unstyled li {
        list-style: none;
    }

    .list-unstyled li a {
        text-decoration: none;
        color: inherit;
        transition: background-color 0.3s ease;  /* Hiệu ứng chuyển màu nền */
        display: flex;          /* Sử dụng Flexbox để căn chỉnh văn bản và icon */
        justify-content: space-between; /* Căn chỉnh văn bản và icon sang hai bên */
        align-items: center;    /* Căn giữa văn bản và icon theo chiều dọc */
        margin-left: 10%;
    }

    .list-unstyled li:hover {
        background-color: #000;  /* Màu nền khi hover */
        color: #fff;             /* Màu chữ khi hover */
    }
    .active{
        display: flex;
    }
    .btn{
        border-radius: 8px
    }
    .delete_btn{
        padding-right: 10px;
    }
    table.table td, table.table th {
        white-space: nowrap; /* Không xuống dòng */
        vertical-align: middle;
    }
</style>
<body>
<c:if test="${not empty message}">
    <script>
        alert("${message}");
    </script>
</c:if>
<div class="container-margin">
    <div class="row">
        <!-- Sidebar (chiếm 3 cột) -->
        <div class="col-md-3 bg-light p-3">
            <br><br><br>
            <h4 class="text-center">Menu</h4><br>
            <ul class="list-unstyled">
                <li><a href="admin" class="d-block py-2">Trang chủ Admin <i class="fa-solid fa-angle-right"></i></a></li>
                <li><a href="adminOrders" class="d-block py-2">Danh sách đơn hàng <i class="fa-solid fa-angle-right"></i></a></li>
                <li><a href="adminAccounts" class="d-block py-2">Danh sách tài khoản <i class="fa-solid fa-angle-right"></i></a></li>
                <li><a href="adminProducts" class="d-block py-2">Danh sách sản phẩm <i class="fa-solid fa-angle-right"></i></a></li>
                <li><a href="adminCategories" class="d-block py-2">Danh mục sản phẩm <i class="fa-solid fa-angle-right"></i></a></li>
                <li><a href="adminAddProduct" class="d-block py-2">Thêm sản phẩm <i class="fa-solid fa-angle-right"></i></a></li>
                <li><a href="adminAddCategory" class="d-block py-2">Thêm danh mục <i class="fa-solid fa-angle-right"></i></a></li>
                <li><a href="adminUpdateProduct" class="d-block py-2">Cập nhật sản phẩm <i class="fa-solid fa-angle-right"></i></a></li>
                <li><a href="adminUpdateCategory" class="d-block py-2">Cập nhật danh mục <i class="fa-solid fa-angle-right"></i></a></li>
            </ul>
        </div>

        <!-- Nội dung chính (chiếm 9 cột) -->
        <div class="col-md-9 p-4 text-center">
            <h1>Danh sách đơn hàng</h1><br>

            <c:choose>
                <c:when test="${not empty listOrder}">
                    <div class="row justify-content-start">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th scope="col">Mã đơn hàng</th>
                                <th scope="col">Mã người dùng</th>
                                <th scope="col">Email</th>
                                <th scope="col">Số điện thoại</th>
                                <th scope="col">Địa chỉ</th>
                                <th scope="col">Tổng tiền</th>
                                <th scope="col">Ngày đặt</th>
                                <th scope="col">Thao tác</th>
                                <th scope="col">Xác nhận</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="order" items="${listOrder}">
                                <tr>
                                    <td>${order.orderId}</td>
                                    <td>${order.userId}</td>
                                    <td>${order.email}</td>
                                    <td>${order.phoneNumber}</td>
                                    <td>${order.address}</td>
                                    <td>${order.total}</td>
                                    <td>
                                        <fmt:formatDate value="${order.orderDate}" pattern="dd-MM-yyyy HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <!-- Có thể thêm nút xem chi tiết đơn hàng -->
                                        <a href="javascript:void(0)" class="btn btn-info btn-sm btn-detail" data-order-id="${order.orderId}">
                                            Chi tiết
                                        </a>
                                    </td>
                                    <td>
                                        <!-- Có thể thêm nút xem chi tiết đơn hàng -->
                                        <a href="" class="btn btn-info btn-sm" data-order-id="${order.orderId}">
                                            Xác nhận
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>
                <c:otherwise>
                    <br><br>
                    <div>
                        <h2>Chưa có đơn hàng nào!</h2>
                        <img src="<c:url value='/images/cart_empty_background.webp'/>" class="img-fluid" alt="Không có đơn hàng">
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

    </div>
</div>
<!-- Modal hiển thị chi tiết đơn hàng -->
<div class="modal fade" id="orderDetailModal" tabindex="-1" aria-labelledby="orderDetailLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg modal-dialog-scrollable">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Chi tiết đơn hàng #<span id="modalOrderId"></span></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
            </div>
            <div class="modal-body">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>Mã sản phẩm</th>
                        <th>Tên sản phẩm</th>
                        <th>ảnh sản phẩm</th>
                        <th>Số lượng</th>
                        <th>Giá</th>
                    </tr>
                    </thead>
                    <tbody id="orderDetailBody">
                    <!-- Chi tiết đơn hàng sẽ được inject bằng JavaScript -->
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script src="js/adminOrderDetails.js"></script>
<%--còn lỗi lấy orderId với lỗi hiển thị đơn hàng chi tiết--%>

</body>
