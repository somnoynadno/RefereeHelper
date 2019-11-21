$(document).on('click', '#cardsPaginator', function(e){
    let page = e.target.text;
    let currentActivePage = parseInt($('#cardsPaginator .active').text());
    
    // toggle page
    setTimeout(function(){
        $('#toggleCardsButton').click();
    }, 500);

    $('#cardsPaginator').children().removeClass('active');
     // change style
    
    let ready = false;
    while (!ready){
        switch(page){
            case "Previous":
                if (currentActivePage > 1){
                    page = (currentActivePage - 1).toString();
                    console.log(page);
                } else page = "1";
                continue;
            case "Next":
                if (currentActivePage < 3){
                    page = (currentActivePage + 1).toString();
                } else page = "3";
                continue;
            case "1":
                $('#cardHolder').removeClass("color-to-blue");
                $('#cardHolder').removeClass("color-to-pink");

                $('#cardHolder').addClass("color-to-green");
                $('#cardsPaginator').children().get(1).classList.add('active');
                break;
            case "2":
                $('#cardHolder').removeClass("color-to-green");
                $('#cardHolder').removeClass("color-to-blue");

                $('#cardHolder').addClass("color-to-pink");
                $('#cardsPaginator').children().get(2).classList.add('active');
                break;
            case "3":
                $('#cardHolder').removeClass("color-to-pink");
                $('#cardHolder').removeClass("color-to-green");

                $('#cardHolder').addClass("color-to-blue");
                $('#cardsPaginator').children().get(3).classList.add('active');
                break;
        };
        ready = true;
    }
    
    // toggle back
    $('#toggleCardsButton').click();
});

﻿document.addEventListener('DOMContentLoaded', async function(){

        let response = await fetch('http://localhost:4567/api/v1/hello');
        let myJson = await response.text();

        console.log(myJson);

})
