const roomImgFilesDiv = document.querySelector('.room-img-files');
const roomImgFileAddBtn = document.querySelector('.room-img-add-div > input[type=button]');

$(document).ready(function() {
    $('#summernote').summernote(
        {
            height: 300,                 // 에디터 높이
            minHeight: null,             // 최소 높이
            maxHeight: null,             // 최대 높이
            focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
            lang: "ko-KR",					// 한글 설정
            placeholder: '최대 2048자까지 쓸 수 있습니다'	//placeholder 설정
        }
    );
});

roomImgFileAddBtn.onclick = addFileDiv;

function addFileDiv(){
    const div = document.createElement('div');
    const removeBtn = document.createElement('input');
    const fileInput = document.createElement('input');
    removeBtn.type = 'button';
    removeBtn.value = '삭제';
    fileInput.type = 'file';
    fileInput.name = 'images';

    removeBtn.onclick = () => {
        div.remove();
    }

    div.appendChild(removeBtn);
    div.appendChild(fileInput);

    roomImgFilesDiv.appendChild(div);
}
