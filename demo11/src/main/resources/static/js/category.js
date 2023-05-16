function getAllCategories(){
    fetch('http://localhost:8080/category')
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            let html ='';
            for(let category of data.categories){
                html += `<li><a href="javascript:void(0);" onclick="getBookByCategory(${category.categoryId})">${category.categoryName}</a></li>`
            }
            document.getElementById("category").innerHTML = html
            document.getElementById("categories").innerHTML = html
        })
}

function getBookByCategory(id){
    fetch('http://localhost:8080/category/Book/' + id)
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            let html =''
            for(let Book of data.BookList){
                html += `<div class="col-lg-4 col-md-6 col-sm-6">
                            <div class="Book__item">
                                <div class="Book__item__pic set-bg" data-setbg="${Book.image}">
                                    <img src="${Book.image}">
                                    <ul class="Book__item__pic__hover">
                                        <li><a href="#"><i class="fa fa-heart"></i></a></li>
                                        <li><a href="#"><i class="fa fa-retweet"></i></a></li>
                                        <li><a  onclick="addToCart('${Book.BookId}', '${Book.BookName}',${Book.image}, '${Book.price}', 1);" href="javascript:void(0);" ><i class="fa fa-shopping-cart"></i></a></li>
                                    </ul>
                                </div>
                                <div class="Book__item__text">
                                    <h6><a href="deltail/${Book.BookId}">${Book.BookName}</a></h6>
                                    <h5>$${Book.price}</h5>
                                </div>
                            </div>
                        </div>`
            }
            document.getElementById("Book").innerHTML = html
            let check = document.getElementById("Book2").style.display = "none"
            let check2 = document.getElementById("pagination7").style.display = "none"
            let html2 = '';
            for(let i = 1 ; i <= data.pageCount ; i++){
                html2 += `<a href="javascript:void(0);" onclick="paging2(${id} , ${i})" >${i}</a>`
            }
            document.getElementById("pagination").innerHTML = html2
        })
}

function paging2(categoryId , page){
    fetch('http://localhost:8080/category/Book/' + categoryId + '?page=' + page)
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            let html =''
            for(let Book of data.BookList){
                html += `<div class="col-lg-4 col-md-6 col-sm-6">
                            <div class="Book__item">
                                <div class="Book__item__pic set-bg" data-setbg="${Book.image}">
                                    <img src="${Book.image}">
                                    <ul class="Book__item__pic__hover">
                                        <li><a href="#"><i class="fa fa-heart"></i></a></li>
                                        <li><a href="#"><i class="fa fa-retweet"></i></a></li>
                                        <li><a  onclick="addToCart('${Book.BookId}', '${Book.BookName}',${Book.image}, '${Book.price}', 1);" href="javascript:void(0);" ><i class="fa fa-shopping-cart"></i></a></li>
                                    </ul>
                                </div>
                                <div class="Book__item__text">
                                    <h6><a href="deltail/${Book.BookId}">${Book.BookName}</a></h6>
                                    <h5>$${Book.price}</h5>
                                </div>
                            </div>
                        </div>`
            }
            document.getElementById("Book").innerHTML = html
            let check = document.getElementById("Book2");
            if(check !== null){
                check.style.display = "none";
            }
        })
}

