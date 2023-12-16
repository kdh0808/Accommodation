// 휴대폰 인증번호 받기 부분
const telInfoDiv = document.querySelector('.join-tel-info');
const [verifyCodeGetBtn, verifyCodeInput, verifyConfirmBtn, verifiedInput] = document.querySelectorAll('.join-tel-cert input');

// 회원가입 확인 번튼
const registForm = document.forms.item(0);
const [backBtn, joinBtn] = document.querySelectorAll('.join-buttons > input');

verifyCodeGetBtn.onclick = get_verify_code;
verifyConfirmBtn.onclick = check_verify_code;

// 나눠져있는 전화번호를 하나로 만드는 함수
function create_tel_number(){
    const frontTel = telInfoDiv.querySelector('.tel-front select').value;
    const middleTel = telInfoDiv.querySelector('.tel-mid input').value;
    const backTel = telInfoDiv.querySelector('.tel-back input').value;
    return frontTel + middleTel + backTel;
}

// 인증번호받기 버튼 눌렀을 때 인증번호 받아오기
function get_verify_code(){
    // 작성되어있는 휴대폰 번호 가져오기
    const phoneNumber = create_tel_number();
    // 해당 휴대폰 번호를 전달해서 GET 요청
    fetch(`/user/sms/key?phoneNumber=${phoneNumber}`)
        .then(response => response.json())
        .then(object => {
            if(object){
                alert("인증번호를 발송 하였습니다.");
                console.log('인증번호를 생성 성공!');
            }else{
                alert("오류 : 인증번호 발급 실패");
                console.log('인증번호를 생성 실패!');
            } })
        .catch();
}

function check_verify_code(){
    const key = verifyCodeInput.value;
    fetch(`/user/sms/verify?key=${key}`)
        .then(response => response.json())
        .then(object => {
            if(object){
                alert("인증에 성공하였습니다.");
               verified();
                console.log('인증 성공!');
            }else{
                alert("인증번호가 일치하지 않습니다.")
                console.log('인증 실패!');
            } })
        .catch();
}


function verified(){
    verifyCodeInput.toggleAttribute("disabled", true);
    verifyCodeGetBtn.toggleAttribute("disabled", true);
    verifyConfirmBtn.toggleAttribute("disabled", true);

    verifyCodeInput.style.backgroundColor = "gray";
    verifyConfirmBtn.style.backgroundColor = "gray";
    verifyCodeGetBtn.onclick = null;
    verifyConfirmBtn.onclick = null;    1

    verifiedInput.value = true;
}


backBtn.onclick = back_home;
joinBtn.onclick = register_submit;
// 홈으로 돌아가기 (돌아가기 버튼)
function back_home(){
    location.href = '/';
}

// 회원가입 확인 눌렀을 시
function register_submit(){
    // 1) 정보들이 전부 정확하게 작성되었는가?
    // 1-1) ID가 중복이 아닌가? (중복체크하였는가?)
    // 1-2) 휴대폰 인증이 되었는가?

    // 위 정보가 문제가 없으면 form 전송
    registForm.submit();
}
