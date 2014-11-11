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
import java.io.UnsupportedEncodingException;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.BinaryCodec;
import org.apache.commons.codec.binary.StringUtils;
import wulee.exception.NotBinaryImageException;

public class Utils {

    // Chuyen doi tu chuoi ki tu UTF8 sang chuoi nhi phan
    public static String convertStringToBinary(String s) throws ClassNotFoundException{
        if (Class.forName("org.apache.commons.codec.binary.BinaryCodec") ==null || Class.forName("org.apache.commons.codec.binary.StringUtils")==null)
            throw new ClassNotFoundException();
        byte[] bytes = StringUtils.getBytesUtf8(s);
        byte[] binary = BinaryCodec.toAsciiBytes(bytes);
        String result = StringUtils.newString(binary, "UTF-8");
        return result;
    }
    // Chuyen doi tu chuoi nhi phan sang chuoi UTF8
    public static String convertBinaryToString(String s) throws ClassNotFoundException, UnsupportedEncodingException {
        if (Class.forName("org.apache.commons.codec.binary.BinaryCodec") ==null || Class.forName("org.apache.commons.codec.binary.StringUtils")==null)
            throw new ClassNotFoundException();
        byte[] bytes = s.getBytes("UTF-8");
        byte[] utf8 = BinaryCodec.fromAscii(bytes);
        String result = StringUtils.newStringUtf8(utf8);
        return result;
    }

    // Chuyen doi tu file nhi phan sang ma tran [0, 1] voi diem trang tuong ung 1, diem den tuong ung 0
    public static Matrix getMatrixFromImage(File input) throws IOException, NotBinaryImageException {
        BufferedImage buff = null;
        if (ImageIO.read(input) == null) {
            throw new NotBinaryImageException();
        } else {
            buff = ImageIO.read(input);
        }
        if (buff.getType() != BufferedImage.TYPE_BYTE_BINARY) {
            throw new NotBinaryImageException();
        }
        int rows = buff.getHeight();
        int columns = buff.getWidth();
        Matrix image = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (buff.getRGB(j, i) == -1) // Diem anh mau trang
                {
                    image.setValue(i, j, 1);
                } else if (buff.getRGB(j, i) == -16777216) // Diem anh mau den
                {
                    image.setValue(i, j, 0);
                }
            }
        }
        return image;
    }

    // Ghi de du lieu anh bang du lieu ma tran
    public static void writeBinaryImage(Matrix m, File input) throws IOException {
        int width = m.getColumns();
        int height = m.getRows();
        BufferedImage buff = ImageIO.read(input);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (m.getValue(j, i) == 0) {
                    buff.setRGB(i, j, -16777216);
                } else if (m.getValue(j, i) == 1) {
                    buff.setRGB(i, j, -1);
                }
            }
        }
        ImageIO.write(buff, "png", input);
    }

    // Copy file
    public static void copyFile(File input, File output) throws IOException {
        FileInputStream reader = new FileInputStream(input);
        FileOutputStream writer = new FileOutputStream(output, Boolean.FALSE);
        int c;
        while ((c = reader.read()) != -1) {
            writer.write(c);
        }
        reader.close();
        writer.close();
    }
}
