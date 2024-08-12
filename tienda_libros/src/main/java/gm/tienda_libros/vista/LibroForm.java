package gm.tienda_libros.vista;


import gm.tienda_libros.modelo.Libro;
import gm.tienda_libros.servicio.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@Component
public class LibroForm extends JFrame {
    //inyectar dependencia spring por constructor
    LibroServicio libroServicio;
    private JPanel panel;
    private JTable tablaLibros;
    private JTextField libroTexto;
    private JTextField autorTexto;
    private JTextField precioTexto;
    private JTextField existenciasTexto;
    private JButton agregarButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    private DefaultTableModel tablaModeloLibros;

    @Autowired
    public LibroForm(LibroServicio libroServicio){
        this.libroServicio = libroServicio;
        iniciarForma(); //metodo que recupera info de la BD

        agregarButton.addActionListener(e -> agregarLibro());
    }

    //especificamos que en inicio cargamos este panel
    private void iniciarForma(){
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(900,700);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension tamanioPantalla = toolkit.getScreenSize();
        int x = (tamanioPantalla.width - getWidth() / 2);
        int y = (tamanioPantalla.height - getHeight() / 2);
        setLocation(x, y);
    }

    private void agregarLibro(){
        //Leer los valores del formulario, aqui validamos si la caja de texto es vacia
        if(libroTexto.equals("")){
            mostrarMensaje("Proporciona el nombre del Libro");
            libroTexto.requestFocusInWindow();
            return;
        }
        var nombreLibro = libroTexto.getText();
        var autor = autorTexto.getText();
        var precio = Double.parseDouble(precioTexto.getText());
        var ecistencias = Integer.parseInt(existenciasTexto.getText());
        //Crear objeto libro
        var libro = new Libro(null, nombreLibro, autor, precio, ecistencias);
        this.libroServicio.guardarLibro(libro);
        mostrarMensaje("Se agrego el Libro");
        limpiarFormulario();
        //Recargar la tabla
        listarLibros();
    }

    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void limpiarFormulario(){
        libroTexto.setText("");
        autorTexto.setText("");
        precioTexto.setText("");
        existenciasTexto.setText("");
    }

    //Este metodo se ejcuta antes del constructor de nuestra clase y lo que realiza es personalizar los componentes del formulario
    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tablaModeloLibros = new DefaultTableModel(0 , 5);
        String[] cabeceros = {"Id", "Libro", "Autor", "Precio", "Existencias"};
        this.tablaModeloLibros.setColumnIdentifiers(cabeceros);
        //Instanciar el objeto JTable
        this.tablaLibros = new JTable(tablaModeloLibros);
        listarLibros();
    }

    public void listarLibros(){
        //Limpiar la tabla
        tablaModeloLibros.setRowCount(0);
        //Posteriormente vamos a obtener todos los libros en nuestra BD
        var libros = libroServicio.listarLibros();
        libros.forEach((libro) ->{
            Object[] renglonLibro = {
                    libro.getIdLibro(),
                    libro.getNombreLibro(),
                    libro.getAutor(),
                    libro.getPrecio(),
                    libro.getExistencias()
            };
            //aqui agregamos a la tabla apartir del objeto, que v a tenr la informacion de cada una d elas columnas
            this.tablaModeloLibros.addRow(renglonLibro);
        });
    }
}
