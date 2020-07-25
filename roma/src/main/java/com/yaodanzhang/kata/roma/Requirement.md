public static long fallingPower(int n, int k)

Python has the integer exponentiation operator ** conveniently built in the language, whereas Java
unfortunately does not have that operator. (In both languages, the caret character ^ denotes the
bitwise exclusive or operation that has nothing to do with integer exponentiation.)

However, in the related operation of falling power that is useful in many combinatorial formulas
and denoted syntactically by underlining the exponent, each term that gets multiplied into the
product is always one less than the previous term. For example, the falling power 8 3 would be
computed as 8 * 7 * 6 = 336. Similarly, the falling power 10 5 would equal 10 * 9 * 8 * 7 * 6 = 30240.
Nothing important changes if the base n is negative. For example, the falling power (-4) 5 is
computed the exact same way as -4 * -5 * -6 * -7 * -8 = -6720.

This method should compute and return the falling power n k where n can be any integer, and k can
be any nonnegative integer. (Analogous to ordinary powers, n 0 = 1 for any n .) The automated tester
is designed so that your method does not need to worry about potential integer overflow as long as
you perform computations using long type of 64-bit integers.