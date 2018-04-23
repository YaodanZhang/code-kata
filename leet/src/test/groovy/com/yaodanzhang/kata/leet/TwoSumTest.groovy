package com.yaodanzhang.kata.leet

import spock.lang.Specification
import spock.lang.Unroll

class TwoSumTest extends Specification {
    @Unroll
    def 'given array #input and target #target, results in #expectation'() {
        expect:
        new TwoSum().calculate(input as int[], target) == expectation as int[]

        where:
        input  | target | expectation
        [2, 7] | 9      | [0, 1]
    }
}
