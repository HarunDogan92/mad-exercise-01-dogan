/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package at.ac.fhcampuswien

import java.util.Scanner
import kotlin.math.abs
import kotlin.math.log10

class App {
    // Game logic for a number guessing game
    fun playNumberGame(digitsToGuess: Int = 4) {
        //TODO: build a menu which calls the functions and works with the return values
        val genNum = generateRandomNonRepeatingNumber(digitsToGuess)
        val reader = Scanner(System.`in`)
        do {
            println("Your Guess: ")
            val guess = reader.nextInt()
            println( checkUserInputAgainstGeneratedNumber(guess, genNum))
        } while (guess != genNum)
        println("Congratulations! The generated Number was: $genNum")
    }

    /**
     * Generates a non-repeating number of a specified length between 1-9.
     *
     * Note: The function is designed to generate a number where each digit is unique and does not repeat.
     * It is important to ensure that the length parameter does not exceed the maximum possible length
     * for non-repeating digits (which is 9 excluding 0 for base-10 numbers).
     *
     * @param length The length of the non-repeating number to be generated.
     *               This dictates how many digits the generated number will have.
     * @return An integer of generated non-repeating number.
     *         The generated number will have a number of digits equal to the specified length and will
     *         contain unique, non-repeating digits.
     * @throws IllegalArgumentException if the length is more than 9 or less than 1.
     */
    val generateRandomNonRepeatingNumber: (Int) -> Int = { length ->
        if (length > 9)
            throw IllegalArgumentException("Cant be longer than 9 digits")
        val numbers = (1..9).shuffled().take(length)
        numbers.joinToString("").toInt()
    }

    /**
     * Compares the user's input integer against a generated number for a guessing game.
     * This function evaluates how many digits the user guessed correctly and how many of those
     * are in the correct position. The game generates number with non-repeating digits.
     *
     * Note: The input and the generated number must both be numbers.
     * If the inputs do not meet these criteria, an IllegalArgumentException is thrown.
     *
     * @param input The user's input integer. It should be a number with non-repeating digits.
     * @param generatedNumber The generated number with non-repeating digits to compare against.
     * @return [CompareResult] with two properties:
     *         1. `n`: The number of digits guessed correctly (regardless of their position).
     *         2. `m`: The number of digits guessed correctly and in the correct position.
     *         The result is formatted as "Output: m:n", where "m" and "n" represent the above values, respectively.
     * @throws IllegalArgumentException if the inputs do not have the same number of digits.
     */
    val checkUserInputAgainstGeneratedNumber: (Int, Int) -> CompareResult = { input, generatedNumber ->
        if (input.length() != generatedNumber.length())
            throw IllegalArgumentException("Wrong input length")
        var n = 0
        var m = 0
        val inDig = input.toString().toCharArray()
        var genDig = generatedNumber.toString().toCharArray()
        for (i in inDig.indices) {
            if (inDig[i] == genDig[i]) {
                m++
            }
        }
        inDig.forEach { digit ->
            if (digit in genDig) {
                n++
                genDig = genDig.filter { it != digit }.toCharArray()
            }
        }
        CompareResult(n, m)   // return value is a placeholder
    }
}

fun Int.length() = when(this) {
    0 -> 1
    else -> log10(abs(toDouble())).toInt() + 1
}
fun main() {
    println("Welcome to NumberGuesser")
    println("Enter the length of numbers you want to guess: ")
    val reader = Scanner(System.`in`)
    val length = reader.nextInt()
    val app = App()
    app.playNumberGame(length)
}
