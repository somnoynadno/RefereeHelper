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

            id.scope = "row";

            id.textContent = elem.id;
            gt.textContent = elem.gameTypeID;
            team1.textContent = elem.team1ID;
            team2.textContent = elem.team2ID;
            date.textContent = elem.date;

            tr.appendChild(id);
            tr.appendChild(gt);
            tr.appendChild(team1);
            tr.appendChild(team2);
            tr.appendChild(date);

            $('#ScheduleTableBody').append(tr);
        }
})