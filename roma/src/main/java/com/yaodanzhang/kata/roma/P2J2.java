package com.yaodanzhang.kata.roma;

class P2J2 {

  static long fallingPower(int n, int k) {
    //boolean flag = true;
    if (k == 0) {
      return 1;
    }

    long result = n;
    for (; k > 1; k--) {
      result = result * (--n);
    }

    return result;
  }
}
