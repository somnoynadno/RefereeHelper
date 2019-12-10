let selectedGT = 1;

document.addEventListener('DOMContentLoaded', async function(){
    let teamResponse = await fetch('http://localhost:4567/api/v1/team/');
    let GTResponse = await fetch('http://localhost:4567/api/v1/game_type/');

    GTJson = await GTResponse.json();
    teamJson = await teamResponse.json();

    console.log(GTJson);
    console.log(teamJson);

    for (let elem of GTJson){
        $('#game_type').append('<option value="' + elem.id + '">' + elem.name + '</option>');
    }

    for (let elem of teamJson){
        if (elem.gameTypeID == selectedGT){
            $('#team1').append('<option value="' + elem.id + '">' + elem.name + '</option>');
            $('#team2').append('<option value="' + elem.id + '">' + elem.name + '</option>');
        }
    }

    $('#game_type').change(function(){
        selectedGT = parseInt($('#game_type').find(":selected").val());

        $('#team1').empty();
        $('#team2').empty();

        for (let elem of GTJson){
            $('#game_type').append('<option value="' + elem.id + '">' + elem.name + '</option>');
        }

        for (let elem of teamJson){
            if (elem.gameTypeID == selectedGT){
                $('#team1').append('<option value="' + elem.id + '">' + elem.name + '</option>');
                $('#team2').append('<option value="' + elem.id + '">' + elem.name + '</option>');
            }
        }
    });

    $('#submit').click(function(){
        let team1ID = parseInt($('#team1').find(":selected").val());
        let team2ID = parseInt($('#team2').find(":selected").val());

        let time = $('#time').val();
        let date = $('#date').val();
        let datetime = Date.parse(date+"T"+time+"Z");


        console.log(selectedGT);
        console.log(team1ID);
        console.log(team2ID);
        console.log(date);
        console.log(time);
        console.log(datetime);

         // send to server
         $.ajax({
            url: '/api/v1/request/create/',
            method: 'GET',
            contentType: 'json',
            data:  {'date': datetime,
                    'team1ID': team1ID,
                    'team2ID': team2ID,
                    'gameTypeID': selectedGT
                    },
            success: function(data){
                console.log(data);
            }
        });

        window.location.replace("/applications.html");
    });
});