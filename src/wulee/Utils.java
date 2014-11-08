/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wulee;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Vu
 */
public class Utils {
    public static String convertStringToBinary(String s){
        byte[] bytes = s.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary.toString();
    }
    public static String convertBinaryToString (String s){
        StringBuilder text = new StringBuilder();
        for (int i=0; i<s.length();i+=8){
            String split = s.subSequence(i, i+8).toString();
            int code = Integer.parseInt(split, 2);
            text.append(Character.toChars(code));
        }
        return text.toString();
        
    }
    // Chuyen doi tu file nhi phan sang ma tran [0, 1] voi diem trang tuong ung 1, diem den tuong ung 0
    public static Matrix getMatrixFromImage(File input) throws IOException{
        BufferedImage buff = null;
        buff = ImageIO.read(input);
        int rows = buff.getHeight();
        int columns = buff.getWidth();
        Matrix image = new Matrix(rows, columns);
        for (int i=0;i<rows; i++){
            for (int j=0; j<columns;j++){
                //System.out.println(Integer.toString(i)+"    "+Integer.toString(j)+"    "+buff.getRGB(j, i));
                if (buff.getRGB(j, i)==-1) // Diem anh mau trang
                    image.setValue(i, j, 1);
                else if (buff.getRGB(j, i)==-16777216) // Diem anh mau den
                    image.setValue(i, j, 0);
            }
        }
        return image;
    }
    // Ghi de du lieu anh bang du lieu ma tran
      public static void writeBinaryImage(Matrix m, File input) throws IOException{
        int width = m.getColumns();
        int height = m.getRows();
        BufferedImage buff = ImageIO.read(input);
        for (int i=0; i<width; i++){
            for (int j=0; j<height; j++){
                if (m.getValue(j, i)==0){
                    buff.setRGB(i, j, -16777216);
                }else if (m.getValue(j, i)==1){
                    buff.setRGB(i, j, -1);
                }
            }
        }
        ImageIO.write(buff, "png", input);
    }
    // Copy file
    public static void copyFile(File input, File output) throws IOException{
        FileInputStream reader = new FileInputStream(input);
        FileOutputStream writer = new FileOutputStream(output);
        int c;
        while ((c=reader.read())!=-1)
            writer.write(c);
        reader.close();
        writer.close();
    }
}
