# Beer Box

For this project I've decided to follow a modular solution, using **MVVM Pattern**, **coroutines**, **LiveData**, 
**Dependency Injection via Hilt** and **NavigationComponent**.

## Modularization

I've decided to separate roles, creating a main *app* module that contains all the classes used to run the app 
(viewModels, presentation layer classes, modules for dependency injection), a *basecomponents* module, 
that provides abstract classes and core components, a *models* module, in which are contained all DTOs and entities data classes,
and a *network* module, that contains all classes used in network calls to *punkapi* apis.

## MVVM Pattern

I chose this architectural pattern because ViewModels are bounded to view class' lifecycle, 
so communication between presentation and data layer is more efficient. 
Also, it's the easiest architectural pattern to implement, without acting much on base classes, as they are already provided by Android.

## Dependency Injection

I chose to use dependency injection as it's more efficient and it simplifies usage of classes, 
even if in this particular project is not used so much, as there aren't too many classes that require dependencies.

## Navigation Component

This is the standard component, suggested by Android documentation, to manage navigation between activities and fragment; 
I chose to use it although I don't implement navigation of any kind, because specifications didn't require it. 
I think it's a good practice to implement it as it can be useful in future developments.