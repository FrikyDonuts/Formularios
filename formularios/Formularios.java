package formularios;

import java.awt.Component;
import java.awt.List;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Clase para gestionar varios componentes de un JPanel a la vez.
 * @author neoWavila
 * @version 1.0.0
 */
public class Formularios 
{
    /**
     * Devuelve el contenido y el nombre de los componentes del panel
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
     * Rellena los componentes de un JPanel. Para diferenciar los componentes 
     * este metodo usa los nombres de los mismos, por eso el array tiene que tener:
     * las dimensiones: [2][nComponentes]
     * formato: [0][n] = Nombre; [1][n] = Texto;
     * @param panel JPanel que contiene los componentes.
     * @param infoFormulario Array que contiene los nombres de los componentes y
     * el texto a poner.
     */
    public static void setInfo(JPanel panel, String[][] infoFormulario)
    {   
        Component[] componentes = panel.getComponents();
        
        for (int i = 0; i < infoFormulario.length; i++) 
        {
            String nombreCampo = infoFormulario[i][0];
            String texto = infoFormulario[i][1];
            for (int j = 0; j < componentes.length; j++) 
            {
                Component componente = componentes[j];
                String nombre = componente.getName();
                if(nombre != null)
                {
                    if(nombre.equals(nombreCampo))
                    {
                        setComponentText(componente, texto);
                        break;
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
        switch (clase) {
            case "class javax.swing.JTextField":
                ((JTextField)componente).setText(texto);
                return true;
            case "class javax.swing.JComboBox":
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
            default:
                return false;
        }
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
        else
            return null;
    }
    /**
     * Pone todos los componentes del formulario a vacio o false.
     * @param panel JPanel que contiene los componentes.
     */
    static void limpia(JPanel panel)
    {
        Component[] componentes = panel.getComponents();
        for (Component componente : componentes)
            setComponentText(componente, "");
    }
}
