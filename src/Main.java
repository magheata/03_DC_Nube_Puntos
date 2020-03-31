/* Created by andreea on 26/03/2020 */
import java.awt.*;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*PuntosService puntosService = new PuntosService();
        String sortPath = pathsSort[1];

        TOTAL_PUNTOS = (int) Math.pow(10, 2);
        Nube nubePuntos = new Nube(TOTAL_PUNTOS);
        Future<Nube> future = nubePuntos.crearNubePuntos(TOTAL_PUNTOS, 2, 1);
        //Future<Nube> future = nubePuntos.crearNubePuntosRandom(0, TOTAL_PUNTOS);
        while (!future.isDone()){}
        puntosService.setNubePuntos(future.get());
        puntosService.setClaseSort(sortPath, 0, TOTAL_PUNTOS - 1, true);
        puntosService.start();
        //new GraphApplication(getExampleGraph2());*/

        Window panelControl = new Presentation.Window();
        panelControl.setVisible(true);
        panelControl.pack();
    }
}
