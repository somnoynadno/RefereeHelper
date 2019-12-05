document.addEventListener('DOMContentLoaded', async function(){
    let url_string = window.location.href;
    let url = new URL(url_string);
    let id = url.searchParams.get("id");
    console.log(id);

    if (id === null) {
        // render window to insert id
        $("#IDFormRow").show();
    } else {
        // render main window
        $("#ProtocolMain").show();
        let response = await fetch('http://localhost:4567/api/v1/request/' + id);
        let myJson = await response.json();
        d = new Date(myJson.date);

        console.log(myJson);

        let response1 = await fetch('http://localhost:4567/api/v1/team/' + myJson.team1ID);
        let response2 = await fetch('http://localhost:4567/api/v1/team/' + myJson.team2ID);

        team1json = await response1.json();
        team2json = await response2.json();

        console.log(team1json);
        console.log(team2json);

        $('#Versus').text(team1json.name + "  VS  " + team2json.name);
        $('#GameDate').text(d.toISOString().substring(0, 10) + " at " + d.toISOString().substring(11, 16));

        let eventTypes = await fetch('http://localhost:4567/api/v1/event_type/')
        eventTypesJson = await eventTypes.json();

        console.log(eventTypesJson);

        for (let elem of eventTypesJson){
            $('#event_type').append('<option value="' + elem.id + '">' + elem.name + '</option>');
        }

        $('#team').append('<option value="1">' + team1json.name + '</option>');
        $('#team').append('<option value="2">' + team2json.name + '</option>');

        $('#team').change(function(){
            console.log("Team changed");
            let id = parseInt($('#team').find(":selected").val());
            console.log(id);
            $('#player1').empty();
            $('#player2').empty();
            if (id == 1){
                for (let elem of team1json.players){
                    $('#player1').append('<option value="' + elem.id + '">' +
                                            elem.name + ' ' + elem.surname + '</option>');
                }
                for (let elem of team1json.players){
                    $('#player2').append('<option value="' + elem.id + '">' +
                                            elem.name + ' ' + elem.surname + '</option>');
                }
            }
            else{
                for (let elem of team2json.players){
                    $('#player1').append('<option value="' + elem.id + '">' +
                                            elem.name + ' ' + elem.surname + '</option>');
                }
                for (let elem of team2json.players){
                    $('#player2').append('<option value="' + elem.id + '">' +
                                            elem.name + ' ' + elem.surname + '</option>');
                }
            }
        });

        $('#event_type').change(function(){
            console.log("Event type changed");
            let id = parseInt($('#event_type').find(":selected").val());
            let players_num = eventTypesJson.find(x => x.id == id).playersNum;
            console.log(players_num);
            if (players_num == 1){
                $('#player2').empty();
                $('#player2').attr("disabled", "disabled");
            }
            else{
                $('#player2').removeAttr("disabled");
            }
        });

        $('#finish').click(function(){
           console.log("Finished");
           window.location.href = 'statistics.html';
       });
    }
});