const loginForm = document.getElementById('login-form');

loginForm.addEventListener('submit', async (event) => {
    event.preventDefault();

    const username = event.target.username.value;
    const password = event.target.password.value;

    try {
        const response = await fetch('/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
        });

        if (response.ok) {
            // 로그인 성공 시 로직 (예: 메인 페이지로 리디렉션)
            const authToken = response.headers.get('Authorization');
            if (authToken) {
                localStorage.setItem('authToken', authToken);
            }
            window.location.href = '/';
        } else {
            // 로그인 실패 시 로직 (예: 오류 메시지 표시)
            alert('로그인에 실패했습니다.');
        }
    } catch (error) {
        console.error('로그인 요청 중 오류 발생:', error);
        alert('오류가 발생했습니다. 다시 시도해주세요.');
    }
});