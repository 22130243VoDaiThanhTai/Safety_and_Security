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
            <h1>Danh mục sản phẩm</h1><br>
			<c:choose>
			    <c:when test="${not empty listCategory}">
			        <div class="row justify-content-start">
			        	<table class="table table-hover">
			        		<thead>
							    <tr>
							      <th scope="col">Mã danh mục</th>
								  <th scope="col">Tên danh mục</th>
								  <th scope="col"></th>
								</tr>
							</thead>
							<tbody>
				            <c:forEach var="category" items="${listCategory}">
								    <tr>
								      <th scope="row">#<strong>${category.id}</strong></th>
								      <td><strong>${category.name}</strong></td>
								      <td>
								      	<form action="adminCategories" method="post" onsubmit="return checkProductQuantity(${product.quantity}, '${product.name}')">
											<input type="hidden" name="action" value="deleted" />
										    <input type="hidden" name="categoryCode" value="${category.id}" />
										    <button type="submit" class="btn btn-outline-danger"><i class="fa-solid fa-trash"></i> Xóa danh mục</button>
										</form>
								      </td>
								    </tr>
				            </c:forEach>
				           	</tbody>
			            </table>
			        </div>
			    </c:when>
			    <c:otherwise>
			    <br><br>
			        <div class="">
			            <h2>Chưa có danh mục sản phẩm :(</h2>
			            <img src="<c:url value='/images/cart_empty_background.webp'/>" class="img-fluid" alt="Không có sản phẩm">
			        </div>
			    </c:otherwise>
			</c:choose>
          </div>
        </div>
    </div>
</body>