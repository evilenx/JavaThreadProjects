# Creating a Thread

- Objetivo: Familiarizarse con  la   creación de hilos por medio de la implementación de la interface Runnable.

1. Escriba una clase con el nombre PrintsNumbers que implemente la interface Runnable.  Agregue un campo de tipo boolean keepGoing y un constructor inicializa a keepGoing con true.
2.	Agregue un método a la clase PrintNumbers llamada stopPrinting() que asigne false al campo keepGoing.
3.	Dentro del método run(), escriba un bucle while usando System.out.println(), el cual despliega los números 1,2,3,4 … mientras el campo keepGoing sea true. Entre los despliegue de cada numero, el hilo debe dormir por un segundo.

4.	Salve y compile la clase PrintNumbers.

5.	Escriba una clase con el nombre Print que contenga el método main(). Dentro del método main(), instancie un objeto PrintNumbers. Instancie un objeto Thread el cual será utilizado para correr el objeto PrintNumbers en un hilo separado y luego inicia el hilo.

6.	El programa Print tomará un sencillo argumento en la línea de comando para representar el numero en milisegundos que el hilo main() dormirá. Convierta este argumento a un valor entero y luego haga que el hilo main() duerma durante esa cantidad de milisegundos.

7.	Cuando el hilo thread se despierta, haga que este invoque al método stopPrinting() en el objeto PrintNumbers. Luego Despliegue “main() is ending… “

8.	Salve, compile y corra el programa Print. No olvide pasarle un argumento entero para representar cuanto debe correr el programa en milisegundos.

Los números 1, 2, 3, … serán desplegados por aproximadamente el numero de segundos que sea especificado con el argumento de la línea de comando.  Por ejemplo, si el argumento en la línea de comando es 10,000 UD. deberá ver cerca de 10 números desplegados. Este lab. Demuestra la necesidad común en la programación de hilo:  Crear un mecanismo para que un hilo creado detenga su ejecución.  El método stopPrinting() puede ser invocado por cualquiera desde otro hilo para informar PrintNumbers que pude terminar de correr.

