package com.karumi.ui.view

import android.os.Bundle
import com.github.salomonbrys.kodein.Kodein.Module
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.karumi.data.repository.SuperHeroRepository
import com.karumi.domain.model.SuperHero
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test
import org.mockito.Mock

class DetailActivityTest : AcceptanceTest<SuperHeroDetailActivity>(SuperHeroDetailActivity::class.java) {

    @Mock
    private lateinit var repository: SuperHeroRepository

    @Test
    fun showDetailSuperHero() {

        givenSuperHeroByName()

        val bundle = Bundle().apply {
            putString("super_hero_name_key", "ChuzasMan")
        }

        val activity = startActivity(bundle)

        compareScreenshot(activity)
    }


    private fun givenThereAreSomeSuperHeroes(
        numberOfSuperHeroes: Int = 1,
        avengers: Boolean = false,
        heroName: String? = null
    ): List<SuperHero> {
        val superHeroes = IntRange(0, numberOfSuperHeroes - 1).map { id ->
            val superHeroName = heroName.let { it } ?: "SuperHero - $id"
            val superHeroDescription = "Description Super Hero - $id"
            SuperHero(
                superHeroName, null, avengers,
                superHeroDescription
            )
        }

        //whenever(repository.getAllSuperHeroes()).thenReturn(superHeroes)
        return superHeroes
    }

    private fun givenSuperHeroByName() {
        whenever(repository.getByName("ChuzasMan")).thenReturn(givenThereAreSomeSuperHeroes(1).first())
    }

    override val testDependencies = Module(allowSilentOverride = true) {
        bind<SuperHeroRepository>() with instance(repository)
    }
}