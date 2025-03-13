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
          <div class="col-md-9 p-4 ext-center">
            <h1 class="text-center">Cập nhật sản phẩm</h1>
			
			<c:if test="${not empty message}">
				<div class="alert alert-info">
					${message}
				</div>
			</c:if>
			
			  <div class="container">
			    <div class="row d-flex justify-content-center">
			      <div class="col-12 col-md-8 col-lg-8 col-xl-8">
			        <div class="card shadow-2-strong" style="border-radius: 1rem;">
			          <div class="card-body p-8">
						<form action="adminUpdateProduct" method="post" enctype="multipart/form-data" onsubmit="return checkProductQuantity(${product.quantity}, '${product.name}')">
						    <div class="form-outline mb-4">
						        <label class="form-label">Danh mục sản phẩm</label>
						        <select class="form-control" name="productCode">
						            <c:forEach var="product" items="${listProduct}">
						                <option value="${product.id}">${product.name}</option>
						            </c:forEach>
						        </select>
						    </div>
						    
						    <div class="form-outline mb-4">
						        <label class="form-label">Tên sản phẩm mới</label>
						        <input type="text" class="form-control" name="productName" required/>
						    </div>
						
						    <div class="form-outline mb-4">
						        <label class="form-label">Giá sản phẩm</label>
						        <input type="number" class="form-control" name="productPrice" required/>
						    </div>
						    
						    <div class="form-outline mb-4">
						        <label class="form-label">Số lượng sản phẩm</label>
						        <input type="number" class="form-control" name="productQuantity" required/>
						    </div>
						
						    <div class="form-outline mb-4">
						        <label class="form-label">Hình ảnh sản phẩm</label>
						        <input type="file" class="form-control" name="productImage" required/>
						    </div>
						
						    <div class="form-outline mb-4">
						        <label class="form-label">Danh mục sản phẩm mới</label>
						        <select class="form-control" name="categoryCode">
						            <c:forEach var="category" items="${listCategory}">
						                <option value="${category.id}">${category.name}</option>
						            </c:forEach>
						        </select>
						    </div>
						    <button type="submit" class="btn btn-success">Cập nhật sản phẩm</button>
						</form>
			          </div>
			        </div>
			      </div>
			    </div>
			  </div>
          </div>
          
        </div>
    </div>
</body>