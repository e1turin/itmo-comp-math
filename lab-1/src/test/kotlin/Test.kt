import org.junit.jupiter.api.Test

class Test {
    @Test
    fun `input data from file 1`() {
        println("####### input data from file 1 #######")
        main(arrayOf("--file", "D:\\Projects\\itmo-comp-math\\lab-1\\src\\test\\resources\\file-input-test-1.txt"))
    }

    @Test
    fun `input data from file 2`() {
        println("####### input data from file 2 #######")
        main(arrayOf("--file", "D:\\Projects\\itmo-comp-math\\lab-1\\src\\test\\resources\\file-input-test-2.txt"))
    }

    @Test
    fun `input data by random`() {
        println("####### input data by random #######")
        main(arrayOf("--random", "20"))
    }

    @Test
    fun `print documentation`(){
        println("####### print documentation #######")
        main(arrayOf("--help"))
    }
}