const productListDiv = document.getElementById('product-list');

async function fetchProducts() {
    try {
        const response = await fetch('/api/products');
        if (!response.ok) {
            throw new Error('상품을 불러오는데 실패했습니다.');
        }
        const products = await response.json();

        products.forEach(product => {
            const productItem = document.createElement('div');
            productItem.classList.add('product-item');
            productItem.innerHTML = `
                <img src="${product.imageUrl || ''}" alt="${product.name}">
                <h3>${product.name}</h3>
                <p>${product.price}원</p>
                <button onclick="addToCart(${product.id})">장바구니에 담기</button>
            `;
            productListDiv.appendChild(productItem);
        });
    } catch (error) {
        console.error('상품 목록 로딩 중 오류 발생:', error);
        productListDiv.innerHTML = '<p>상품을 불러올 수 없습니다.</p>';
    }
}

async function addToCart(productId) {
    const authToken = localStorage.getItem('authToken');
    if (!authToken) {
        alert('로그인이 필요합니다.');
        window.location.href = '/login.html';
        return;
    }

    try {
        const response = await fetch('/api/cart', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': authToken,
            },
            body: JSON.stringify({ productId, quantity: 1 }), // 기본 수량 1로 추가
        });

        if (response.ok) {
            alert('상품이 장바구니에 추가되었습니다.');
        } else {
            if (response.status === 401 || response.status === 403) {
                alert('로그인이 필요합니다.');
                window.location.href = '/login.html';
                return;
            }
            alert('장바구니 추가에 실패했습니다.');
        }
    } catch (error) {
        console.error('장바구니 추가 중 오류 발생:', error);
        alert('오류가 발생했습니다. 다시 시도해주세요.');
    }
}

// 페이지 로드 시 상품 목록을 불러옵니다.
fetchProducts();