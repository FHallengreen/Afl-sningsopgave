package com.example.quizapplication.model

/**
 * Enum classes for the different options in the app
 * These are used to convert the user input to the API call
 */
enum class Category(val id: Int, val displayName: String) {
    GeneralKnowledge(9,"General Knowledge"),
    ScienceComputers(18,"Science: Computers"),
    Geography(22,"Geography"),
    Animals(27,"Animals"),
    Celebrities(26,"Celebrities")
}

enum class Difficulty(val level: String) {
    Easy("Easy"),
    Medium("Medium"),
    Hard("Hard")
}
