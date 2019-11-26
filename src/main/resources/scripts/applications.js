document.addEventListener('DOMContentLoaded', async function(){
        let response = await fetch('http://localhost:4567/api/v1/request/not_accepted/');
        let myJson = await response.json();

        console.log(myJson);

        for (let elem of myJson){
            let tr = document.createElement('tr');
            let id = document.createElement('th');
            let gt = document.createElement('td');
            let team1 = document.createElement('td');
            let team2 = document.createElement('td');
            let date = document.createElement('td');
            let buttons = document.createElement('td');
            let button1 = document.createElement('button');
            let button2 = document.createElement('button');

            id.scope = "row";
            button1.type = "button";
            button2.type = "button";

            tr.id = "TR" + elem[0];

            id.textContent = elem[0];
            gt.textContent = elem[1];
            team1.textContent = elem[2];
            team2.textContent = elem[3];

            d = new Date(elem[4]);
            date.textContent = d.toISOString().substring(0, 10) + " " + d.toISOString().substring(11, 16);

            button1.textContent = "Apply";
            button2.textContent = "Discard";

            button1.className = "btn btn-success btn-sm mr-1";
            button2.className = "btn btn-danger btn-sm";

            button1.addEventListener('click', async function(){
                console.log("Apply " + elem[0]);
                let response = await fetch('http://localhost:4567/api/v1/request/accept/' + elem[0]);
                let result = await response.text();
                console.log(result);
                $("#TR" + elem[0]).remove();
            });

            button2.addEventListener('click', async function(){
                console.log("Discard " + elem[0]);
                let response = await fetch('http://localhost:4567/api/v1/request/remove/' + elem[0]);
                let result = await response.text();
                console.log(result);
                $("#TR" + elem[0]).remove();
            });

            buttons.appendChild(button1);
            buttons.appendChild(button2);

            tr.appendChild(id);
            tr.appendChild(gt);
            tr.appendChild(team1);
            tr.appendChild(team2);
            tr.appendChild(date);
            tr.appendChild(buttons);

            $('#ApplicationsTableBody').append(tr);
        }
})