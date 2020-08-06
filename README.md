# JetpackTestDemo（Google推荐的Jetpack架构组件学习，及MVVM项目架构搭建）


### Jetpack架构组件介绍

#### 1. LifeCycle:
用于创建可感知(Activity,Fragment,Service,Application)生命周期的自定义组件类，解耦的同时也避免造成内存泄露。
原理是通过LifeCycleOwner（被观察者）和LifeCycleObserver(观察者)这两个类实现观察者模式，完成对页面生命周期的监听。   

###### Activity&Fragment   
新版本Activity/Fragment中默认实现了LifeCycleOwner，所以自定义组件直接实现LifeCycleObserver接口即可，并在需要感知生命周期的方法上添加@OnLifecycleEvent(Lifecycle.Event.ON_XXX)注解,
当页面声明周期发生变化会自动调用这些方法。最后在Activity/Fragment中通过调用getLifecycle().addObserver(xxx自定义组件类)，将观察者和被观察者绑定起来完成生命周期的检测。

###### Service
Service没有实现LifeCycleOwner接口,要想感知Service生命周期,需要使用他的实现了LifeCycleOwner接口的子类LifecycleService。所以我们创建自己的Service然后继承LifecycleService即可，
其他用法与Activity一样。

###### Application
使用ProcessLifecycleOwner可以感知应用程序的生命周期，像Activity、Service一样先自定义一个类并实现LifeCycleObserver接口，在方法上添加具体要感知的生命周期注解，然后在自定义Application的
onCreate()方法中调用ProcessLifecycleOwner.get().getLifecycle().addObserver(new xxx自定义组件类())即可。根据Application不同生命周期的回调可以实现应用前后台状态切换的监听。

#### 2. Navigation:
主要适用于单Activity包含多个Fragment的情况，用于解决Fragment切换，不同Fragment中的Appbar管理,Fragment之间切换动画和参数传递等问题。

##### Navigation主要包含如下元素：

###### Navigation Graph
它是一种新的xml类型的资源文件，需在res目录下创建navigation文件夹，然后在该文件夹右键New->Navigation Resource File创建根标签为navigation的xml文件。在该文件中添加所有的要切换fragment,
配置activity初始显示fragment的app:startDestination属性,配置某个fragment跳转到另一个fragment的action动作标签(包含android:id动作id,app:destination跳转的目的地,enterAnim、exitAnim、
popEnterAnim、popExitAnim跳转动画)。

###### NavHostFragment
它是一个在Activity的xml中配置的特殊的fragment,它相当于Navigation Graph中配置的其他fragment的容器。设置android:id属性，设置android:name="androidx.navigation.fragment.NavHostFragment"
属性，设置app:defaultNavHost="true"属性(自动处理系统返回键)，设置app:navGraph="@navigation/xxx"属性指向创建的Navigation Graph。

###### NavController
它是一个java对象，用于在Activity代码中对Fragment切换事件进行监听，用于在Fragment代码中实现Navigation Graph中配置有跳转逻辑action的具体切换工作。   
Activity中监听fragment切换方法：Navigation.findNavController(this,NavHostFragment配置的id).addOnDestinationChangedListener(new NavController.OnDestinationChangedListener(){...})。   
Fragment中切换动作实现：Navigation.findNavController(view).navigate(Navigation Graph配置的action id，跳转bundle参数);   
或者：Navigation.createNavigateOnClickListener(Navigation Graph配置的action id，跳转bundle参数)。

###### 它们三者的关系是：NavController负责切换Navigation Graph中配置的某个fragment并把它展示在NavHostFragment上。fragment跳转可以配合使用safe args插件更安全的实现参数传递

##### NavigationUI介绍：    
主要为了使Navigation和App Bar相结合，使AppBar中的菜单按钮能够和Navigation Graph中配置的fragment关联起来，并实现点击菜单按钮页面跳转。

##### Navigation DeepLink介绍：    
主要为了点击通知通过PendingIntent方式跳转到指定通知打开fragment，或者通过URL方式当浏览器加载某个页面提示在应用内打开，跳转到指定的页面

#### 3. ViewModel:
作为视图(View)和数据模型(Model)之间的桥梁，专门用于存放程序页面所需的数据，使页面只负责数据展示和接收用户交互动作。   
ViewModel独立于页面存在，屏幕旋转不会使ViewModel销毁，ViewModel所持有的数据仍然存在。所以使用ViewModel时不要传入context相关的引用。   
ViewModel使用方法也很简单，自定义一个类然后继承ViewModel，由于ViewModel是抽象类所以要实现一个onCleared()方法，该方法会在与之对应的Activity销毁时被系统自动调用，主要可用于资源释
放相关操作。然后在相关Activity中通过new ViewModelProvider(this).get(自定义ViewModel.class)方法实例化ViewModel，此处的this指代的不是activity/context,指代的是ViewModelStoreOwner
因为上层的Activity(Fragment也是)默认实现了ViewModelStoreOwner接口。   
AndroidViewModel是ViewModel的子类，它构造函数中带有Application，如在ViewModel中需要使用Context可以使用AndroidViewModel。

#### 4. LiveData:
LiveData通常配合ViewModel使用，将LiveData放在ViewModel中，包装ViewModel中对外提供的数据。   
LiveData本身相当于一个数据容器，并且是可被观察的数据容器类，它将数据包装起来成为被观察者，当数据发生变化观察者就能获得通知。所以ViewModel用来存放页面相关的数据，而LiveData用来包装
数据使之能够感知数据变化，结合起来就变成了在页面中获得ViewModel中用LiveData包装起来的数据，当数据发生变化页面自动收到通知，Amazing！   
由于LiveData是抽象类无法直接使用，我们可以使用它的直接子类MutableLiveData，在自定义ViewModel中定义MutableLiveData<数据>对象,并暴露对外获取的方法，在页面上通过ViewModelProvider获取
自定义的ViewModel对象，然后通过暴露的方法获取MutableLiveData<数据>对象，最后通过liveData.observe(this, new Observer<Integer>() {...}获取实时变化的数据，也可以通过liveData.setValue()在UI线程更改liveData中包装的数据，或通过liveData.postValue()在非UI线程中更改数据。   
liveData.observe()方法中第一个参数指的是LifecycleOwner，第二个参数是Observer，方法内部调用的是lifecycleOwner.getLifecycle().addObserver(lifecycleObserver)，将observer和页面的生命周期关联起来。所以liveData可以感知生命周期，在页面处于活动状态时才可以收到LiveData的数据变化通知，销毁状态下自动清除与页面的关联。   
liveData.observeForever()方法可以使页面不管处于何种状态下都能收到数据变化通知，但是在恰当位置要加上与之对应的liveData.removeObserver()方法，以免造成页面无法回收。

#### 5. Room:
作为Android官方的ORM数据库，与其他开源的ORM库一样都是在Sqlite基础上做进一步封装，目的是简化Sqlite操作数据库代码编写的复杂度，可以方便的对数据库进行创建、升级、CRUD表。




