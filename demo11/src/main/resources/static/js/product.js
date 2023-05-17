function getAll() {
    fetch('http://localhost:8080/Book/search')
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            render(data.bookList)
            for (let i = 1; i <= data.pageCount; i++) {
                document.getElementById("pagination").innerHTML += `<a href="javascript:void(0);" onclick="paging(${i})" >${i}</a>`
            }
        })
}

function search(keyword) {
    fetch('http://localhost:8080/Book/search?keyword=' + keyword)
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            console.log(data.bookList)
            render(data.bookList)
            // let html = '';
            // for(let i = 1 ; i <= data.pageCount ; i++){
            //     html += `<a href="javascript:void(0);" onclick="paging4(${i},keyword)"  >${i}</a>`
            // }
        })
}

function paging4(count, keyword) {
    fetch('http://localhost:8080/Book/search?keyword=' + keyword + '&page=' + count)
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            let BookBlock = document.getElementById("Book2")
            let html = ''
            for (let Book of data.bookList) {
                html += `<div class="col-lg-4 col-md-6 col-sm-6">
                            <div class="Book__item">
                                <div class="Book__item__pic set-bg" data-setbg="${Book.image}">
                                    <img src="${Book.image}">
                                    <ul class="Book__item__pic__hover">
                                        <li><a href="#"><i class="fa fa-heart"></i></a></li>
                                        <li><a href="#"><i class="fa fa-retweet"></i></a></li>
                                        <li><a  onclick="addToCart('${data.bookId}', '${data.bookName}' ,'${data.image}', '${data.price}', 1);" href="javascript:void(0);"><i class="fa fa-shopping-cart"></i></a></li>
                                    </ul>
                                </div>
                                <div class="Book__item__text">
                                    <h6><a href="deltail/${Book.bookId}">${Book.bookName}</a></h6>
                                    <h5>$${Book.price}</h5>
                                </div>
                            </div>
                        </div>`
            }
            BookBlock.innerHTML = html
        })
}


function paging(count) {
    fetch('http://localhost:8080/Book/search?page=' + count)
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            render(data.bookList)
        })
}


function render(arr) {
    let BookBlock = document.getElementById("Book")
    let html = ''
    for (let Book of arr) {
        html += `<div class="col-lg-4 col-md-6 col-sm-6">
                            <div class="Book__item">
                                <div class="Book__item__pic set-bg" data-setbg="${Book.image}">
                                    <img src="${Book.image}">
                                    <ul class="Book__item__pic__hover">
                                        <li><a href="#"><i class="fa fa-heart"></i></a></li>
                                        <li><a href="#"><i class="fa fa-retweet"></i></a></li>
                                        <li><a  onclick="addToCart('${Book.bookId}', '${Book.bookName}', '${Book.image}' , '${Book.price}', 1);" href="javascript:void(0);"><i class="fa fa-shopping-cart"></i></a></li>
                                    </ul>
                                </div>
                                <div class="Book__item__text">
                                    <h6><a href="deltail/${Book.bookId}">${Book.bookName}</a></h6>
                                    <h5>$${Book.price}</h5>
                                </div>
                            </div>
                        </div>`
    }
    BookBlock.innerHTML = html
}

function getBook(id) {
    console.log(id)
    if (id !== undefined) {
        fetch('http://localhost:8080/Book/detail/' + id)
            .then((response) => {
                return response.json()
            })
            .then((data) => {
                if (data !== undefined) {
                    console.log("begin")
                    console.log(data)
                    let html = document.getElementById("Book-detail")
                    html.innerHTML = `<div class="col-lg-6 col-md-6">
                    <div class="Book__details__pic">
                        <div class="Book__details__pic__item">
                            <img class="Book__details__pic__item--large"
                                src="${data.image}" alt="">
                        </div>
                        <div class="Book__details__pic__slider owl-carousel">
                            <img data-imgbigurl="img/Book/details/Book-details-2.jpg"
                                src="img/Book/details/thumb-1.jpg" alt="">
                            <img data-imgbigurl="img/Book/details/Book-details-3.jpg"
                                src="img/Book/details/thumb-2.jpg" alt="">
                            <img data-imgbigurl="img/Book/details/Book-details-5.jpg"
                                src="img/Book/details/thumb-3.jpg" alt="">
                            <img data-imgbigurl="img/Book/details/Book-details-4.jpg"
                                src="img/Book/details/thumb-4.jpg" alt="">
                        </div>
                    </div>
                </div>
                <div class="col-lg-6 col-md-6">
                    <div class="Book__details__text">
                        <h3>${data.bookName}</h3>
                        <div class="Book__details__rating">
                            <i class="fa fa-star"></i>
                            <i class="fa fa-star"></i>
                            <i class="fa fa-star"></i>
                            <i class="fa fa-star"></i>
                            <i class="fa fa-star-half-o"></i>
                            <span>(18 reviews)</span>
                        </div>
                        <div class="Book__details__price">$${data.price}</div>
                        <p>${data.description}</p>
                        <div class="Book__details__quantity">
                            <div class="quantity">
                                <div class="pro-qty">
                                    <input type="text" value="1">
                                </div>
                            </div>
                        </div>
                        <a class="primary-btn" onclick="addToCart('${data.bookId}', '${data.bookName}' ,'${data.image}', '${data.price}', 1);" href="javascript:void(0);">ADD TO CARD</a>
                        <a href="#" class="heart-icon"><span class="icon_heart_alt"></span></a>
                        <ul>
                            <li><b>Author</b> <span>${data.author}</span></li>
                            <li><b>Publication Date</b> <span>${new Date(data.publicationDate).toString().split(" ")[1] + ' ' + new Date(data.publicationDate).toString().split(" ")[2] + ' '+ new Date(data.publicationDate).toString().split(" ")[3] }   <span>   Free pickup today</span></span></li>
                            <li><b>Share on</b>
                                <div class="share">
                                    <a href="#"><i class="fa fa-facebook"></i></a>
                                    <a href="#"><i class="fa fa-twitter"></i></a>
                                    <a href="#"><i class="fa fa-instagram"></i></a>
                                    <a href="#"><i class="fa fa-pinterest"></i></a>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="col-lg-12">
                    <div class="Book__details__tab">
                        <ul class="nav nav-tabs" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active" data-toggle="tab" href="#tabs-1" role="tab"
                                    aria-selected="true">Description</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" data-toggle="tab" href="#tabs-2" role="tab"
                                    aria-selected="false">Information</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" data-toggle="tab" href="#tabs-3" role="tab"
                                    aria-selected="false">Reviews <span>(1)</span></a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" id="tabs-1" role="tabpanel">
                                <div class="Book__details__tab__desc">
                                    <p>${data.description}</p>
                                    <p>${data.description}</p>
                                </div>
                            </div>
                            <div class="tab-pane" id="tabs-2" role="tabpanel">
                                <div class="Book__details__tab__desc">
                                    <p>${data.description}</p>
                                    <p>${data.description}</p>
                                </div>
                            </div>
                            <div class="tab-pane" id="tabs-3" role="tabpanel">
                                <div class="Book__details__tab__desc">
                                    <p>${data.description}</p>
                                </div>
                            </div>
                        </div>
                        <div class="container mt-8" id = "loadComment">
                                        <div id = "comment" class="d-flex  row">
                                            
                                        </div>
                        </div>
                    </div>
                </div>`
                }
            })

    } else {
        console.log("no Book have id " + id)
    }
}

function getSameBook(id) {
    fetch('http://localhost:8080/Book/same-category/' + id)
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            let Books = ''
            for (let Book of data.bookList) {
                Books += `<div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="Book__item">
                        <div class="Book__item__pic set-bg" data-setbg="img/Book/Book-1.jpg">
                            <img src="${Book.image}" >
                            <ul class="Book__item__pic__hover">
                                <li><a href="#"><i class="fa fa-heart"></i></a></li>
                                <li><a href="#"><i class="fa fa-retweet"></i></a></li>
                                <li><a onclick="addToCart('${Book.bookId}', '${Book.bookName}','${Book.image}', '${Book.price}', 1);" href="javascript:void(0);"><i class="fa fa-shopping-cart"></i></a></li>
                            </ul>
                        </div>
                        <div class="Book__item__text">
                            <h6><a href="/view/deltail/${Book.bookId}">${Book.bookName}</a></h6>
                            <h5>$${Book.price}</h5>
                        </div>
                    </div>
                </div>`
            }
            let html = document.getElementById("related-Book")
            html.innerHTML = Books
            let html2 = ''
            for (let i = 1; i <= data.pageCount; i++) {
                html2 += `<a href="javascript:void(0);" onclick="paging3(${id} , ${i})" >${i}</a>`
            }
            document.getElementById("pagination2").innerHTML = html2
        })
}

function paging3(id, page) {
    fetch('http://localhost:8080/Book/same-category/' + id + '?page=' + page)
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            let Books = ''
            for (let Book of data.BookList) {
                Books += `<div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="Book__item">
                        <div class="Book__item__pic set-bg" data-setbg="img/Book/Book-1.jpg">
                            <img src="${Book.image}" >
                            <ul class="Book__item__pic__hover">
                                <li><a href="#"><i class="fa fa-heart"></i></a></li>
                                <li><a href="#"><i class="fa fa-retweet"></i></a></li>
                                <li><a  onclick="addToCart('${Book.bookId}', '${Book.bookName}','${Book.image}', '${Book.price}', 1);" href="javascript:void(0);" ><i class="fa fa-shopping-cart"></i></a></li>
                            </ul>
                        </div>
                        <div class="Book__item__text">
                            <h6><a href="/view/deltail/${Book.BookId}">${Book.BookName}</a></h6>
                            <h5>$${Book.price}</h5>
                        </div>
                    </div>
                </div>`
            }
            let html = document.getElementById("related-Book")
            html.innerHTML = Books
        })
}


function getLastestBook() {
    fetch('http://localhost:8080/Book/lastestBook')
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            console.log(data)
            let html1 = '';

            for (let i = 0; i < 3; i++) {
                html1 += `<a href="/view/deltail/${data[i].bookId}"     class="latest-Book__item">
                                            <div class="latest-Book__item__pic">
                                                <img src="${data[i].image}" alt="">
                                            </div>
                                            <div class="latest-Book__item__text">
                                                <h6>${data[i].bookName}</h6>
                                                <span>$${data[i].price}</span>
                                            </div>
                                        </a>`
            }

            document.getElementById("lastestBook").innerHTML = html1;
        })
}

function getBookSale() {
    fetch('http://localhost:8080/produt/BookSale')
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            console.log(data)
            let html = ''
            for (let Book of data) {
                html += `<div class="col-lg-3 col-md-6 col-sm-6">
                        <div class="Book__discount__item">
                            <div class="Book__discount__item__pic set-bg"
                                 data-setbg="img/Book/discount/pd-1.jpg">
                                 <img src="${Book.image}">
                                <div class="Book__discount__percent">-20%</div>
                                <ul class="Book__item__pic__hover">
                                    <li><a href="#"><i class="fa fa-heart"></i></a></li>
                                    <li><a href="#"><i class="fa fa-retweet"></i></a></li>
                                    <li><a  onclick="addToCart('${Book.bookId}', '${Book.bookName}','${Book.image}' , '${Book.price}', 1);" href="javascript:void(0);"><i class="fa fa-shopping-cart"></i></a></li>
                                </ul>
                            </div>
                            <div class="Book__discount__item__text">
                                
                                <h5><a href="/view/deltail/${Book.bookId}">${Book.bookName}</a></h5>
                                <div class="Book__item__price">$${Book.price} <span>$360.00</span></div>
                            </div>
                        </div>
                    </div>`
            }

            document.getElementById('BookSale').innerHTML = html
        })
}

let BookList = []

function all() {
    fetch('http://localhost:8080/Book/getAll')
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            BookList = data
        })
}

all()


function searchByPrice() {
    let min = document.getElementById("minPrice").value
    let max = document.getElementById("maxPrice").value
    console.log(min)
    console.log(max)
    if (min === '') min = 0
    if (max === '') max = 2000
    let Books = BookList.filter((Book) => {
        return Book.price >= parseInt(min) && Book.price <= parseInt(max);
    })
    render(Books)
}
