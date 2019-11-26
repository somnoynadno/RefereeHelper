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

            tr.id = "TR" + elem.id;

            id.textContent = elem.id;
            gt.textContent = elem.gameTypeID;
            team1.textContent = elem.team1ID;
            team2.textContent = elem.team2ID;
            date.textContent = elem.date;

            button1.textContent = "Apply";
            button2.textContent = "Discard";

            button1.className = "btn btn-success btn-sm mr-1";
            button2.className = "btn btn-danger btn-sm";

            button1.addEventListener('click', async function(){
                console.log("Apply " + elem.id);
                let response = await fetch('http://localhost:4567/api/v1/request/accept/' + elem.id);
                let result = await response.text();
                console.log(result);
                $("#TR" + elem.id).remove();
            });

            button2.addEventListener('click', async function(){
                console.log("Discard " + elem.id);
                let response = await fetch('http://localhost:4567/api/v1/request/remove/' + elem.id);
                let result = await response.text();
                console.log(result);
                $("#TR" + elem.id).remove();
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