sealed class Maybe<out T> {
    fun <R> map(f: (T) -> R): Maybe<R> = when (this) {
        is Empty -> empty()
        is Just -> just(f(this.value))
    }

    fun <R> fold(ifEmpty: () -> R, ifSomething: (T) -> R) = when (this) {
        is Empty -> ifEmpty()
        is Just -> ifSomething(this.value)
    }

    companion object {
        fun <T> just(value: T): Maybe<T> = Just(value)
        fun <T> empty(): Maybe<T> = Empty
    }
}

object Empty : Maybe<Nothing>()
data class Just<T>(val value: T) : Maybe<T>()

fun <T> Maybe<T>.orElse(tSupplier: () -> T) = when (this) {
    is Empty -> tSupplier()
    is Just -> this.value
}