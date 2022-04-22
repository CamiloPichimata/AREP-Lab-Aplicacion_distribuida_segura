function guardarNota() {
    let titulo = document.getElementsByClassName("ContenidoTitulo")[0].outerText;
    let contenido = document.getElementsByClassName("ContenidoNota")[0].outerText;
    var xhttp = new XMLHttpRequest();
    xhttp.open("PUT", "localhost:5000/putNotes", false);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send("{'titulo': '" + document + "', 'contenido': '" + contenido + "'}");
    console.log("JSON:", "{'titulo': '" + document + "', 'contenido': '" + contenido + "'}")
    console.log("Título:", titulo, "\nContenido:", contenido);
}