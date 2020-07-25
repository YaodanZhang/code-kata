import java.lang.Math;
import java.util.*;

public class Primes {

  private static int max = 10;
  static private List<Integer> primeList = new ArrayList<>(Arrays.asList(2, 3, 5, 7, 11));
  private static Map<Integer, List<Integer>> primeFactors = new HashMap<>();

  private static boolean ifPrime(int n) {
    int root = (int) Math.sqrt(n) + 1;
    expandPrime(root);
    for (int d : primeList) {
      if (n % d == 0) {
        return false;
      }
      if (d * d > n) {
        return true;
      }
    }
    return true;
  }

  private static void expandPrime(int n) {
    int m = lastOf(primeList) + 2;
    while (n > lastOf(primeList)) {
      if (ifPrime(m)) {
        primeList.add(m);
      }
      m = m + 2;
    }
  }

  private static <T> T lastOf(List<T> primeList) {
    return primeList.get(primeList.size() - 1);
  }

  public static boolean isPrime(int n) {
    if (n < max) {
      expandPrime(n);
      return primeList.contains(n);
    } else {
      return ifPrime(n);
    }
  }

  public static int kthPrime(int k) {

    while (primeList.size() < k + 1) {
      expandPrime((lastOf(primeList) * 2));
    }
    return primeList.get(k);
  }

  public static List<Integer> factorize(int n) {
    if (primeFactors.containsKey(n)) {
      return primeFactors.get(n);
    }
    expandPrime(n);
    List<Integer> list = new ArrayList<>();
    primeFactors.put(n, list);
    for (int i : primeList) {
      if (n % i == 0) {
        list.add(i);
        list.addAll(factorize(n / i));
        break;
      }
    }
    return list;
  }
  public static void main(String[] args){
    System.out.println(factorize(405341));
  }
}