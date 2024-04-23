package kotlinx.coroutines

@SubclassOptInRequired(BrittleForInheritanceCoroutinesApi::class)
public actual abstract class CloseableCoroutineDispatcher actual constructor() : CoroutineDispatcher() {
    public actual abstract fun close()
}
