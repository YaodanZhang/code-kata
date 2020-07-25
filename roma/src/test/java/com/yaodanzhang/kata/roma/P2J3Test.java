package com.yaodanzhang.kata.roma;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.zip.CRC32;
import org.junit.Test;

public class P2J3Test {

  private static final int SEED = 12345;
  private static final int ROUNDS = 1000;

  @Test
  public void testPancakeScramble() throws IOException {
    CRC32 check = new CRC32();
    BufferedReader fr = new BufferedReader(
        new InputStreamReader(new FileInputStream("warandpeace.txt"), "UTF-8")
    );
    String line = fr.readLine();
    while (line != null) {
      String result = P2J3.pancakeScramble(line);
      check.update(result.getBytes());
      line = fr.readLine();
    }
    fr.close();
    assertEquals(1606800991L, check.getValue());
  }

  @Test
  public void testReverseVowels() throws IOException {
    CRC32 check = new CRC32();
    BufferedReader fr = new BufferedReader(
        new InputStreamReader(new FileInputStream("warandpeace.txt"), "UTF-8")
    );
    String line = fr.readLine();
    while (line != null) {
      String result = P2J3.reverseVowels(line);
      check.update(result.getBytes());
      line = fr.readLine();
    }
    fr.close();
    assertEquals(3844894811L, check.getValue());
  }

  @Test
  public void testReverseAscendingSubarrays() {
    Random rng = new Random(SEED);
    CRC32 check = new CRC32();
    for (int i = 0; i < ROUNDS; i++) {
      int len = rng.nextInt(1000);
      int[] a = new int[len];
      for (int j = 0; j < len; j++) {
        a[j] = rng.nextInt(1000000);
      }
      P2J3.reverseAscendingSubarrays(a);
      for (int j = 0; j < len; j++) {
        check.update(a[j]);
      }
    }
    assertEquals(3118921076L, check.getValue());
  }


}