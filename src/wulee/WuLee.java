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
import javax.swing.JOptionPane;
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
        int i=0;
        for (Matrix m:list){
            if (i >= binaryString.length()) break;
            Matrix and = key.andMatrix(m);
            
            if (and.numOfBit1()>0 && and.numOfBit1()<key.numOfBit1()){
            // Dieu kien de co the giau bit trong khoi ma tran
                if (and.numOfBit1()%2 != Integer.parseInt(Character.toString(binaryString.charAt(i)))){
                    if (and.numOfBit1() == 1){
                        MyPair p = key.findMatch(m, new MyPair(1, 0));
                        m.setValue(p.getFirst(), p.getSecond(), 1);
                    }else if (and.numOfBit1() == key.numOfBit1()-1){
                        MyPair p = key.findMatch(m, new MyPair(1, 1));
                        m.setValue(p.getFirst(), p.getSecond(), 0);
                    }else{
                        MyPair p = key.getRandomByCondition(1);
                        m.reverse(p.getFirst(), p.getSecond());
                    }
                }
                i++;
                System.out.println(i);
            }
        }
        Matrix merge = Matrix.merge(imageMatrix.getRows(), imageMatrix.getColumns(), list);
        System.out.println(imageMatrix.totalDiff(merge));
        Utils.writeBinaryImage(merge, input);
        
    }

    public static void main(String[] args) {

            
            
        try {
            //        System.out.println(Utils.convertStringToBinary(s).length());
//        Matrix key = new Matrix(new int[][]{
//            {1, 0, 0, 1},
//            {1, 0, 1, 1},
//            {0, 0, 0, 1},
//            {0, 1, 0, 1},
//            
//        });
//        File original = new File("resource/binary.png");
//        File copy = new File ("resource/out.png");
//        try {
//            hideInformation(s, key, copy);
//        } catch (IOException ex) {
//            Logger.getLogger(WuLee.class.getName()).log(Level.SEVERE, null, ex);
            
//        }
            String s = "Vũ Long Hải";
            String binary = Utils.convertStringToBinary(s);
            String decode = Utils.convertBinaryToString(binary);
            System.out.println(binary.length());
            System.out.println(binary);
            System.out.println(decode);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(WuLee.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        

    }
}
