package hw7_5;

import java.util.HashMap;

public class Program {
  static boolean U;

  static boolean D;

  static boolean C;

  static boolean T;

  static boolean P;

  static boolean R;

  static boolean G;

  static Double cur = new Double(0);

  static HashMap<Integer, Double> results = new HashMap<Integer, Double>();

  static Integer getIndex() {
    Integer answer = 0;
    if (U)
      answer += 1;
    if (D)
      answer += 2;
    if (C)
      answer += 4;
    if (T)
      answer += 8;
    if (P)
      answer += 16;
    if (R)
      answer += 32;
    if (G)
      answer += 64;
    return answer;
  }

  static public void main(String[] args) {
    goU();
    System.out.println(compute(4,false,64));

    System.out.println(compute(2,true,64));

    System.out.println(compute(4));

    System.out.println(compute(8,true,16));
  }

  static public Double compute(Integer index){
    Double prob = 0.;
    for (Integer i = 0; i < 128; ++i) {
      if((i & index)!=0){
        prob = prob + results.get(i);
      }
    }
    return prob;
  }
  
  static public Double compute(Integer givenIndex, boolean givenTrue, Integer computeIndex) {
    Double givenProb = 0.;
    Double bothProb = 0.;
    for (Integer i = 0; i < 128; ++i) {
      if ((((i & givenIndex) != 0) && givenTrue) || (((i & givenIndex) == 0) && !givenTrue)) {
        givenProb += results.get(i);
        if ((computeIndex & i) != 0) {
          bothProb += results.get(i);
        }
      }
    }

    Double answer = bothProb/givenProb;
    return answer;
  }

  static public void goU() {
    U = true;
    cur = 0.1;
    goD();
    U = false;
    cur = 0.9;
    goD();
  }

  static public void goD() {
    Double prv = cur;
    if (U) {
      D = true;
      cur = prv * 0.6;
      goC();
      D = false;
      cur = prv * 0.4;
      goC();
    } else {
      D = true;
      cur = prv * 0.5;
      goC();
      D = false;
      cur = prv * 0.5;
      goC();
    }
  }

  static public void goC() {
    Double prv = cur;
    if (U && D) {
      C = true;
      cur = prv * 0.7;
      goT();
      C = false;
      cur = prv * 0.3;
      goT();
    }
    if (U && !D) {
      C = true;
      cur = prv * 0.5;
      goT();
      C = false;
      cur = prv * 0.5;
      goT();
    }
    if (!U && D) {
      C = true;
      cur = prv * 0.6;
      goT();
      C = false;
      cur = prv * 0.4;
      goT();
    }
    if (!U && !D) {
      C = true;
      cur = prv * 0.05;
      goT();
      C = false;
      cur = prv * 0.95;
      goT();
    }
  }

  static public void goT() {
    Double prv = cur;
    if (C) {
      T = true;
      cur = prv * 0.7;
      goP();
      T = false;
      cur = prv * 0.3;
      goP();
    } else {
      T = true;
      cur = prv * 0.6;
      goP();
      T = false;
      cur = prv * 0.4;
      goP();
    }
  }

  static public void goP() {
    Double prv = cur;
    if (C) {
      P = true;
      cur = prv * 0.8;
      goR();
      P = false;
      cur = prv * 0.2;
      goR();
    } else {
      P = true;
      cur = prv * 0.6;
      goR();
      P = false;
      cur = prv * 0.4;
      goR();
    }
  }

  static public void goR() {
    Double prv = cur;
    if (P) {
      R = true;
      cur = prv * 0.7;
      goG();
      R = false;
      cur = prv * 0.3;
      goG();
    } else {
      R = true;
      cur = prv * 0.1;
      goG();
      R = false;
      cur = prv * 0.9;
      goG();
    }
  }

  static public void goG() {
    Double prv = cur;
    if (P && R) {
      G = true;
      cur = prv * 0.9;
      print();
      G = false;
      cur = prv * 0.1;
      print();
    }
    if (P && !R) {
      G = true;
      cur = prv * 0.3;
      print();
      G = false;
      cur = prv * 0.7;
      print();
    }
    if (!P && R) {
      G = true;
      cur = prv * 0.5;
      print();
      G = false;
      cur = prv * 0.5;
      print();
    }
    if (!P && !R) {
      G = true;
      cur = prv * 0.1;
      print();
      G = false;
      cur = prv * 0.9;
      print();
    }

  }

  static public void print() {
    System.out.format("%b\t%b\t%b\t%b\t%b\t%b\t%b\t%f\n", U, D, C, T, P, R, G, cur);
    System.out.println(getIndex());
    results.put(getIndex(), cur);
  }
}
