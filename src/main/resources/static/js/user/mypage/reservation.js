const csrfToken = document.querySelector('meta[name=_csrf]').getAttribute('content');
// 예약 확정 / 취소 하기 .. block: 누른 버튼(this), state: 확정(ACCEPTED) / 취소 (CANCELED)
function change_reservation_state(reservationNo, state){
    fetch(`/reservation/${reservationNo}?state=${state}`, {
        method: 'PUT',
        headers: {'Content-type':'application/json', 'X-CSRF-TOKEN': csrfToken},
        body: JSON.stringify(state)
    })
        .then(response => response.json())
        .then(result => {
            if(result){
                alert('변경 완료!');
                location.reload();
            }else{
                alert('변경 실패!')
            }
        })
        .catch();
}


function registered_rate(reservationNo){
    const rate = document.getElementById('rate').value;
    fetch(`/reservation/${reservationNo}?rete=${rate}`,{
        method:'PUT',

    })
}
