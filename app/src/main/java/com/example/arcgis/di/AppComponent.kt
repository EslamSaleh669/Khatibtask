package com.example.arcgis.di
import dagger.Component


@Component(modules = [
    ViewModelFactoryModule::class
])
interface AppComponent {
   // fun inject(weatherListFragment: WeatherListFragment)

}