package gm.tienda_libros.vista;


import gm.tienda_libros.modelo.Libro;
import gm.tienda_libros.servicio.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class LibroForm extends JFrame {
    //inyectar dependencia spring por constructor
    LibroServicio libroServicio;
    private JPanel panel;
    private JTable tablaLibros;
    private JTextField idTexto;
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
        tablaLibros.addMouseListener(new MouseAdapter() {
            @Override //Cuando damos clic sobre uno de los registros en la tabla
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarLibroSeleccionado();
            }
        });
        modificarButton.addActionListener(e -> modificarLibro());
        eliminarButton.addActionListener(e -> eliminarLibro());
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

    private void cargarLibroSeleccionado(){
        //Los indices de las columnas inician en 0
        var renglon = tablaLibros.getSelectedRow();//para saber cual renglon fue el que se presiono
        if(renglon != -1){ //Regrresa -1 si no se selecciono ningun registro
            String idLibro =
                    tablaLibros.getModel().getValueAt(renglon, 0).toString();
            idTexto.setText(idLibro);
            String nombreLibro = tablaLibros.getModel().getValueAt(renglon, 1).toString(); //Columna 1
            libroTexto.setText(nombreLibro);
            String autor = tablaLibros.getModel().getValueAt(renglon, 2).toString();
            autorTexto.setText(autor);
            String precio = tablaLibros.getModel().getValueAt(renglon, 3).toString();
            precioTexto.setText(precio);
            String existencias = tablaLibros.getModel().getValueAt(renglon, 4).toString();
            existenciasTexto.setText(existencias);
        }
    }

    private void modificarLibro(){
        //Cuando se selecciona el libro  en la tabla ya tiene un id respectivo
        if(this.idTexto.getText().equals("")){
            mostrarMensaje("Debe seleccionar un libro ...");
        }
        else{
            //Verificamos que el nombre del libro no sea nulo
            if(libroTexto.getText().equals("")){
                mostrarMensaje("Proporciona el nombre del Libro...");
                libroTexto.requestFocusInWindow();//Para que se seleccione el texto y pueda escribir  en el nombre del libro
                return; //retornamos el control para que se muestre el formulario y el usuario pueda proporcionar esta info
            }
            //Si es dif de nulo Llenamos e objeto a actualizar
            int idLibro = Integer.parseInt(idTexto.getText());
            var nombreLibro = libroTexto.getText();
            var autor = autorTexto.getText();
            var precio = Double.parseDouble(precioTexto.getText());
            var existencias = Integer.parseInt(existenciasTexto.getText());
            var libro = new Libro(idLibro, nombreLibro, autor, precio, existencias);
            libroServicio.guardarLibro(libro);
            mostrarMensaje("Libro Actualizado");
            limpiarFormulario();
            listarLibros(); //se recarge el libro en la tabla
        }
    }

    private void eliminarLibro(){
        //vamos   a saber cual renglo se selecciono
        var renglon = tablaLibros.getSelectedRow();
        if(renglon != -1){
            String idLibro =tablaLibros.getModel().getValueAt(renglon, 0).toString(); //Obt id es la columna 0
            var libro = new Libro();
            //establecemos el libro con el id recuperado
            libro.setIdLibro(Integer.parseInt(idLibro));//convertimos a un entero
            libroServicio.eliminarLibro(libro);
            mostrarMensaje("Libro" + idLibro + " Eliminado.");
            limpiarFormulario();
            listarLibros();
        }
        else{
            mostrarMensaje("Debe seleccionar un libro ...");
        }
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
        //Creamos el elemento idTexto oculto
        idTexto = new JTextField("");
        idTexto.setVisible(false);

        this.tablaModeloLibros = new DefaultTableModel(0 , 5){
            @Override // Cuando se crea un libro en la tabla estos no son editables
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] cabeceros = {"Id", "Libro", "Autor", "Precio", "Existencias"};
        this.tablaModeloLibros.setColumnIdentifiers(cabeceros);
        //Instanciar el objeto JTable
        this.tablaLibros = new JTable(tablaModeloLibros);
        //Evitar que se seleccionen varios registros
        tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
