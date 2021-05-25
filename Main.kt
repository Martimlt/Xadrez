val invalid = "Invalid response."
fun main() {
    var numColumns: Int?
    var numLines: Int?
    var showLegend: String
    var showPieces: String
    var player1: String
    var player2: String
    var pieces: Array<Pair<String, String>?>
    println("Welcome to the Chess Board Game!")
    do {
        println(buildMenu())
        val escolha = readLine()?.toIntOrNull()
        if (escolha == 1) {
            var validName = false
            do {
                println("First player name?\n")
                player1 = readLine().toString()
                if (!checkName(player1)) {
                    println(invalid)
                } else {
                    validName = true
                }
            } while (!validName)
            validName = false
            do {
                println("Second player name?\n")
                player2 = readLine().toString()
                if (!checkName(player2)) {
                    println(invalid)
                } else {
                    validName = true
                }
            } while (!validName)
            do {
                do {
                    println("How many chess columns?\n")
                    numColumns = readLine()?.toIntOrNull()
                    var numeroMenor = false
                    if (numColumns != null && numColumns >= 4 && numColumns <= 8) {
                        numeroMenor = true
                    } else {
                        println(invalid)
                        numeroMenor = false
                    }
                } while (!(checkIsNumber(numColumns.toString()) && numeroMenor))
                println("How many chess lines?\n")
                numLines = readLine()?.toIntOrNull()
                var numeroMenor = false
                if (numLines != null && (numColumns == 4 && numLines == 4) || (numColumns == 6 && numLines == 7) ||
                    (numColumns == 6 && numLines == 6) || (numColumns == 7 && numLines == 7) ||
                    (numColumns == 8 && numLines == 8)) {
                    numeroMenor = true
                } else {
                    println(invalid)
                    numeroMenor = false
                }
            } while (!numeroMenor)
            do {
                println("Show legend (y/n)?\n")
                showLegend = readLine().toString()
                if (showLegend == ("y") || showLegend == ("Y") || showLegend == ("n") || showLegend == ("N")) {
                } else {
                    println(invalid)
                }
            } while (showLegend != "y" && showLegend != "Y" && showLegend != "n" && showLegend != "N")
            do {
                println("Show pieces (y/n)?\n")
                showPieces = readLine().toString()
                if (showPieces == ("y") || showPieces == ("Y") || showPieces == ("n") || showPieces == ("N")) {
                } else {
                    println(invalid)
                }
            } while (showPieces != ("y") && showPieces != ("Y") && showPieces != ("n") && showPieces != ("N"))
            if (numColumns != null && numLines != null) {
                pieces = createInitialBoard(numColumns,numLines)
                startNewGame(player1, player2, pieces,
                    createTotalPiecesAndTurn(numColumns, numLines), numColumns, numLines,
                    showChessLegendOrPieces(showLegend)!!, showChessLegendOrPieces(showPieces)!!)
            }
        }
    } while (escolha != 2)
}


fun buildMenu(): String {
    return ("1-> Start New Game;\n" +
            "2-> Exit Game.\n")
}

fun checkName(number: String): Boolean {
    var nameValid: Boolean
    val names = number.split(" ")
    var nameCount = 0
    nameValid = true
    for (nameString in names) {
        if (nameString.isNotEmpty()) {
            nameCount++
            if (nameString[0].toInt() !in 65..90) {
                nameValid = false
            }
        }
    }
    if (nameCount < 2) {
        nameValid = false
    }
    return (nameValid)
}

fun checkIsNumber(number: String): Boolean {
    val verification = number.toIntOrNull()
    return when (verification) {
        null -> false
        else -> true
    }
}

fun showChessLegendOrPieces(message: String): Boolean? {
    when (message) {
        "y" -> return true
        "Y" -> return true
        "n" -> return false
        "N" -> return false
        else -> return null
    }
}

fun buildBoard(numColumns: Int, numLines: Int, showLegend: Boolean = false, showPieces: Boolean = false,
               pieces: Array<Pair<String, String>?>): String {
    val esc: String = Character.toString(27)
    val startBlue = "$esc[30;44m"
    val startGrey = "$esc[30;47m"
    val startWhite = "$esc[30;30m"
    val end = "$esc[0m"
    val blue = "$startBlue   $end"
    var count = 0
    var count2 = 0
    var count3 = 0
    val alphabetic = "ABCDEFGH"
    var countAlphabetic = -1
    var sum = ""
    var posicao = 0
    if (showLegend) {
        while (2 > count2) {
            count2++
            sum += blue
            while (numColumns - 1 != countAlphabetic) {
                countAlphabetic++
                val final = alphabetic[countAlphabetic]
                sum += "$startBlue $final $end"
            }
        }
        sum += "\n"
    }
    while (numLines > count) {
        count++
        if (showLegend) {
            count3++
            sum += "$startBlue $count3 $end"
        }
        count2 = 0
        while (numColumns > count2) {
            var piecefinal: String = " "
            val separatePiece = pieces[posicao]
            val piece1 = separatePiece?.first
            val piece2 = separatePiece?.second
            if (piece1 != null && piece2 != null) {
                piecefinal = convertStringToUnicode(piece1, piece2)
            }
            if (showPieces) {
                when {
                    count2 % 2 != 0 && count % 2 != 0 -> sum += "$startGrey $piecefinal $end"
                    count2 % 2 == 0 && count % 2 == 0 -> sum += "$startGrey $piecefinal $end"
                    count2 % 2 == 0 && count % 2 != 0 -> sum += "$startWhite $piecefinal $end"
                    count2 % 2 != 0 && count % 2 == 0 -> sum += "$startWhite $piecefinal $end"
                }
                count2++
                posicao++
            } else {
                when {
                    count2 % 2 != 0 && count % 2 != 0 -> sum += "$startGrey   $end"
                    count2 % 2 == 0 && count % 2 == 0 -> sum += "$startGrey   $end"
                    count2 % 2 == 0 && count % 2 != 0 -> sum += "$startWhite   $end"
                    count2 % 2 != 0 && count % 2 == 0 -> sum += "$startWhite   $end"
                }
                count2++
                posicao++
            }
        }
        if (showLegend) {
            sum += blue
        }
        sum += "\n"
    }
    if (showLegend) {
        count2 = 0
        while (numColumns + 2 > count2) {
            sum += blue
            count2++
        }
        sum += "\n"
    }
    return sum
}

fun startNewGame(whitePlayer: String, blackPlayer: String, pieces: Array<Pair<String, String>?>,
                 totalPiecesAndTurn: Array<Int?>, numColumns: Int, numLines: Int, showLegend: Boolean = false,
                 showPieces: Boolean = false) {
    var atualCoord : String
    var atualCoordInt : Pair<Int, Int>? = null
    var nextCoord : String
    var nextCoordInt : Pair<Int, Int>? = null
    var validPieceSelected : Boolean
    var validTargetSelected : Boolean
    var validMove : Boolean
    var gameEnded : Boolean
    gameEnded = false
    val turnsPieces = arrayOf(totalPiecesAndTurn[0]!!,totalPiecesAndTurn[1]!!,totalPiecesAndTurn[2]!!)
    do {
        println(buildBoard(numColumns, numLines, showLegend, showPieces, pieces))
        validPieceSelected = false
        do {
            if (turnsPieces[2] == 0) {
                println("$whitePlayer, choose a piece (e.g 2D).\nMenu-> m;\n")
            } else {
                println("$blackPlayer, choose a piece (e.g 2D).\nMenu-> m;\n")
            }
            atualCoord = readLine().toString()
            if (atualCoord == "m") {
                validPieceSelected = true
            }
            if (atualCoord.length == 2) {
                atualCoordInt = getCoordinates(atualCoord)

                if (atualCoordInt != null) {
                    if(isCoordinateInsideChess(atualCoordInt!!,numColumns,numLines)) {
                        var selectedArrayPosition : Int
                        selectedArrayPosition = (atualCoordInt.first-1) * numColumns + atualCoordInt.second-1

                        if (pieces[selectedArrayPosition] != null &&
                            checkRightPieceSelected(pieces[selectedArrayPosition]!!.second,turnsPieces[2]!!)) {
                            validPieceSelected = true
                        }
                    }
                }
            }
            if (!validPieceSelected) {
                println(invalid)
                println(buildBoard(numColumns, numLines, showLegend, showPieces, pieces))
            }
        } while (!validPieceSelected)
        if (atualCoord == "m") {
            return
        }
        validTargetSelected = false
        do {
            if (turnsPieces[2] == 0) {
                println("$whitePlayer, choose a target piece (e.g 2D).\n" +
                        "Menu-> m;\n")
            } else {
                println("$blackPlayer, choose a target piece (e.g 2D).\n" +
                        "Menu-> m;\n")
            }
            nextCoord = readLine().toString()
            if (nextCoord == "m") {
                validTargetSelected = true
            }
            if (nextCoord.length == 2) {
                nextCoordInt = getCoordinates(nextCoord)
                if (nextCoordInt != null) {
                    if(isCoordinateInsideChess(nextCoordInt!!,numColumns,numLines)) {
                        validTargetSelected = true
                    }
                }
            }
            if (!validTargetSelected) {
                println(invalid)
                println(buildBoard(numColumns, numLines, showLegend, showPieces, pieces))
            }
        } while (!validTargetSelected)
        if (atualCoord == "m") {
            return
        }
        if (movePiece(pieces, numColumns, numLines, atualCoordInt!!, nextCoordInt!!, turnsPieces)) {
            validMove = true
            if (turnsPieces[0] == 0) {
                println("Congrats! $blackPlayer wins!")
                gameEnded = true
            }
            if (turnsPieces[1] == 0) {
                println("Congrats! $whitePlayer wins!")
                gameEnded = true
            }
        } else {
            validMove = false
            println(invalid)
        }
    } while (!validMove || !gameEnded)
}


fun createInitialBoard(numColumns: Int, numLines: Int): Array<Pair<String, String>?> {
    val dados = Array<Pair<String, String>?>(numColumns * numLines) { Pair(String(), String()) }
    when {
        numLines == 8 && numColumns == 8-> {
            dados[0] = Pair("T", "b")
            dados[1] = Pair("H", "b")
            dados[2] = Pair("B", "b")
            dados[3] = Pair("Q", "b")
            dados[4] = Pair("K", "b")
            dados[5] = Pair("B", "b")
            dados[6] = Pair("H", "b")
            dados[7] = Pair("T", "b")
            for (i in 8..15) {
                dados[i] = Pair("P", "b")
            }
            for (i in 16..47){
                dados[i] = null
            }
            for (i in 48..55) {
                dados[i] = Pair("P", "w")
            }
            dados[56] = Pair("T", "w")
            dados[57] = Pair("H", "w")
            dados[58] = Pair("B", "w")
            dados[59] = Pair("K", "w")
            dados[60] = Pair("Q", "w")
            dados[61] = Pair("B", "w")
            dados[62] = Pair("H", "w")
            dados[63] = Pair("T", "w")
        }
        numLines == 7 && numColumns == 7 ->{
            dados[0] = Pair("T", "b")
            dados[1] = Pair("H", "b")
            dados[2] = Pair("B", "b")
            dados[3] = Pair("K", "b")
            dados[4] = Pair("B", "b")
            dados[5] = Pair("H", "b")
            dados[6] = Pair("T", "b")
            for (i in 7..13) {
                dados[i] = Pair("P", "b")
            }
            for (i in 14..34){
                dados[i] = null
            }
            for (i in 35..41) {
                dados[i] = Pair("P", "w")
            }
            dados[42] = Pair("T", "w")
            dados[43] = Pair("H", "w")
            dados[44] = Pair("B", "w")
            dados[45] = Pair("K", "w")
            dados[46] = Pair("B", "w")
            dados[47] = Pair("H", "w")
            dados[48] = Pair("T", "w")
        }
        numLines == 7 && numColumns == 6 ->{
            dados[0] = Pair("T", "b")
            dados[1] = Pair("B", "b")
            dados[2] = Pair("Q", "b")
            dados[3] = Pair("K", "b")
            dados[4] = Pair("B", "b")
            dados[5] = Pair("H", "b")
            for (i in 6..11) {
                dados[i] = Pair("P", "b")
            }
            for (i in 12..29){
                dados[i] = null
            }
            for (i in 30..35) {
                dados[i] = Pair("P", "w")
            }
            dados[36] = Pair("T", "w")
            dados[37] = Pair("B", "w")
            dados[38] = Pair("K", "w")
            dados[39] = Pair("Q", "w")
            dados[40] = Pair("B", "w")
            dados[41] = Pair("H", "w")
        }
        numLines == 6 && numColumns == 6 ->{
            dados[0] = Pair("H", "b")
            dados[1] = Pair("B", "b")
            dados[2] = Pair("Q", "b")
            dados[3] = Pair("K", "b")
            dados[4] = Pair("B", "b")
            dados[5] = Pair("T", "b")
            for (i in 6..11) {
                dados[i] = Pair("P", "b")
            }
            for (i in 12..23){
                dados[i] = null
            }
            for (i in 24..29) {
                dados[i] = Pair("P", "w")
            }
            dados[30] = Pair("H", "w")
            dados[31] = Pair("B", "w")
            dados[32] = Pair("K", "w")
            dados[33] = Pair("Q", "w")
            dados[34] = Pair("B", "w")
            dados[35] = Pair("T", "w")
        }
        numLines == 4 && numColumns == 4->{
            dados[0] = null
            dados[1] = null
            dados[2] = Pair("T", "b")
            dados[3] = Pair("B", "b")
            for (i in 4..11){
                dados[i] = null
            }
            dados[12] = Pair("T", "w")
            dados[13] = Pair("Q", "w")
            dados[14] = null
            dados[15] = null
        }
        else -> return arrayOf()
    }
    return dados
}

fun createTotalPiecesAndTurn(numColumns: Int, numLines: Int): Array<Int?> {
    return when {
        numColumns == 8 && numLines == 8 -> arrayOf(16, 16, 0)
        numColumns == 7 && numLines == 7 -> arrayOf(14, 14, 0)
        numColumns == 6 && numLines == 7 -> arrayOf(12, 12, 0)
        numColumns == 6 && numLines == 6 -> arrayOf(12, 12, 0)
        numColumns == 4 && numLines == 4 -> arrayOf(2, 2, 0)
        else -> arrayOf()
    }
}

fun convertStringToUnicode(piece: String, color: String): String {
    return when {
        piece.toUpperCase() == "P" && color.toUpperCase() == "W" -> "\u2659"
        piece.toUpperCase() == "H" && color.toUpperCase() == "W" -> "\u2658"
        piece.toUpperCase() == "K" && color.toUpperCase() == "W" -> "\u2654"
        piece.toUpperCase() == "T" && color.toUpperCase() == "W" -> "\u2656"
        piece.toUpperCase() == "B" && color.toUpperCase() == "W" -> "\u2657"
        piece.toUpperCase() == "Q" && color.toUpperCase() == "W" -> "\u2655"
        piece.toUpperCase() == "P" && color.toUpperCase() == "B" -> "\u265F"
        piece.toUpperCase() == "H" && color.toUpperCase() == "B" -> "\u265E"
        piece.toUpperCase() == "K" && color.toUpperCase() == "B" -> "\u265A"
        piece.toUpperCase() == "T" && color.toUpperCase() == "B" -> "\u265C"
        piece.toUpperCase() == "B" && color.toUpperCase() == "B" -> "\u265D"
        piece.toUpperCase() == "Q" && color.toUpperCase() == "B" -> "\u265B"
        else -> " "
    }
}

fun getCoordinates(readText: String?): Pair<Int, Int>? {
    if (readText != null && readText.length == 2) {
        val numero = readText[0]
        var posicaoNumero: Int = 0
        when (numero) {
            '1' -> posicaoNumero = 1
            '2' -> posicaoNumero = 2
            '3' -> posicaoNumero = 3
            '4' -> posicaoNumero = 4
            '5' -> posicaoNumero = 5
            '6' -> posicaoNumero = 6
            '7' -> posicaoNumero = 7
            '8' -> posicaoNumero = 8
            else -> return null
        }
        val letra = readText[1]
        var posicaoLetra: Int = 0
        when (letra.toUpperCase()) {
            'A' -> posicaoLetra = 1
            'B' -> posicaoLetra = 2
            'C' -> posicaoLetra = 3
            'D' -> posicaoLetra = 4
            'E' -> posicaoLetra = 5
            'F' -> posicaoLetra = 6
            'G' -> posicaoLetra = 7
            'H' -> posicaoLetra = 8
            else -> return null
        }
        return Pair(posicaoNumero, posicaoLetra)
    }
    return null
}

fun isCoordinateInsideChess(coord: Pair<Int, Int>, numColumns: Int, numLines: Int): Boolean {
    if (coord.first > 0 && coord.second > 0 && coord.first <= numLines && coord.second <= numColumns) {
        return true
    }
    return false
}

fun checkRightPieceSelected(pieceColor: String, turn: Int): Boolean {
    if ((pieceColor == "w" && turn == 0) || (pieceColor == "b" && turn == 1)) {
        return true
    }
    return false
}


fun movePiece(pieces: Array<Pair<String, String>?>,
              numColumns: Int, numLines: Int,
              currentCoord: Pair<Int, Int>,
              targetCoord: Pair<Int, Int>,
              totalPiecesAndTurn: Array<Int>): Boolean {
    val selectedArrayPosition : Int
    selectedArrayPosition = (currentCoord.first-1) * numColumns + currentCoord.second-1

    if (isValidTargetPiece(pieces[selectedArrayPosition]!!, currentCoord, targetCoord, pieces, numColumns, numLines)) {
        val originArrayPosition : Int = (currentCoord.first-1) * numColumns + currentCoord.second-1
        val targetArrayPosition : Int = (targetCoord.first-1) * numColumns + targetCoord.second-1

        totalPiecesAndTurn[2] = 1 - totalPiecesAndTurn[2]
        if (pieces[targetArrayPosition] != null) {
            totalPiecesAndTurn[totalPiecesAndTurn[2]]--
        }
        pieces[targetArrayPosition] = pieces[originArrayPosition]
        pieces[originArrayPosition] = null
        return true
    } else {
        return false
    }
}

fun isValidTargetPiece(currentSelectedPiece: Pair<String, String>,
                       currentCoord: Pair<Int, Int>,
                       targetCoord: Pair<Int, Int>,
                       pieces: Array<Pair<String, String>?>,
                       numColumns: Int,
                       numLines: Int): Boolean {
    when (currentSelectedPiece.first) {
        "P" -> return isKnightValid(currentCoord,targetCoord,pieces,numColumns,numLines)
        "H" -> return isHorseValid(currentCoord,targetCoord,pieces,numColumns,numLines)
        "K" -> return isKingValid(currentCoord,targetCoord,pieces,numColumns,numLines)
        "T" -> return isTowerValid(currentCoord,targetCoord,pieces,numColumns,numLines)
        "B" -> return isBishopValid(currentCoord,targetCoord,pieces,numColumns,numLines)
        "Q" -> return isQueenValid(currentCoord,targetCoord,pieces,numColumns,numLines)
    }

    return false
}

fun isValidTargetPosition(currentCoord: Pair<Int, Int>, targetCoord: Pair<Int, Int>, pieces: Array<Pair<String, String>?>, numColumns: Int): Boolean {
    val originArrayPosition : Int = (currentCoord.first-1) * numColumns + currentCoord.second-1
    val targetArrayPosition : Int = (targetCoord.first-1) * numColumns + targetCoord.second-1

    val originPieceColor : String = pieces[originArrayPosition]!!.second
    val targetPiece : Pair<String, String>? = pieces[targetArrayPosition]

    if (targetPiece == null || (originPieceColor != targetPiece.second)) {
        return true
    } else {
        return false
    }
}

fun isKnightValid(currentCoord: Pair<Int, Int>, targetCoord: Pair<Int, Int>, pieces: Array<Pair<String, String>?>,
                  numColumns: Int, numLines: Int): Boolean {
    val verticalMove : Int = targetCoord.first - currentCoord.first
    val horizontalMove : Int = targetCoord.second - currentCoord.second

    if ((verticalMove == 1 || verticalMove == -1) && horizontalMove == 0) {
        if (isValidTargetPosition(currentCoord, targetCoord, pieces, numColumns)) {
            return true
        }
    }
    return false
}

fun isHorseValid(currentCoord: Pair<Int, Int>, targetCoord: Pair<Int, Int>, pieces: Array<Pair<String, String>?>,
                 numColumns: Int, numLines: Int): Boolean {
    val verticalMove : Int = targetCoord.first - currentCoord.first
    val horizontalMove : Int = targetCoord.second - currentCoord.second

    if (((verticalMove == 1 || verticalMove == -1) && (horizontalMove == 2 || horizontalMove == -2)) ||
        ((verticalMove == 2 || verticalMove == -2) && (horizontalMove == 1 || horizontalMove == -1))) {
        if (isValidTargetPosition(currentCoord, targetCoord, pieces, numColumns)) {
            return true
        }
    }
    return false
}

fun isKingValid(currentCoord: Pair<Int, Int>, targetCoord: Pair<Int, Int>, pieces: Array<Pair<String, String>?>,
                numColumns: Int, numLines: Int): Boolean {
    val verticalMove : Int = targetCoord.first - currentCoord.first
    val horizontalMove : Int = targetCoord.second - currentCoord.second

    if (verticalMove == 1 || verticalMove == -1 || horizontalMove == 1 || horizontalMove == -1) {
        if (isValidTargetPosition(currentCoord, targetCoord, pieces, numColumns)) {
            return true
        }
    }
    return false
}

fun isTowerValid(currentCoord: Pair<Int, Int>, targetCoord: Pair<Int, Int>, pieces: Array<Pair<String, String>?>,
                 numColumns: Int, numLines: Int): Boolean {
    val verticalMove : Int = targetCoord.first - currentCoord.first
    val horizontalMove : Int = targetCoord.second - currentCoord.second

    if ((verticalMove != 0 && horizontalMove == 0) || (verticalMove == 0 && horizontalMove != 0)) {
        if (isValidTargetPosition(currentCoord, targetCoord, pieces, numColumns)) {
            return true
        }
    }
    return false
}

fun isBishopValid(currentCoord: Pair<Int, Int>, targetCoord: Pair<Int, Int>, pieces: Array<Pair<String, String>?>,
                  numColumns: Int, numLines: Int): Boolean {
    val verticalMove : Int = targetCoord.first - currentCoord.first
    val horizontalMove : Int = targetCoord.second - currentCoord.second

    if (((verticalMove == horizontalMove) || (verticalMove == -horizontalMove)) && verticalMove != 0) {
        if (isValidTargetPosition(currentCoord, targetCoord, pieces, numColumns)) {
            return true
        }
    }
    return false
}

fun isQueenValid(currentCoord: Pair<Int, Int>, targetCoord: Pair<Int, Int>, pieces: Array<Pair<String, String>?>,
                 numColumns: Int, numLines: Int): Boolean {
    val verticalMove : Int = targetCoord.first - currentCoord.first
    val horizontalMove : Int = targetCoord.second - currentCoord.second

    if ((verticalMove != 0 && horizontalMove == 0) || (verticalMove == 0 && horizontalMove != 0) ||
        (((verticalMove == horizontalMove) || (verticalMove == -horizontalMove)) && verticalMove != 0)) {
        if (isValidTargetPosition(currentCoord, targetCoord, pieces, numColumns)) {
            return true
        }
    }
    return false
}
