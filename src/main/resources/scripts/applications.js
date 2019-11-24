document.addEventListener('DOMContentLoaded', async function(){
        let response = await fetch('http://localhost:4567/api/v1/request/');
        let myJson = await response.text();

        console.log(myJson);
})
