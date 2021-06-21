/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hamming_huffman;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.ImageIcon;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingConstants;

/**
 *
 * @author Inalen
 */
public class Principal extends javax.swing.JFrame {

    /**
     * Creates new form principal
     */
    public Principal() {

        initComponents();
        this.panel1.setContentType("text/html");
        this.panel2.setContentType("text/html");
        this.panel3.setContentType("text/html");
        llenarListaArchivos(1);
        llenarListaArchivos(2);
    }

    public void llenarListaArchivos(int op) {//devuelve una lista con los nombres de los archivos en el directorio principal del proyecto
        String[] archivosPermitidos = {".HA", ".HE"};
        if (op == 1) {
            archivos_directorio.removeAllItems();
        } else {
            archivos_directorio2.removeAllItems();
        }
        File directorio = new File("./");
        File[] archivos = null;
        if (directorio.exists()) {
            archivos = directorio.listFiles();
        }
        int i;

        for (i = 0; i < archivos.length; i++) {
            if (op == 1 && ("" + archivos[i]).contains(".txt")) {
                archivos_directorio.addItem("" + archivos[i]);
            } else {
                int j;
                for (j = 0; j < archivosPermitidos.length; j++) {
                    if (("" + archivos[i]).contains(archivosPermitidos[j])) {
                        archivos_directorio2.addItem("" + archivos[i]);
                        break;
                    }
                }
            }
        }

    }

    public void llenarPanel1(String nombre, String text_panel) {
        archivo_panel1.setText("Nombre Archivo: " + nombre);
        this.panel1.setText(text_panel);
    }

    public void llenarPanel2(String nombre, String text_panel) {
        archivo_panel2.setText("Nombre Archivo: " + nombre);
        this.panel2.setText(text_panel);
    }

    public void llenarPanel3(String nombre, String text_panel) {
        archivo_panel3.setText("Nombre Archivo: " + nombre);
        this.panel3.setText(text_panel);
    }

    public void readFileToText(boolean arreglar) {
        try {

            String nombreArchivo = Hamming.getNombre_archivo();
            String ext = Hamming.getExtension_archivo();
            byte[] bytes = Files.readAllBytes(Paths.get(nombreArchivo + ext));
            byte[] bytesTXT = Files.readAllBytes(Paths.get(nombreArchivo + ".txt"));
            String textoIzq = new String(bytes);
            File archivo2 = new File(nombreArchivo + Hamming.getExtension_nuevo_archivo());
            OutputStream bw = new FileOutputStream(archivo2);
            byte[] aEscribir;

            if (ext.equals(".HUF") || ext.equals(".HEF")) {
                aEscribir = decodificar(bytes, arreglar, true);
                byte[] tabla = Files.readAllBytes(Paths.get("./" + nombreArchivo + (ext.equals(".HUF") ? ".TH" : ".TE") + ext.substring(ext.length() - 1)));
                File archivo3 = new File("./" + nombreArchivo + (ext.equals(".HUF") ? ".TUF" : ".TEF"));
                OutputStream os = new FileOutputStream(archivo3);
                tabla = decodificar(tabla, arreglar, false);
                os.write(tabla);
            } else {
                aEscribir = decodificar(bytes, arreglar, false);
            }

            String textoDer = new String(aEscribir);
            bw.write(aEscribir);
            bw.close();
            llenarPanel1(Hamming.getNombre_archivo() + ".txt", new String(bytesTXT));
            llenarPanel2(nombreArchivo + ext, textoIzq);
            llenarPanel3(archivo2.getPath(), textoDer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] decodificar(byte[] codigo, boolean arreglar, boolean huffman) {
        String auxString = "";
        String aEscribir;
        int hasta = Hamming.getTamanio_bloque() - 1;
        int desde = 0;
        byte[] bytesAux = codigo;
        while (hasta / 8 < bytesAux.length) {//Si bloque <  final del String
            boolean[] arr = Hamming.bytesDelBloqueABoolean(bytesAux, desde, hasta);
            desde = hasta + 1;
            hasta += Hamming.getTamanio_bloque();
            arr = Hamming.getInfo(arr, arreglar);
            auxString += Hamming.toString(arr);
        }
        if (desde / 8 < bytesAux.length) {
            boolean[] arr = Hamming.bytesDelBloqueABoolean(bytesAux, desde, hasta);
            arr = Hamming.getInfo(arr, arreglar);
            auxString += Hamming.toString(arr);
        }
        ArrayList<Byte> bytesDinamico = new ArrayList<>();
        byte aux;
        while (8 < auxString.length()) {
            aEscribir = auxString.substring(0, 8);
            auxString = auxString.substring(8);
            aux = (byte) Integer.parseInt(aEscribir, 2);
            if (aux != 0 || huffman) {
                bytesDinamico.add(aux);
            }
        }
        return Nodo.getArregloDeBytes(bytesDinamico);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contenedor = new javax.swing.JPanel();
        menu_lateral = new javax.swing.JPanel();
        logo_menu = new javax.swing.JLabel();
        panel_botones = new javax.swing.JPanel();
        proteger = new javax.swing.JButton();
        introd_error = new javax.swing.JButton();
        desproteger = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        tamaniobloque = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        archivos_directorio2 = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        archivos_directorio = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cuerpo_panel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panel2 = new javax.swing.JEditorPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        panel3 = new javax.swing.JEditorPane();
        archivo_panel2 = new javax.swing.JLabel();
        archivo_panel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        panel1 = new javax.swing.JEditorPane();
        archivo_panel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        contenedor.setBackground(new java.awt.Color(255, 255, 255));
        contenedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));

        menu_lateral.setBackground(new java.awt.Color(255, 255, 255));
        menu_lateral.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        logo_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/images/logo_chico.png"))); // NOI18N

        panel_botones.setBackground(new java.awt.Color(255, 255, 255));

        proteger.setText("Proteger");
        proteger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                protegerActionPerformed(evt);
            }
        });

        introd_error.setText("Introducir Error");
        introd_error.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                introd_errorActionPerformed(evt);
            }
        });

        desproteger.setText("Desproteger");
        desproteger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                desprotegerActionPerformed(evt);
            }
        });

        jLabel3.setText("Tamaño Bloque");

        tamaniobloque.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "16bits", "2048 bits", "16384 bits" }));
        tamaniobloque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tamaniobloqueActionPerformed(evt);
            }
        });

        jLabel4.setText("Elegir Archivo");

        archivos_directorio2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "HAX", "HEX" }));
        archivos_directorio2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archivos_directorio2ActionPerformed(evt);
            }
        });

        archivos_directorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archivos_directorioActionPerformed(evt);
            }
        });

        jLabel1.setText("Elegir Archivo");

        jLabel2.setText("a cargar");

        javax.swing.GroupLayout panel_botonesLayout = new javax.swing.GroupLayout(panel_botones);
        panel_botones.setLayout(panel_botonesLayout);
        panel_botonesLayout.setHorizontalGroup(
            panel_botonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2)
            .addComponent(jSeparator3)
            .addGroup(panel_botonesLayout.createSequentialGroup()
                .addGroup(panel_botonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_botonesLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tamaniobloque, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_botonesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_botonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_botonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_botonesLayout.createSequentialGroup()
                        .addGroup(panel_botonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_botonesLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(archivos_directorio2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_botonesLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(desproteger, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_botonesLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(archivos_directorio, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(31, 31, 31))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_botonesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(proteger, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_botonesLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(introd_error, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        panel_botonesLayout.setVerticalGroup(
            panel_botonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_botonesLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(panel_botonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(archivos_directorio)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(panel_botonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tamaniobloque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(proteger, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(introd_error, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(panel_botonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(archivos_directorio2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(desproteger, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        ImageIcon icon =new ImageIcon(getClass().getResource("/interfaz/images/button_azul.png"));
        proteger.setIcon(icon);
        proteger.setMargin(new Insets(0, 0, 0, 0));
        proteger.setIconTextGap(0);
        proteger.setBorderPainted(false);
        proteger.setBorder(null);
        proteger.setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
        proteger.setHorizontalTextPosition(SwingConstants.CENTER);
        proteger.setForeground(Color.WHITE);
        Font font = new Font("Arial", Font.BOLD, 14);
        proteger.setFont(font);

        proteger.setPressedIcon(new ImageIcon(getClass().getResource("/interfaz/images/button_celeste_press.png")));//button-down
        proteger.setRolloverIcon(new ImageIcon(getClass().getResource("/interfaz/images/button_celeste_claro.png")));//button-over
        icon =new ImageIcon(getClass().getResource("/interfaz/images/button_azul.png"));
        introd_error.setIcon(icon);
        introd_error.setMargin(new Insets(0, 0, 0, 0));
        introd_error.setIconTextGap(0);
        introd_error.setBorderPainted(false);
        introd_error.setBorder(null);
        introd_error.setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
        introd_error.setHorizontalTextPosition(SwingConstants.CENTER);
        introd_error.setForeground(Color.WHITE);
        font = new Font("Arial", Font.BOLD, 14);
        introd_error.setFont(font);

        introd_error.setPressedIcon(new ImageIcon(getClass().getResource("/interfaz/images/button_celeste_press.png")));//button-down
        introd_error.setRolloverIcon(new ImageIcon(getClass().getResource("/interfaz/images/button_celeste_claro.png")));//button-over
        icon =new ImageIcon(getClass().getResource("/interfaz/images/button_azul.png")); introd_error.setIcon(icon); introd_error.setMargin(new Insets(0, 0, 0, 0)); introd_error.setIconTextGap(0); introd_error.setBorderPainted(false); introd_error.setBorder(null); introd_error.setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null)); introd_error.setHorizontalTextPosition(SwingConstants.CENTER); introd_error.setForeground(Color.WHITE); font = new Font("Arial", Font.BOLD, 14); introd_error.setFont(font);  introd_error.setPressedIcon(new ImageIcon(getClass().getResource("/interfaz/images/button_celeste_press.png")));//button-down introd_error.setRolloverIcon(new ImageIcon(getClass().getResource("/interfaz/images/button_celeste_claro.png")));//button-over
        desproteger.setIcon(icon);
        desproteger.setMargin(new Insets(0, 0, 0, 0));
        desproteger.setIconTextGap(0);
        desproteger.setBorderPainted(false);
        desproteger.setBorder(null);
        desproteger.setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
        desproteger.setHorizontalTextPosition(SwingConstants.CENTER);
        desproteger.setForeground(Color.WHITE);
        font = new Font("Arial", Font.BOLD, 14);
        desproteger.setFont(font);

        desproteger.setPressedIcon(new ImageIcon(getClass().getResource("/interfaz/images/button_celeste_press.png")));//button-down
        desproteger.setRolloverIcon(new ImageIcon(getClass().getResource("/interfaz/images/button_celeste_claro.png")));//button-over

        javax.swing.GroupLayout menu_lateralLayout = new javax.swing.GroupLayout(menu_lateral);
        menu_lateral.setLayout(menu_lateralLayout);
        menu_lateralLayout.setHorizontalGroup(
            menu_lateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logo_menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_botones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        menu_lateralLayout.setVerticalGroup(
            menu_lateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_lateralLayout.createSequentialGroup()
                .addComponent(logo_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_botones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel2.setEditable(false);
        jScrollPane1.setViewportView(panel2);

        panel3.setEditable(false);
        jScrollPane2.setViewportView(panel3);

        archivo_panel2.setText("Nombre archivo:");

        archivo_panel3.setText("Nombre archivo:");

        panel1.setEditable(false);
        jScrollPane3.setViewportView(panel1);

        archivo_panel1.setText("Nombre archivo:");

        javax.swing.GroupLayout cuerpo_panelLayout = new javax.swing.GroupLayout(cuerpo_panel);
        cuerpo_panel.setLayout(cuerpo_panelLayout);
        cuerpo_panelLayout.setHorizontalGroup(
            cuerpo_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cuerpo_panelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(cuerpo_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(archivo_panel1))
                .addGap(18, 18, 18)
                .addGroup(cuerpo_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(archivo_panel2))
                .addGap(18, 18, 18)
                .addGroup(cuerpo_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(archivo_panel3))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        cuerpo_panelLayout.setVerticalGroup(
            cuerpo_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cuerpo_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cuerpo_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(archivo_panel1)
                    .addComponent(archivo_panel2)
                    .addComponent(archivo_panel3))
                .addGap(13, 13, 13)
                .addGroup(cuerpo_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        javax.swing.GroupLayout contenedorLayout = new javax.swing.GroupLayout(contenedor);
        contenedor.setLayout(contenedorLayout);
        contenedorLayout.setHorizontalGroup(
            contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorLayout.createSequentialGroup()
                .addComponent(menu_lateral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cuerpo_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        contenedorLayout.setVerticalGroup(
            contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menu_lateral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cuerpo_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(contenedor, javax.swing.GroupLayout.PREFERRED_SIZE, 1548, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void protegerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_protegerActionPerformed
        Hamming.setNombre_y_extension_archivo("" + archivos_directorio.getSelectedItem());
        Hamming.setConfiguracion(tamaniobloque.getSelectedIndex(), "HA");
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(Hamming.getNombre_archivo() + Hamming.getExtension_archivo()));//leee todo el archivo, cada posicion contiene el ascii del caracter
            //System.out.println(Integer.toBinaryString((bytes[0]+256)%256)); //dado un byte me devuelve el num en binario
            String texto_protegido = Hamming.generarHamming(bytes);//le pasa el archivo en bytes y la cantidad de bits de informacion

            //lleno el panel con el texto protegido
            llenarPanel1(Hamming.getNombre_archivo() + ".txt", new String(bytes));
            llenarPanel2(Hamming.getNombre_archivo() + Hamming.getExtension_nuevo_archivo(), texto_protegido);
            llenarPanel3(" ", " ");
            llenarListaArchivos(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        proteger.setIcon(new ImageIcon(getClass().getResource("/interfaz/images/button_azul_press.png")));
        introd_error.setIcon(new ImageIcon(getClass().getResource("/interfaz/images/button_azul.png")));
        desproteger.setIcon(new ImageIcon(getClass().getResource("/interfaz/images/button_azul.png")));
    }//GEN-LAST:event_protegerActionPerformed

    private void introd_errorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_introd_errorActionPerformed

        introd_error.setIcon(new ImageIcon(getClass().getResource("/interfaz/images/button_azul_press.png")));
        proteger.setIcon(new ImageIcon(getClass().getResource("/interfaz/images/button_azul.png")));
        desproteger.setIcon(new ImageIcon(getClass().getResource("/interfaz/images/button_azul.png")));
    }//GEN-LAST:event_introd_errorActionPerformed

    private void desprotegerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_desprotegerActionPerformed
        Hamming.setNombre_y_extension_archivo("" + archivos_directorio2.getSelectedItem());
        Hamming.setConfiguracion(Integer.parseInt(Hamming.getExtension_archivo().substring(Hamming.getExtension_archivo().length() - 1)) - 1, "DH");

        readFileToText(false);

// TODO add your handling code here:
        introd_error.setIcon(new ImageIcon(getClass().getResource("/interfaz/images/button_azul.png")));
        proteger.setIcon(new ImageIcon(getClass().getResource("/interfaz/images/button_azul.png")));
        desproteger.setIcon(new ImageIcon(getClass().getResource("/interfaz/images/button_azul_press.png")));
    }//GEN-LAST:event_desprotegerActionPerformed

    private void tamaniobloqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tamaniobloqueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tamaniobloqueActionPerformed

    private void archivos_directorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archivos_directorioActionPerformed
        // TODO add your handling code here:
        try {
            byte[] bytes = Files.readAllBytes(Paths.get("" + archivos_directorio.getSelectedItem()));//cada posicion contiene el ascii del caracter
            llenarPanel1("" + archivos_directorio.getSelectedItem(), new String(bytes));
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_archivos_directorioActionPerformed

    private void archivos_directorio2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archivos_directorio2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_archivos_directorio2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel archivo_panel1;
    private javax.swing.JLabel archivo_panel2;
    private javax.swing.JLabel archivo_panel3;
    private javax.swing.JComboBox<String> archivos_directorio;
    private javax.swing.JComboBox<String> archivos_directorio2;
    private javax.swing.JPanel contenedor;
    private javax.swing.JPanel cuerpo_panel;
    private javax.swing.JButton desproteger;
    private javax.swing.JButton introd_error;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel logo_menu;
    private javax.swing.JPanel menu_lateral;
    private javax.swing.JEditorPane panel1;
    private javax.swing.JEditorPane panel2;
    private javax.swing.JEditorPane panel3;
    private javax.swing.JPanel panel_botones;
    private javax.swing.JButton proteger;
    private javax.swing.JComboBox<String> tamaniobloque;
    // End of variables declaration//GEN-END:variables
}
