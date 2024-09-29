/*
*  Tarea: Matriz Transpuesta
** @author: Emanuel Avilés
*  Fecha: 26-09-24
*
*/

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MatrizTranspuesta {

    public static void main(String[] args) {
        int[][] matrix = {
            {1, 4, 3},
            {3, 5, 2},
            {5, 2, 3}
        };

        int rows = matrix.length;
        int cols = matrix.length;

        int[][] transposedMatrix = new int[cols][rows];

        /*
        * Crear un pool de hilos
        */
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());



        for (int i = 0; i < rows; i++) {
            final int row = i;
            executor.submit(() -> transposeRow(matrix, transposedMatrix, row));
        }

        /*
        * Detener el executor
        */
        executor.shutdown();

        /*
        * Imprimir la matriz transpuesta
        */
        printMatrix(transposedMatrix);
    }


    /*
    *Método para transponer una fila
    */
    private static void transposeRow(int[][] matrix, int[][] transposedMatrix, int row) {
        for (int j = 0; j < matrix.length; j++) {
            transposedMatrix[j][row] = matrix[row][j];
        }
    }

    /*
    * Método para imprimir la matriz
    */
    private static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
