const cartItemsDiv = document.getElementById('cart-items');
const orderButton = document.getElementById('order-button');
let currentCartItems = []; // 현재 장바구니 아이템을 저장할 변수

async function fetchCartItems() {
    const authToken = localStorage.getItem('authToken');
    if (!authToken) {
        alert('로그인이 필요합니다.');
        window.location.href = '/login.html';
        return;
    }

    try {
        const response = await fetch('/api/cart', {
            headers: {
                'Authorization': authToken,
            }
        });

        if (response.status === 401 || response.status === 403) {
            alert('로그인이 필요합니다.');
            window.location.href = '/login.html';
            return;
        }
        if (!response.ok) {
            throw new Error('장바구니 정보를 불러오는데 실패했습니다.');
        }
        const cart = await response.json();
        currentCartItems = cart.cartItems; // 백엔드 응답 구조에 맞춰 수정 (cart.cartItems)

        if (!currentCartItems || currentCartItems.length === 0) {
            cartItemsDiv.innerHTML = '<p>장바구니가 비어있습니다.</p>';
            orderButton.disabled = true;
            return;
        }

        cartItemsDiv.innerHTML = ''; // 기존 목록 초기화
        currentCartItems.forEach(item => {
            const cartItemDiv = document.createElement('div');
            cartItemDiv.classList.add('cart-item');
            cartItemDiv.innerHTML = `
                <span>${item.productName}</span>
                <span>${item.quantity}개</span>
                <span>${item.price * item.quantity}원</span>
            `;
            cartItemsDiv.appendChild(cartItemDiv);
        });

    } catch (error) {
        console.error('장바구니 정보 로딩 중 오류 발생:', error);
        cartItemsDiv.innerHTML = '<p>장바구니 정보를 불러올 수 없습니다.</p>';
    }
}

orderButton.addEventListener('click', async () => {
    const authToken = localStorage.getItem('authToken');
    if (!authToken) {
        alert('로그인이 필요합니다.');
        window.location.href = '/login.html';
        return;
    }

    if (!currentCartItems || currentCartItems.length === 0) {
        alert('장바구니가 비어있습니다.');
        return;
    }

    // 백엔드가 요구하는 OrderRequest 형식으로 데이터 구성
    const orderRequest = {
        orderItems: currentCartItems.map(item => ({
            productId: item.productId,
            quantity: item.quantity
        }))
    };

    try {
        const response = await fetch('/api/orders', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': authToken,
            },
            body: JSON.stringify(orderRequest)
        });

        if (response.ok) {
            alert('주문이 완료되었습니다.');
            // 주문 완료 후 장바구니 비우기 및 페이지 새로고침
            window.location.reload();
        } else {
            if (response.status === 401 || response.status === 403) {
                alert('로그인이 필요합니다.');
                window.location.href = '/login.html';
                return;
            }
            alert('주문에 실패했습니다.');
        }
    } catch (error) {
        console.error('주문 생성 중 오류 발생:', error);
        alert('오류가 발생했습니다. 다시 시도해주세요.');
    }
});

// 페이지 로드 시 장바구니 정보를 불러옵니다.
fetchCartItems();