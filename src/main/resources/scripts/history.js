document.addEventListener('DOMContentLoaded', async function(){
        let response = await fetch('http://localhost:4567/api/v1/match/');
        let myJson = await response.json();

        console.log(myJson);

        for (let i = 0; i < myJson.length-1; i += 2){
            let tr = document.createElement('tr');
            let id = document.createElement('th');
            let gt = document.createElement('td');
            let team1 = document.createElement('td');
            let team2 = document.createElement('td');
            let date = document.createElement('td');
            let button = document.createElement('button');

            id.scope = "row";

            let elem = myJson[i];
            let elem1 = myJson[i+1];

            id.textContent = elem[0];
            gt.textContent = elem[2];
            team1.textContent = elem[3];
            team2.textContent = elem1[3];

            d = new Date(elem[1]);
            date.textContent = d.toISOString().substring(0, 10) + " " + d.toISOString().substring(11, 16);

            button.className = "btn btn-primary btn-sm mt-2";
            button.textContent = "Show statistics";

            button.addEventListener('click', function(){
                console.log("To statistics " + elem[0]);
                window.location.href = 'statistics.html?id=' + elem[0];
            });

            tr.appendChild(id);
            tr.appendChild(gt);
            tr.appendChild(team1);
            tr.appendChild(team2);
            tr.appendChild(date);
            tr.appendChild(button);

            $('#HistoryTableBody').append(tr);
        }
})