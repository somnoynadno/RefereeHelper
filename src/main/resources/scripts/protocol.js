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
    }
});