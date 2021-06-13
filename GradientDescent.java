/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradientdescent;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Dell
 */
public class GradientDescent {

    /**
     * @param args the command line arguments
     */
        
    public static void main(String[] args) {
        // TODO code application logic here
       
        String feature1, feature2, feature3, feature4, label_name;
        List<Sample> list = new ArrayList<>();
        List<Sample> training = new ArrayList<>();
        List<Sample> testing = new ArrayList<>();
        System.out.println("------------------------------------------------IRIS DATASET-------------------------------------------");
        
        //FILING
        try {  
                
            File file = new File("C:\\Users\\Noyan\\Desktop\\Noyan\\6th Semester\\AI\\Assignments\\GradientDescent\\src\\gradientdescent\\iris dataset.xlsx");   //creating a new file instance  
            FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
                
            //creating Workbook instance that refers to .xlsx file  
            XSSFWorkbook wb = new XSSFWorkbook(fis);   
            XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  
            Iterator<Row> itr = sheet.iterator();    //iterating over excel file  
            int index = 0 ; 
            Row row = itr.next();      
            Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column 
            while (itr.hasNext()) {  
            
                Cell cell = cellIterator.next(); 
                if(index==0){
                    index++;
                    feature1 = cell.getStringCellValue();
                    cell = cellIterator.next();
                    feature2 = cell.getStringCellValue();
                    cell = cellIterator.next();
                    feature3 = cell.getStringCellValue();
                    cell = cellIterator.next();
                    feature4 = cell.getStringCellValue();
                    cell = cellIterator.next();
                    label_name = cell.getStringCellValue();
                    System.out.println(feature1+"\t\t"+feature2+"\t\t"+feature3+"\t\t"+feature4+"\t\t"+label_name);
                    row = itr.next();      
                    cellIterator = row.cellIterator(); 
                    cell = cellIterator.next();
                }
                Sample s = new Sample();
                 
                s.f1=(float)cell.getNumericCellValue();
               // System.out.print(s.f1+"\t\t\t");
                cell = cellIterator.next();
                s.f2=(float)cell.getNumericCellValue();
                // System.out.print(s.f2+"\t\t\t");
                cell = cellIterator.next();
                s.f3=(float)cell.getNumericCellValue();
                // System.out.print(s.f3+"\t\t\t");
                cell = cellIterator.next();
                s.f4=(float)cell.getNumericCellValue();
                // System.out.print(s.f4+"\t\t\t");
                cell = cellIterator.next();
                if ("I. setosa".equals(cell.getStringCellValue())) {
                    s.label = 1;
                }
                else if ("I. versicolor".equals(cell.getStringCellValue())) {
                    s.label = 2;
                }
                if ("I. virginica".equals(cell.getStringCellValue())) {
                    s.label = 3;
                }
                
                list.add(s);
                
                
                row = itr.next();      
                cellIterator = row.cellIterator();  
            }
          
        }  
        catch(Exception e){  
            e.printStackTrace();  
        }  
        
        
        //RANDOMIZING
        Collections.shuffle(list);
        for(int i  = 0 ; i < list.size(); i++) {
            list.get(i).print();
        }
        
        //SPLITTING
        for (int i = 0; i < 100; i++) {
            training.add(list.get(i));
        }
        for (int i =  100; i < list.size(); i++) {
            testing.add(list.get(i)); 
        }
        
        //---------GRADIENT DESCENT ALGORITHM---------///
        
        //randomizing inital weight vector values
        Random rand=new Random();
        
        float []W=new float[5];
        for(int i=0;i<5;i++){
            float random=rand.nextInt(10);
            W[i]=random;
        }
        
        //prinitng the initial weight vector
        System.out.print("\nWeight vector:\n[ ");
        for(int i=0;i<5;i++){
            System.out.print(W[i]);
            if(i!=4){
                System.out.print(" ,");
            }
        }
        System.out.println(" ]\n");
         
        //summation(y- yHat)*Ik
        int correct=1;
        float trainingRate=(float)0.001;
        
        int itr=0;
        float x[]=new float[10];
        int m=0;
        while(correct<training.size()&&itr<1000){
             itr++;
             correct=1;
            for (int k = 0; k < 5; k++) {
                float val=(float)0.0;
               
                for(int i=0;i<training.size();i++){
                    //initialzing I
                    float []I={1,training.get(i).f1,training.get(i).f2,training.get(i).f3,training.get(i).f4};
                    float yHat=(float) 0.0;
                    
                    //calculating y hat
                    for(int j=0;j<5;j++){
                        yHat+=(float) W[j]*I[j];
                    }
                 
                    //calculating y-yHat
                    float difference = training.get(i).label - yHat;
                    
                    //calculating cost
                    if(itr%100==0&&k==0){
                        x[m]+=difference*difference;
                       
                        if(i==training.size()-1) {
                            
                             System.out.println("Cost of "+itr+ "th iteration:   " +x[m]);
                             m++;
                        }
                    }
                    
                    //calculating y-yHat*Ik
                    val += difference*I[k];
                }
                if(val==0){
                        correct++;
                        System.out.println("Correct: "+correct);
                }
                
                //calculating updated weight values
                W[k] = W[k] - val*trainingRate*(-1);
            }
        }
        
        //prinitng the updated weight vector
        System.out.print("\nTrained Weight vector:\n[ ");
            for(int i=0;i<5;i++){
                System.out.print(W[i]);
                if(i!=4){
                    System.out.print(" ,");
                }
            }
            System.out.println(" ]\n");
			
	//-----TESTING WITHOUT ROUND----//
        System.out.println("\nTESTING DATA WITHOUT ROUNDING");
        int mismatch = 0;
        float difference = 0;
        for(int i = 0; i < testing.size(); i++){
            for (int k = 0; k < 5; k++) {
                float val=(float)0.0;

                float []I={1,testing.get(i).f1,testing.get(i).f2,testing.get(i).f3,testing.get(i).f4};
                float yHat=(float) 0.0;
                    
                //calculating y hat
                for(int j=0;j<5;j++){
                    yHat+=(float) W[j]*I[j];
                }
                System.out.println("------------------------\nyhat  " + yHat + " y: "+testing.get(i).label);
                difference = testing.get(i).label - yHat;        
            }
            if (difference != 0) {
                mismatch++;
            }
        }
        System.out.println("\nMismatches: " + mismatch);	
        
        //-----TESTING WITH ROUND----//
         System.out.println("\n\nTESTING DATA WITH ROUNDING");
        mismatch = 0;
        difference = 0;
        for(int i = 0; i < testing.size(); i++){
            for (int k = 0; k < 5; k++) {
                float val=(float)0.0;

                float []I={1,testing.get(i).f1,testing.get(i).f2,testing.get(i).f3,testing.get(i).f4};
                float yHat=(float) 0.0;
                    
                //calculating y hat
                for(int j=0;j<5;j++){
                    yHat+=(float) W[j]*I[j];
                }
                
                //round off
                if (yHat <= 1.5) {
                    yHat = (float) 1.0;
                }
                else if (yHat <= 2.5 && yHat > 1.5) {
                    yHat = (float) 2.0;
                }
                else if (yHat > 2.5) {
                    yHat = (float) 3.0;
                }
                System.out.println("------------------------\nyhat  " + yHat + " y: "+testing.get(i).label);
                difference = testing.get(i).label - yHat;        
            }
            if (difference != 0) {
                mismatch++;
            }
        }
        System.out.println("\nMismatches after rounding off: " + mismatch);	  
        
    }
}
