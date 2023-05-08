import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaControles extends JFrame {

    VentanaPrincipal VP;

    VentanaControles(VentanaPrincipal VP){
        this.VP = VP;
        this.setSize(500, 80);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        inicializaControles();
    }

    public void inicializaControles(){

        GridLayout gl = new GridLayout(2, 5);
        this.setLayout(gl);

        JLabel lEscalaEjeX = new JLabel("Escala X:");
        JLabel lEscalaEjeY = new JLabel("Escala Y:");
        JLabel lDesplX = new JLabel("%Despl. X:");
        JLabel lDesplY1 = new JLabel("%Despl. Y1:");
        JLabel lDesplY2 = new JLabel("%Despl. Y2:");
        JLabel lLissajous = new JLabel("Lissajous");
        JTextField tfEscalaEjeX = new JTextField(String.valueOf(VP.lienzo.nanoSegundosPorPixelX));
        JTextField tfEscalaEjeY = new JTextField(String.valueOf(VP.lienzo.pixelesPorVoltY));
        JTextField tfDesplX = new JTextField(String.valueOf(VP.lienzo.desfaseX));
        JTextField tfDesplY1 = new JTextField(String.valueOf(VP.lienzo.desfaseY1));
        JTextField tfDesplY2 = new JTextField(String.valueOf(VP.lienzo.desfaseY2));
        JButton bModoLissajous = new JButton("Inactivo");

        lEscalaEjeX.setFont(new Font("Courier New", Font.PLAIN, 12));
        lEscalaEjeY.setFont(new Font("Courier New", Font.PLAIN, 12));
        lDesplX.setFont(new Font("Courier New", Font.PLAIN, 12));
        lDesplY1.setFont(new Font("Courier New", Font.PLAIN, 12));
        lDesplY2.setFont(new Font("Courier New", Font.PLAIN, 12));
        lLissajous.setFont(new Font("Courier New", Font.PLAIN, 12));
        tfEscalaEjeX.setFont(new Font("Courier New", Font.PLAIN, 12));
        tfEscalaEjeY.setFont(new Font("Courier New", Font.PLAIN, 12));
        tfDesplX.setFont(new Font("Courier New", Font.PLAIN, 12));
        tfDesplY1.setFont(new Font("Courier New", Font.PLAIN, 12));
        tfDesplY2.setFont(new Font("Courier New", Font.PLAIN, 12));
        bModoLissajous.setFont(new Font("Courier New", Font.PLAIN, 12));

        //Colocar
        {
            this.add(lEscalaEjeX);
            this.add(lEscalaEjeY);
            this.add(lDesplX);
            this.add(lDesplY1);
            this.add(lDesplY2);
            this.add(lLissajous);
            this.add(tfEscalaEjeX);
            this.add(tfEscalaEjeY);
            this.add(tfDesplX);
            this.add(tfDesplY1);
            this.add(tfDesplY2);
            this.add(bModoLissajous);
        }

        //Eventos
        {
            tfEscalaEjeX.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        VP.lienzo.nanoSegundosPorPixelX = Double.parseDouble(tfEscalaEjeX.getText().replace("_", ""));
                    }catch (NumberFormatException err){
                        VP.lienzo.nanoSegundosPorPixelX = 1000_000.0;
                        tfEscalaEjeX.setText("1000_000.0");
                    }
                    VP.lienzo.pintaFondo();
                    VP.requestFocus();
                }
            });

            tfEscalaEjeY.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        VP.lienzo.pixelesPorVoltY = Double.parseDouble(tfEscalaEjeY.getText().replace("_", ""));
                        VP.lienzo.pixelesPorVoltX = Double.parseDouble(tfEscalaEjeY.getText().replace("_", ""));
                    }catch (NumberFormatException err){
                        VP.lienzo.pixelesPorVoltX = 1.0;
                        tfEscalaEjeY.setText("1.0");
                    }
                    VP.lienzo.pintaFondo();
                    VP.requestFocus();
                }
            });

            tfEscalaEjeY.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        VP.lienzo.pixelesPorVoltY = Double.parseDouble(tfEscalaEjeY.getText().replace("_", ""));
                        VP.lienzo.pixelesPorVoltX = Double.parseDouble(tfEscalaEjeY.getText().replace("_", ""));
                    }catch (NumberFormatException err){
                        VP.lienzo.pixelesPorVoltX = 1.0;
                        tfEscalaEjeY.setText("1.0");
                    }
                    VP.lienzo.pintaFondo();
                    VP.requestFocus();
                }
            });

            tfDesplX.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        VP.lienzo.desfaseX = Integer.parseInt(tfDesplX.getText().replace("_", ""));
                    }catch (NumberFormatException err){
                        VP.lienzo.desfaseX = 0;
                        tfDesplX.setText("0");
                    }
                    VP.lienzo.pintaFondo();
                    VP.requestFocus();
                }
            });

            tfDesplY1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        VP.lienzo.desfaseY1 = Integer.parseInt(tfDesplY1.getText().replace("_", ""));
                    }catch (NumberFormatException err){
                        VP.lienzo.desfaseY1 = 0;
                        tfDesplY1.setText("0");
                    }
                    VP.lienzo.pintaFondo();
                    VP.requestFocus();
                }
            });

            tfDesplY2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        VP.lienzo.desfaseY2 = Integer.parseInt(tfDesplY2.getText().replace("_", ""));
                    }catch (NumberFormatException err){
                        VP.lienzo.desfaseY2 = 0;
                        tfDesplY2.setText("0");
                    }
                    VP.lienzo.pintaFondo();
                    VP.requestFocus();
                }
            });

            bModoLissajous.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    VP.lienzo.modoLissajous = !VP.lienzo.modoLissajous;
                    if(!VP.lienzo.modoLissajous){
                        bModoLissajous.setText("Inactivo");
                    }
                    if(VP.lienzo.modoLissajous){
                        bModoLissajous.setText("Activo");
                    }
                    VP.lienzo.pintaFondo();
                    VP.requestFocus();
                }
            });
        }
        this.revalidate();
    }
}
