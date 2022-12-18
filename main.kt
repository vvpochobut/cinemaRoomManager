package bot

import jdk.jfr.Percentage


fun main() {
    var work = true //Цикл жизни программы
    println("Enter the number of rows:")
    val numberOfRows = readln().toInt()
    println("Enter the number of seats in each row:")
    val numberOfSeatsInRows = readln().toInt()
    val cinema = CreateMovieTheatre(numberOfRows,numberOfSeatsInRows) //Создание кинотеатра
    var count = 0
    var currentIncome = 0
    val allSeats = numberOfSeatsInRows*numberOfRows
    while(work){  //Меню
        println()
        println("1. Show the seats")
        println( "2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")
        val choce = readln().toInt()
        when(choce){
            1 ->{
                println()
                printIlustrationCinema(cinema)
            }
            2 ->{
                var workBuy = true //Цикл покупки билетов
                println()
                while (workBuy) {
                    println("Enter a row number:")
                    val enterRow = readln().toInt() //Выбор ряда
                    println("Enter a seat number in that row:")
                    val enterSeats = readln().toInt() //Выбор Места
                    if(enterRow in 0 ..numberOfRows && enterSeats in 0 .. numberOfSeatsInRows) {
                        if (cinema[enterRow][enterSeats] == "B") { //Проверяем свободно ли данное место
                            println("That ticket has already been purchased!") 
                        } else {
                            val oneSeats = fOneSeats(allSeats, numberOfRows, enterRow, numberOfSeatsInRows)[0] //Цена билета
                            println()
                            statistickEmployment(cinema)
                            choiceCinema(cinema, enterRow, enterSeats)
                            println("Ticket price: $$oneSeats")
                            count++
                            currentIncome += oneSeats
                            println()
                            workBuy = false
                        }
                    }else {
                        println("Wrong input!")
                    }
                }
            }
            3 -> {
                println()
                println("Number of purchased tickets: $count")
                println("Percentage: ${"%.2f".format(100 / allSeats.toDouble()  * count.toDouble())}%")
                println("Current income: $$currentIncome")
                println("Total income: $${totalIncome(allSeats, numberOfRows, numberOfSeatsInRows)}")
                println()

            }
            else -> {work=false}
        }
    }
}

fun choiceCinema(
    cinema: MutableList<MutableList<Any>>,
    enterRow: Int,
    enterSeats: Int) {
    cinema[enterRow][enterSeats] = "B"
}

fun totalIncome(allSeats: Int, numberOfRows: Int,numberOfSeatsInRows: Int): Int {
    var oneSeats1 = 0
    var profit = 0
    if (allSeats < 60) {
        oneSeats1 = 10
        profit = allSeats * oneSeats1

    } else {
        if (numberOfRows % 2 == 0) {

            profit = (( numberOfRows / 2)) * 10 * numberOfSeatsInRows + (( numberOfRows / 2) * 8 * numberOfSeatsInRows)
        } else {
            val firstRows = numberOfRows / 2

            profit = (firstRows * 10 * numberOfSeatsInRows) + ((numberOfRows - firstRows) * 8 * numberOfSeatsInRows)

        }
    }
    return profit
}


fun fOneSeats(allSeats: Int, numberOfRows: Int, enterRow: Int,numberOfSeatsInRows: Int): List<Int> {
    val oneSeats1: Int
    val profit: Int
    if (allSeats < 60) { //Если количество мест в кинотеатре меньше 60,цена за 1 место 10$
        oneSeats1 = 10
        profit = allSeats * oneSeats1

    } else {
        if (numberOfRows % 2 == 0) {
            if (enterRow in 0..numberOfRows / 2) {
                oneSeats1 = 10
            } else {
                oneSeats1 = 8
            }
            profit = (( numberOfRows / 2)) * 10 * numberOfSeatsInRows + (( numberOfRows / 2) * 8 * numberOfSeatsInRows)
        } else {
            val firstRows = numberOfRows / 2
            when (enterRow) {
                in 0..firstRows -> oneSeats1 = 10
                else -> oneSeats1 = 8
            }
            profit = (firstRows * 10 * numberOfSeatsInRows) + ((numberOfRows - firstRows) * 8 * numberOfSeatsInRows)

        }
    }
    val fOneSeats = mutableListOf<Int>(oneSeats1,profit)
    return fOneSeats
}

fun CreateMovieTheatre( numberOfRows: Int,numberOfSeatsInRows: Int): MutableList<MutableList<Any>> { //Создаем кинотеатр
    val cinema = mutableListOf(
        mutableListOf<Any>(" ")
    )
    for (i in 1..numberOfSeatsInRows) {
        cinema[0].add(i)
    }
    for (i in 1..numberOfRows) {
        cinema.add(MutableList(numberOfSeatsInRows + 1) { "S" })
        cinema[i][0] = i
    }

    return cinema
}

fun printIlustrationCinema(cinema: MutableList<MutableList<Any>>) { //Визуальное предоставление нашего кинотеатра
    println("Cinema:")
    for (i in 0 until cinema.size) {
        println(cinema[i].joinToString(" "))
    }
}

fun statistickEmployment (cinema : MutableList<MutableList<Any>>,) { //Статистика купленных билетов
    var s = 0
    for (i in 0 until cinema.size) {
        for (k in 0 until cinema[i].size) {

            if (cinema[i][k] == "B") {
                s++
            }
        }
    }
}
