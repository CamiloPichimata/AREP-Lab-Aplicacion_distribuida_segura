function doLogin() {
    console.log("Logging in...")
    var isLogged = false;
    var userName = document.getElementById("GET-Username").value;
    var password = document.getElementById("GET-Password").value;
    
    sha256(password)
        .then(hashHex => {
            console.log("User:", userName, " - Password:", password, " - Hash:", hashHex);
            urlLogin = "https://localhost:5000/login?uName="+userName+"&uPasswd="+hashHex;
            return urlLogin
        })
        .then(urlLogin => {
            console.log("urlLogin: ", urlLogin)
            fetch(urlLogin)
                .then(response => response.json())
                .then(data => {
                    isLogged = data;
                    console.log("isLogged:", isLogged);
                    if (isLogged) {
                        window.location.href = "https://localhost:5000/home.html";
                    } else {
                        alert("Los datos ingresados no son correctos");
                    }
                });
        });
}

async function sha256(text) {
    // Codificar como UTF-8
    const msgBuffer = new TextEncoder('utf-8').encode(text);
    // Cifrar el mensaje
    const hashBuffer = await crypto.subtle.digest('SHA-256', msgBuffer);
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    // Convertir bytes a cadena hexadecimal
    const hashHex = hashArray.map(b => ('00' + b.toString(16)).slice(-2)).join('');
    const string = await console.log("hashHex: " + hashHex);
    /*console.log("string: " + hashHex);
    console.log(hashHex);
    urlLogin = "https://localhost:5000/login?uName=userName+&uPasswd="+hashHex;
    console.log(urlLogin);*/
    return hashHex;
}
