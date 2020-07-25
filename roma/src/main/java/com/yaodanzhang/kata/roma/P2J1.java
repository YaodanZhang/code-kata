package com.yaodanzhang.kata.roma;

class P2J1 {

  static long fallingPower(int n, int k) {
    if (k == 0) {
      return 1;
    }
    if (k == 1) {
      return n;
    }
    return n * fallingPower(n - 1, k - 1);
  }
}
