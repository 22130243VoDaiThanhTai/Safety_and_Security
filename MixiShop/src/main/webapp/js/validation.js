
    // Kiểm tra email tồn tại
    function checkEmailExists(email) {
    if (!email) return;

    const emailError = document.getElementById('emailError');
    emailError.textContent = 'Đang kiểm tra...';

    fetch('${pageContext.request.contextPath}/check-email?email=' + encodeURIComponent(email))
    .then(response => response.json())
    .then(data => {
    emailError.textContent = data.exists ? 'Email đã được sử dụng' : '';
    emailError.style.color = data.exists ? 'red' : 'green';
})
    .catch(() => emailError.textContent = '');
}

    // Kiểm tra số điện thoại tồn tại
    function checkPhoneExists(phone) {
    if (!phone) return;

    const phoneError = document.getElementById('phoneError');
    phoneError.textContent = 'Đang kiểm tra...';

    fetch('${pageContext.request.contextPath}/check-phone?phone=' + encodeURIComponent(phone))
    .then(response => response.json())
    .then(data => {
    phoneError.textContent = data.exists ? 'Số điện thoại đã được sử dụng' : '';
    phoneError.style.color = data.exists ? 'red' : 'green';
})
    .catch(() => phoneError.textContent = '');
}

    // Validate form trước khi submit
    document.getElementById('registerForm').addEventListener('submit', function(e) {
    const emailError = document.getElementById('emailError');
    const phoneError = document.getElementById('phoneError');

    if (emailError.textContent.includes('đã được sử dụng') ||
    phoneError.textContent.includes('đã được sử dụng')) {
    e.preventDefault();
    alert('Vui lòng kiểm tra lại thông tin');
}
});
