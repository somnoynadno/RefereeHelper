document.addEventListener('DOMContentLoaded', async function(){
        let response = await fetch('http://localhost:4567/api/v1/request/accepted/');
        let myJson = await response.json();

        console.log(myJson);

        for (let elem of myJson){
            let tr = document.createElement('tr');
            let id = document.createElement('th');
            let gt = document.createElement('td');
            let team1 = document.createElement('td');
            let team2 = document.createElement('td');
            let date = document.createElement('td');
            let button = document.createElement('button');

            id.scope = "row";

            id.textContent = elem[0];
            gt.textContent = elem[1];
            team1.textContent = elem[2];
            team2.textContent = elem[3];

            d = new Date(elem[4]);
            date.textContent = d.toISOString().substring(0, 10) + " " + d.toISOString().substring(11, 16);

            button.className = "btn btn-primary btn-sm mt-2";
            button.textContent = "New protocol";

            button.addEventListener('click', function(){
                console.log("To protocol " + elem[0]);
                window.location.href = 'protocol.html?id=' + elem[0];
            });

            tr.appendChild(id);
            tr.appendChild(gt);
            tr.appendChild(team1);
            tr.appendChild(team2);
            tr.appendChild(date);
            tr.appendChild(button);

            $('#ScheduleTableBody').append(tr);
        }
})