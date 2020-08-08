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
##### Room主要包含如下元素：
###### @Entity注解
定义一个模型类并在类上方添加@Entity注解，表示此类对应数据库中一张表，可在注解内设置tableName值即表的名字，不设置默认表名和类名相同，与@Entity配合使用的是修饰成员变量的@ColumnInfo
注解，表示表中的字段，注解内可设置name值表示字段名，同样不设置默认为成员变量名，设置typeAffinity值表示字段类型，此外还有@PrimaryKey注解表示此字段为主键，注解内的autoGenerate值表示自增，@NonNull注解表示此字段非空，@Ignore注解表示告诉Room忽略该字段或方法。
###### @Dao注解
定义一个接口并在接口上方添加@Dao注解，表示此接口为操作Entity表的Dao(Data Access Objects)对象，一个Entity表对应一个Dao对象，主要用于对表进行CRUD。Dao对象是在数据库初始化时通过数据
库声明类的抽象方法获得的。与@Dao注解配合使用的是修饰接口方法的@Insert、@Delete、@Update、@Query增删改查注解，@Query注解内要写上查询语句 eg @Query("SELECT * FROM 表名")，@Insert和
@Update注解内可设置onConflict值表示当出现冲突时的处理策略，默认为OnConflictStrategy.ABORT即终止并回滚事务，此外增删改方法需要声明表对象为参数。同时Room原生支持与LiveData组件配合，查询方法的返回值除了Entity修饰的表对象外，还可以为LiveData<List<Entrity修饰对象>>,这样ViewMode+LiveData 包装Dao对象查询数据库的结果后，当数据库数据发生变化不用再重复查询，页面直接能收到数据变化的通知。Excellent!!
###### @Database注解
自定义一个继承RoomDatabase的抽象类并在类上方添加@Database注解，表示此类为数据库对象，注解内可设置entities值用于声明数据库中包含哪些表，多个表可用{表类.class,表类.class}括起来，设置version值表示数据库版本，exportSchema值表示是否导出数据库信息 默认为true。   
数据库对象的实例化要通过Room对象获得，首先声明一个公有的获得数据库对象的方法，一般为单例模式，在方法内通过Room.databaseBuilder(applicationContext(),数据库对象.class, 数据库名字).build()获得数据库对象实例，在获得RoomDatabase.Builder对象后还可以追加一些额外的配置，eg .addMigrations(new Migration(数据库老版本号,数据库新版本号),策略2)配置数据库升级策略 .fallbackToDestructiveMigration()配置升级异常时处理策略，代表没有对应版本间的升级方案时不让程序崩溃但是数据会丢失 即重新创建数据表 .createFromAsset("本地现有数据库.db")配置以现有数据库生成Room数据库 用于预填充数据。最后在类内部声明一个公有的抽象方法，用来返回操作表的Dao对象。然后就可以在ViewModel中先获得Database对象，再获得Dao对象，进而在子线程中通过Dao对象调用Dao中定义的操作数据库表的增删改查方法。    
@Database注解中exportSchema表示是否导出数据库信息，但是需要在gradle文件中指定输出路径，数据库生成修改成功就可以在对应目录下看到生成的 数据库版本号.json文件了。   
android{ defaultConfig{ javaCompileOptions{ annotationProcessorOptions{ arguments = ["room.schemaLocation":"model名字/schemas".toString()]}}}}

#### 6. WorkManager:
WorkManager为了解决后台任务，并且是不需要立刻完成，当达到规定的条件时自动触发的 兼容范围非常广的异步任务处理组件，目的是控制后台任务以达到节省电量提升用户体验。不需要立即完成的任务比如日志上报、同步应用数据、静默下载更新包等。   
WorkManager根据当前设备的系统版本自动选择不同的执行方式，当设备系统版本>=23时采用JobScheduler完成任务，当系统版本<23时采用AlarmManager+Broadcast Receivers完成任务，但最终任务都是由Executor执行的。
WorkManager使用方法先创建一个继承Worker的自定义类，表示即将要执行的任务，实现相关方法并在doWork方法内执行具体耗时任务，可以通过getInputData()接收外面传入进来的数据，并在任务执行完返回Result.failure() Result.success() 或者Result.retry()。然后通过new Constraints.Builder().build()创建一个任务触发条件的约束对象，Builder可以配置的条件有eg .setRequiresCharging(true)需要设备正在充电 .setRequiredNetworkType(NetworkType.CONNECTED)需要设备连接网络 .setRequiresBatteryNotLow(true)需要设备电量充足等。在创建一个任务请求对象new OneTimeWorkRequest.Builder (自定义任务类.class).build(),请求对象Builder也可配置一些参数 eg .setConstraints(约束对象)设置任务触发的约束条件 .setInitialDelay(duration, timeUnit)设置满足条件后任务触发的延迟时间 .addTag(tag)为任务设置标签 .setInputData(Data)为任务类传入数据等。最后通过WorkManager  .getInstance(this).enqueue(workRequest)将配置好的任务提交给系统。可以通过workManager.cancel...()方法取消任务，可以通过workManager.getWorkInfoByTagLiveData(tag).
.observe(this, new Observer<WorkInfo>() {...})方法观察任务状态 并可以在内部回调方法中通过workInfo获得Worker类回传回来的数据。   
PeriodicWorkRequest周期性任务和OneTimeWorkRequest一次性任务使用配置一样，但是周期间隔不能小于15分钟。    
当存在多个任务的情况下,可以通过workManager.beginWith(workRequestA).then(workRequestB).enqueue()将两个任务构建成一个任务链，begin会优先于then执行。
  
#### 7. DataBinding:
DataBinding可以将数据模型直接声明到xml布局文件中，然后在UI控件上把模型中的属性直接赋值到控件的数据属性上，然后在页面Activity/Fragment中通过将具体的模型对象设置给布局文件，当页面中的模型对象数据变化时，布局上控件的数据显示也会自动变化，这就是DataBinding最主要的功能，此外它还支持双向绑定，即通过更改UI控件的显示，自动修改页面中模型的数据。   

DataBinding让布局承担了部分页面的功能，使xml布局和页面Activity之间更加解耦，减轻了页面的工作量。同时DataBinding也为能够更好的实现MVVM架构提供了基础。   

首先在build.grdle中通过android{dataBinding{enabled = true}}配置开启DataBinding功能，然后创建普通的数据模型类，接着到布局文件中将布局根标签更改为layout,然后整个layout标签内可分为两部分，一部分为原本的布局构建代码放在一个大的布局标签内，一部分为数据代码放到data标签内，在data标签内可以通过variable标签声明一个变量，name代表变量名，type代表变量类型，可以为基本数据类型或是引用数据类型，如果引用数据类型是自定义类要写上类的全路径。data标签内还可以通过import标签导入一个自定义类，注意如果是用variable声明的代表此变量是一个对象，需要在Activity中设置对应的对象值进来，如果是import则代表引入的是一个类，可以在xml布局文件内直接调用此类的静态成员变量或方法。控件使用声明的数据时需要用@{}将java代码包裹起来，比如设置一个TextView控件的文本显示，则直接在text属性上写@{model对象.属性}。最后回到Activity页面中将原本的setContentView方法移除，用DataBindingUtil. 
setContentView(this,R.layout.xxx)方法代替，此时同步一下项目DataBinding会自动生成Activity类名倒过来并拼接上Binding字符的辅助类。用此类接收
DataBindingUtil.setContentView()的方法作为返回值类型，通过该辅助类对象的set变量名方法将数据模型对象设置给布局文件，DataBinding基本用法变掌握完毕。   










