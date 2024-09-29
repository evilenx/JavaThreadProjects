# Simulating a Car Race - cli

- Objetivo: Familiarizarse con la codificación de un hilo extendiendo la clase Thread.

1. Escriba una clase con el nombre RaceCar que extiende a la clase Thread y
que contenga el método run().
2. Agregue un campo private int con el nombre finish y un campo private
String con el nombre name. Agregue un constructor que inicialice ambos
campos.
3. Dentro de run(), agregue un bucle for que ejecute un numero de veces de
finish. Dentro del bucle for, use System.out.println() para desplegar el campo
name y la iteración actual del bucle. Por ejemplo, la tercera vez que pasa el
tercer bucle debe desplegar “Mario: 3” para el carro llamado Mario.
Entonces, hacer que el hilo duerma durante un número aleatorio de veces
entre 0 y 5 segundos; en cada i-ésima iteración
4. Al final del bucle for, desplegar el mensaje que establece que la carrera de
carros ha terminado y desplegar el campo name. Por ejemplo “Mario
finished!”
5. Salve y compile la clase RaceCar.
6. Escriba una clase con el nombre Race que contenga el main().
7. Dentro del main(), declare y cree un arreglo con el nombre cars lo
suficientemente grande para almacenar cinco referencias Thread.
8. Escriba un bucle for que rellene el arreglo con cinco objetos RaceCar. Los
nombres se obtendrán de los cinco argumentos de la línea de comando, y el
int debe ser el mismo para cada RaceCar. Este valor representará que tan
larga será la carrera y este debe ser ingresado desde la línea de comando.
9. Escriba un segundo bucle for que invoque a start() en cada Thread en el
arreglo.
10. Salve, compile y corra el programa Race.
El programa Race se verá como una carrera de carros que progresa despacio
cuando UD. la ve. El ganador será el hilo RaceCar que termina primero.

