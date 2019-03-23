const clearData = function clearData() {
    $('#data').empty();
};

const renderViruses = function renderViruses(viruses) {

    let html = `<table class="table">
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Magnitude</th>
                    <th>Released On</th>
                    <th></th>
                    <th></th>
                </tr>`;
    viruses.forEach((virus,i) => {
        html += `<tr>
                    <td>${i + 1}</td>
                    <td>${virus.name}</td>
                    <td>${virus.magnitude}</td>
                    <td>${virus.releasedOn}</td>
                    <td><a href="/viruses/${virus.id}/edit">Edit</a></td>
                    <td><a href="/viruses/${virus.id}/delete">Delete</a></td>
                </tr>`;
    });
    html += "</table>";

    $("#data").append(html);

};

const renderCapitals = function renderCapitals(capitals) {

    let html = `<table class="table">
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Latitude</th>
                    <th>Longitude</th>
                    <th></th>
                    <th></th>
                </tr>`;
    capitals.forEach((capital,i) => {
        html += `<tr>
                    <td>${i + 1}</td>
                    <td>${capital.name}</td>
                    <td>${capital.latitude}</td>
                    <td>${capital.longitude}</td>
                </tr>`;
    });
    html += "</table>";

    $("#data").append(html);

};


const showViruses = function showViruses() {
    clearData();

    fetch('/viruses/show/fetch')
        .then((response) => response.json())
        .then((viruses) => renderViruses(viruses));
};

const showCapitals = function showCapitals() {
    clearData();

    fetch('/capitals/show/fetch')
        .then((response) => response.json())
        .then((capitals) => renderCapitals(capitals));
};

$('#virusesRadioBnt').click(showViruses);
$('#capitalsRadioBnt').click(showCapitals);


