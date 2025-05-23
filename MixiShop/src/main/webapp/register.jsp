<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<section class="vh-100" style="background-color: #eee;">
	<div class="container h-100">
		<div class="row d-flex justify-content-center align-items-center h-100">
			<div class="col-lg-12 col-xl-11">
				<div class="card text-black" style="border-radius: 25px;">
					<div class="card-body p-md-5">
						<div class="row justify-content-center">
							<div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">

								<c:if test="${not empty errorMessage}">
									<div class="alert alert-danger">${errorMessage}</div>
								</c:if>

										<!-- Registration Form -->
										<p class="text-center h2 fw-bold mb-5 mx-1 mx-md-4 mt-4">Đăng ký tài khoản</p>
										<p class="text-center fw-bold mb-5 mx-1 mx-md-4 mt-4">
											Bạn đã có tài khoản? <a href="login">Đăng nhập tại đây</a>
										</p>

										<form class="mx-1 mx-md-4" method="POST" action="register" id="registerForm" accept-charset="UTF-8">
											<div class="d-flex flex-row align-items-center mb-4">
												<i class="fas fa-user fa-lg me-3 fa-fw"></i>
												<div data-mdb-input-init class="form-outline flex-fill mb-0">
													<label class="form-label" for="username">Tên người dùng:</label>
													<input type="text" id="username" class="form-control" name="username" required />
												</div>
											</div>

											<div class="d-flex flex-row align-items-center mb-4">
												<i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
												<div data-mdb-input-init class="form-outline flex-fill mb-0">
													<label class="form-label" for="email">Email của bạn:</label>
													<input type="email" id="email" class="form-control" name="email" required
														   onblur="checkEmailExists(this.value)"/>
													<span id="emailError" class="text-danger small"></span>
													<c:if test="${not empty emailError}">
														<span class="text-danger small">${emailError}</span>
													</c:if>
												</div>
											</div>

											<div class="d-flex flex-row align-items-center mb-4">
												<i class="fas fa-map-marker-alt fa-lg me-3 fa-fw"></i>
												<div data-mdb-input-init class="form-outline flex-fill mb-0">
													<label class="form-label">Địa chỉ:</label>
													<input type="text" class="form-control" name="address" required />
												</div>
											</div>

											<div class="d-flex flex-row align-items-center mb-4">
												<i class="fa-solid fa-phone fa-lg me-3 fa-fw"></i>
												<div data-mdb-input-init class="form-outline flex-fill mb-0">
													<label class="form-label">Số điện thoại:</label>
													<div class="input-group">
														<input type="text" class="form-control" name="phone" id="phone" required
															   onblur="checkPhoneExists(this.value)"/>
													</div>
													<span id="phoneError" class="text-danger small"></span>
													<c:if test="${not empty phoneError}">
														<span class="text-danger small">${phoneError}</span>
													</c:if>
												</div>
											</div>

											<div class="d-flex flex-row align-items-center mb-4">
												<i class="fas fa-lock fa-lg me-3 fa-fw"></i>
												<div data-mdb-input-init class="form-outline flex-fill mb-0">
													<label class="form-label" for="password">Mật khẩu:</label>
													<input type="password" id="password" class="form-control" name="password" required />
												</div>
											</div>

											<div class="d-flex flex-row align-items-center mb-4">
												<i class="fas fa-key fa-lg me-3 fa-fw"></i>
												<div data-mdb-input-init class="form-outline flex-fill mb-0">
													<label class="form-label" for="confirm-password">Nhập lại mật khẩu:</label>
													<input type="password" id="confirm-password" class="form-control" name="confirm-password" required />
												</div>
											</div>

											<div class="d-flex flex-row align-items-center mb-4">
												<div data-mdb-input-init class="form-outline flex-fill mb-0">
													<input type="hidden" id="role" class="form-control" name="role" value="1" readonly />
												</div>
											</div>

											<div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
												<button type="submit" data-mdb-button-init
														data-mdb-ripple-init class="btn btn-success btn-radius btn-scale" style="padding: 8px 50px">
													<strong>Đăng ký</strong>
												</button>
											</div>
											<input type="hidden" name="user-id" value="1">
										</form>

								<script>
									const contextPath = "${pageContext.request.contextPath}";

									function resendOTP(phone) {
										const form = document.createElement('form');
										form.method = 'post';
										form.action = 'register';

										const actionInput = document.createElement('input');
										actionInput.type = 'hidden';
										actionInput.name = 'action';
										actionInput.value = 'sendOTP';
										form.appendChild(actionInput);

										const phoneInput = document.createElement('input');
										phoneInput.type = 'hidden';
										phoneInput.name = 'phone';
										phoneInput.value = phone;
										form.appendChild(phoneInput);

										document.body.appendChild(form);
										form.submit();
									}

									document.getElementById('sendOTPBtn').addEventListener('click', function() {
										const phone = document.getElementById('phone').value;
										if (!phone.match(/^0\d{9,10}$/)) {
											document.getElementById('phoneError').textContent = 'Số điện thoại không hợp lệ';
											return;
										}

										// Create a form dynamically to submit the OTP request
										const form = document.createElement('form');
										form.method = 'post';
										form.action = 'register';

										const actionInput = document.createElement('input');
										actionInput.type = 'hidden';
										actionInput.name = 'action';
										actionInput.value = 'sendOTP';
										form.appendChild(actionInput);

										const phoneInput = document.createElement('input');
										phoneInput.type = 'hidden';
										phoneInput.name = 'phone';
										phoneInput.value = phone;
										form.appendChild(phoneInput);

										document.body.appendChild(form);
										form.submit();
									});
								</script>
								<script src="${pageContext.request.contextPath}/js/validation.js"></script>
							</div>
							<div class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">
								<img src="<c:url value='/images/mixicity.png'/>" class="img-fluid" alt="Sample image">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>