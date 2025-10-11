document.addEventListener('DOMContentLoaded', () => {
    const nav = document.querySelector('header nav ul');
    const authToken = localStorage.getItem('authToken');

    // 로그인 상태와 관계없이 항상 표시되는 메뉴
    let navHTML = `
        <li><a href="/products.html">상품 목록</a></li>
        <li><a href="/cart.html">장바구니</a></li>
        <li><a href="/orders.html">주문 내역</a></li>
    `;

    if (authToken) {
        // 로그인 상태일 때 "로그아웃" 버튼 추가
        navHTML += '<li><a href="#" id="logout-button">로그아웃</a></li>';
    } else {
        // 비로그인 상태일 때 "로그인", "회원가입" 링크 추가
        navHTML += `
            <li><a href="/login.html">로그인</a></li>
            <li><a href="/register.html">회원가입</a></li>
        `;
    }

    nav.innerHTML = navHTML;

    if (authToken) {
        const logoutButton = document.getElementById('logout-button');
        logoutButton.addEventListener('click', (e) => {
            e.preventDefault();
            localStorage.removeItem('authToken');
            alert('로그아웃 되었습니다.');
            window.location.href = '/';
        });
    }
});