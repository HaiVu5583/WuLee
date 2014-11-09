/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wulee;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.codec.binary.BinaryCodec;
import org.apache.commons.codec.binary.StringUtils;

/**
 *
 * @author Vu
 */
public class WuLee {

    /**
     * @param s
     * @param key
     * @param input
     * @throws java.io.IOException
     */
    public static void hideInformation(String s, Matrix key, File input) throws IOException {
        String binaryString = Utils.convertStringToBinary(s);
        Matrix imageMatrix = Utils.getMatrixFromImage(input);
        List<Matrix> list = imageMatrix.divide(key.getRows(), key.getColumns());
        int i = 0;
        for (Matrix m : list) {
            if (i >= binaryString.length()) {
                break;
            }
            Matrix and = key.andMatrix(m);

            if (and.numOfBit1() > 0 && and.numOfBit1() < key.numOfBit1()) {
                // Dieu kien de co the giau bit trong khoi ma tran
                if (and.numOfBit1() % 2 != Integer.parseInt(Character.toString(binaryString.charAt(i)))) {
                    if (and.numOfBit1() == 1) {
                        MyPair p = key.findMatch(m, new MyPair(1, 0));
                        m.setValue(p.getFirst(), p.getSecond(), 1);
                    } else if (and.numOfBit1() == key.numOfBit1() - 1) {
                        MyPair p = key.findMatch(m, new MyPair(1, 1));
                        m.setValue(p.getFirst(), p.getSecond(), 0);
                    } else {
                        MyPair p = key.getRandomByCondition(1);
                        m.reverse(p.getFirst(), p.getSecond());
                    }
                }
                i++;
                System.out.println(i);
            }
        }
        Matrix merge = Matrix.merge(imageMatrix.getRows(), imageMatrix.getColumns(), list);
        //System.out.println(imageMatrix.totalDiff(merge));
        Utils.writeBinaryImage(merge, input);

    }

    public static String readInformation(Matrix key, File input, int length)
            throws IOException {
        Matrix imageMatrix = Utils.getMatrixFromImage(input);
        List<Matrix> list = imageMatrix.divide(key.getRows(), key.getColumns());
        StringBuilder bitinfo = new StringBuilder("");
        int i = 0;
        for (Matrix m : list) {
            if (i >= 8 * length) {
                break;
            }
            Matrix and = key.andMatrix(m);
            int b = and.numOfBit1();
            if (and.numOfBit1() > 0 && and.numOfBit1() < key.numOfBit1()) {
                if (b % 2 == 0) {
                    bitinfo.append("0");
                } else {
                    bitinfo.append("1");
                }
                i++;
            }
        }
        int len = bitinfo.length();
        int mis = 8 - (len % 8);
        if (mis != 8) {
            for (int j = 0; j < mis; j++) {
                bitinfo.append("0");
            }
        }
        //System.out.println(bitinfo);
        String info = Utils.convertBinaryToString(bitinfo.toString());
        return info;
    }
//    public static void main(String[] args) {
//        try {
//            File input = new File("resource/binary.png");
//            File output = new File("resource/out.png");
//            String s = "Hôm nay chích hít tại 3G3";
//            Matrix key = new Matrix(new int[][]{
//                {1, 0, 0, 0},
//                {1, 0, 1, 0},
//                {1, 1, 0, 1},
//                {0, 0, 0, 0}
//            });
//            Utils.copyFile(input, output);
//            hideInformation(s, key, output);
//            System.out.println("Output: "+readInformation(key, output, s.getBytes("UTF-8").length));
//        } catch (IOException ex) {
//            Logger.getLogger(WuLee.class.getName()).log(Level.SEVERE, null, ex);
////        }
//        JFileChooser chooser = new JFileChooser();
//    FileNameExtensionFilter filter = new FileNameExtensionFilter(
//        "JPG & GIF Images", "jpg", "gif");
//    chooser.setFileFilter(filter);
//    int returnVal = chooser.showOpenDialog(null);
//    if(returnVal == JFileChooser.APPROVE_OPTION) {
//       System.out.println("You chose to open this file: " +
//            chooser.getSelectedFile().getName());
//    }
////        
//    }
}
//a = readInformation(key, source, s.getBytes("UTF-8").length);