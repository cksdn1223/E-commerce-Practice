const orderListDiv = document.getElementById('order-list');

async function fetchOrders() {
    const authToken = localStorage.getItem('authToken');
    if (!authToken) {
        alert('로그인이 필요합니다.');
        window.location.href = '/login.html';
        return;
    }

    try {
        const response = await fetch('/api/orders', {
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
            throw new Error('주문 내역을 불러오는데 실패했습니다.');
        }
        const orders = await response.json();

        if (orders.length === 0) {
            orderListDiv.innerHTML = '<p>주문 내역이 없습니다.</p>';
            return;
        }

        orders.forEach(order => {
            const orderDiv = document.createElement('div');
            orderDiv.classList.add('order-item');
            
            let orderItemsHTML = '<ul>';
            // 백엔드 응답 구조가 order.orderItems가 아닌 order.orderedItems 일 수 있으므로 확인 필요
            // 우선 order.orderItems로 가정하고 진행, 안되면 orderedItems로 변경
            order.orderItems.forEach(item => {
                orderItemsHTML += `<li>${item.productName} - ${item.quantity}개</li>`;
            });
            orderItemsHTML += '</ul>';

            orderDiv.innerHTML = `
                <h4>주문 번호: ${order.id}</h4>
                <p>주문 날짜: ${new Date(order.orderDate).toLocaleDateString()}</p>
                <p>총액: ${order.totalPrice}원</p>
                <h5>주문 상품:</h5>
                ${orderItemsHTML}
            `;
            orderListDiv.appendChild(orderDiv);
        });

    } catch (error) {
        console.error('주문 내역 로딩 중 오류 발생:', error);
        orderListDiv.innerHTML = '<p>주문 내역을 불러올 수 없습니다.</p>';
    }
}

// 페이지 로드 시 주문 내역을 불러옵니다.
fetchOrders();