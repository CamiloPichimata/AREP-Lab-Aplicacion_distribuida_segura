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

Esto nos genera un archivo llamada `loginkeystore.p12` el cuál se almacena en la carpeta **keystores** como se puede ver a continuación:

