/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////// 날짜 관련
const reserveDayCountSpan = document.getElementById('room-reserve-day'); // 총 박수
const reserveDayPriceSpan = document.getElementById('room-day-price'); // 1박당 가격
const reserveFinalPriceSpan = document.getElementById('room-reserve-final-price'); // 최종 금액
const reserveTotalPriceSpan = document.getElementById('room-reserve-total-price'); // 선택한 박에 따름 금액

const personCountSelect = document.getElementById('person-count'); // 총 인원수
const reserveStartDateInput = document.getElementById('reserve-start-date'); // 예약 시작 날짜
const reserveEndDateInput = document.getElementById('reserve-end-date') // 예약 종료 날짜
////////////////////////////////////////////////////////////// 예약 관련
const reservationForm = document.forms.namedItem('reservation-form');
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////// 예약 관련
reservationForm.addEventListener('submit', (event) => {
    // 기존 폼 submit을 무력화 시킴
    event.preventDefault();

    // 예약 정보 확인 (날짜 형식 확인)
    const dateRegExp = RegExp(/[0-9]{4}-[0-9]{2}-[0-9]{2}/);
    if(!dateRegExp.test(reserveStartDateInput.value) || !dateRegExp.test(reserveEndDateInput.value)){
        alert('시작 날짜 / 종료 날짜 를 선택해야 합니다!');
        return;
    }

    const formData = new FormData(reservationForm);
    const csrfToken = document.querySelector('meta[name=_csrf]').getAttribute('content');
    fetch(`/reservation`, {
        method: "POST",
        headers: {'X-CSRF-TOKEN': csrfToken},
        body: formData
    })
        .then(response => response.json())
        .then(result => {
            if(result === true){
                alert("예약 성공!");
                location.href= '/user/mypage/reservation';
            } //예약 성공
            else{ alert("이미 예약이 존재합니다!"); } //예약 실패
        })
        .catch();
});

////////////////////////////////////////////////////////////// 달력 관련
// 인원수 변경 시 총 가격 변경
personCountSelect.onchange = set_reserve_total_price;
// 시작일 변경 시
reserveStartDateInput.addEventListener('change', () => {
    set_end_date(); // 시작 날짜 변경 시 종료 날짜(가능날짜) 설정
    set_reserve_total_price(); //총 가격 변경
});
// 종료일 변경 시 총 가격 변경
reserveEndDateInput.onchange = set_reserve_total_price;

// 첫 페이지 로딩 시 예약 시작 날짜 범위 지정 (최소 선택할 수 있는 시작 날짜)
set_start_date();
// 첫 페이지 로딩 시 초기 가격 설정
set_reserve_total_price();

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function set_start_date(){
    const nowDate = new Date();
    const startMinDate = calc_date_string(nowDate);
    reserveStartDateInput.setAttribute('min', startMinDate);
    set_end_date();
}

// 예약 종료 범위 지정 (최소 선택할 수 있는 시작 날짜 => 예약 시작 날짜보다 뒷 날짜)
// 1. 처음 페이지 로딩 시 => 오늘 다음 날 (시작 날짜 다음 날)
// 2. 시작 날짜 변경 시
//// 2-1) 시작 날짜가 기준에 설정된 종료 날짜보다 뒷 날짜가 된 경우 => 시작 날짜 다음 날짜로 변경
//// 2-2) 시작 날짜가 기준에 설정된 종료 날짜보다 앞 날짜인 경우 => 변경없음
function set_end_date(){
    // 기존 설정된 종료 날짜
    const originalEndDate =  Date.parse(reserveEndDateInput.value);
    const startDate = Date.parse(reserveStartDateInput.value);
    const dayDiff = originalEndDate - startDate;
    let nextDate = new Date();
    // 예약 시작 날짜가 종료날짜보다 앞인 경우 혹은 아직 선택 안했을 경우
    if(dayDiff > 0 || !isNaN(startDate)) {
        nextDate = new Date(startDate + (60 * 60 * 24 * 1000));

    }
    // 맨 처음 로딩 된 경우 혹은 시작날짜를 기존 종료 날짜보다 뒷 날짜 선택 시
    else{
        // 새로 선택한 시작날짜 바로 다음 날짜로 설정
        nextDate.setDate(nextDate.getDate() + 1);
    }
    // 시작날짜를 기존 종료 날짜보다 뒷 날짜 선택 시 기존 선택한 종료 날짜 초기화
    if (dayDiff < 0){
        reserveEndDateInput.value = '';
    }
    const endMinDate = calc_date_string(nextDate);
    reserveEndDateInput.setAttribute('min', endMinDate);
}

// 날짜 변환
function calc_date_string(originalDate){
    const dates = originalDate.toLocaleDateString().split('.');
    const year = dates[0].trim();
    let month = dates[1].trim();
    let day = dates[2].trim();
    month = month.length === 1 ? '0' + month : month;
    day = day.length === 1 ? '0' + day : day;
    return `${year}-${month}-${day}`;
}

// 선택한 날수(일정) + 인원에 따른 총 금액 계산
function set_reserve_total_price(){
    const endDate = Date.parse(reserveEndDateInput.value);
    const startDate = Date.parse(reserveStartDateInput.value);
    const dayDiff = (endDate - startDate) / (60 * 60 * 24 * 1000);
    // 제대로 날짜 계산되었을 경우
    if(!isNaN(dayDiff)){
        const oneDayPrice = reserveDayPriceSpan.textContent.replace(/,/g, '');
        const totalPrice = (dayDiff * oneDayPrice);
        reserveDayCountSpan.textContent = ` x ${dayDiff}박`;
        reserveTotalPriceSpan.textContent = totalPrice.toLocaleString();
        set_reserve_final_price(totalPrice);
    }
    // 첫 페이지 로딩 시
    else{
        reserveDayCountSpan.textContent = ` x 1박`;
        reserveTotalPriceSpan.textContent = reserveDayPriceSpan.textContent;
        reserveFinalPriceSpan.textContent = reserveDayPriceSpan.textContent;
    }
}

// 총 금액에 따른 최종 결제 금액 계산
function set_reserve_final_price(totalPrice){
    const personCount = personCountSelect.value;
    const finalPrice = totalPrice * personCount;
    reserveFinalPriceSpan.textContent = finalPrice.toLocaleString();
}

//////////////////////////////////////////////////////////////





