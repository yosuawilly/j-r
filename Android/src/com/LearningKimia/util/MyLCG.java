package com.LearningKimia.util;

/**
 *
 * @author Dwidasa
 */
public class MyLCG {
    //X1n = ( 5*X1n-1 + 13 ) mod 23

    public static int[]getLCG(int m){
        int b = (m/2)+1;
        int a = (b/2)+1;
        int[]hasil = new int[m];
        for(int i=0;i<m;i++){
            if(i==0)hasil[i] = (a*0 + b) % m;
            else hasil[i] = (a*hasil[i-1] + b) % m;
        }
        return hasil;
    }

//    public static void main(String[] args) {
//        MyLCG lCG = new MyLCG();
//        int[]hasil = lCG.getLCG(5);
//        for(int i=0;i<hasil.length;i++){
//            System.out.println(hasil[i]);
//        }
//    }

}
