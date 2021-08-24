# Meep-application
## Api rest service for pooling meep service

### Introducción
Este proyecto esta desarrollado para utilizarse con java 1.8 y Maven.
Para compilar el proyecto:

```sh
$ mvn clean install
```
Para arrancar el proyecto:

```sh
$ mvn spring-boot:run
```

También se puede generar una imagen Docker y utilizarla así:

```sh
$ docker build -t meep-polling .
```

```sh
$ docker run -p 9090:9090 -t meep-polling
```

Una vez arrancado el proyecto están habilitadas las siguientes urls:

- [Swagger](http://localhost:9090/api/v1/swagger-ui/index.html?configUrl=/api/v1/v3/api-docs/swagger-config)



### Objetivo
El objetivo es hacer pooling de un api de meep que ofrece vehículos y conocer los vehículos disponibles así como la diferencia de vehículos con la llamada anterior.
Se ha creado un servicio con springboot que hace peticiones al api de meep cada 30 segundos esto se hace con la librería Quartz, la configuración se aloja en memoria 
ya que no se ha usado ninguna base de datos.
Para saber la diferencia de vehículos también guarda en memoria los resultados de la petición anterior.
Muestra por consola el numero de vehículos nuevos o que ya no están disponibles así como sus objetos.
Como evolutivo se podría añadir una base de datos que almacenara la configuración de Quartz y los vehículos que se han traído del API.


### Tecnología
En el proyecto se ha utilizado Git, Docker, Maven, Springboot, Lombock, Mapstruct, Actuator, Quartz

---

