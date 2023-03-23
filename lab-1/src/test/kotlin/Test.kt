import org.junit.jupiter.api.Test

class Test {
    @Test
    fun `input data from file`() {
        println("####### input data from file #######")
        main(arrayOf("--file", "D:\\Projects\\itmo-comp-math\\lab-1\\src\\test\\resources\\file-input-test.txt"))
    }

    @Test
    fun `input data by random dim=3`() {
        println("####### input data by random #######")
        main(arrayOf("--random", "3"))
    }

    @Test
    fun `print documentation`(){
        println("####### print documentation #######")
        main(arrayOf("--help"))
    }
}