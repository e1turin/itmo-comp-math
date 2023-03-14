import org.junit.jupiter.api.Test

class Test {
    @Test
    fun `input file path`() {
        main(arrayOf("--file", "D:\\Projects\\itmo-comp-math\\lab-1\\src\\test\\resources\\file-input-test.txt"))
    }

    @Test
    fun `random input`() {
        main(arrayOf("--random", "3"))
    }

    @Test
    fun `docs print`(){
        main(arrayOf("--help"))
    }



}