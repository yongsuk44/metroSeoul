package com.young.metro

import com.young.domain.model.DomainStationNameAndMapXY
import com.young.domain.model.DomainTrailCodeAndLineCode
import org.junit.Test

class groupbyTest {

    val list = listOf<DomainStationNameAndMapXY>(
        DomainStationNameAndMapXY(
            DataFactory.getRandomString(),
            DomainTrailCodeAndLineCode(
                DataFactory.getRandomString(),
                DataFactory.getRandomString()
            ),
            DataFactory.getRandomString(),
            0.3,
            0.6
        ),
        DomainStationNameAndMapXY(
            DataFactory.getRandomString(),
            DomainTrailCodeAndLineCode(
                DataFactory.getRandomString(),
                DataFactory.getRandomString()
            ),
            DataFactory.getRandomString(),
            DataFactory.getRandomDouble(),
            DataFactory.getRandomDouble()
        ),
        DomainStationNameAndMapXY(
            DataFactory.getRandomString(),
            DomainTrailCodeAndLineCode(
                DataFactory.getRandomString(),
                DataFactory.getRandomString()
            ),
            DataFactory.getRandomString(),
            0.3,
            0.6
        ),
        DomainStationNameAndMapXY(
            DataFactory.getRandomString(),
            DomainTrailCodeAndLineCode(
                DataFactory.getRandomString(),
                DataFactory.getRandomString()
            ),
            DataFactory.getRandomString(),
            DataFactory.getRandomDouble(),
            DataFactory.getRandomDouble()
        )
    )

    @Test
    fun test() {


        list.groupBy {
            it.mapX to it.mapY
        }.map {
            it.value.let {
                if (it.size >= 2) {

                    sortdata(
                        it.first().stinCd,
                        it.first().stationName,
                        it.map { it.trailCodeAndLineCode.railOprIsttCd },
                        it.map { it.trailCodeAndLineCode.lnCd }
                    )
                } else {
                    it.first().run {
                        sortdata(
                            stinCd,
                            stationName,
                            listOf(trailCodeAndLineCode.railOprIsttCd),
                            listOf(trailCodeAndLineCode.lnCd)
                        )
                    }
                }
            }
        }.run {
            this
        }
    }

    data class sortdata(
        val stinCd: String,
        val stationName: String,
        val trailCode: List<String>,
        val lineCode: List<String>
    )
}