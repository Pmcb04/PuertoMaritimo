
# :books: Realizado por: 
  - :computer: [Pedro Miguel Carmona Broncano](https://github.com/Pmcb04)
  - :date: Curso 2019/2020
  - :office: [Universidad de Extremadura](https://www.unex.es)


Supongamos que estamos programando un sistema en el que, de momento, queremos
controlar las entradas y salidas de barcos de un Puerto. Tenemos dos tipos de barcos: los que
quieren entrar y los que quieren salir y una puerta de control por la que entran y salen los
barcos.

// figura 1 paso 1


# :runner: Paso :one:

Crearemos un proyecto denominado *Barcos*.
Crear una clase Puerta que implemente los métodos *entrar* y *salir*. Crear una clase *Barco*
que llama al método *salir* o *entrar* de la clase *Puerta* (dependiendo de si es barco de
entrada o de salida).
El hecho de entrar o salir lo simularemos imprimiendo 3 veces el mensaje “El barco X
entra/sale”, siendo X el *id* del barco que recibe por parámetro en el constructor. El barco
también recibe otro parámetro que indica si es un barco que sale o que entra al Puerto.
Crear un programa principal que lance diversos threads *Barco*, unos que quieran entrar y
otros que quieran salir (por ejemplo, 10 de entrada y 10 de salida).
En esta solución no te preocupes si los barcos colisionan en la *Puerta* (Figura 1) Esto ocurrirá
si los mensajes se entremezclan de forma no deseada.
A continuación, tienes dos extractos de posibles salidas, en la primera no están colisionando y
en la segunda sí.

// tabla paso 1

Un posible diagrama de clases de la solución: 

// diagrama paso 1

# :runner: Paso :two: 

Modificar el programa apra que sólo pase un barco a la vez por la puerta de control. Esto implica hacer uso de *synchronized*

# :runner: Paso :three:

Ahora pueden pasar por la puerta de control más barcos, siempre y cuando todos vayan en el
mismo sentido. Si quiero entrar y hay otro entrando en el mismo sentido, podré hacerlo. Si no
está pasando nadie, también podré pasar. Si viene alguien de frente no. Igual para las Salidas.

// figura 4 paso 3

Puesto que esto se va a ir complicando poco a poco, vamos a optar por crear una nueva clase denominada *TorreDeControl* que es la encargada de gestionar los permisos de entrada y salida por la puerta. Tendrá los siguientes métodos: *permisoEntrada, permisoSalida, finEntrada y finSalida*. Ahora, antes de entrar, se invocará el método *permisoEntrada* de *TorreDeControl*. Cuendo termina de entrar, se invocará el método *finEntrada* para que la torre sepa que ha terminado de entrary haga las acciones oportunas. En la siguiente imagen tenemos el diagrama de clases de la solución. 

// diagrama paso 3

# :runner: Paso :four: 
Además de lo anterior, ahora tienen preferencia los que quieren salir. Si hay algún barco que quiere entrar, pero hay algún barco está esperando por salir, entonces no puede entrar. Sólo lo hará cuando no haya nadie saliendo ni esperando por salir. 

# :runner: Paso :five: monitores :computer:

De los barcos que entran al puerto, hay uno que es un barco mercante que transporta contenedores de azúcar, sal y harina. Cuando este barco entra, se dirige a una zona de descarga en la que hay esperando 3 grúas. La grúa 1 sólo puede coger contenedores de azúcar, la 2 de harina y la 3 de sal. El barco también tiene su propia grúa que, mientras el barco tenga contenedores los va  dejando en una plataforma con capacidad para un contenedor. De esa plataforma lo cogerá la grúa que pueda cogerlo con capacidad en función del contenido (sal, harina o azúcar)

Suponemos que inicialmente hay 12 contenedores de azúcar, 20 de sal y 5 de harina. Cuando el barco ha descargado todos sus contenedores, entonces sale del puerto. 

// paso 5

# :runner: Paso :six: semaforos :vertical_traffic_light:

De los barcos que entran al puerto, vamos a tener 5 barcos que son petroleros. Según van entrando en el puerto, se dirigen a una zona de carga de petróleo. Allí pueden cargar gasóleo y agua. Para cada barco, la zona de carga dispone de un contenedor de gasóleo privado para ese barco, para el de agua es común a los 5 barcos (están en un contenedor común). Cuando los 5 barcos han llegado a la zona de carga (sólo cuando los 5 han llegado), entonces el comportimento de cada barco es el siguiente:
  - Carga 3.000 litros de gasóleo.
  - Carga 5.000 litros de agua.

Se supone que la cantidad de agua es infinita, pero no la cantidad de gasóleo en cada contenedor de la zona de carga, que es de 1.000 litros. Cuando se acaba el gasóleo en los 5 contenedores de la zona de carga **(tienen que estar vaciós los 5 contenedores)**, entonces un proceso reponedor llenará los 5 contenedores de gasóleo. La capacidad de los contenedores de cada barco es de 3.000 litros de gasóleo y 5.000 litros de agua. El barco coge ambos fluidos de los contenedores de la zona de carga de 1.000 en 1.000 litros. Cuando los barcos entán llenos, entonces salen del puerto de nuevo (no es necesario que se esperen unos a otros). Cuando un barco está cogiendo agua, ningún otro barco puede coger agua. Sin embargo, los 5 barcos pueden estar llenando sus contenedores de gasóleo al mismo tiempo.

# :runner: Paso :seven:
Ahora vamos a hacer de forma concurrente el llenado de gasóleo y agua. Para ello crearemos dos threads por cada barco, cada uno haciendo una tarea. Estos threads se crearán dentro del metodo *run()* del barco petrolero. El barco ha de esperar a que terminen los dos nuevos threads. Cuendo los dos threads han teminado su tarea, el barco puede continuar. Hay que lanzar estos threads con *Executor*

*Observación: estos threads se lenzan desde un sitio distinto al main*

# :runner: Paso :eight:

En clases de teoria hemos visto difentes clases y frameworks que nos permiten simplificar la creación y sincronización de threads (CyclicBarrier, CountDownLatch, SynchronousQueue, Exchanger, etc). 

En este paso de la prática se pide rehacer el proyecto Puerto Marítimo, pero usando las nuevas clases y framenworks aprendidos. La solución se simplificará en algunos puntos y entenderemos perfectamente por qué surgieron. Sientate libre de usar las que quieras. Al menos deberías utilizar tres de ellas. 
