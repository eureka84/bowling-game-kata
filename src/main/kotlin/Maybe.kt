sealed class Maybe<out T> {
    fun <R> map(f: (T) -> R): Maybe<R> = when (this) {
        is None -> empty()
        is Just -> just(f(this.value))
    }

    fun <R> fold(ifEmpty: () -> R, ifSomething: (T) -> R) = when (this) {
        is None -> ifEmpty()
        is Just -> ifSomething(this.value)
    }

    companion object {
        fun <T> just(value: T): Maybe<T> = Just(value)
        fun <T> empty(): Maybe<T> = None
    }
}

object None : Maybe<Nothing>()
data class Just<T>(val value: T): Maybe<T>()