document.addEventListener("DOMContentLoaded", function () {
    const detailButtons = document.querySelectorAll(".btn-detail");

    detailButtons.forEach(button => {
        button.addEventListener("click", function () {
            const orderId = this.getAttribute("data-order-id");
            if (!orderId) {
                console.error("Không có orderId!");
                return;  // Dừng lại nếu không có orderId
            }else{
                console.log("có"+ orderId)
            }
            fetch(`${window.location.origin}/MixiShop_war/getOrderDetail?orderId=${orderId}`)
                .then(response => {
                    console.log('Response:', response);  // Log để kiểm tra phản hồi
                    if (!response.ok) {
                        return response.text().then(text => {
                            throw new Error(`Lỗi khi tải chi tiết đơn hàng. Mã lỗi: ${response.status}, Nội dung lỗi: ${text}`);
                        });
                    }
                    return response.json();  // Nếu thành công, chuyển thành JSON
                })
                .then(data => {
                    console.log('Dữ liệu chi tiết đơn hàng:', data);  // Kiểm tra dữ liệu trả về từ API
                    const tbody = document.getElementById("orderDetailBody");
                    const modalOrderId = document.getElementById("modalOrderId");
                    tbody.innerHTML = "";
                    modalOrderId.textContent = orderId;
                    console.log(Array.isArray(data)); // Phải trả ra true

                    data.forEach(function(item)  {
                        console.log("Item :", item); // Xem log có ra không
                        console.log(typeof item);    // Xem có phải object không
                        console.log("Item id :", item.productId);
                        console.log("Item name :", item.productName);
                        console.log("img :" + item.image);
                        console.log("Item quantity :", item.quantity);
                        console.log("Item price :", item.price);
                        const row = `
                            <tr>
                                <td>${item.productId}</td>
                                <td>${item.productName}</td>
                                <td style="display: flex;
                                    justify-content: center;">
                                    <img src="/MixiShop_war/images/${item.image}" alt="Ảnh sản phẩm" width="60" height="60" style="object-fit: cover;">
                                    </td>
                                <td>${item.quantity}</td>
                                <td>${item.price}</td>
                            </tr>`;
                        tbody.innerHTML += row;

                    });

                    const modal = new bootstrap.Modal(document.getElementById('orderDetailModal'));
                    modal.show();
                })
                .catch(error => {
                    console.error("Lỗi khi tải chi tiết đơn hàng:", error);
                    alert("Không thể tải chi tiết đơn hàng. Mã lỗi: " + error.message);
                });
        });
    });
});