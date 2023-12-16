const form = document.forms.item(0);

function accept_reservation(roomNO){
    form.action = `/rooms/${roomNO}/accept`;
    form.submit();
}

function cancel_reservation(roomNO){
    form.action = `/rooms/${roomNO}/cancel`;
    form.submit();
}
