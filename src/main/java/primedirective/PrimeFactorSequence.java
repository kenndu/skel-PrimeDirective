package primedirective;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import primedirective.Primes;

public class PrimeFactorSequence {
    private List<Integer> primes;
    private int upperBound;

    private List<Integer> numbers = new ArrayList<>();
    /**
     * Create a PrimeFactorSequence object with a defined upperbound.
     *
     * @param _upperBound the upper bound for sequences and primes in this instance,
     * {@code upperBound > 0}.
     */
    public PrimeFactorSequence(int _upperBound) {
        upperBound = _upperBound;
        primes = Primes.getPrimes(upperBound);
        for (int i = 0; i <= _upperBound; i++) {
            numbers.add(i);
        }
    }

    /**
     * Obtain a sequence L[0 .. upper bound] where L[i] represents the number of prime factors i
     * has, including repeated factors
     *
     * @return sequence L[0 .. upper bound] where L[i] represents the number of prime factors i
     * has, including repeated factors
     */
    public List<Integer> primeFactorSequence() {
        List<Integer> seq = new ArrayList<>();
        for (int num : numbers) {
            seq.add(primeFactorCount(num));
        }
        return seq;
    }

    private int primeFactorCount(int n) {
        if (n <= 1) {
            return 0;
        }
        if (primes.contains(n)) {
            return 1;
        }

        int nextNumber = 0;

        for (int i = 0; i < primes.size(); i++) {
            if (n % primes.get(i) == 0) {
                nextNumber = n / primes.get(i);
                break;

            }
        }
        return 1 + primeFactorCount(nextNumber);
    }

    /**
     * Obtain a sequence L that is sorted in ascending order and where L[i] has exactly m
     * prime factors (including repeated factors) and L[i] <= upper bound
     *
     * @param m the number of prime factors that each element of the output sequence has
     * @return a sequence L that is sorted in ascending order and where L[i] has exactly
     * m prime factors (including repeated factors) and L[i] <= upper bound
     */
    public List<Integer> numbersWithMPrimeFactors(int m) {
        List<Integer> seq = new ArrayList<>();
        List<Integer> pfs = primeFactorSequence();

        for (int i = 0; i < pfs.size(); i++) {
            if (pfs.get(i) == m) {
                seq.add(i);
            }
        }
        return seq;
    }

    /**
     * Obtain a sequence of integer pairs [(Sa, Sb)] where Sa and Sb have exactly m prime factors
     * (including repeated factors), and where |Sa - Sb| <= gap and where Sa and Sb are
     * adjacent each other in the output of {@code numbersWithMPrimeFactors(m)}
     *
     * @param m   the number of prime factors that each element in the output sequence has
     * @param gap the maximum gap between two adjacent entries of interest in the output
     *            of {@code numbersWithMPrimeFactors(m)}
     * @return a sequence of integer pairs [(Sa, Sb)] where Sa and Sb have exactly
     * m prime factors (including repeated factors), and where |Sa - Sb| <= gap and where
     * Sa and Sb are adjacent each other in the output of {@code numbersWithMPrimeFactors(m)}
     */
    public List<IntPair> numbersWithMPrimeFactorsAndSmallGap(int m, int gap) {
        List<IntPair> listOfPairs = new ArrayList<>();
        List<Integer> primeFactorCounts = numbersWithMPrimeFactors(m);

        for (int i = 0; i < primeFactorCounts.size()-1; i++) {
            if ((primeFactorCounts.get(i+1) - primeFactorCounts.get(i)) <= gap) {
                IntPair newPair = new IntPair(primeFactorCounts.get(i), primeFactorCounts.get(i+1));
                listOfPairs.add(newPair);
            }
        }

        return listOfPairs;
    }

    /**
     * Transform n to a larger prime (unless n is already prime) using the following steps:
     * <p>
     *     <ul>
     *         <li>A 0-step where we obtain 2 * n + 1</li>,
     *         <li>or a 1-step where we obtain n + 1</li>.
     *     </ul>
     *      We can apply these steps repeatedly, with more details in the problem statement.
     * </p>
     * @param n the number to transform
     * @return a string representation of the smallest transformation sequence
     */
    public String changeToPrime(int n) {
        List<String> pathsToPrime = new ArrayList<>();

        StringBuilder path = getPath(n);

        if (primes.contains(n)) {
            return "";
        }

        if (path.indexOf("-") != -1) {
            return "-";
        }

        return path.toString();
    }

    private StringBuilder getPath(int n) {

        StringBuilder path = new StringBuilder();

        if (primes.contains(n)) {
            return new StringBuilder();
        }
        if (n > primes.get(primes.size()-1)) {
            return new StringBuilder("-");
        }
        if (!primes.contains(n+1) && !primes.contains(2*n+1)) {
            return path.append("0").append(getPath(2*n+1));
        } else if (primes.contains(n+1)) {
            return new StringBuilder("1");
        } else {
            return new StringBuilder("0");
        }

    }

    private int binaryToDecimal(String binary) {
        int decimal = 0;
        char[] binArray = binary.toCharArray();
        for (int i = binArray.length-1; i >= 0; i--) {
            decimal += binArray[i]*Math.pow(2,i);
        }

        return decimal;
    }

}
