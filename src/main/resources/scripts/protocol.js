let currentEventID = 1;
let currentPlayerNum = 1;
let currentTeam = 1;

document.addEventListener('DOMContentLoaded', async function(){
    let url_string = window.location.href;
    let url = new URL(url_string);
    let id = url.searchParams.get("id");
    console.log(id);

    if (id === null) {
        // render window to insert id
        $("#IDFormRow").show();
    } else {
        let response = await fetch('http://localhost:4567/api/v1/request/' + id);
        let myJso;
        try{
            myJson = await response.json();
        } catch (SyntaxError){
            $('#Title').html("<h1> Sorry, this application is not found =( </h1>");
            console.log("Not found");
            return;
        }

        // render main window
        $("#ProtocolMain").show();

        console.log(myJson);
        d = new Date(myJson.date);

        let response1 = await fetch('http://localhost:4567/api/v1/team/' + myJson.team1ID);
        let response2 = await fetch('http://localhost:4567/api/v1/team/' + myJson.team2ID);

        team1json = await response1.json();
        team2json = await response2.json();

        currentTeam = team1json.id;

        console.log(team1json);
        console.log(team2json);

        $('#Versus').text(team1json.name + "  VS  " + team2json.name);
        $('#GameDate').text(d.toISOString().substring(0, 10) + " at " + d.toISOString().substring(11, 16));

        let eventTypes = await fetch('http://localhost:4567/api/v1/event_type/')
        eventTypesJson = await eventTypes.json();

        console.log(eventTypesJson);

        for (let elem of eventTypesJson){
            if (elem.gameTypeID == myJson.gameTypeID)
                $('#event_type').append('<option value="' + elem.id + '">' + elem.name + '</option>');
        }

        $('#team').append('<option value="1">' + team1json.name + '</option>');
        $('#team').append('<option value="2">' + team2json.name + '</option>');

        $('#player2').attr("disabled", "disabled");

        $('#team').change(function(){
            console.log("Team changed");

            let id = parseInt($('#team').find(":selected").val());
            currentTeam = id;
            console.log(currentTeam);

            $('#player1').empty();
            $('#player2').empty();

            if (currentTeam == 1){
                for (let elem of team1json.players){
                    $('#player1').append('<option value="' + elem.id + '">' +
                                            elem.name + ' ' + elem.surname + '</option>');
                }
                if (currentPlayerNum == 2){
                    for (let elem of team1json.players){
                        $('#player2').append('<option value="' + elem.id + '">' +
                                                elem.name + ' ' + elem.surname + '</option>');
                    }
                }
            }
            else {
                for (let elem of team2json.players){
                    $('#player1').append('<option value="' + elem.id + '">' +
                                            elem.name + ' ' + elem.surname + '</option>');
                }
                if (currentPlayerNum == 2){
                    for (let elem of team2json.players){
                        $('#player2').append('<option value="' + elem.id + '">' +
                                                elem.name + ' ' + elem.surname + '</option>');
                    }
                }
            }
        });

        $('#event_type').change(function(){
            console.log("Event type changed");
            let id = parseInt($('#event_type').find(":selected").val());
            let players_num = eventTypesJson.find(x => x.id == id).playersNum;
            console.log(players_num);
            currentPlayerNum = players_num;
            if (players_num == 1){
                $('#player2').empty();
                $('#player2').attr("disabled", "disabled");
            }
            else{
                $('#player2').removeAttr("disabled");

                if (currentTeam == 1){
                    for (let elem of team1json.players){
                        $('#player2').append('<option value="' + elem.id + '">' +
                                                elem.name + ' ' + elem.surname + '</option>');
                    }
                }
                else {
                    for (let elem of team2json.players){
                        $('#player2').append('<option value="' + elem.id + '">' +
                                                elem.name + ' ' + elem.surname + '</option>');
                    }
                }
            }
        });

        $('#finish').click(function(){
           console.log("Finished");
           window.location.href = 'statistics.html?id=' + myJson.id;
       });

       $('#SubmitButton').click(function(){
            if ($('#time').val() == ""){
                alert("You need to provide time");
                return;
            }
            if ($('#player1').val() == "") return;

            // send to server
             $.ajax({
                url: '/api/v1/match/add_event/',
                method: 'GET',
                contentType: 'json',
                data:  {'time': $('#time').val(),
                        'player1ID': $('#player1').val(),
                        'player2ID': $('#player2').val(),
                        'eventTypeID': $('#event_type').val(),
                        'id': myJson.id},
                success: function(data){
                    console.log(data);
                }
            });

            // add to table
            let tr = document.createElement('tr');
            let id = document.createElement('th');
            let event = document.createElement('td');
            let team = document.createElement('td');
            let player1 = document.createElement('td');
            let player2 = document.createElement('td');
            let time = document.createElement('td');

            id.textContent = currentEventID;
            event.textContent = $('#event_type').find(":selected").text();
            player1.textContent = $('#player1').find(":selected").text();
            player2.textContent = $('#player2').find(":selected").text();
            time.textContent = $('#time').val();
            team.textContent = $('#team').find(":selected").text();

            currentEventID++;

            tr.appendChild(id);
            tr.appendChild(event);
            tr.appendChild(team);
            tr.appendChild(player1);
            tr.appendChild(player2);
            tr.appendChild(time);

            $('#ProtocolTableBody').append(tr);
       });

        // create new match here
       $.ajax({
            url: '/api/v1/match/create/',
            method: 'GET',
            contentType: 'json',
            data:  {'date': myJson.date,
                    'team1ID': myJson.team1ID,
                    'team2ID': myJson.team2ID,
                    'gameTypeID': myJson.gameTypeID,
                    'id': myJson.id},
            success: function(data){
       		    console.log(data);
            }
       });
    }
});