# Ordenación ponderada de productos
## _Enfoque funcional y técnico_

### Funcional

Teniendo en cuenta que existen varias categorías de productos y que pueden presentar diferentes criterios de ordenación, se ha planteado una estructura inicial reutilizable para otras categorías (ej:zapatos), pero con la implementación concreta de camisetas y/o camisas.

Funcionalmente se permite realizar búsquedas por métricas identificadas por un nombre y un peso en porcentaje.

Los pesos de las métricas variarán entre 0.0 (0%) y 1.0 (100%). No se permitirá una suma de pesos superior a 1.0 (100%)

Todas las métricas tendrán que tener especificada su peso, excepto la última que si no se indica explícitamente, será el resultado de 1.0 - (suma de los pesos del resto de métricas)

Por otro lado se permitirá busca de forma ascendente (comportamiento por defecto) o descendente, si así se indica.

### Técnico

##### _URL de consulta_

Se han considerado varias opciones. Desde un enfoque más dinámico, de forma que el input fuera un JSON codificado en Base64 para ser incluido como _query parameter_ a uno más estático que permita modificar la implementación de forma más sencilla sin impactar a los clientes del API. Esta última estrategia ha sido la adoptada.

En primer lugar, se ha analizado la posibilidad de que cada consulta fuera en un POST. Esto facilita el paso de información en el cuerpo del mensaje mediante un objeto JSON, especificando las métricas afectadas en la ordenación y sus pesos.
Sin embargo, se ha considerado que es más importante que el método expuesto del API fuera un GET, ya que es un endpoint de consulta de datos, idempotente y que permite optimizar en producción cachés de información de datos, cosa que no debe ocurrir con un POST.
Por otro lado, usar en un método GET el cuerpo del mensaje no es en absoluto recomendado. Entre el cliente del API y el servidor podría haber elementos de infraestructura que directament eliminaran el cuerpo del mensaje de la petición.

Por lo tanto, se ha optado finalmente por un enfoque de método GET filtrando por query parameters el comportamiento deseado.

El formato de la URL es:
__endpoint__?om=_nombre_metrica1_-_peso_metrica1_,_nombre_metrica2_-_peso_metrica2...._&o=true|false

siendo el peso de la última métrica opcional, así como el parámetro de ordenación, que por defecto será descendente (de mayor a menor según las métricas y sus pesos) y cuyo valor se puede establecer si el cliente del API lo necesita. Solo será ascendente si el valor del parámetro "o" es igual a "true".

Cuando se arranque la aplicación, el endpoint sería __http://localhost:8080/shirts__ y a partir de ahí, los parámetros comentados anteriormente.
NOTA: en entorno real, este endpoint "base" debería devolver todos los objetos _shirt_. Sin embargo, no es el propósito de este ejercicio, y esa URL devolverá directamente un HTTP 400 (Bad request)

##### _Implementación_

El ejercicio ha sido implementado en este [proyecto de GitHub](https://github.com/jjmargon/product-search-service), utilizando Java 17 y Spring Boot 3.

A continuación se mencionan las principales interfaces y clases con las responsabilidades definidas en cada una de ellas:

__ProductOrderingStock__
Interfaz sin métodos. Se utiliza solo como "marker interface" para identificar las clases que lo implementen como 
aquellas que implementan métodos que devuelven información de stock.
Simboliza una abstracción sobre el stock, teniendo en cuenta que dependiendo de la categoría, los stocks pueden ser resueltos de diferentes maneras. Por ejemplo, en camisetas se localiza por tallas (S,M,L), mientras que en zapatos sería por el número.

__ProductCategory__
Interfaz genérica a partir de objetos que implementen ProductOrderingStock.
Los objetos que la implementen tienen que definir un nombre (String) y un objeto genérico (Generics) que devuelva el stock.

__ProductOrdering__
Clase abstracta que contiene la información global de los productos. Esto es, el id, nombre, unidades vendidas y su categoría.

__ShirtStock__
Una implmentación de ProductOrderingStock que devuelve el stock para cada una de las tallas (S,M y L).

__ShirtProductCategory__
Una implementación de ProductCategory, devolviendo "SHIRTS" como nombre de la categoría así como una instancia de ShirtStock como clase que implementa el Stock.

__ShirtProductOrdering__
Clase concreta que hereda de ProductOrdering y que define su categoría (ShirtProductCategory)

__ShirtOrderingMetric__
Clase abstracta que implementa un Comparator<ShirtProductOrdering>. Define un método abstracto que simboliza el nombre 
que tendrá la métrica para que pueda ser referenciada en el API

__StockAvailableShirtOrderingMetric__
Métrica que implementa ShirtOrderingMetric.
Esta métrica ordena los objetos ShirtProductOrdering a partir de su stock total (la suma del stock de todas las tallas)
Define su nombre como _"TotalStock"_

__SalesShirtOrderingMetric__
Métrica que implementa ShirtOrderingMetric.
Esta métrica ordena los objetos ShirtProductOrdering a partir de sus unidades vendidas
Define su nombre como _"Sales"_

__WeightedShirtProductOrderingMetric__
Contiene al objeto ShirtOrderingMetric y además, define el peso que tendrá la métrica en la consulta. NOTA: se sigue la estrategia de implementación de preferir "composición sobre herencia". Esto es, que la métrica con peso __no__ extiende de métrica, sino que contiene a la clase de métrica y añade el dato del peso.

__GlobalWeightedShirtProductOrderingMetric__
Objeto que contiene la lista de métricas con peso (List<WeightedShirtProductOrderingMetric>) a utilizar para la consulta

__Utils__
Objeto que contiene la lógica principal de ordernación a partir del listado de productos a ordenar (List<ShirtProductOrdering> products), las métricas con peso (GlobalWeightedShirtProductOrderingMetric metrics) y un booleano que indicará el orden ascendente o descendente de la búsqueda.

__ShirtOrderingController__
Controlador REST de Spring para implementar el método GET del API

El desarrollo también contiene una serie de tests unitarios de JUnit 5 para comprobar el resultado 

## Ejecución

El proyecto requiere Java 17 para su correcto funcionamiento. Al ser un proyecto de Spring Boot, sobre el directorio principal del proyecto (directorio product-search-service que contiene los archivos build.gradle o gradlew) ejecutar
```sh
gradlew bootJar
java -jar build/libs/product-search-service-0.0.1-SNAPSHOT.jar
```
Otra opción es tener Docker instalado en el equipo. En este caso, desde el directorio principal, basta con escribir 
```sh
gradlew bootBuildImage
```
Se puede comprobar que en las imágenes de Docker existe una que es product-search-service
```sh
docker images
```
Y en ese caso, se puede ejecutar la aplicación con
```sh
docker run -p 8080:8080 product-search-service
```
NOTA: si el equipo tiene el puerto 8080 en uso, se puede cambiar el puerto principal a otro. En ese caso, se tendría que cambiar el puerto en la llamada del endpoint.
Por ejemplo, se podría ejecutar
```sh
docker run -p 80:8080 product-search-service
``` 
y para invocar la aplicación, la URL base sería http://localhost/shirts...

NOTA: para ejecutar los tests de JUnit del proyecto, se podría, con Java 17 y en el directorio principal del proyecto, ejecutar:
```sh
gradlew test
```