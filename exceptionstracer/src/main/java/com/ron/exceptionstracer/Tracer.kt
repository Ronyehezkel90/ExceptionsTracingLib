package com.ron.exceptionstracer

import android.content.Context
import com.ron.exceptionstracer.network.ExceptionService
import com.ron.exceptionstracer.room.ExceptionEntity
import com.ron.exceptionstracer.room.ExceptionRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit


class Tracer private constructor(applicationContext: Context) {

    private val exceptionPostInterface = ExceptionService().getPostExceptionInterface()
    private val exceptionRepository = ExceptionRepository(applicationContext)
    private var disposable = Disposables.disposed()

    init {
        Timber.plant(Timber.DebugTree())
        createDbLogger(exceptionRepository)
        UncaughtExceptionsHandlerSetter()
        createServerLogger()
    }

    private fun createServerLogger() {
        disposable = Observable.interval(1, TimeUnit.MINUTES)
            .subscribeOn(Schedulers.io())
            .startWith(0)
            .flatMapSingle { exceptionRepository.getAllExceptions() }
            .flatMapSingle {
                if (it.isNotEmpty()) {
                    Timber.d(
                        "exceptions to send:\n %s",
                        it.map { exceptionEntity -> exceptionEntity.toString() + "\n" })
                    exceptionPostInterface.postException(it)
                        .onErrorResumeNext {
                            Timber.e("Failed to send, exception are still in room for next try in one minute.")
                            Single.just(false)
                        }
                } else {
                    Timber.d("no exceptions to send")
                    Single.just(false)
                }
            }
            .subscribe {
                if (it) {
                    Timber.d("Exceptions sent to server. Clean room.")
                    exceptionRepository.deleteAllExceptions()
                }
            }
    }

    companion object {

        private var tracer: Tracer? = null

        fun createInstance(applicationContext: Context): Tracer {
            if (tracer == null) {
                tracer = Tracer(applicationContext)
            }
            return tracer!!
        }

        private lateinit var exceptionRepository: ExceptionRepository

        internal fun createDbLogger(exceptionRepository: ExceptionRepository) {
            this.exceptionRepository = exceptionRepository
        }

        fun log(e: Throwable, description: String) {
            val message: String = if (e.message != null) e.message!! else "No message"
            val stackTrace = buildStackTraceString(e)
            val exceptionEntity =
                ExceptionEntity(
                    0,
                    e.javaClass.name,
                    message,
                    stackTrace,
                    description
                )
            exceptionRepository.insertException(exceptionEntity)
                .subscribe()
        }

        private fun buildStackTraceString(e: Throwable): String {
            var stackTraceString = ""
            for (stackTraceRow in e.stackTrace) {
                stackTraceString += "$stackTraceRow\n"
            }
            return stackTraceString
        }
    }

}