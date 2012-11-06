/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ares.platform.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
  
/** 
 * http://www.javafrikitutorials.com/2011/10/tutorial-para-manipular-imagenes-en.html
 * @author jzavaleta 
 */  
public class ImageUtils {  
  
    /** 
     * Carga una variable BufferedImage desde un archivo tipo imagen 
     * 
     * @param pathname el archivo de la imagen 
     *  
     * @return BufferedImage con la imagen cargada desde el archivo 
     */  
    public static BufferedImage loadImage(File imageFile) {  
        BufferedImage bufim = null;  
        try {
            bufim = ImageIO.read(imageFile);  
        } catch (IOException ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return bufim;  
    }  
  
    /** 
     * Redimensiona una imagen BufferedImage 
     * 
     * @param bufferedImage la imagen que se desea redimensionar 
     * @param newW el nuevo ancho que se desea redimensionar 
     * @param newH el nuevo alto que se desea redimensionar 
     * 
     * @return BufferedImage redimensionada 
     */  
    public static BufferedImage resize(BufferedImage bufferedImage, int newW, int newH) {  
        int w = bufferedImage.getWidth();  
        int h = bufferedImage.getHeight();  
        BufferedImage bufim = new BufferedImage(newW, newH, bufferedImage.getType());  
        Graphics2D g = bufim.createGraphics();  
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
        g.drawImage(bufferedImage, 0, 0, newW, newH, 0, 0, w, h, null);  
        g.dispose();  
        return bufim;  
    }  
  
    /** 
     * Rota una imagen BufferedImage 
     * 
     * @param bufferedImage la imagen que se desea rotar 
     * @param angle los grados que se desea rotar 
     * 
     * @return BufferedImage rotada 
     */  
    public static BufferedImage rotate(BufferedImage bufferedImage, int angle) {  
        int w = bufferedImage.getWidth();  
        int h = bufferedImage.getHeight();  
        BufferedImage bufim = new BufferedImage(w, h, bufferedImage.getType());  
        Graphics2D g = bufim.createGraphics();  
        g.rotate(Math.toRadians(angle), w / 2, h / 2);  
        g.drawImage(bufferedImage, null, 0, 0);  
        return bufim;  
    }  
  
    /** 
     * Guarda una BufferedImage al disco en formato ".png" o ".jpg" 
     * 
     * @param bufferedImage la imagen que se desea guardar 
     * @param pathname la ruta del archivo en el cual se desea guardar la imagen incluyendo el nombre del archivo y su formato 
     */  
    public static void saveImage(BufferedImage bufferedImage, String pathname) {  

            String format = (pathname.endsWith(".png")) ? "png" : "jpg";  
        try {  
            ImageIO.write(bufferedImage, format, new File(pathname));
        } catch (IOException ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

    }  
  
    /** 
     * Le aplica la transparencia seleccionada a una BufferedImage 
     * 
     * @param bufferedImage la imagen que se desea hacer transparente algun color 
     * @param transparency variable tipo <code>float</code> entre el rango 0.0 - 1.0 que indica el porcentaje de transparencia 
     * 
     * @return BufferedImage con el porcentaje de transparencia seleccionada 
     */  
    public static BufferedImage makeTranslucentImage(BufferedImage bufferedImage, float transparency) {  
        BufferedImage bufim = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TRANSLUCENT);  
        Graphics2D g = bufim.createGraphics();  
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));  
        g.drawImage(bufferedImage, null, 0, 0);  
        g.dispose();  
        return bufim;  
    }  
  
    /** 
     * Hace que el color seleccionado sea transparente en un BufferedImage 
     * 
     * @param bufferedImage la imagen que se desea hacer transparente algun color 
     * @param color el color que se desea hacer transparente 
     * 
     * @return BufferedImage con el color seleccionado transparente 
     */  
    public static BufferedImage makeColorTransparent(BufferedImage bufferedImage, Color color) {  
        BufferedImage bufim = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);  
        Graphics2D g = bufim.createGraphics();  
        g.setComposite(AlphaComposite.Src);  
        g.drawImage(bufferedImage, null, 0, 0);  
        g.dispose();  
        for (int i = 0; i < bufim.getHeight(); i++) {  
            for (int j = 0; j < bufim.getWidth(); j++) {  
                if (bufim.getRGB(j, i) == color.getRGB()) {  
                    bufim.setRGB(j, i, 0x8F1C1C);  
                }  
            }  
        }  
        return bufim;  
    }  
  
    /** 
     * Corta una BufferedImage en filas y/o columnas del mismo tamaÃ±o 
     * 
     * @param bufferedImage la imagen que se desea cortar 
     * @param rows el numero filas en las que se desea cortar la imagen 
     * @param cols el numero de columnas en las que se desea cortar la image 
     * 
     * @return BufferedImare[] devuelve un arreglo tipo BufferedImage con las imagenes cortadas de la imagen original 
     */  
    
    //EDITADO
    public static BufferedImage[] splitImageUnitInformation(BufferedImage bufferedImage, int rows, int cols) {  
        //int w = bufferedImage.getWidth() / cols;  
        //int h = bufferedImage.getHeight() / rows;  
        int num = 0;  
        BufferedImage imgs[] = new BufferedImage[rows * cols]; 
        int w;
        int h;
        int displacementLetters;
        int displacementSquares; 
        
        for (int y = 0; y < rows; y ++){
            displacementLetters = 0;
            displacementSquares = 0;
            for (int x = 0; x < cols; x++)
            {            
                h = 7;
                Graphics2D g = null;
                
                if(y < 110)
                {
                    if(x <= 9){
                    w = 6;
                    imgs[num] = new BufferedImage(w, h, bufferedImage.getType());                
                    //g.drawImage(bufferedImage, 0, 0, w, h, w * x, h * y, w * x + w, h * y + h, null);
                    g = imgs[num].createGraphics(); 
                    g.drawImage(bufferedImage, 0, 0, w, h, w * x, h * y, w * x + w, h * y + h, null);
                    }

                    else if(x == 10){
                    w = 9;
                    imgs[num] = new BufferedImage(w, h, bufferedImage.getType()); 
                    g = imgs[num].createGraphics(); 
                    g.drawImage(bufferedImage, 0, 0, w, h, 65, h * y, 65 + w, h * y + h, null);
                    }

                    else if(x == 11){
                    w = 15;
                    imgs[num] = new BufferedImage(w, h, bufferedImage.getType()); 
                    g = imgs[num].createGraphics(); 
                    g.drawImage(bufferedImage, 0, 0, w, h, 81, h * y, 81 + w, h * y + h, null);
                    }

                    else if(x == 12){
                    w = 5;
                    imgs[num] = new BufferedImage(w, h, bufferedImage.getType()); 
                    g = imgs[num].createGraphics(); 
                    g.drawImage(bufferedImage, 0, 0, w, h, 105, h * y, 105 + w, h * y + h, null);
                    }

                    else if(x == 13){
                    w = 10;
                    imgs[num] = new BufferedImage(w, h, bufferedImage.getType());
                    g = imgs[num].createGraphics(); 
                    g.drawImage(bufferedImage, 0, 0, w, h, 121, h * y, 121 + w, h * y + h, null);
                    }

                    else if(x == 14){
                    w = 15;
                    imgs[num] = new BufferedImage(w, h, bufferedImage.getType()); 
                    g = imgs[num].createGraphics(); 
                    g.drawImage(bufferedImage, 0, 0, w, h, 138, h * y, 138 + w, h * y + h, null);
                    }

                    else if(x == 15){
                    w = 9;
                    imgs[num] = new BufferedImage(w, h, bufferedImage.getType()); 
                    g = imgs[num].createGraphics(); 
                    g.drawImage(bufferedImage, 0, 0, w, h, 160, h * y, 160 + w, h * y + h, null);
                    }

                    else if(x == 16){
                    w = 15;
                    imgs[num] = new BufferedImage(w, h, bufferedImage.getType()); 
                    g = imgs[num].createGraphics(); 
                    g.drawImage(bufferedImage, 0, 0, w, h, 176, h * y, 176 + w, h * y + h, null);
                    }

                    else if(x == 17){
                    w = 20;
                    imgs[num] = new BufferedImage(w, h, bufferedImage.getType()); 
                    g = imgs[num].createGraphics(); 
                    g.drawImage(bufferedImage, 0, 0, w, h, 193, h * y, 193 + w, h * y + h, null);
                    }

                    else if(x == 18){
                    w = 20;
                    imgs[num] = new BufferedImage(w, h, bufferedImage.getType()); 
                    g = imgs[num].createGraphics(); 
                    g.drawImage(bufferedImage, 0, 0, w, h, 212, h * y, 212 + w, h * y + h, null);
                    }

                    else if(x == 19){
                    w = 4;
                    imgs[num] = new BufferedImage(w, h, bufferedImage.getType()); 
                    g = imgs[num].createGraphics(); 
                    g.drawImage(bufferedImage, 0, 0, w, h, 232, h * y, 232 + w, h * y + h, null);
                    }

                    else if(x >= 20){
                    w = 3;
                    imgs[num] = new BufferedImage(w, h, bufferedImage.getType()); 
                    g = imgs[num].createGraphics(); 
                    g.drawImage(bufferedImage, 0, 0, w, h, 238 + displacementLetters, h * y, 238 + displacementLetters + w, h * y + h, null);
                    displacementLetters += 3;
                    }
                   
                    g.dispose();
                    num++;
                }
                
                else
                {
                    if(y == 110)
                    {
                        w = 5;
                        h = 5;
                        imgs[num] = new BufferedImage(w, h, bufferedImage.getType()); 
                        g = imgs[num].createGraphics(); 
                        g.drawImage(bufferedImage, 0, 0, w, h, 10 + displacementSquares, 7 * y, 10 + displacementSquares + w, 7 * y + h, null);
                        displacementSquares += 5;
                        g.dispose();
                        num++;
                    }
                    
                    
                }
                
                
            }
        }

        /*for (int y = 0; y < rows; y++) {  
            for (int x = 0; x < cols; x++) {  
                imgs[num] = new BufferedImage(w, h, bufferedImage.getType());  
                // Tell the graphics to draw only one block of the image  
                Graphics2D g = imgs[num].createGraphics();  
                g.drawImage(bufferedImage, 0, 0, w, h, w * x, h * y, w * x + w, h * y + h, null);  
                g.dispose();  
                num++;  
            }  
        }*/  
        return imgs;  
    }  
    
  
    public static BufferedImage[] splitImageHexagons(BufferedImage bufferedImage, int rows, int cols) {  
        int w = bufferedImage.getWidth() / cols;  
        int h = bufferedImage.getHeight() / rows;  
        int num = 0;  
        BufferedImage imgs[] = new BufferedImage[rows * cols];  
        for (int x = 0; x < cols; x++) {  
            for (int y = 0; y < rows; y++) {  
                imgs[num] = new BufferedImage(w, h, bufferedImage.getType());  
                // Tell the graphics to draw only one block of the image  
                Graphics2D g = imgs[num].createGraphics();  
                g.drawImage(bufferedImage, 0, 0, w, h, w * x, h * y, w * x + w, h * y + h, null);  
                g.dispose();  
                num++;  
            }  
        }  
        return imgs;  
    }  
    
    public static BufferedImage[] splitImageUnits(BufferedImage bufferedImage, int rows, int cols) {  
        int w = bufferedImage.getWidth() / cols;  
        int h = bufferedImage.getHeight() / rows;
        int num = 0;  
        BufferedImage imgs[] = new BufferedImage[w * h];  
        for (int x = 0; x < rows; x++) {  
            for (int y = 0; y < cols; y++) {  
                imgs[num] = new BufferedImage(w, h, bufferedImage.getType());  
                // Tell the graphics to draw only one block of the image  
                Graphics2D g = imgs[num].createGraphics();  
                //g.drawImage(bufferedImage, 0, 0, w, h, w * y, h * x, w * y + w, h * x + h, null); 
                g.drawImage(bufferedImage, 0, 0, w, h, w * y, h * x, w * y + w, h * x + h, null);
                g.dispose();  
                num++;  
            }  
        }  
        return imgs;  
    } 
    /** 
     * Gira una imagen en sentido vertical 
     * 
     * @param bufferedImage la imagen que se desea girar 
     * 
     * @return BufferedImage es la imagen girada 
     */  
    public static BufferedImage flipVertical(BufferedImage bufferedImage) {  
        int w = bufferedImage.getWidth();  
        int h = bufferedImage.getHeight();  
        BufferedImage bufim = new BufferedImage(w, h, bufferedImage.getColorModel().getTransparency());  
        Graphics2D g = bufim.createGraphics();  
        g.drawImage(bufferedImage, 0, 0, w, h, 0, h, w, 0, null);  
        g.dispose();  
        return bufim;  
    }  
  
    /** 
     * Gira una imagen en sentido horizontal 
     * 
     * @param bufferedImage la imagen que se desea girar 
     * 
     * @return BufferedImage es la imagen girada 
     */  
    public static BufferedImage flipHorizontal(BufferedImage bufferedImage) {  
        int w = bufferedImage.getWidth();  
        int h = bufferedImage.getHeight();  
        BufferedImage bufim = new BufferedImage(w, h, bufferedImage.getType());  
        Graphics2D g = bufim.createGraphics();  
        g.drawImage(bufferedImage, 0, 0, w, h, w, 0, 0, h, null);  
        g.dispose();  
        return bufim;  
    }  
    
} 