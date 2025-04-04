// Hàm kiểm tra email
function checkEmailExists(email) {
    const errorElement = document.getElementById('emailError');
    if (!errorElement) return; // Phòng trường hợp không tìm thấy element

    errorElement.textContent = '';

    if (!email || !email.includes('@')) {
        errorElement.textContent = 'Email không hợp lệ';
        errorElement.style.color = 'red';
        return;
    }

    errorElement.textContent = 'Đang kiểm tra...';
    errorElement.style.color = 'gray';

    fetch(`${contextPath}/check-existence?type=email&value=${encodeURIComponent(email)}`)
        .then(response => {
            if (!response.ok) throw new Error(`HTTP ${response.status}`);
            return response.json();
        })
        .then(data => {
            if (data.error) throw new Error(data.error);

            errorElement.textContent = data.exists
                ? '✖ Email đã được sử dụng'
                : '✓ Có thể sử dụng';
            errorElement.style.color = data.exists ? 'red' : 'green';
        })
        .catch(error => {
            errorElement.textContent = '⚠ Lỗi hệ thống';
            errorElement.style.color = 'orange';
            console.error('Email Validation Error:', error);
        });
}

// Hàm kiểm tra số điện thoại (phiên bản đã sửa)
function checkPhoneExists(phone) {
    const phoneError = document.getElementById('phoneError');
    if (!phoneError) return;

    phoneError.textContent = '';

    if (!phone) return;

    phoneError.textContent = 'Đang kiểm tra...';
    phoneError.style.color = 'gray';

    fetch(`${contextPath}/check-existence?type=phone&value=${encodeURIComponent(phone)}`)
        .then(response => {
            if (!response.ok) throw new Error(`HTTP ${response.status}`);
            return response.json(); // ⚠️ Quan trọng: phải có return
        })
        .then(data => {
            phoneError.textContent = data.exists
                ? '✖ Số điện thoại đã được sử dụng'
                : '✓ Có thể sử dụng';
            phoneError.style.color = data.exists ? 'red' : 'green';
        })
        .catch(error => {
            phoneError.textContent = '⚠ Lỗi kiểm tra';
            phoneError.style.color = 'orange';
            console.error('Phone Validation Error:', error);
        });
}

// Xử lý form submit (phiên bản an toàn)
document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('registerForm');
    if (!form) return;

    form.addEventListener('submit', function(e) {
        const emailError = document.getElementById('emailError');
        const phoneError = document.getElementById('phoneError');

        // Kiểm tra tồn tại và nội dung lỗi
        const hasEmailError = emailError?.textContent.includes('đã được sử dụng');
        const hasPhoneError = phoneError?.textContent.includes('đã được sử dụng');

        if (hasEmailError || hasPhoneError) {
            e.preventDefault();
            alert('Vui lòng sửa các thông tin bị trùng lặp trước khi đăng ký');
        }
    });
});