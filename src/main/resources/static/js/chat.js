const chatWindow = document.getElementById('chat-window');
const box = document.getElementById('box');
const sentinel = document.getElementById('sentinel');
const contentInput = document.getElementById('contentInput');

if (chatWindow && box && sentinel && contentInput) {
    // 메시지 전송 버튼 바인딩
    document.getElementById('questionButton')?.addEventListener('click', () => sendMessage('나'));
    document.getElementById('answerButton')?.addEventListener('click', () => sendMessage(' '));

    // 한글 조합 중복 입력 방지
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

    // 페이징 상태 관리 변수
    let loading = false;
    // 처음 로드 시 Thymeleaf에서 렌더링해 둔 nextCursor 값을 읽어온다.
    let nextCursor = document.querySelector('meta[name="nextCursor"]')?.getAttribute('content') || null;

    // scroll 이벤트 기반 무한 스크롤
    chatWindow.addEventListener('scroll', async () => {
        // 1) 이미 로딩 중이거나, nextCursor가 없으면 아무것도 안 함
        if (loading || !nextCursor) return;

        // 2) 맨 위에 가까이 scrollTop <= threshold(예: 20px)일 때 이전 페이지 호출
        if (chatWindow.scrollTop <= 20) {
            loading = true;

            try {
                // 3) 실제로 데이터를 불러올 엔드포인트
                const path = window.location.pathname.includes('/me') ? '/chat/me' : '/chat/any';
                const res = await fetch(`${path}/load?cursor=${encodeURIComponent(nextCursor)}`);
                if (!res.ok) throw new Error(`Error ${res.status}`);

                // 4) 서버에서 반환된 HTML(older messages + 새로운 <meta name="nextCursor">)을 text 형태로 읽는다
                const html = await res.text();
                // 5) 임시 div에 응답 HTML을 넣어서 DOM 트리로 변환
                const temp = document.createElement('div');
                temp.innerHTML = html;

                // 6) prepend 직전 스크롤 전체 높이를 저장한다.
                //    (기존 chatWindow 전체 높이)
                const oldScrollHeight = chatWindow.scrollHeight;

                // 7) temp.firstChild부터 순차적으로 box 맨 위에 붙인다.
                //    -> older messages, 그리고 새로운 meta 태그도 함께 들어온다고 가정
                while (temp.firstChild) {
                    box.insertBefore(temp.firstChild, box.firstChild);
                }

                // 8) 새로 들어온 <meta name="nextCursor">를 찾아서 nextCursor 업데이트
                const meta = box.querySelector('meta[name="nextCursor"]');
                nextCursor = meta ? meta.getAttribute('content') : null;

                // 9) prepend 후 새로운 전체 높이를 구한 뒤,
                //    (newScrollHeight - oldScrollHeight) 만큼 scrollTop을 올려서
                //    사용자가 보던 위치가 밀리지 않도록 보정
                const newScrollHeight = chatWindow.scrollHeight;
                chatWindow.scrollTop = newScrollHeight - oldScrollHeight;
            } catch (err) {
                console.error('무한스크롤 로딩 실패:', err);
            }

            loading = false;
        }
    });

    // 메시지 전송 함수(기존 로직 그대로)
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

    // 페이지 로드 시 맨 아래(최신 메시지)로 자동 스크롤, input 포커스
    window.onload = () => {
        chatWindow.scrollTop = chatWindow.scrollHeight;
        contentInput.focus();
    };
}
