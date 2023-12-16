const sortFilterSelect = document.getElementById('sort-filter-select');
const minPriceInput = document.getElementById('min-price');
const maxPriceInput = document.getElementById('max-price');
const filterConfirmBtn = document.querySelector('.filter-confirm-button input');
const roomsSection = document.querySelector("section.rooms");

get_all_rooms();

filterConfirmBtn.onclick = add_filter;

function add_filter(){
    const sort = sortFilterSelect.value;
    const minPrice = minPriceInput.value;
    const maxPrice = maxPriceInput.value;
    const paramURL = `min=${minPrice}&max=${maxPrice}&sort=${sort}`;
    get_all_rooms(paramURL);
}

// 서버에 모든 방 데이터 요청
function get_all_rooms(filterParam){
    fetch(`/rooms?${filterParam}`)
        .then(response => response.json())
        .then(object => { create_rooms(object); })
        .catch();
}

// 서버에서 받은 모든 방 데이터를 화면에 생성
function create_rooms(roomList){
    roomsSection.innerHTML = '';
    for (const room of roomList) {
        const roomNo = room.roomNo;
        let title = room.roomVO.title;
        const price = room.roomVO.price;
        const image = room.roomImagesVO[0].roomImage;
        const ratings = room.ratingVO;
        let rate = 0;
        ratings.forEach(rating => {rate += rating.score});

        if(title.length >= 15){
            title = title.slice(0,15) + "...";
        }

        roomsSection.insertAdjacentHTML("beforeend",
            `<a href="/rooms/${roomNo}" class="room">
                <input type="checkbox" name="heart" id="heart" onchange="change_heart()">
                <label for="heart"><i class="fa-regular fa-heart"></i></label>
                <div class="room-img">
                    <img src="/rooms/image/${image}" alt="방모습">
                </div>

                <div>
                    <span class="title">${title}</span>
                </div>

                <div class="room-info">
                    <div class="room-price">
                        <span>&#8361;</span><span>${price}</span><span> /박</span>
                    </div>
                    <div class="star">
                        <i class="fa-solid fa-star"></i><span>${rate}</span>
                    </div>
                </div>
            </a>`)
    }
}

function change_heart(){
    const heartIcons = document.querySelector('label[for="heart"]');
    console.log(isLoggIn);
    if(document.getElementById('heart').checked == true){
        heartIcons.innerHTML = '<i class="fa-solid fa-heart" style="color: #ff0000;"></i>';
    }else{
        heartIcons.innerHTML = '<i class="fa-regular fa-heart"></i>';
    }

}