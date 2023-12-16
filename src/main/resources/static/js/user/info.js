const [idEmailInput, idFindBtn] = document.querySelectorAll('.id-email-section input');

idFindBtn.onclick = find_id;

function find_id(){
    const userEmail = idEmailInput.value;
    fetch(`/user/find?userEmail=${userEmail}`)
        .then(response => response)
        .catch();
}

