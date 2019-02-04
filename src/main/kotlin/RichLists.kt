fun <T> List<T>.tail(): List<T> =
        if (this.isEmpty()) throw IllegalAccessException("Tail of empty list") else this.subList(1, this.size)

fun <T> List<T>.head(): T =
        if (this.isEmpty()) throw IllegalAccessException("Head of empty list") else this[0]