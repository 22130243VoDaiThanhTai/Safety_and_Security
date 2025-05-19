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
                    <!-- OTP Verification Step -->
                    <p class="text-center h2 fw-bold mb-5 mx-1 mx-md-4 mt-4">Xác thực OTP</p>
                    <p class="text-center mb-4">Mã OTP đã được gửi đến số ${phone}</p>

                    <form class="mx-1 mx-md-4" method="POST" action="verify-otp">
                      <input type="hidden" name="action" value="verifyOTP">
                      <input type="hidden" name="phone" value="${phone}">

                      <div class="d-flex flex-row align-items-center mb-4">
                        <i class="fas fa-shield-alt fa-lg me-3 fa-fw"></i>
                        <div data-mdb-input-init class="form-outline flex-fill mb-0">
                          <label class="form-label">Mã OTP:</label>
                          <input type="text" name="otp" class="form-control" required maxlength="6">
                          <small class="text-muted">Nhập mã 6 số bạn nhận được qua SMS</small>
                        </div>
                      </div>

                      <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
                        <button type="submit" data-mdb-button-init
                                data-mdb-ripple-init class="btn btn-success btn-radius btn-scale" style="padding: 8px 50px">
                          <strong>Xác thực</strong>
                        </button>
                      </div>
                    </form>

                    <div class="text-center">
                      Không nhận được mã? <a href="#" onclick="resendOTP('${phone}')">Gửi lại mã</a>
                    </div>


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