<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

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
            <h1>Danh sách tài khoản</h1><br>
			<c:choose>
			    <c:when test="${not empty listAccount}">
			        <div class="row justify-content-start">
			        	<table class="table table-hover">
			        		<thead>
							    <tr>
									<th scope="col">ID</th>
							      <th scope="col">Tên tài khoản</th>
							      <th scope="col">Mật khẩu</th>
								  <th scope="col">Địa chỉ</th>
								  <th scope="col">Số điện thoại</th>
								  <th scope="col">Admin</th>
									<th scope="col">Hoạt động</th>
								</tr>
							</thead>
							<tbody>
				            <c:forEach var="account" items="${listAccount}">
								    <tr>
										<td>${account.id}</td>
								      <td>${account.username}</td>
								      <td>${account.password}</td>
								      <td>${account.address}</td>
								      <td>${account.phone}</td>
								      <td>
								      	    <c:choose>
										        <c:when test="${account.role == 0}">
										            <i class="fa-solid fa-circle-check text-success"></i>
										        </c:when>
										        <c:otherwise>
										            <i class="fa-solid fa-circle-xmark text-danger"></i>
										        </c:otherwise>
										    </c:choose>
								      </td>
										<td>
											<form action="deleteuser" method="post" style="display:inline;">
												<input type="hidden" name="action" value="delete">
												<input type="hidden" name="id" value="${account.id}">
												<button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc muốn xóa tài khoản này không?');">
													Xóa tài khoản
												</button>
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
			            <h2>Chưa có sản phẩm nào được bán :(</h2>
			            <img src="<c:url value='/images/cart_empty_background.webp'/>" class="img-fluid" alt="Không có sản phẩm">
			        </div>
			    </c:otherwise>
			</c:choose>
          </div>
        </div>
    </div>
</body>