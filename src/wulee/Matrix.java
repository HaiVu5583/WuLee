/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wulee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import wulee.exception.MatrixInputException;

// Class bieu dien ma tran nhi phan
public class Matrix {
    private int[][] matrix;
    private int rows;
    private int columns;
    public Matrix(int[][] matrix) {
        this.rows = matrix.length;
        this.columns = matrix[0].length;
        this.matrix = new int[rows][columns];
        for (int i=0; i<rows; i++)
            for (int j=0; j<columns; j++)
                this.matrix[i][j] = matrix[i][j];
    }

    public Matrix() {
    }
    
    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        matrix = new int[rows][columns];
    }
    public Matrix andMatrix(Matrix m){
        int[][] a = m.getMatrix();
        int [][] b = this.getMatrix();
        int[][] result = new int[rows][columns];
        for (int i=0; i<rows; i++){
            for (int j=0; j<columns; j++){
                if (a[i][j]==b[i][j] && a[i][j]==1)
                    result[i][j] = 1;
                else 
                    result[i][j] = 0;
            }
        }
        Matrix resultMatrix = new Matrix(result);
        return resultMatrix;
    }
    // Tinh tong so bit 1 trong ma tran
    public int numOfBit1(){
        int sum=0;
        for (int i=0; i<rows; i++){
            for (int j=0; j<columns; j++){
                sum+= matrix[i][j];
            }
        }
        return sum;
    }
    public int totalDiff(Matrix m){
        int total = 0;
        for (int i=0; i<rows; i++){
            for (int j=0; j<columns; j++){
                if (matrix[i][j] != m.getValue(i, j))
                    total += 1;
            }
        }
        return total;
    }
    // Tin 
    public MyPair findMatch(Matrix m, MyPair p){
        List<MyPair> list = new ArrayList<>();
        for (int i=0; i<rows; i++){
            for (int j=0; j<columns; j++){
                if (matrix[i][j] == p.getFirst() && m.getValue(i, j) == p.getSecond()){
                    list.add(new MyPair(i, j));
                }
            }
        }
        Collections.shuffle(list);
        return (list.get(0));
    }
    // Lay ngau nhien toa do (i,j) trong ma tran thoa man gia tri tai (i, j) = gia tri dau vao  
    public MyPair getRandomByCondition (int in){
        List<MyPair> list = new ArrayList<>();
        for (int i=0; i<rows; i++){
            for (int j=0; j<columns; j++){
                if (matrix[i][j] == in){
                    list.add(new MyPair(i, j));
                }
            }
        }
        Collections.shuffle(list);
        return (list.get(0));
    }
    // Chia ma tran thanh cac ma tran con dua vao kich thuoc ma tran con moi
    public ArrayList<Matrix> divide(int m, int n){
        int numOfSubMatrix = (rows/m) * (columns/n);
        int newRows = rows/m;
        int newColumns = columns/n;
//        System.out.println(newRows);
//        System.out.println(newColumns);
        ArrayList<Matrix> list = new ArrayList<>();
        for (int i=0; i<newRows; i++){
            for (int j=0; j<newColumns; j++){
                int[][] temp = new int[m][n];
                for (int k=0; k<m; k++){
                    for (int l=0; l<n; l++){
                        temp[k][l] = matrix[m*i+k][n*j+l];
                    }
                }
                Matrix mTemp = new Matrix(temp);
                list.add(mTemp);
            }
        }
        return list;
    }
    // Doc ma tran tu chuoi nhi phan va so hang, so cot
    public static Matrix readFromString (String s, int r, int c)throws MatrixInputException{
        if (s.length()!= r*c) throw new MatrixInputException();
        Matrix m = new Matrix(r, c);
        for (int i=0; i<r;i++){
            for (int j=0; j<c; j++){
                int value = Integer.parseInt(Character.toString(s.charAt(i*c+j)));
                if (value!= 0 && value!= 1) throw new MatrixInputException();
                m.setValue(i, j, value);
            }
        }
        return m;
    }
    // Ghep chuoi cac ma tran nho thanh ma tran lon
    public static Matrix merge(int originalRows, int originalColumns, List<Matrix> list){
        Matrix sample = list.get(0);
        
        int m = sample.getRows();
       // System.out.println("m = "+Integer.toString(m));
        int n = sample.getColumns();
       // System.out.println("n = "+Integer.toString(n));
        int newRows = originalRows/m;
       // System.out.println("NewRows = "+Integer.toString(newRows));
        int newColumns = originalColumns/n;
       // System.out.println("NewColumns = "+Integer.toString(newColumns));
        Matrix result = new Matrix(newRows*m, newColumns*n);
        for (int i=0; i<newRows; i++){
            for (int j=0; j<newColumns; j++){
                Matrix temp = list.get(i*newColumns+j);
                //temp.print();
                for (int k=0; k<m; k++){
                    for (int l=0; l<n; l++){
                        result.setValue(i*m+k, j*n+l, temp.getValue(k, l));
                    }
                }
            }
        }
        return result;
    }
    public void reverse (int i, int j){
        if (matrix[i][j] == 0) matrix[i][j] = 1;
        else if (matrix[i][j] == 1) matrix[i][j] = 0;
    }
    // Lay gia tri tai vi tri xac dinh cua ma tran
    public int getValue(int i, int j){
        return matrix[i][j];
    }
    public void setValue(int i, int j, int value){
        matrix[i][j] = value;
    }
    public void print(){
        for (int i=0; i<rows; i++){
            for (int j=0; j<columns; j++){
                System.out.print(Integer.toString(matrix[i][j])+"   ");
            }
            System.out.println("");
        }
        System.out.println("---------------------------------");
    }
    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }
    
}
