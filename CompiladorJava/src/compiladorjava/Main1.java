package compiladorjava;
import Grafico.UICompilador;

/**
 *
 * @author eduardo
 */
public class Main1 {
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UICompilador().setVisible(true);
            }
        });
    }
}
