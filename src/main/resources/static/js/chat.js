const chatWindow = document.getElementById('chat-window');
const contentInput = document.getElementById('contentInput');

if (chatWindow && contentInput) {
    document.getElementById('questionButton')?.addEventListener('click', () => sendMessage('나'));
    document.getElementById('answerButton')?.addEventListener('click', () => sendMessage(' '));

    let isComposing = false;

    contentInput.addEventListener('compositionstart', () => isComposing = true);
    contentInput.addEventListener('compositionend', () => isComposing = false);
    contentInput.addEventListener('keydown', function (event) {
        if (isComposing || event.repeat) return;

        if (event.key === 'Enter' && !event.shiftKey) {
            event.preventDefault();
            sendMessage(event.ctrlKey ? '나' : ' ');
        }
    });

    function sendMessage(senderType) {
        const content = contentInput.value.trim();
        if (!content) return alert('메시지를 입력해주세요.');

        const path = window.location.pathname.includes('/me') ? '/chat/me' : '/chat/any';

        fetch(path, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            credentials: 'same-origin',
            body: JSON.stringify({sender: senderType, content})
        })
            .then(res => {
                if (!res.ok) throw new Error(`Error ${res.status}`);
                window.location.reload();
            })
            .catch(err => {
                console.error(err);
                alert('메시지 전송 중 오류가 발생했습니다.');
            });
    }

    window.onload = () => {
        chatWindow.scrollTop = chatWindow.scrollHeight;
        contentInput.focus();
    };
}
