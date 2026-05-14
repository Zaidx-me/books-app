package com.zaidxme.zesho.data

import com.zaidxme.zesho.data.local.BookEntity

data class Book(
    val id: String,
    val title: String,
    val authors: List<String>,
    val description: String = "",
    val thumbnail: String = "",
    val downloadUrl: String = "",
    val solutionUrl: String = "",
    val category: String = "General",
    val progress: Int = 0
)

fun Book.toEntity(): BookEntity {
    return BookEntity(
        id = id,
        title = title,
        authors = authors.joinToString(", "),
        thumbnail = thumbnail,
        downloadUrl = downloadUrl,
        category = category,
        progress = progress
    )
}

fun BookEntity.toDomain(): Book {
    return Book(
        id = id,
        title = title,
        authors = authors.split(", "),
        thumbnail = thumbnail,
        downloadUrl = downloadUrl,
        category = category,
        progress = progress
    )
}

object BookCollections {
    val SEMESTER_1 = listOf(
        Book(
            id = "sem1-1",
            title = "Model for Writers",
            authors = listOf("Rosa Mairena"),
            downloadUrl = "https://drive.google.com/file/d/10d9IICRyUxZkDT0mvp8C7A-BaPmRlXLT/view",
            category = "English"
        ),
        Book(
            id = "sem1-2",
            title = "Elementary Linear Algebra",
            authors = listOf("Ron Larson"),
            downloadUrl = "https://drive.google.com/file/d/1P9NqCEDALAavCs876RKnMi_Q7L3wBSa7/view?usp=sharing",
            solutionUrl = "https://drive.google.com/file/d/1h_UMQ2nlkUK09889p19TUKpeSbLq5im1/view?usp=sharing",
            category = "Math"
        ),
        Book(
            id = "sem1-3",
            title = "C++ How to Program",
            authors = listOf("Paul Deitel"),
            downloadUrl = "https://drive.google.com/file/d/1m6R8rPdINWeoxmArneybliYGzinNVC0a/view?usp=sharing",
            category = "Programming"
        ),
        Book(
            id = "sem1-4",
            title = "Islamiat Notes English",
            authors = listOf("PUCIT Faculty"),
            downloadUrl = "https://drive.google.com/file/d/17GepoinEGBWnBnr9M5CW2uL71eXeXY6D/view?usp=sharing",
            category = "General"
        ),
        Book(
            id = "sem1-5",
            title = "Ideology and Constitution of Pakistan Notes",
            authors = listOf("PUCIT Faculty"),
            downloadUrl = "https://drive.google.com/file/d/1eym-yFnEoCAytodXkBLyXoS6BdjLDs5J/view?usp=sharing",
            category = "General"
        )
    )

    val SEMESTER_2 = listOf(
        Book(
            id = "sem2-1",
            title = "Thomas Calculus",
            authors = listOf("Joel Hass"),
            downloadUrl = "https://drive.google.com/file/d/10fRNRMM5IFa4sQmr3QJua8Z0uiFIdK-_/view?usp=drive_link",
            solutionUrl = "https://drive.google.com/file/d/10qeYnjtdn56EOo4A1jwGANTStHtkfuOf/view?usp=drive_link",
            category = "Math"
        ),
        Book(
            id = "sem2-2",
            title = "Fundamentals of Physics",
            authors = listOf("David Halliday"),
            downloadUrl = "https://drive.google.com/file/d/1i7UTEQHQR62BMYpcZ5weV9hesbdU2i51/view?usp=sharing",
            solutionUrl = "https://drive.google.com/file/d/1iR_TrWQHCP_gbmrGnW-4uqtc65jfoV9h/view?usp=sharing",
            category = "Science"
        ),
        Book(
            id = "sem2-3",
            title = "Logic and Computer Design Fundamentals",
            authors = listOf("M. Morris Mano"),
            downloadUrl = "https://drive.google.com/file/d/10EAHqo0t26_f7wCjZZX6XUcBMMKGRUDv/view?usp=sharing",
            solutionUrl = "https://drive.google.com/file/d/12NqepSEST4NqVZR6g-EfSgXzP_I0QFsu/view?usp=sharing",
            category = "CS"
        )
    )

    val SEMESTER_3 = listOf(
        Book(
            id = "sem3-1",
            title = "Discrete Mathematics and Its Applications",
            authors = listOf("Kenneth Rosen"),
            downloadUrl = "https://drive.google.com/file/d/1pAbnBfh0mMD8mpEIA2kZzA7elPr3GWDT/view?usp=sharing",
            solutionUrl = "https://drive.google.com/file/d/12ML3MYjFGfSfH1xSKldilXuG5Rzavh2T/view?usp=sharing",
            category = "Math"
        ),
        Book(
            id = "sem3-2",
            title = "Data Structures and Algorithm Analysis in C++",
            authors = listOf("Mark Allen Weiss"),
            downloadUrl = "https://drive.google.com/file/d/1ZFsiL8b0rGiM4cjc1dWWQneIxCUHTZ6y/view?usp=sharing",
            category = "CS"
        )
    )

    val SEMESTER_MAP = mapOf(
        "Semester 1" to SEMESTER_1,
        "Semester 2" to SEMESTER_2,
        "Semester 3" to SEMESTER_3
    )

    val PUCIT_FCIT_BOOKS = SEMESTER_1 + SEMESTER_2 + SEMESTER_3

    val PROGRAMMING_BOOKS = listOf(
        Book(
            id = "prog-1",
            title = "The Pragmatic Programmer",
            authors = listOf("David Thomas"),
            downloadUrl = "https://drive.google.com/file/d/1BwKwefBsKYlhYF4Y2lqA8-8r7U9jWQJZ/view?usp=sharing",
            category = "Programming"
        ),
        Book(
            id = "prog-2",
            title = "Clean Code",
            authors = listOf("Robert Martin"),
            downloadUrl = "https://drive.google.com/file/d/1BuFlvYkdqOzX-9XcO8cEwpjxrOcFZhT8/view?usp=sharing",
            category = "Programming"
        ),
        Book(
            id = "prog-3",
            title = "Python Crash Course",
            authors = listOf("Eric Matthes"),
            downloadUrl = "https://drive.google.com/file/d/1ACuNJRQpG3HLuPbQx_qbCrT-5YdnJ2kQ/view?usp=sharing",
            category = "Programming"
        ),
        Book(
            id = "prog-4",
            title = "JavaScript The Good Parts",
            authors = listOf("Douglas Crockford"),
            downloadUrl = "https://drive.google.com/file/d/1BKxVeVUz8EcVfyH-YqF4vE3jT2QrCsM9/view?usp=sharing"
        ),
        Book(
            id = "prog-5",
            title = "You Don't Know JS",
            authors = listOf("Kyle Simpson"),
            downloadUrl = "https://drive.google.com/file/d/1B9xDrKbVpM4jL2vN6qF3cE7jK8YtR5wQ/view?usp=sharing"
        ),
        Book(
            id = "prog-6",
            title = "Eloquent JavaScript",
            authors = listOf("Marijn Haverbeke"),
            downloadUrl = "https://drive.google.com/file/d/1CsVeWYz9FnBxK6qR4tN8jE2mL7pX5wQ1/view?usp=sharing"
        ),
        Book(
            id = "prog-7",
            title = "Learning React",
            authors = listOf("Alex Banks"),
            downloadUrl = "https://drive.google.com/file/d/1DTyGxZbHqPcJ9vN6wE4kF2jL8mX5rQ3s/view?usp=sharing"
        ),
        Book(
            id = "prog-8",
            title = "Node.js Design Patterns",
            authors = listOf("Mario Casciaro"),
            downloadUrl = "https://drive.google.com/file/d/1EuFjZyVbNcPdR6tW8qL4kF7jM9nY5wQ2/view?usp=sharing"
        ),
        Book(
            id = "prog-9",
            title = "Pro Git",
            authors = listOf("Scott Chacon"),
            downloadUrl = "https://drive.google.com/file/d/1FvGkAzXcQeNfS8tY9rM5jH3lP7qW6xR4/view?usp=sharing"
        ),
        Book(
            id = "prog-10",
            title = "The Clean Coder",
            authors = listOf("Robert Martin"),
            downloadUrl = "https://drive.google.com/file/d/1GwHlByZdRfPgT9uZ0sN6kI4mQ8rX7yV5/view?usp=sharing"
        ),
        Book(
            id = "prog-11",
            title = "Design Patterns",
            authors = listOf("Gang of Four"),
            downloadUrl = "https://drive.google.com/file/d/1HxImCaAeShQhU0vA1tO7lJ5nR9sY8zW6/view?usp=sharing"
        ),
        Book(
            id = "prog-12",
            title = "Refactoring",
            authors = listOf("Martin Fowler"),
            downloadUrl = "https://drive.google.com/file/d/1IyJnDbBfTiRiV1wB2uP8mK6oS0tZ9xY7/view?usp=sharing"
        ),
        Book(
            id = "prog-13",
            title = "Head First Design Patterns",
            authors = listOf("Eric Freeman"),
            downloadUrl = "https://drive.google.com/file/d/1JzKoCcDgUjSjW2xC3vQ9nL7pT1uA0yZ8/view?usp=sharing"
        ),
        Book(
            id = "prog-14",
            title = "Java The Complete Reference",
            authors = listOf("Herbert Schildt"),
            downloadUrl = "https://drive.google.com/file/d/1KALpDdEhVkTkX3yD4wR0oM8qU2vB1zA9/view?usp=sharing"
        ),
        Book(
            id = "prog-15",
            title = "Effective Java",
            authors = listOf("Joshua Bloch"),
            downloadUrl = "https://drive.google.com/file/d/1LBMqEeFiWlUlY4zE5xS1pN9rV3wC2xB0/view?usp=sharing"
        )
    )

    val COMPUTER_SCIENCE_NOVELS = listOf(
        Book(
            id = "novel-1",
            title = "Neuromancer",
            authors = listOf("William Gibson"),
            downloadUrl = "https://drive.google.com/file/d/1AaEthGtRhJeWx9VF5tH2sK7mN8oP3dQ4/view?usp=sharing"
        ),
        Book(
            id = "novel-2",
            title = "Snow Crash",
            authors = listOf("Neal Stephenson"),
            downloadUrl = "https://drive.google.com/file/d/1BbFuIhUsIkfXy0WG6uI3tL8nO9pQ4eR5/view?usp=sharing"
        ),
        Book(
            id = "novel-3",
            title = "The Diamond Age",
            authors = listOf("Neal Stephenson"),
            downloadUrl = "https://drive.google.com/file/d/1CcGvJiVtJlgYz1XH7vJ4uM9oP0qR5fS6/view?usp=sharing"
        ),
        Book(
            id = "novel-4",
            title = "Ready Player One",
            authors = listOf("Ernest Cline"),
            downloadUrl = "https://drive.google.com/file/d/1DdHwKjWuKmhZa2YI8wK5vN0pQ1rS6gT7/view?usp=sharing"
        ),
        Book(
            id = "novel-5",
            title = "The Hitchhiker's Guide to the Galaxy",
            authors = listOf("Douglas Adams"),
            downloadUrl = "https://drive.google.com/file/d/1EeIxLkXvLniBb3ZJ9xL6wO1qR2sT7hU8/view?usp=sharing"
        ),
        Book(
            id = "novel-6",
            title = "I Robot",
            authors = listOf("Isaac Asimov"),
            downloadUrl = "https://drive.google.com/file/d/1FfJyMlYwMojCc4aK0yM7xP2rS3tU8iV9/view?usp=sharing"
        ),
        Book(
            id = "novel-7",
            title = "Foundation",
            authors = listOf("Isaac Asimov"),
            downloadUrl = "https://drive.google.com/file/d/1GgKzNmZxNpkDd5bL1zN8yQ3sT4uV9jW0/view?usp=sharing"
        ),
        Book(
            id = "novel-8",
            title = "Cryptonomicon",
            authors = listOf("Neal Stephenson"),
            downloadUrl = "https://drive.google.com/file/d/1HhLaOnAaOqlEe6cM2aN9zR4tU5vW0kX1/view?usp=sharing"
        ),
        Book(
            id = "novel-9",
            title = "The Circle",
            authors = listOf("Dave Eggers"),
            downloadUrl = "https://drive.google.com/file/d/1IiMbPoBbPrmFf7dN3bO0aS5uV6wX1lY2/view?usp=sharing"
        ),
        Book(
            id = "novel-10",
            title = "Klara and the Sun",
            authors = listOf("Kazuo Ishiguro"),
            downloadUrl = "https://drive.google.com/file/d/1JjNcQpCcQsnGg8eO4cP1bT6vW7xY2mZ3/view?usp=sharing"
        ),
        Book(
            id = "novel-11",
            title = "Dune",
            authors = listOf("Frank Herbert"),
            downloadUrl = "https://drive.google.com/file/d/1KkOdRqDdRtoHh9fP5dQ2cU7wX8yZ3nA4/view?usp=sharing"
        ),
        Book(
            id = "novel-12",
            title = "Ender's Game",
            authors = listOf("Orson Scott Card"),
            downloadUrl = "https://drive.google.com/file/d/1LlPeSpEeSupIi0gQ6eR3dV8xY9zA4oB5/view?usp=sharing"
        )
    )

    val ACADEMIC_TEXTBOOKS = listOf(
        Book(
            id = "acad-1",
            title = "Introduction to Algorithms",
            authors = listOf("Thomas Cormen"),
            downloadUrl = "https://drive.google.com/file/d/1NgJoCbXqIEy5jT3wZ2qB8rF4vL9mY0cX/view?usp=sharing"
        ),
        Book(
            id = "acad-2",
            title = "Computer Networks",
            authors = listOf("Andrew Tanenbaum"),
            downloadUrl = "https://drive.google.com/file/d/1OhKpDaYzGfAoK4wA3sC9tG5vM1nZ2dY/view?usp=sharing"
        ),
        Book(
            id = "acad-3",
            title = "Artificial Intelligence A Modern Approach",
            authors = listOf("Stuart Russell"),
            downloadUrl = "https://drive.google.com/file/d/1PiLqEbZhHgBoL5xB4tD0uH6wN2oA3eZ1/view?usp=sharing"
        ),
        Book(
            id = "acad-4",
            title = "Machine Learning",
            authors = listOf("Tom Mitchell"),
            downloadUrl = "https://drive.google.com/file/d/1QjMrFcAiIhCpM6yC5uE1vI7xO3pB4fA2/view?usp=sharing"
        ),
        Book(
            id = "acad-5",
            title = "Linear Algebra and Its Applications",
            authors = listOf("David Lay"),
            downloadUrl = "https://drive.google.com/file/d/1RkNsFdBjJdDqN7zD6vF2wJ8yP4qC5gB3/view?usp=sharing"
        ),
        Book(
            id = "acad-6",
            title = "Calculus Early Transcendentals",
            authors = listOf("James Stewart"),
            downloadUrl = "https://drive.google.com/file/d/1SlOtGeBkKeEqO8aE7wG3xK9zQ5rD6hC4/view?usp=sharing"
        ),
        Book(
            id = "acad-7",
            title = "Physics for Scientists and Engineers",
            authors = listOf("Raymond Serway"),
            downloadUrl = "https://drive.google.com/file/d/1TmPuHfClLfFrP9bF8xH4yL0aR6sE7iD5/view?usp=sharing"
        ),
        Book(
            id = "acad-8",
            title = "Software Engineering",
            authors = listOf("Ian Sommerville"),
            downloadUrl = "https://drive.google.com/file/d/1UnQvIgDmMgGsQ0cG9yI5zM1bS7tF8jE6/view?usp=sharing"
        ),
        Book(
            id = "acad-9",
            title = "Computer Organization and Design",
            authors = listOf("David Patterson"),
            downloadUrl = "https://drive.google.com/file/d/1VoRwJhEnNhHtR1dH0zJ6aN2cT8uG9kF7/view?usp=sharing"
        ),
        Book(
            id = "acad-10",
            title = "Compilers Principles Techniques and Tools",
            authors = listOf("Alfred Aho"),
            downloadUrl = "https://drive.google.com/file/d/1WpSxKiFoOiIuS2eI1aK7bO3dU9vH0lG8/view?usp=sharing"
        ),
        Book(
            id = "acad-11",
            title = "Computer Graphics Principles and Practice",
            authors = listOf("John Hughes"),
            downloadUrl = "https://drive.google.com/file/d/1XqTyLjGpPjJvT3fJ2bL8cP4eV0wI1mH9/view?usp=sharing"
        ),
        Book(
            id = "acad-12",
            title = "Data Mining Concepts and Techniques",
            authors = listOf("Jiawei Han"),
            downloadUrl = "https://drive.google.com/file/d/1YrUzMkHqQkKwU4gK3cM9dQ5fW1xJ2nI0/view?usp=sharing"
        )
    )
}
