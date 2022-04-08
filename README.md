# Aplicación distribuida segura en todos sus frentes
## Arquitecturas Empresariales (AREP)
#### Camilo Andrés Pichimata Cárdenas
##### Abril del 2022

## Descripción
En el presente laboratorio se tiene como fin desarrollar una aplicación Web segura con los siguientes requerimientos:

+ Se debe permitir un acceso seguro desde el browser a la aplicación. Es decir debe garantizar autenticación, autorización e integridad de usuarios.

+ Se deben tener al menos dos computadores comunicandose entre ellos y el acceso de servicios remotos debe garantizar: autenticación, autorización e integridad entre los servicios. Nadie puede invocar los servicios si no está autorizado.

+ Explicar como se escalaría la arquitectura de seguridad para incorporar nuevos servicios.

La aplicación web a desarrollar tiene como fin la creación y almacenamiento de notas rápidas por parte del usuario, su arquitectura se presenta a continuación:

![](img/Arquitectura.png)

## Arquitectura de seguridad del prototipo

Para permitir acceso seguro a los usuarios se utilizarán llaves públicas, llaves privadas y certificados con el fin de garantizar la autenticación, autorización e integridad de los usuarios y entre los diferentes servicios desplegados.

Para esto comenzamos creando un par de llaves mediante la herramienta `keytool`, el comando a ejecutar es el siguiente:

```
keytool -genkeypair -alias loginkeypair -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore loginkeystore.p12 -validity 3650
```

Esto nos genera un archivo llamado `loginkeystore.p12` el cuál se almacena en la carpeta **keystores** como se puede ver a continuación:

![](img/keytool.png)

![](img/keytool_key.png)

En este caso se generaron las llaves usando como contraseña los digitos del 1 al 6 (`123456`)

A continuación para poder realizar la conexión segura en la aplicación se agregó la siguiente línea al método `main` donde se inicializa el servidor:

```java
// Especificación
void spark.Spark.secure(String keystoreFile, String keystorePassword, String truststoreFile, String truststorePassword)

// Implementación en la inicialización del servidor
secure(getKeystore(), "123456", null, null);
```

En este caso, el primer argumento, se hace un llamado al método `getKeyStore`, este tiene como fin retornar un String con la el path para acceder al archivo generado al usar la herramienta **keytool**, su implementación es la siguiente:

```java
static String getKeystore() {
	if (System.getenv("KEYSTORE") != null) {
        return System.getenv("keystore");
	}
	return "keystores/loginkeystore.p12";
}
```

Después de realizadas estas configuraciones se inicia el servicio usando el siguiente comando:

```bash
mvn exec:java -Dexec.mainClass="co.edu.escuelaing.AppDistribuidaSegura.SecureSparkWeb"
```

Después de inicializado el servicio ingresamos a la siguiente dirección con el fin de verificar que la conexión se realiza de forma segura, mediante el uso del protocolo https:

```
https://localhost:5000/Hello
```

Al ingresar a dicha dirección desde el navegador se puede ver que la página carga correctamente y muestra el mensaje especificado:

![](img/https_Hello.png)

De acuerdo con esto se puede ver que la página está usando las llaves anteriormente creadas para realizar la conexión, si verificamos el certificado podemos ver los datos ingresados anteriormente:

![](img/localhost_cert.png)

