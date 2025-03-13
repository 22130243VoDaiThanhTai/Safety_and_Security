/**
 * 
 */
window.onload = function() {
    var notification = document.querySelector('.notification');
    if (notification) {
        notification.classList.add('show');
        setTimeout(function() {
            notification.classList.remove('show');
        }, 3000);
    }
}
