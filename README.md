# Operación Fuego de Quasar
El sistema está compuesto de una consola interactiva (desarrollada en *Java* plano, versión 8) y una API REST (desarrollada con *Spring Boot*).

## Consola interactiva

La misma puede descargarse empaquetada desde el siguiente link:

[QuasarFireOperationConsole.jar](https://drive.google.com/file/d/1PuJk4nk4AsUUxo45NhTZQSu-F2o19bhF/view?usp=sharing)

Para ejecutarla, desde una consola, ubicarse en la carpeta donde se encuentra el archivo JAR y ejecutar el siguiente comando:

![Imgur](https://i.imgur.com/4AGHZtg.png)


Tras el mensaje de bienvenida se presentan las dos opciones a escoger:

![Consola01](https://i.imgur.com/A7ICQmc.png)

La primera opción devuelve las coordenadas del carguero según las distancias ingresadas para cada uno de los satélites:

![Consola02](https://imgur.com/M90SDV9.png)

La segunda opción intenta descifrar el mensaje secreto del carguero según las palabras que fueron interceptadas por los satélites. Por cada uno de los satélites se pedirá ingresar la cantidad de palabras interceptadas y tras ella el ingreso de cada una de las mismas:

![Consola03](https://imgur.com/dU2z0aG.png)

## API REST

Puede ingresarse a la interfaz de *Swagger* desde el siguiente link:
[QUASAR FIRE OPERATION API](https://quasar-fire-operation-test.herokuapp.com/swagger-ui.html#)

Desde cualquier cliente externo la *URL* base es: https://quasar-fire-operation-test.herokuapp.com

En la vista principal se presentan los tres *endpoints* disponibles.

![Api01](https://i.imgur.com/0EdZzqt.png)

Presionando el botón junto al *endpoint* se despliegan las características del mismo, entre ellas el modelo de datos requerido para el *request*.
Este primero (**topsecret**) recibe un arreglo de satélites (con sus nombres, distancias y arreglos de palabras interceptadas) y devuelve las coordenadas del carguero y el mensaje descifrado.

![Api02](https://i.imgur.com/nUKeT1R.png)

Presionando el botón *Try it out* podemos realizar el un request desde la misma interfaz.

![Api03](https://i.imgur.com/BXxwstK.png)

En la sección *Server* response podemos podemos observar datos devueltos por el servidor.

![Api04](https://i.imgur.com/NZThYka.png)

El segundo endpoint (**topsecret_split**) no requiere el envío de parametros. 

![Imgur](https://i.imgur.com/q3CYjvR.png)

En caso de haber como mínimo tres satélites ingresados y pueda descifrarse el mensaje secreto se retonarnará el mismo y las coordenadas del carguero.

![Imgur](https://i.imgur.com/DDoWpNY.png)

El tercer *endpoint* (**topsecret_split/{name}**) recibe en el cuerpo la distancia y las palabras interceptadas por un satélite y en el parámetro de la *URL* el nombre del mismo.
Si ya existe un satélite con el nombre suministrado, se actualiza, caso contrario, se crea.

![Api05](https://i.imgur.com/qeaw3o6.png)
