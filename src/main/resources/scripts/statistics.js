function wrap(string){
    return '"' + string + '"'
}

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
        $("#StatisticsMain").show();

        let response = await fetch('http://localhost:4567/api/v1/statistics/' + id);
        let json;

        try{
            json = await response.json();
        } catch (SyntaxError){
            $('#Versus').text("Sorry, match not found =(");
            console.log("Not found");
            return;
        }

        console.log(json);
        d = new Date(json.date);

        let p1 = json.passes1;
        let p2 = json.passes2;
        let p = p1 + p2;
        let possession;

        if (p == 0){
            possession = "can't count";
        } else {
            possession = Math.round(p1 / p * 100).toString() + "% / " +
                         Math.round(p2 / p * 100).toString() + "%";
        }

        $('#Versus').text(wrap(json.team1.name) + "  vs  " + wrap(json.team2.name));
        $('#Score').text(json.score1 + " : " + json.score2);
        $('#GameDate').text(d.toISOString().substring(0, 10) + " at " + d.toISOString().substring(11, 16));
        $("#Penalties").text("Sum of penalties: " + json.penalties1 + " : " + json.penalties2);
        $('#BallPossession').text("Possession of ball: " + possession);
    }
});