const registerForm = document.getElementById('register-form');

registerForm.addEventListener('submit', async (event) => {
    event.preventDefault();

    const username = event.target.username.value;
    const password = event.target.password.value;

    try {
        const response = await fetch('/api/users', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
        });

        if (response.ok) {
            // 회원가입 성공 시 로직 (예: 로그인 페이지로 리디렉션)
            alert('회원가입에 성공했습니다. 로그인 페이지로 이동합니다.');
            window.location.href = '/login.html';
        } else {
            // 회원가입 실패 시 로직 (예: 오류 메시지 표시)
            alert('회원가입에 실패했습니다.');
        }
    } catch (error) {
        console.error('회원가입 요청 중 오류 발생:', error);
        alert('오류가 발생했습니다. 다시 시도해주세요.');
    }
});