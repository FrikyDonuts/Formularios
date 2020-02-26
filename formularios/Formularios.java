package formularios;

import java.awt.Component;
import java.awt.List;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 * Clase para gestionar varios componentes de un JPanel a la vez.
 * @author neoWavila
 * @version 2.0.0
 */
public class Formularios 
{
    /**
     * Devuelve el contenido y el nombre de los componentes del panel
     * Si el panel tiene dentro otros paneles tambien coge el contenido de este.
     * @param panel JPanel que contiene los componentes.
     * @param vacios true/false en funcion de si quieres recibir tambien los
     * campos vacios.
     * @return String array [numeroComponentes con nombre][2] en formato:
     * info[n][0] = nombreComponente;
     * info[n][1] = contenidoComponente;
     */
    public static String[][] getInfo(JPanel panel, boolean vacios)
    {
        List listaCampos = new List();
        List listaValores = new List();
        String valor;
        String campo;
        
        Component[] componentes = panel.getComponents();
        for (Component componente : componentes) 
        {
            String clase = componente.getClass().toString();
            
            if(clase.equals("class javax.swing.JPanel"))
            {
                String[][] infoPanel = getInfo((JPanel) componente, vacios);
                for (int i = 0; i < infoPanel.length; i++)
                {
                    String campoPanel = infoPanel[i][0];
                    String valorPanel = infoPanel[i][1];
                    listaCampos.add(campoPanel);
                    listaValores.add(valorPanel);
                }
            }
            
            campo = componente.getName();
            valor = null;
            
            if(campo!=null)
            {
                if(clase.equals("class javax.swing.JTextField"))
                    valor = ((JTextField)componente). getText();
                else if(clase.equals("class javax.swing.JComboBox"))
                    valor = ((JComboBox)componente).getSelectedItem().toString();
                else if(clase.equals("class javax.swing.JTextArea"))
                    valor = ((JTextArea)componente).getText();
                else if(clase.equals("class javax.swing.JLabel"))
                    valor = ((JLabel)componente).getText();
                else if(clase.equals("class javax.swing.JCheckBox"))
                    valor = String.valueOf(((JCheckBox)componente).isSelected());         
                
                if(valor!=null)
                {
                    if(valor.isEmpty() && vacios || !valor.isEmpty())
                    {
                        listaValores.add(valor);
                        listaCampos.add(campo);
                    }
                }
            }
        }
        
        String[][] arrayFinal = new String [listaValores.getItemCount()][2];
            
        for (int i = 0; i < arrayFinal.length; i++) 
        {
            arrayFinal[i][0]=listaCampos.getItem(i);
            arrayFinal[i][1]=listaValores.getItem(i);
        }

        return arrayFinal;
    }
    /**
     * Devuelve el contenido y el nombre de los componentes del panel
     * Si el panel tiene dentro otros paneles tambien coge el contenido de este.
     * @param panel JPanel que contiene los componentes.
     * @return String array [numeroComponentes con nombre][2] en formato:
     * info[n][0] = nombreComponente;
     * info[n][1] = contenidoComponente;
     */
    public static String[][] getInfo(JPanel panel)
    {
        List listaCampos = new List();
        List listaValores = new List();
        String valor;
        String campo;
        
        Component[] componentes = panel.getComponents();
        for (Component componente : componentes) 
        {
            String clase = componente.getClass().toString();
            
            if(clase.equals("class javax.swing.JPanel"))
            {
                System.out.println("a punto de recursivar");
                String[][] infoPanel = getInfo((JPanel) componente);
                System.out.println("recursivasion terminada");
                for (int i = 0; i < infoPanel.length; i++)
                {
                    String campoPanel = infoPanel[i][0];
                    String valorPanel = infoPanel[i][1];
                    listaCampos.add(campoPanel);
                    listaValores.add(valorPanel);
                }
            }
            
            campo = componente.getName();
            valor = null;
            
            if(campo!=null)
            {
                if(clase.equals("class javax.swing.JTextField"))
                    valor = ((JTextField)componente). getText();
                else if(clase.equals("class javax.swing.JComboBox"))
                    valor = ((JComboBox)componente).getSelectedItem().toString();
                else if(clase.equals("class javax.swing.JTextArea"))
                    valor = ((JTextArea)componente).getText();
                else if(clase.equals("class javax.swing.JLabel"))
                    valor = ((JLabel)componente).getText();
                else if(clase.equals("class javax.swing.JCheckBox"))
                    valor = String.valueOf(((JCheckBox)componente).isSelected());                
                if(valor!=null)
                {
                    listaValores.add(valor);
                    listaCampos.add(campo);
                }
            }
        }
        
        String[][] arrayFinal = new String [listaValores.getItemCount()][2];
            
        for (int i = 0; i < arrayFinal.length; i++) {
            arrayFinal[i][0]=listaCampos.getItem(i);
            arrayFinal[i][1]=listaValores.getItem(i);
        }

        return arrayFinal;
    }
    /**
     * Modifica el texto de los componentes del panel en funcion del array
     * infoFormulario[n][0] = nombre del formulario a cambiar
     * infoFormulario[n][i] = Texto a colocar en ese componente.
     * @param panel JPanel que contiene los componentes a modificar
     * @param infoFormulario String[][] array que contiene la info.
     */
    public static void setInfo(JPanel panel, String[][] infoFormulario)
    {   
        Component[] componentes = panel.getComponents();
        
        for (Component componente : componentes) 
        {
            String clase = componente.getClass().toString();

            if(clase.equals("class javax.swing.JPanel"))
            {
                setInfo((JPanel) componente, infoFormulario);
            }
            else
            {
                String nombre = componente.getName();
                if(nombre != null)
                {
                    for (int i = 0; i < infoFormulario.length; i++) 
                    {
                        String nombreCampo = infoFormulario[i][0];
                        String texto = infoFormulario[i][1];

                        if(nombre.equals(nombreCampo))
                        {
                            setComponentText(componente, texto);
                            break;
                        }
                    }
                }
            }
        }
    }
    /**
     * Modifica el texto del componente sin importar de que tipo sea,
     * los boolean solo pueden modificarse a true/false (por dfcto: false).
     * @param componente Component a modificar.
     * @param texto texto que deseamos poner.
     * @return false en caso de errores.
     */
    public static boolean setComponentText(Component componente, String texto)
    {
        String clase = componente.getClass().toString();
        String nombre = componente.getName();
        if(nombre != null)
        {
            switch (clase) {
                case "class javax.swing.JTextField":
                    ((JTextField)componente).setText(texto);
                    return true;
                case "class javax.swing.JComboBox":
                    if(texto.equals(""))
                        ((JComboBox)componente).setSelectedIndex(0);
                    else
                        ((JComboBox)componente).setSelectedItem(texto);
                    return true;
                case "class javax.swing.JTextArea":
                    ((JTextArea)componente).setText(texto);
                    return true;
                case "class javax.swing.JCheckBox":
                    if(texto.equalsIgnoreCase("true"))
                        ((JCheckBox)componente).setSelected(true);
                    else
                        ((JCheckBox)componente).setSelected(false);
                    return true;
                case "class javax.swing.JLabel":
                    ((JLabel)componente).setText(texto);
                    return true;
                default:
                    return false;
            }
        }
        else
            return false;
    }
    /**
     * Devuelve el texto contenido en un componente.
     * @param componente componente que tiene el texto.
     * @return String con el texto.
     */
    public static String getComponentText(Component componente)
    {
        String clase = componente.getClass().toString();
        
        if(clase.equals("class javax.swing.JTextField"))
            return ((JTextField)componente). getText();
        else if(clase.equals("class javax.swing.JComboBox"))
            return ((JComboBox)componente).getSelectedItem().toString();
        else if(clase.equals("class javax.swing.JTextArea"))
            return ((JTextArea)componente).getText();
        else if(clase.equals("class javax.swing.JCheckBox"))
            return String.valueOf(((JCheckBox)componente).isSelected());
        else if(clase.equals("class javax.swing.JLabel"))
            return ((JLabel)componente).getText();
        else
            return null;
    }
    /**
     * Pone todos los componentes del formulario a vacio, false y primer elemento.
     * @param panel JPanel que contiene los componentes.
     */
    public static void limpia(JPanel panel)
    {
        Component[] componentes = panel.getComponents();
        for (Component componente : componentes)
        {
            String clase = componente.getClass().toString();
            if(clase.equals("class javax.swing.JPanel"))
            {
                limpia((JPanel) componente);
            }
            else
            {
                setComponentText(componente, "");
            }
        }
    }
}
