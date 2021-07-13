package com.young.base

import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

typealias Mapper<I, O> = (I) -> O

class BaseMapper<I : Any, O : Any>(
    private val inPut: KClass<I>,
    private val outPut: KClass<O>
) : Mapper<I, O> {
    companion object {
        inline operator fun <reified I : Any, reified O : Any> invoke() =
            BaseMapper(I::class, O::class)

        fun <I : Any, O : Any> toSet(mapper: Mapper<I, O>) = object : Mapper<Set<I>, Set<O>> {
            override fun invoke(objectData: Set<I>): Set<O> =
                objectData.map(mapper).toSet()
        }

        fun <I : Any, O : Any> setList(mapper: Mapper<I, O>) = object : Mapper<List<I>, List<O>> {
            override fun invoke(data: List<I>): List<O> = data.map(mapper)
        }
    }

    val outConstructor = outPut.primaryConstructor!!
    val inProperty by lazy { inPut.memberProperties.associateBy { it.name } }
    val fieldMappers = mutableMapOf<String, Mapper<Any, Any>>()

    override fun invoke(data: I): O {
        return with(outConstructor) {
            callBy(parameters.associateWith {
                argFor(it, data)
            })
        }
    }

    private fun argFor(param: KParameter, data: I): Any? {
        val value = inProperty[param.name]?.get(data) ?: return null
        return fieldMappers[param.name]?.invoke(value) ?: value
    }

    inline fun <reified S : Any, reified T : Any> register(
        parameterName: String,
        crossinline mapper: Mapper<S, T>
    ): BaseMapper<I, O> = apply {
        fieldMappers[parameterName] = object : Mapper<Any, Any> {
            override fun invoke(data: Any): Any {
                return mapper.invoke(data as S)
            }
        }
    }
}