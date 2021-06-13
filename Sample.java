/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradientdescent;

/**
 *
 * @author Dell
 */
public class Sample {
    float f1, f2, f3, f4, label;
    
    Sample() {
        
    }
    
    Sample(float f1, float f2, float f3, float f4, float label) {
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.f4 = f4;
        this.label = label;
    }
    
    void print() {
        System.out.println(f1 + "\t\t\t" + f2 + "\t\t\t" + f3 + "\t\t\t" + f4 + "\t\t\t" + label);
    }
    
}
